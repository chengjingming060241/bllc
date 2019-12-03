package com.gizwits.lease.user.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gizwits.boot.base.ResponseObject;

import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.entity.SysUserExt;
import com.gizwits.boot.sys.service.SysUserExtService;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.*;
import com.gizwits.lease.app.utils.LeaseUtil;
import com.gizwits.lease.app.utils.XmlResp;
import com.gizwits.lease.card.entity.Card;
import com.gizwits.lease.card.entity.CardEvent;



import com.gizwits.lease.config.CommonSystemConfig;
import com.gizwits.lease.constant.BindType;
import com.gizwits.lease.constant.DeviceStatus;
import com.gizwits.lease.constant.UserStatus;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.DeviceLaunchArea;
import com.gizwits.lease.device.entity.UserDevice;
import com.gizwits.lease.device.service.DeviceLaunchAreaService;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.device.service.UserDeviceService;
import com.gizwits.lease.device.vo.DeviceAuth;
import com.gizwits.lease.event.DeviceWeiXinUnBindEvent;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.model.JSTicket;
import com.gizwits.lease.product.entity.Product;
import com.gizwits.lease.product.entity.ProductServiceDetail;
import com.gizwits.lease.product.service.ProductService;
import com.gizwits.lease.product.service.ProductServiceDetailService;
import com.gizwits.lease.redis.RedisService;
import com.gizwits.lease.user.entity.User;
import com.gizwits.lease.user.service.UserService;
import com.gizwits.lease.user.service.UserWeixinService;

import com.gizwits.lease.util.WxUtil;
import com.gizwits.lease.weixin.util.News;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * Created by zhl on 2017/6/28.
 */
@Service
public class UserWeixinServiceImpl implements UserWeixinService {

    private final static Logger logger = LoggerFactory.getLogger("WEIXIN_LOGGER");

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private SysUserExtService sysUserExtService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private UserDeviceService userDeviceService;
    @Autowired
    private DeviceLaunchAreaService deviceLaunchAreaService;
    @Autowired
    private ProductServiceDetailService productServiceDetailService;




