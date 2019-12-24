package com.gizwits.lease.ohmynoti.service;

import com.alibaba.fastjson.JSONObject;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.lease.config.bean.ProductUpdateNettyConfigEvent;
import com.gizwits.lease.config.bean.SnotiDeviceControlEvent;
import com.gizwits.lease.constant.CommandType;
import com.gizwits.lease.device.entity.dto.ControlDto;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.ohmynoti.handler.PushEventRouter;
import com.gizwits.lease.ohmynoti.handler.impl.DeviceStatusKvHandler;
import com.gizwits.lease.product.entity.Product;
import com.gizwits.lease.product.service.ProductDataPointService;
import com.gizwits.lease.product.service.ProductService;
import com.gizwits.lease.redis.RedisService;
import com.gizwits.noti.noticlient.OhMyNotiClient;
import com.gizwits.noti.noticlient.OhMyNotiClientImpl;
import com.gizwits.noti.noticlient.bean.req.NotiReqPushEvents;
import com.gizwits.noti.noticlient.bean.req.body.AuthorizationData;
import com.gizwits.noti.noticlient.config.SnotiCallback;
import com.gizwits.noti.noticlient.config.SnotiConfig;
import com.gizwits.noti.noticlient.util.CommandUtils;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/**
 * @authord yinhui
 * @date 2019-06-12
 */
@Slf4j
@Service
public class NotiService implements InitializingBean, ApplicationListener<ApplicationEvent> {

    ExecutorService executor = Executors.newSingleThreadExecutor();

    ExecutorService singleExecutor = Executors.newSingleThreadExecutor();


    private final OhMyNotiClient client = new OhMyNotiClientImpl();

    @Value("${gizwits.noti.enable:true}")
    private boolean notiEnable;

    @Autowired
    private ProductService productService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private PushEventRouter pushEventRouter;

    @Autowired
    private DeviceStatusKvHandler deviceStatusKvHandler;

    @Autowired
    private ProductDataPointService productDataPointService;

    @Autowired
    private DeviceService deviceService;


    private int count = 0;

    private long time = 0;

    private volatile static boolean flag;

    public static boolean isFlag() {
        return flag;
    }

    public static synchronized void setFlag(boolean flag) {
        NotiService.flag = flag;
    }

    private static final List<String> binaryAttrs=Arrays.asList("alarmPlan");


    private void pushEventProcess(JSONObject json) {
        long start = System.currentTimeMillis();
        if (start - time >= 1000) {
            time = start;
            log.info("当前时间：{},消费次数:{}", time, count);
            count = 1;
        } else {
            count = count + 1;
        }

        String code = CommandUtils.getPushEventCode(json);
        log.info("code:{}",code);
        if ("device_status_kv".equals(code)) {
            deviceStatusKvHandler.processPushEventMessage(json);
        } else {
            pushEventRouter.dispatch(json);
        }

    }

    /**
     * 获取所有noti启动信息
     *
     * @return
     */
    private AuthorizationData[] getLoginAuthorizes() {
        log.info("=======================>获取noti登录信息");
        List<Product> orgData = productService.getAllUseableProduct();
        if (CollectionUtils.isEmpty(orgData)) {
            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
        }
        return
                orgData.stream()
                        .map(NotiService::convertLoginAuthorize)
                        .toArray(AuthorizationData[]::new);
    }

    private static AuthorizationData convertLoginAuthorize(Product product) {
        return
                new AuthorizationData()
                        .setSubkey(product.getSubkey())
                        .setAuth_id(product.getAuthId())
                        //不订阅app2dev事件
                        .addEvents(Stream.of(NotiReqPushEvents.values()).filter(event -> !StringUtils.contains(event.getCode(), "app2dev")).toArray(NotiReqPushEvents[]::new))
                        .setAuth_secret(product.getAuthSecret())
                        .setProduct_key(product.getGizwitsProductKey());
    }

