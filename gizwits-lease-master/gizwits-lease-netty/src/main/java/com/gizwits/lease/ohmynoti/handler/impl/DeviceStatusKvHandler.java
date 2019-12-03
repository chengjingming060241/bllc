package com.gizwits.lease.ohmynoti.handler.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.lease.constant.DeviceStatus;
import com.gizwits.lease.constant.OrderStatus;
import com.gizwits.lease.constant.StatusCommandType;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.dto.DeviceChargeCardDtoForMahjong;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.enums.OrderEndType;
import com.gizwits.lease.message.entity.MessageTemplate;
import com.gizwits.lease.message.entity.SysMessage;
import com.gizwits.lease.message.service.MessageTemplateService;
import com.gizwits.lease.message.service.SysMessageService;
import com.gizwits.lease.ohmynoti.handler.PushEventHandler;
import com.gizwits.lease.order.entity.OrderBase;
import com.gizwits.lease.order.service.OrderBaseService;
import com.gizwits.lease.order.service.OrderDataFlowService;
import com.gizwits.lease.redis.RedisService;
import com.gizwits.lease.user.service.UserChargeCardService;
import com.gizwits.lease.util.DeviceUtil;
import com.gizwits.noti.noticlient.bean.resp.body.StatusKvEventBody;
import com.gizwits.noti.noticlient.util.CommandUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * @author Jcxcc
 * @date 3/29/18
 * @email jcliu@gizwits.com
 * @since 1.0
 */
@Slf4j
@Component
public class DeviceStatusKvHandler implements PushEventHandler {

    @Autowired
    private DeviceService deviceService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private OrderBaseService orderBaseService;
    @Autowired
    private OrderDataFlowService orderDataFlowService;
    @Autowired
    private MessageTemplateService messageTemplateService;
    @Autowired
    private SysMessageService sysMessageService;
    @Autowired
    private UserChargeCardService userChargeCardService;

    @Override
    public void processPushEventMessage(JSONObject json) {
        StatusKvEventBody data = CommandUtils.parsePushEvent(json, StatusKvEventBody.class);
        String
                did = data.getDid(),
                mac = data.getMac(),
                productKey = data.getProductKey();
        JSONObject kv = data.getData();

        log.info("设备数据点===>pk:{} did:{} mac:{} kv:{}", productKey, did, mac, kv.toJSONString());
        JSONObject existDevice = redisService.getDeviceCurrentStatus(productKey, mac);


        //Redis中还未缓存设备上报数据
        if (existDevice == null || existDevice.toJSONString().equals("{}")) {
            log.info("======Device {} do not find in redis cache=====", mac);
            Device dbDevice = deviceService.getDeviceByMacAndPk(mac, productKey);
            if (dbDevice == null) {
                //log.error("====> 设备在系统中不存在,mac[" + mac + "],productKey[" + productKey + "]");
                return;
            }
            if (dbDevice.getOnlineStatus().equals(DeviceStatus.OFFLINE.getCode())
                    || StringUtils.isBlank(dbDevice.getGizDid())) {
                log.info("======Device {} status is {} or GizDid is {}, update Device Status to ONLINE and GizDid====", mac, dbDevice.getOnlineStatus(), dbDevice.getGizDid());
                deviceService.updateDeviceOnOffLineStatus(mac, productKey, did, true);//数据库设备在线状态
                redisService.cacheDeviceOnlineStatus(productKey, mac, true);//在线状态
            }

            if (redisService.containNeedClockCorrectDevice(productKey, mac)) {
                if (orderBaseService.handleNeedClockCorrect(productKey, dbDevice, kv)) {
                    redisService.deleteNeedClockCorrectDevice(productKey, mac);
                    return;
                }
            }

            //缓存上报数据点
            redisService.cacheDeviceCurrentStatus(productKey, mac, kv);//缓存上报数据点

            //根据上报数据库与产品配置的状态点,判断设备状态
            changeDeviceStatusByRealTimeData(productKey, dbDevice, kv);
        } else {
            Device dbDevice = deviceService.getDeviceByMacAndPk(mac, productKey);
            if (dbDevice == null) {
                log.error("====> 设备在系统中不存在,mac[" + mac + "],productKey[" + productKey + "]");
                return;
            }

            if (dbDevice.getOnlineStatus().equals(DeviceStatus.OFFLINE.getCode())
                    || StringUtils.isBlank(dbDevice.getGizDid())) {
                log.info("======Device {} status is {} or GizDid is {}, update Device Status to ONLINE and GizDid====", mac, dbDevice.getOnlineStatus(), dbDevice.getGizDid());
                deviceService.updateDeviceOnOffLineStatus(mac, productKey, did, true);//数据库设备在线状态
                redisService.cacheDeviceOnlineStatus(productKey, mac, true);//在线状态
            }
            //比较Redis中缓存数据与上报数据,只有不相同的时候才做处理
            if (!equalRealTimeDataAndCacheData(productKey, mac, kv, existDevice)) {

                if (redisService.containNeedClockCorrectDevice(productKey, mac)) {
                    if (orderBaseService.handleNeedClockCorrect(productKey, dbDevice, kv)) {
                        redisService.deleteNeedClockCorrectDevice(productKey, mac);
                        return;
                    }
                }

                existDevice = redisService.getDeviceCurrentStatus(productKey, mac);

                //根据上报数据库与产品配置的状态点,判断设备状态
                changeDeviceStatusByRealTimeData(productKey, dbDevice, existDevice);
            }
        }
    }