    @Autowired
    private ProductService productService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 验证微信token
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @return
     */
    public boolean verifySignature(String signature, String timestamp, String nonce) {
        List<SysUserExt> list = sysUserExtService.selectList(new EntityWrapper<>());
        if (list == null || list.size() <= 0) {
            return false;
        }
        try {
            //由于微信验证的回调无法确定是哪个公众号,因此需要一个一个的循环验证,只要有一个验证通过就可以
            for (SysUserExt sysUserExt : list) {
                if (StringUtils.isNotBlank(sysUserExt.getWxToken())
                        && signature.equals(SHA1.gen(sysUserExt.getWxToken(), timestamp, nonce))) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("===> 微信验证Token失败");
            return false;
        }
        return false;
    }

    /**
     * 获取Code请求页面
     *
     * @param request
     * @param param
     */
    public void skipToGetCode(HttpServletRequest request, HttpServletResponse response, String param) {
        if (org.springframework.util.StringUtils.isEmpty(param)) {
            logger.error("===> 未传递相应参数进来进来");
            return;
        } else {

            //先判断传递进来的参数是wxId
            if (redisService.containWxConfig(LeaseUtil.judgeWxId(param))) {
                WxUtil.getCodeRedirectUrl(request, response, LeaseUtil.judgeWxId(param), param);
                return;
            }
            //传递过来的参数是deviceId
            Device device = deviceService.selectById(param);
            if (device != null) {
                SysUserExt sysUseExt = deviceService.getWxConfigByDeviceId(param);
                if (sysUseExt != null) {
                    WxUtil.getCodeRedirectUrl(request, response, sysUseExt.getWxId(), param);
                    return;
                }
            }
        }
    }


    @Override
    public String authorizeDevice(List<DeviceAuth> deviceAuths, SysUserExt sysUserExt) {
        JSONObject json = new JSONObject();
        json.put("device_num", String.valueOf(deviceAuths.size()));
        json.put("op_type", 1);
        json.put("device_list", deviceAuths);
        return WxUtil.getAuthorizeDeviceUrl(json.toString(), sysUserExt);
    }

    /**
     * 根据Code获取openid
     *
     * @param code
     * @param state
     * @return
     */
    public String getOpenid(String code, String state) {
        //获取相应的微信配置信息
        SysUserExt sysUseExt = null;
        if (redisService.containWxConfig(state)) {
            sysUseExt = JSONObject.parseObject(redisService.getWxConfig(state), SysUserExt.class);
        }
        //传递过来的参数是deviceId
        Device device = deviceService.selectById(state);
        if (device != null && sysUseExt == null) {
            sysUseExt = deviceService.getWxConfigByDeviceId(state);
        }
        if (sysUseExt == null) {
            return null;
        }
        return WxUtil.getOpenid(code, sysUseExt);
    }

    /**
     * 用户扫码进入微信页面,需要生成H5页面调动微信JSSDK的所需参数
     *
     * @param req
     * @param openid
     * @param param  有可能是wxId,或者deviceId
     * @return
     */
    public ResponseObject createJSSDKSignature(HttpServletRequest req, String openid, String param) {
        if (StringUtils.isBlank(openid)) {
            logger.error("====>>>>> 用户扫码未获取到openid");
            LeaseException.throwSystemException(LeaseExceEnums.WEIXIN_OPENID_IS_NULL);
        }

        logger.debug("createJSSDKSignature param:" + param);

        //获取相应的微信配置信息
        SysUserExt sysUseExt = deviceService.getWxConfigByDeviceId(param);

        String wxId = LeaseUtil.judgeWxId(param);
        if (redisService.containWxConfig(wxId)) {
            sysUseExt = JSONObject.parseObject(redisService.getWxConfig(wxId), SysUserExt.class);
        }

        //传递过来的参数是deviceId
        Device device = deviceService.selectById(param);
        if (device != null && sysUseExt == null) {
            if (!deviceService.checkDeviceIsInOperator(param)) {
                LeaseException.throwSystemException(LeaseExceEnums.DEVICE_DONT_EXISTS);
            }
            sysUseExt = deviceService.getWxConfigByDeviceId(param);
        }
        if (sysUseExt == null) {
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_NOT_IN_OPERATOR);
        }

        //获取并保存微信用户信息
        User user;
        synchronized (openid.intern()) {
            user = userService.getUserByIdOrOpenidOrMobile(openid);
            if (user == null) {//数据库中还未存储用户信息
                String wxUserinfo = WxUtil.getUserInfo(openid, sysUseExt);
                logger.info("H5页面新增微信用户：" + wxUserinfo + "，openid = " + openid);
                Integer sysOperatorId = deviceService.getDeviceOperatorSysuserid(param);
                SysUser manufacturer = sysUserService.selectById(sysUserExtService.getLatestSysUserExt().getSysUserId());
                if (manufacturer == null) {
                    logger.error("=====>>> 系统用户id:{}厂商不存在", sysOperatorId);
                    LeaseException.throwSystemException(LeaseExceEnums.MANUFACTURER_NOT_EXIST);
                }
                user = userService.addUserByWx(wxUserinfo, sysUseExt, manufacturer.getId());
                if (user == null) {
                    logger.error("=====>>> 无法获取并保存用户信息");
                    LeaseException.throwSystemException(LeaseExceEnums.WEIXIN_OPENID_IS_NULL);
                }
            }
        }

        //检查用户是否在黑名单
        if (UserStatus.BLACK.getCode().equals(user.getStatus())) {
           logger.error("The user is on the blacklist : userId ==>"+user.getId());
           return new ResponseObject(LeaseExceEnums.USER_IN_BLACK.getCode(),LeaseExceEnums.USER_IN_BLACK.getMessage());
        }

        //获取JSJSTicket用户JSSDK的签名
        JSTicket jsapiTicket = WxUtil.getJSAPITicket(sysUseExt);
        if (jsapiTicket == null || StringUtils.isBlank(jsapiTicket.getTicket())) {
            logger.error("====>>>> 获取微信JSTicket出错");
            LeaseException.throwSystemException(LeaseExceEnums.WEIXIN_GET_JSTICKET_ERROR);
        }

        StringBuffer reqUri = req.getRequestURL();
        if (StringUtils.isNotEmpty(req.getQueryString())) {
            reqUri = StringUtils.isNotEmpty(req.getQueryString()) ? reqUri.append("?").append(req.getQueryString()) : reqUri;
        }
        String url = req.getRequestURL().toString();
        url = url.substring(0, url.indexOf(req.getRequestURI())) + "/";

        long timestamp = System.currentTimeMillis() / 1000;
        String noncestr = RandomStringUtils.randomAlphanumeric(16);
        try {
            String signature = SHA1.jsGen("jsapi_ticket=" + jsapiTicket.getTicket(),
                    "noncestr=" + noncestr, "timestamp=" + timestamp, "url=" + url);

            Map<String, Object> result = new HashedMap();
            result.put("signature", signature);
            result.put("timestamp", timestamp);
            result.put("noncestr", noncestr);
            result.put("url", reqUri.toString());
            result.put("appId", sysUseExt.getWxAppid());
            result.put("isBind", ParamUtil.isNullOrEmptyOrZero(user.getMobile()) ? 0 : 1);
            result.put("mobile", ParamUtil.isNullOrEmptyOrZero(user.getMobile()) ? "" : user.getMobile());
            if (device != null) {
                result.put("deviceId", device.getSno());
            }
            return ResponseObject.ok(result);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 微信公众号回调处理
     *
     * @param message
     * @return
     */
    public String handleCallbackMsg(String message) {

        Map<String, String> reqMap = WxUtil.parseXml(message);

        if (reqMap == null)
            return null;
        return switchMessageTypeHandle(reqMap);
    }

    private String switchMessageTypeHandle(Map<String, String> reqMap) {
        String msgType = reqMap.get("MsgType");
        switch (msgType) {
            case "text": // 文本消息
            case "image": //媒体消息
            case "video":
            case "voice":
            case "shortvideo":
                return "";
            case "event":
                return handleEventMsg(reqMap); // 基础事件推送
            case "device_event":
                return handleDeviceEventMsg(reqMap); // 设备事件推送
            default:
                return "";
        }
    }

    private String handleDeviceEventMsg(Map<String, String> reqMap) {
        //获取微信里面的wxDid，用于获取设备
        String wxDid = reqMap.get("DeviceID");
        Device device = deviceService.selectOne(new EntityWrapper<Device>().eq("wx_did", wxDid));
        String openId = reqMap.get("OpenID");
        String wxId = reqMap.get("ToUserName");
        logger.info("微信推送设备信息==>openId:" + openId + ",微信did==>" + wxDid + ", wxId="+wxId);

        if (Objects.isNull(device)) {
            logger.error("设备不能通过" + wxDid + "获取,请查看数据库中设备是否存在");
            return "";
        }

        String event = reqMap.get("Event");
        logger.info("微信推送设备信息==>event: " + event);
        //如果是绑定设备的操作
        if ("bind".equals(event)) {
            //判断user是否已经存在于数据库（用openId和sysUserId判断），不存在就添加到数据库
            User dbUser;
            synchronized (openId.intern()) {
                dbUser = userService.getUserByIdOrOpenidOrMobile(openId);
                if (dbUser == null) {
                    String userInfo = WxUtil.getUserInfoBySubscribe(openId, wxId);
                    SysUserExt sysUserExt =
                            sysUserExtService.selectOne(new EntityWrapper<SysUserExt>().eq("wx_id", wxId));
                    logger.info("绑定设备新增微信用户：" + userInfo);
                    // modify date 2018/6/28 用户归属从厂商开始， 从产品拿厂商id
                    Product product = productService.getProductByProductId(device.getProductId());
                    dbUser = userService.addUserByWx(userInfo, sysUserExt, product.getManufacturerId());
                }
            }
            //用户绑定设备记录表
            UserDevice userDevice = userDeviceService.selectOne(new EntityWrapper<UserDevice>().eq("openid", openId).eq("wechat_device_id", wxDid));
            userDevice = userDevice == null ? userDevice = new UserDevice(new Date()) : userDevice;
            userDevice.setUtime(new Date());
            userDevice.setWechatDeviceId(wxDid);
            userDevice.setSno(device.getSno());
            userDevice.setIsBind(BindType.BIND.getCode());
            userDevice.setMac(device.getMac());
            userDevice.setOpenid(dbUser.getOpenid());
            userDevice.setUserId(dbUser.getId());
            userDevice.setOwnerId(device.getOwnerId());
            userDevice.setIsDeleted(0);
            userDeviceService.insertOrUpdate(userDevice);
            //解绑操作
            SysUserExt sysUserExt = deviceService.getWxConfigByDeviceId(device.getSno());
            CommonEventPublisherUtils.publishEvent(new DeviceWeiXinUnBindEvent("").build(openId, wxDid, sysUserExt, userDevice));
            //判断当前用户是否是该设备的使用者
            Boolean usingFlag = deviceService.checkDeviceIfUsedByOpenid(device, openId);
            //推送消息
            sendMessage(reqMap.get("FromUserName"), openId, device, sysUserExt, usingFlag);
        } else if ("unbind".equals(event)) {
            //记录用户解绑信息（userDevice）
        } else {//其他事件
            //不管订阅和不订阅的情况
            if ("subscribe_status".equals(event) || "unsubscribe_status".equals(event)) {
                return null;
            }
        }
        return null;
    }

    private void sendMessage(String fromUserName, String toUserOpenId, Device device, SysUserExt sysUserExt, Boolean flag) {
        News news = new News();
        StringBuilder sb = new StringBuilder();
        StringBuilder firstSb = new StringBuilder();
        StringBuilder statusSb = new StringBuilder();
        StringBuilder remarkSb = new StringBuilder();
        String toUrl;
        //根据wxDid获取device
        DeviceLaunchArea deviceLaunch = deviceLaunchAreaService.selectById(device.getLaunchAreaId());
        sb.append("您扫描的设备位于：").append("\n");
        firstSb.append("您扫描的设备位于：");
        if (Objects.isNull(deviceLaunch) || StringUtils.isEmpty(deviceLaunch.getAddress())) {
            sb.append("地址不详").append("\n");
            firstSb.append("地址不详");
        } else {
            String address = deviceLaunch.getProvince() + deviceLaunch.getCity() + deviceLaunch.getArea() +
                    deviceLaunch.getAddress();
            sb.append(address).append("\n");
            firstSb.append(address);
        }
        //如果被租用中,故障，异常，离线
        if (device.getWorkStatus().equals(DeviceStatus.USING.getCode()) && flag) {
            sb.append("设备租用状态:").append("\n");
            sb.append("设备正在被您使用").append("\n");
            statusSb.append("设备正在被您使用");
            sb.append("点击阅读全文进行设备租赁").append("\n");
            remarkSb.append("点击阅读全文进行设备租赁");
            toUrl = SysConfigUtils.get(CommonSystemConfig.class).getWxAccessUrl() + device.getSno();
        } else if (device.getWorkStatus().equals(DeviceStatus.USING.getCode()) || device.getWorkStatus().equals(DeviceStatus.FAULT.getCode())
                || device.getWorkStatus().equals(DeviceStatus.STOP.getCode())) {
            sb.append("设备租用状态:").append("\n");
            String deviceTipMsgForWx = getDeviceTipMsgForWx(device.getWorkStatus());
            sb.append(deviceTipMsgForWx);
            statusSb.append(deviceTipMsgForWx);
            toUrl = SysConfigUtils.get(CommonSystemConfig.class).getWxTipUrl();
        } else if (device.getOnlineStatus().equals(DeviceStatus.OFFLINE.getCode())) {
            sb.append("设备租用状态:").append("\n");
            String deviceTipMsgForWx = getDeviceTipMsgForWx(device.getOnlineStatus());
            sb.append(deviceTipMsgForWx);
            statusSb.append(deviceTipMsgForWx);
            toUrl = SysConfigUtils.get(CommonSystemConfig.class).getWxTipUrl();
        } else {
            String deviceTipMsgForWx = getDeviceTipMsgForWx(device.getWorkStatus());
            statusSb.append(deviceTipMsgForWx);
            sb.append("设备租用价格：").append("\n");
            remarkSb.append("设备租用价格：");
            List<ProductServiceDetail> productServiceDetails = productServiceDetailService.selectList(new EntityWrapper<ProductServiceDetail>().eq("service_mode_id", device.getServiceId()));
            for (int i = 0; i < productServiceDetails.size(); ++i) {
                ProductServiceDetail p = productServiceDetails.get(i);
                String detailStr = null;
                if (i != productServiceDetails.size() - 1) {
                    detailStr = p.getNum() + p.getUnit() + p.getPrice() + "元,";
                } else {
                    detailStr = p.getNum() + p.getUnit() + p.getPrice() + "元\n";
                }
                sb.append(detailStr);
                remarkSb.append(detailStr);
            }
            sb.append("点击阅读全文进行设备租赁").append("\n");
            remarkSb.append("点击阅读全文进行设备租赁");
            //TODO:设备H5路径还没确定
            toUrl = SysConfigUtils.get(CommonSystemConfig.class).getWxAccessUrl() + device.getSno();
        }
        news.setUrl(toUrl);
        news.setPicUrl(SysConfigUtils.get(CommonSystemConfig.class).getWxNewsPicUrl());
        news.setDescription(sb.toString());
        news.setTitle(SysConfigUtils.get(CommonSystemConfig.class).getWxWelcomeMsg());
        String result = WxUtil.customSendNews(fromUserName, news, sysUserExt);
        logger.info("客服消息发送结果====》："+result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        Integer errcode = jsonObject.getInteger("errcode");
        // 客户消息发送失败，转为模版消息发送
        if (errcode == null || !errcode.equals(0)) {
            JSONObject body = new JSONObject();
            body.put("touser", toUserOpenId);
            body.put("template_id", SysConfigUtils.get(CommonSystemConfig.class).getWxTipTemplateId());
            body.put("url", toUrl);
            JSONObject first = new JSONObject();
            first.put("value", firstSb.toString());
            first.put("color", "#173177");
            JSONObject keyword1 = new JSONObject();
            keyword1.put("value", device.getSno());
            keyword1.put("color", "#173177");
            JSONObject keyword2 = new JSONObject();
            keyword2.put("value", statusSb.toString());
            keyword2.put("color", "#173177");
            JSONObject remark = new JSONObject();
            remark.put("value", remarkSb.toString());
            remark.put("color", "#173177");
            JSONObject data = new JSONObject();
            data.put("first", first);
            data.put("keyword1", keyword1);
            data.put("keyword2", keyword2);
            data.put("remark", remark);
            body.put("data", data);
            String resp = WxUtil.sendTemplateNotice(body.toJSONString(), sysUserExt);
            logger.info("模版消息发送结果====》："+resp);
        }
    }

    String getDeviceTipMsgForWx(Integer status) {
        switch (status) {
            case 2:
                return "设备已离线，请选择其他设备";
            case 3:
                return "设备已被租赁，请选择其他设备";
            case 5:
                return "设备已被禁用，请选择其他设备";
            case 6:
                return "设备出现故障，请选择其他设备";
            default:
                return StringUtils.isEmpty(DeviceStatus.getName(status)) ? "设备出现问题，请选择其他设备" : "设备状态:" + DeviceStatus.getName(status);
        }
    }

    /**
     * 关注公众号消息推送:
     * <xml>
     * <ToUserName><![CDATA[gh_3c719aa39cad]]></ToUserName>
     * <FromUserName><![CDATA[oTGqGwit7iDZ0uNb_MX9D0QnvfaY]]></FromUserName>
     * <CreateTime>1499825357</CreateTime>
     * <MsgType><![CDATA[event]]></MsgType>
     * <Event><![CDATA[subscribe]]></Event>
     * <EventKey><![CDATA[]]></EventKey>
     * </xml>
     *
     * @param reqMap
     * @return
     */
    private String handleEventMsg(Map<String, String> reqMap) {
        String event = reqMap.get("Event");
        logger.info("微信推送公众号信息==>: event=" + event);
        // 关注公众号,save user info
        if ("subscribe".equals(event)) {
            String wxId = reqMap.get("ToUserName");
            String openid = reqMap.get("FromUserName");
            logger.info("微信推送公众号信息==>: wxId={}, openid={}", wxId, openid);
            if (redisService.containWxConfig(wxId)) {//WxId在缓存中存在
                String wxConfig = redisService.getWxConfig(wxId);
                if (StringUtils.isBlank(wxConfig)) {
                    return null;
                }
                SysUserExt sysUserExt = JSONObject.parseObject(redisService.getWxConfig(wxId), SysUserExt.class);
                User dbUser;
                synchronized (openid.intern()) {
                    dbUser = userService.getUserByIdOrOpenidOrMobile(openid);
                    if (Objects.isNull(dbUser)) {
                        String wxUserinfo = WxUtil.getUserInfoBySubscribe(openid, sysUserExt);
                        //添加用户通过wxUserinfo
                        logger.info("关注公众号新增微信用户：" + wxUserinfo);
                        SysUser manufacturer = sysUserService.selectById(sysUserExt.getSysUserId());
                        if (manufacturer == null) {
                            logger.error("=====>>> 系统用户id:{}厂商不存在", sysUserExt.getSysUserId());
                            LeaseException.throwSystemException(LeaseExceEnums.MANUFACTURER_NOT_EXIST);
                        }
                        userService.addUserByWx(wxUserinfo, sysUserExt, manufacturer.getId());
                        return XmlResp.buildText(openid, wxId, StringUtils.isBlank(sysUserExt.getWxSubscribeMsg()) ?
                                SysConfigUtils.get(CommonSystemConfig.class).getWxSubscribeMsg() :
                                sysUserExt.getWxSubscribeMsg());
                    }
                }

                // 回复欢迎语
                return XmlResp.buildText(openid, wxId, SysConfigUtils.get(CommonSystemConfig.class).getWxSubscribeMsg());
            }

            //取消关注,do nothing
            if ("unsubscribe".equals(event)) {
                //String openId = reqMap.get("FromUserName");
                return "";
            }

            // 菜单点击事件
            if ("CLICK".equals(event)) {
                return "";
            }
            return "";
        }
        else if (StringUtils.equals(event, "user_get_card")) {


        }
        return "";
    }
}