    /**
     * 启动noti
     */
    private void startup() {
        client
                .addLoginAuthorizes(getLoginAuthorizes())
                .setCallback(new SnotiCallback() {
                    @Override
                    public void startup() {
                        log.debug("snoti客户端启动成功.");
                    }

                    @Override
                    public void loginSuccessful() {
                        log.info("snoti登陆成功.");
                        setFlag(false);
                        singleExecutor.execute(() -> {
                            while (true) {
                                if (isFlag()) {
                                    log.warn("有正在执行控制指令的进程，退出执行");
                                    break;
                                }
                                setFlag(true);
                                try {
                                    resolveControlCommand();
                                    setFlag(false);
                                } catch (Exception e) {
                                    log.warn("执行设备控制报错：", e);
                                    setFlag(false);
                                }

                            }
                        });

                    }

                    @Override
                    public void loginFailed(String errorMessage) {
                        log.error("snoti客户端登陆失败. 错误信息[{}]", errorMessage);
                        setFlag(true);
                    }

                    @Override
                    public void noDataForAWhile(Long minutes, ChannelHandlerContext ctx) {
                        log.warn("snoti客户端[{}]分钟没有接收到数据. 即将重新连接客户端 ", minutes);
                        ctx.channel().close();
                        setFlag(true);
                    }

                    @Override
                    public void disconnected() {
                        //TODO 需要的话，可以使用短信或者邮件提醒
                        log.warn("snoti连接断开.");
                        setFlag(true);

                    }

                    @Override
                    public void reload(AuthorizationData... authorizationData) {
                        log.warn("snoti客户端重新加载登陆信息. 登陆信息[{}]", authorizationData);
                        setFlag(true);
                    }

                    @Override
                    public void stop() {
                        log.warn("snoti客户端终止.");
                        setFlag(true);
                    }
                });

        client.doStart();

        executor.execute(() -> {
            while (true) {
                try {
                    pushEventProcess(client.receiveMessage());
                }catch (Exception e){
                    log.error("snoti捕获意外异常：{}",e.getMessage());
                }

            }
        });
    }

    private void resolveControlCommand() {
        ControlDto controlDto = redisService.getSnotiControlInfo(CommandType.CONTROL.getCode());
        if (null == controlDto) {
            return;
        }
        ControlDto cacheData;
        Map<String, Object> origin;

        cacheData = new ControlDto();
        BeanUtils.copyProperties(controlDto, cacheData);
        Map<String, Object> attrs = controlDto.getAttrs();
        if (null == attrs || attrs.size() == 0) {
            log.warn("设备控制指令为空不进行处理，device_mac= {}", controlDto.getMac());
            return;
        }
        origin = new HashMap<>();
        origin.putAll(attrs);
        if (attrs.size() > 0) {
            for (String key : binaryAttrs) {
                Object object = attrs.get(key);
                if(!ParamUtil.isNullOrEmptyOrZero(object)) {
                    origin.put(key, object);
                    String value = (String) object;
                    attrs.put(key, hexStrToIntArr(value));
                }
            }
            controlDto.setAttrs(attrs);
            Boolean flag=control(controlDto);
            log.info("设备:{},控制：{}",controlDto.getMac(),flag?"成功":"失败");
            cacheData.setAttrs(origin);

        }
    }

    public static int[] hexStrToIntArr(String hex16Str) {
        byte[] bytes = hexStrToBytes(hex16Str);
        int[] ret = new int[bytes.length];

        for(int i = 0; i < bytes.length; ++i) {
            ret[i] = bytes[i] & 255;
        }

        return ret;
    }
    public static byte[] hexStrToBytes(String hex16Str) {
        int len = hex16Str.length();
        byte[] ba = new byte[len / 2];

        for(int i = 0; i < ba.length; ++i) {
            int j = i * 2;
            int t = Integer.parseInt(hex16Str.substring(j, j + 2), 16);
            byte b = (byte)(t & 255);
            ba[i] = b;
        }

        return ba;
    }
    public boolean control(ControlDto dto) {
        String did = dto.getDid();
        String mac = dto.getMac().toLowerCase();
        String productKey = dto.getProductKey();
        String messageId = getMessageId();
        log.info("发起设备控制. messageId = {},productKey=[{}] , mac=[{}],did={} 控制数据点[{}]", messageId, productKey, mac, did, JSONObject.toJSONString(dto.getAttrs()));
        return client.control(messageId, productKey, mac, did, dto.getAttrs());


    }
    @Override
    public void afterPropertiesSet() throws Exception {
        if (notiEnable) {
            log.info("初始化noti客户端...");
            SnotiConfig snotiConfig = new SnotiConfig();
            snotiConfig.setEnableCheckNoData(true);
            snotiConfig.setNoDataWarningMinutes(1);
            snotiConfig.setHost("snoti.gizwits.com");
//            snotiConfig.setHost("eusnoti.gizwits.com");
            client.setSnotiConfig(snotiConfig);
            startup();
        }
    }

    private static String getMessageId() {
        return RandomStringUtils.randomAlphabetic(10);
    }



    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (!notiEnable) {
            log.info("snoti不处理事件{}===>snoti未开启", event);
            return;
        }

        if (event instanceof ProductUpdateNettyConfigEvent) {
            log.info("======产品{}参数修改,Netty需要重新登录===");
            client.reload(getLoginAuthorizes());
        }
        else if (event instanceof SnotiDeviceControlEvent) {
            ControlDto snotiDeviceControlDTO = ((SnotiDeviceControlEvent) event).getSnotiDeviceControlDTO();
            control(snotiDeviceControlDTO);
        }
    }

}