    /**
     * 比较上报数据点和缓存的数据点是否有差异
     *
     * @param productKey
     * @param realTimeData
     * @param cacheData
     * @return
     */
    private boolean equalRealTimeDataAndCacheData(String productKey, String mac, JSONObject realTimeData, JSONObject cacheData) {
        if (!redisService.containProductMoint(productKey)) {
            log.info("=====Product: {}, Device:{} do not have monit datapoint in redis cache.====== ", productKey, mac);
            return false;
        }

        String monitPoints = redisService.getProductMonitPoint(productKey);
        if (StringUtils.isBlank(monitPoints)) {
            log.info("=====Product: {}, Device:{} monit datapoint is Blank.====== ", productKey, mac);
            return false;
        }
        String[] points = monitPoints.split(",");
        if (points == null || points.length <= 0) {
            log.info("=====Product: {}, Device:{} monit datapoint count is zero.====== ", productKey, mac);
            return false;
        }

        boolean resultFlag = true;
        //判断是否是定长的数据点
        for (String point : points) {
            //有的设备可能是不定长上报,因此只检测实时上报中的数据点
            if (StringUtils.isNotBlank(point) && realTimeData.containsKey(point)) {
                if (cacheData.containsKey(point)) {
                    if (!realTimeData.get(point).equals(cacheData.get(point))) {//相同数据点的值不同
                        resultFlag = false;
                        break;
                    }
                } else {//要监控的数据点在缓存中不存在,需要缓存
                    resultFlag = false;
                    break;
                }
            }
        }

        //由于变长数据点的原因,需要将上报数据点循环覆盖缓存中的数据点
        Iterator it = realTimeData.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            cacheData.put(key, realTimeData.get(key));
        }

        //监控数据点有变动,将修改后的缓存数据再放到缓存中
        if (!resultFlag) {
            log.info("====Product: {}, mac: {} upload value is not equal last time, update new value into redis cache.======", productKey, mac);
        }
        // 因为需要通过上报时间来判断设备网络通信是否正常，从而执行退款逻辑，所以这里无论数据点是否有变化，都要重新缓存一遍，更新一下时间
        redisService.cacheDeviceCurrentStatus(productKey, mac, cacheData);//缓存上报数据点与缓存数据点的合并

        return resultFlag;
    }


    /**
     * 根据上报的数据点与产品中配置的状态指令进行对比,判断设备的状态
     *
     * @param productKey
     * @param device
     * @param jsonData
     * @return
     */
    private boolean changeDeviceStatusByRealTimeData(String productKey, Device device, JSONObject jsonData) {
        //刷卡相关的业务
//        resolveChargeCardOrCallOut(productKey, jsonData, device);
        boolean flag = resolveDeviceStatus(productKey, device, jsonData);
        //处理消息推送
//        resolveSendMessage(device, jsonData);
        return flag;
    }

    private Boolean resolveDeviceStatus(String productKey, Device device, JSONObject jsonData) {
        if (productKey.equals("158c6efa9eb64a72b259b18ca06b91af")) {
            if (jsonData.containsKey("Working_Mode") && jsonData.getString("Working_Mode").equals("free")) {
                log.info("====Product:{} ,Device:{} Working_Mode is Free.===", productKey, device.getMac());
                updateDeviceStatus(device.getSno(), DeviceStatus.FREE);
                return true;
            }
        }

        //使用完成,要修改订单状态和设备状态
        if (redisService.containsProductStatusCommand(productKey, StatusCommandType.FINISH.getCode())) {
            JSONObject commandJson = redisService.getProductStatusCommand(productKey, StatusCommandType.FINISH.getCode());
            if (DeviceUtil.equalCommandAndRealTimeData(commandJson, jsonData)) {
                log.info("====Product:{} ,Device:{} upload data is equals FINISH command ,start finish order and update device status.===", productKey, device.getMac());
                OrderBase orderBase = orderBaseService.getDeviceLastUsingOrder(device.getSno());
                if (orderBase != null) {
                    log.info("====Start Finish Order:{}======", orderBase.getOrderNo());
                    // 设置订单结束方式为正常结束
                    orderBase.setEndType(OrderEndType.END_BY_SNOTI.getCode());
                    orderDataFlowService.saveFinishData(orderBase, jsonData.toJSONString());
                    //订单状态流转
                    orderBaseService.updateOrderStatusAndHandle(orderBase, OrderStatus.FINISH.getCode());
                } else {
                    orderBase = orderBaseService.getDeviceLastOrderEndByJob(device.getSno());
                    if (Objects.nonNull(orderBase)) {
                        log.info("====Start Change Order End Type:{}======", orderBase.getOrderNo());
                        // 设置订单结束方式为正常结束
                        orderBase.setUtime(new Date());
                        orderBase.setEndType(OrderEndType.END_BY_SNOTI.getCode());
                        orderBaseService.updateById(orderBase);
                    }
                    log.info("=====Device: {} do not have USING order===", device.getMac());
                }

                updateDeviceStatus(device.getSno(), DeviceStatus.FREE);
                return true;
            }
        }

        //空闲状态
        if (redisService.containsProductStatusCommand(productKey, StatusCommandType.FREE.getCode())) {
            JSONObject commandJson = redisService.getProductStatusCommand(productKey, StatusCommandType.FREE.getCode());
            if (DeviceUtil.equalCommandAndRealTimeData(commandJson, jsonData)) {
                log.info("====Product:{} ,Device:{} upload data is equals FREE command .===", productKey, device.getMac());
                updateDeviceStatus(device.getSno(), DeviceStatus.FREE);
                return true;
            }
        }

        //使用中
        if (redisService.containsProductStatusCommand(productKey, StatusCommandType.USING.getCode())) {
            JSONObject commandJson = redisService.getProductStatusCommand(productKey, StatusCommandType.USING.getCode());
            if (DeviceUtil.equalCommandAndRealTimeData(commandJson, jsonData)) {
                log.info("====Product:{} ,Device:{} upload data is equals USING command .===", productKey, device.getMac());
                updateDeviceStatus(device.getSno(), DeviceStatus.USING);
                return true;
            }
        }
        return false;
    }

    private void updateDeviceStatus(String sno, DeviceStatus deviceStatus) {
        Device forUpdate = new Device();
        forUpdate.setSno(sno);
        forUpdate.setWorkStatus(deviceStatus.getCode());
        forUpdate.setOnlineStatus(DeviceStatus.ONLINE.getCode());
        forUpdate.setUtime(new Date());
        deviceService.updateById(forUpdate);
    }

    private void resolveSendMessage(Device device, JSONObject jsonObject) {
        MessageTemplate messageTemplate = null;
        List<String> triggers = messageTemplateService.getTriggerByProduct(device.getProductId());
        for (String command : triggers) {
            JSONObject commandJson = JSONObject.parseObject(command);
            messageTemplate = messageTemplateService.selectOne(new EntityWrapper<MessageTemplate>().eq("command", command).eq("product_id", device.getProductId()).eq("is_deleted", 0));
            //判断是否已经推送
            if (ParamUtil.isNullOrEmptyOrZero(messageTemplate)) {
                continue;
            }
            SysMessage
                    sysMessage = sysMessageService.selectOne(new EntityWrapper<SysMessage>().eq("title", messageTemplate.getTitle()).eq("content", messageTemplate.getContent()).eq("mac", device.getMac()).eq("is_deleted", 0));
            boolean flag = false;
            if (DeviceUtil.equalCommandAndRealTimeData(commandJson, jsonObject)) {
                if (ParamUtil.isNullOrEmptyOrZero(sysMessage)) {
                    flag = true;
                } else if (sysMessage.getIsFixed().compareTo(DeleteStatus.DELETED.getCode()) == 0) {
                    flag = true;
                }
                if (flag) {
                    //插入系统消息
                    messageTemplateService.sendSysMessage(device, messageTemplate);
                }
            } else {
                if (!ParamUtil.isNullOrEmptyOrZero(sysMessage)) {
                    sysMessage.setUtime(new Date());
                    sysMessage.setIsFixed(1);
                    sysMessageService.updateById(sysMessage);
                }
            }
        }
    }

    private void resolveChargeCardOrCallOut(String productKey, JSONObject jsonObject, Device device) {
        if (StringUtils.isEmpty(productKey) || ParamUtil.isNullOrEmptyOrZero(jsonObject)) {
            log.error("productKey,jsonObject为空");
            return;
        }
        //写死成麻将，因为设备上报的数据点不同产品不一样，一次为麻将写了个DeviceChargeCardDtoForMahjong类用于判断各个指令的转图
        if (productKey.equals("158c6efa9eb64a72b259b18ca06b91af")) {
            log.info("Majiang Device upload data:" + jsonObject.toString());

            //用户呼叫
            if (jsonObject.containsKey("CallOut")) {
                if (jsonObject.getInteger("CallOut").equals(1)) {
                    deviceService.sendCallOutToManage(device);
                }
            }

            //锁定状态
            if (device.getLock()) {
                log.info("====设备:{}是锁定的状态,刷卡无效", device.getMac());
                return;
            }

            //禁用状态
            if (jsonObject.containsKey("Stop") && jsonObject.getInteger("Stop").equals(1)) {
                log.info("====设备:{}是禁用的状态,刷卡无效", device.getMac());
                return;
            }

            //非空闲
            if (!device.getWorkStatus().equals(DeviceStatus.FREE.getCode())) {
                log.info("====设备:{}是{}的状态,刷卡无效", device.getMac(), DeviceStatus.getName(device.getWorkStatus()));
                return;
            }

            // 有人支付完了，正在启动设备，这时候不允许下单
            EntityWrapper<OrderBase> wrapper = new EntityWrapper<>();
            wrapper.eq("sno", device.getSno()).orderBy("ctime", false).last("limit 1");
            List<OrderBase> orders = orderBaseService.selectList(wrapper);
            if (orders.size() > 0 && orders.get(0).getOrderStatus().equals(OrderStatus.PAYED.getCode())) {
                log.info("====设备:{}最后一张订单{}为已支付状态，有人正在占用，刷卡无效", device.getMac(), orders.get(0).getOrderNo());
                return;
            }

            //将json转成DeviceChargeCardDtoForMahjong对象
            DeviceChargeCardDtoForMahjong deviceChargeCardDtoForMahjong = DeviceChargeCardDtoForMahjong.fromJson(jsonObject.toJSONString());
            //刷卡操作上报此标志位,需要添加判断用户卡余额,然后再下发相应的启动指令
            if (deviceChargeCardDtoForMahjong.getClock_Correction()
                    && StringUtils.isNotBlank(deviceChargeCardDtoForMahjong.getCardNum())) {
                //数据点上报的卡号
                String cardNum = getCardNumFromDeviceData(deviceChargeCardDtoForMahjong.getCardNum());
                //防止用户重复刷卡的问题
                if (redisService.containDeviceLockByOrder(device.getSno())) {
                    String cacheCardNum = redisService.getDeviceLockByOpenidAndOrder(device.getSno());
                    //相同的用户在规定时间内重复刷卡,忽略刷卡操作
                    if (cacheCardNum.equals(cardNum)) {
                        log.debug("卡号：" + cacheCardNum + "重复刷卡不做处理");
                        return;
                    }
                }
                log.info("开始创建为本次充值卡消费创建订单，卡号：" + cardNum + "，设备号：" + device.getSno());
                OrderBase orderBase = orderBaseService.createOrderForChargeCard(cardNum, device);
                //创建订单失败的操作
                if (ParamUtil.isNullOrEmptyOrZero(orderBase)) {
                    //下发指令使设备不在上报卡号报文
                    log.debug("创建订单失败，下发指令使设备不在上报卡号报文");
                    deviceService.remoteDeviceControl(device.getSno(), "Clock_Correction", false);
                }
                userChargeCardService.pay(orderBase, cardNum);
            }
        }
    }

    /**
     * 将数据点的卡号转换成数据库里面保存的卡号
     * 上报卡号数据点中前10位为卡号,因此需要截取
     */
    private String getCardNumFromDeviceData(String originCardNum) {
        if (StringUtils.isEmpty(originCardNum)) {
            log.error("卡号为空");
        }
        if (originCardNum.length() < 10) {
            log.error("卡号");
        }
        String cardNum = originCardNum.substring(0, 10);
        return cardNum.toUpperCase();
    }
}
