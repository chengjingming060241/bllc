package com.gizwits.lease.trade.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.base.ResponseObject;
import com.gizwits.boot.sys.entity.SysUserExt;
import com.gizwits.boot.sys.service.SysUserExtService;
import com.gizwits.boot.utils.HttpUtil;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.boot.utils.SignUtils;
import com.gizwits.boot.utils.SysConfigUtils;
import com.gizwits.lease.app.utils.ContextUtil;
import com.gizwits.lease.app.utils.LeaseUtil;
import com.gizwits.lease.config.CommonSystemConfig;
import com.gizwits.lease.constant.*;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.install.entity.InstallFeeOrder;

import com.gizwits.lease.model.WxMpPayCallback;
import com.gizwits.lease.model.WxMpPrepayIdResult;
import com.gizwits.lease.model.WxMpQueryResult;
import com.gizwits.lease.order.dto.PrePayDto;
import com.gizwits.lease.order.entity.OrderBase;
import com.gizwits.lease.order.service.OrderBaseService;
import com.gizwits.lease.order.service.OrderPayRecordService;
import com.gizwits.lease.redis.RedisService;
import com.gizwits.lease.trade.dao.TradeWeixinDao;
import com.gizwits.lease.trade.entity.TradeBase;
import com.gizwits.lease.trade.entity.TradeWeixin;
import com.gizwits.lease.trade.service.TradeBaseService;
import com.gizwits.lease.trade.service.TradeWeixinService;
import com.gizwits.lease.user.entity.User;
import com.gizwits.lease.user.entity.UserChargeCardOrder;
import com.gizwits.lease.user.entity.UserWxExt;
import com.gizwits.lease.user.service.UserChargeCardOrderService;
import com.gizwits.lease.user.service.UserService;
import com.gizwits.lease.user.service.UserWxExtService;
import com.gizwits.lease.util.WxUtil;
import com.gizwits.lease.wallet.entity.UserWallet;
import com.gizwits.lease.wallet.entity.UserWalletChargeOrder;
import com.gizwits.lease.wallet.service.UserWalletChargeOrderService;
import com.gizwits.lease.wallet.service.UserWalletService;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by zhl on 2017/6/30.
 */
@Service
public class TradeWeixinServiceImpl extends ServiceImpl<TradeWeixinDao, TradeWeixin> implements TradeWeixinService {

    private static Logger logger = LoggerFactory.getLogger("WEIXIN_LOGGER");
    private static Logger loggerOrder = LoggerFactory.getLogger("ORDER_LOGGER");

    @Autowired
    private UserService userService;
    @Autowired
    private UserWxExtService userWxExtService;
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private OrderPayRecordService orderPayRecordService;

    @Autowired
    private TradeBaseService tradeBaseService;

    @Autowired
    private UserWalletChargeOrderService userWalletChargeOrderService;

    @Autowired
    private SysUserExtService sysUserExtService;

    @Autowired
    private TradeWeixinDao tradeWeixinDao;

    @Autowired
    private OrderBaseService orderBaseService;
    @Autowired
    private UserChargeCardOrderService userChargeCardOrderService;

    @Autowired
    private UserWalletService userWalletService;

    /**
     * 生成JSAPI支付所需参数
     *
     * @param orderNo
     * @param fee
     * @param user
     * @param sysUserExt
     * @param tradeBase  @return
     */
    public ResponseObject getWxJsapipayInfo(String orderNo, Double fee, User user, SysUserExt sysUserExt, TradeBase tradeBase) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String callbackUrl = getPayCallbackUrl(request);
        String ip = WxUtil.getIpAddr(request);
        //获取预支付Id,返回前端使用
        Map<String, String> map = getPrepayId(tradeBase, user, sysUserExt, callbackUrl, ip, "JSAPI");
        if (map == null || !map.containsKey("prepayId")) {
            LeaseException.throwSystemException(LeaseExceEnums.WEIXIN_PREPAYID_ERROR);
        }
        String prepayId = map.get("prepayId");
        Map<String, String> payInfo = new HashMap();
        payInfo.put("appId", sysUserExt.getWxAppid());
        payInfo.put("timeStamp", String.valueOf(System.currentTimeMillis() / 1000));
        payInfo.put("nonceStr", System.currentTimeMillis() + "");
        payInfo.put("package", "prepay_id=" + prepayId);
        payInfo.put("signType", "MD5");

        String finalSign = SignUtils.createSign(payInfo, sysUserExt.getWxPartnerSecret());
        payInfo.put("paySign", finalSign);
        payInfo.put("outTradeNo", tradeBase.getTradeNo());
        payInfo.put("orderId", orderNo);
        BigDecimal money = new BigDecimal(fee);
        payInfo.put("money", money.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        //支付时需要支付的信息
        orderPayRecordService.saveOne(orderNo, PayType.WX_JSAPI.getCode(), payInfo.toString());
        return ResponseObject.ok(payInfo);
    }

    public ResponseObject getWxH5Payinfo(String orderNo, Double fee, User user, SysUserExt sysUserExt, TradeBase tradeBase) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String callbackUrl = getPayCallbackUrl(request);
        String ip = WxUtil.getIpAddr(request);
        //获取预支付Id,返回前端使用
        Map<String, String> map = getPrepayId(tradeBase, user, sysUserExt, callbackUrl, ip, "MWEB");
        if (map == null || !map.containsKey("mwebUrl")) {
            if (map.containsKey("errorMessage") && map.get("errorMessage").equals("appid and openid not match")) {
                // TODO: 2017/8/19 浏览器支付时无法确定用户的微信openid,只能一个一个循环试
            }
            LeaseException.throwSystemException(LeaseExceEnums.WEIXIN_PREPAYID_ERROR);

        }
        orderPayRecordService.saveOne(orderNo, PayType.WX_H5.getCode(), map.get("mwebUrl"));
        return ResponseObject.ok(map.get("mwebUrl"));
    }

    public ResponseObject getWxAppPayInfo(String orderNo, Double fee, User user, SysUserExt sysUserExt, TradeBase tradeBase) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String callbackUrl = getPayCallbackUrl(request);
        String ip = WxUtil.getIpAddr(request);
        //获取预支付Id,返回前端使用
        Map<String, String> map = getPrepayId(tradeBase, user, sysUserExt, callbackUrl, ip, "APP");
        if (map == null || !map.containsKey("prepayId")) {
            LeaseException.throwSystemException(LeaseExceEnums.WEIXIN_PREPAYID_ERROR);
        }

        String appId = "";
        String mchId = "";
        String partnerSecret = "";

        CommonSystemConfig commonSystemConfig = SysConfigUtils.get(CommonSystemConfig.class);
        appId = commonSystemConfig.getAppId();
        mchId = commonSystemConfig.getPartnerId();
        partnerSecret = commonSystemConfig.getPartnerSecret();


        //微信获取prepayId后再次签名, 注意参数的字母为小写, 新的sign放在finalSign中
        String prepayId = map.get("prepayId");
        Map<String, String> payInfo = new HashMap();
        payInfo.put("appid", appId);
        payInfo.put("partnerid", mchId);
        payInfo.put("prepayid", prepayId);
        payInfo.put("package", "Sign=WXPay");
        payInfo.put("noncestr", System.currentTimeMillis() + "");
        payInfo.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));

        String finalSign = SignUtils.createSign(payInfo, partnerSecret);
        payInfo.put("paySign", finalSign);
        payInfo.put("outTradeNo", tradeBase.getTradeNo());
        payInfo.put("orderId", orderNo);
        BigDecimal money = new BigDecimal(fee);
        payInfo.put("money", money.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        //支付时需要支付的信息
        orderPayRecordService.saveOne(orderNo, PayType.WX_APP.getCode(), payInfo.toString());
        return ResponseObject.ok(payInfo);
    }


    /**
     * 微信统一下单
     *
     * @param tradeBase
     * @param callbackUrl
     * @param ip
     * @return
     */
    private Map<String, String> getPrepayId(TradeBase tradeBase, User user, SysUserExt sysUserExt, String callbackUrl, String ip, String payType) {

        String nonce_str = System.currentTimeMillis() + "";
        String appId = "";
        String mchId = "";
        String partnerSecret = "";
        if (payType.equals("APP")) {
            CommonSystemConfig commonSystemConfig = SysConfigUtils.get(CommonSystemConfig.class);
            appId = commonSystemConfig.getAppId();
            mchId = commonSystemConfig.getPartnerId();
            partnerSecret = commonSystemConfig.getPartnerSecret();
        } else if (payType.equals("JSAPI")) {
            appId = sysUserExt.getWxAppid();
            mchId = sysUserExt.getWxParenterId();
            partnerSecret = sysUserExt.getWxPartnerSecret();
        }

        SortedMap<String, String> packageParams = new TreeMap();
        packageParams.put("appid", appId);
        packageParams.put("body", sysUserExt.getWxPayBody());
        packageParams.put("mch_id", mchId);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("notify_url", callbackUrl);
        packageParams.put("out_trade_no", tradeBase.getTradeNo());
        UserWxExt wxExt = userWxExtService.getByOpenidAndWxId(user.getOpenid(), sysUserExt.getWxId());
        if (!payType.equals("APP")) {
            packageParams.put("openid", wxExt.getOpenid());
        }
        packageParams.put("spbill_create_ip", ip);
        double total  = tradeBase.getTotalFee()*100;
        BigDecimal decimal = new BigDecimal(total);
        total = decimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
        packageParams.put("total_fee", String.valueOf((int) (total)));
        packageParams.put("trade_type", payType);
        if (payType.equals("MWEB")) {
            packageParams.put("scene_info", "{\"h5_info\":{\"type\":\"Wap\",\"wap_url\":\"https://charge.gizwits.com\",\"wap_name\":\"租赁消费\"}}");
        }

        String sign = SignUtils.createSign(packageParams, partnerSecret);

        //将要提交给API的数据对象转换成XML格式数据Post给API
        String postDataXML = "<xml>" +
                "<appid>" + appId + "</appid>" +
                "<mch_id>" + mchId + "</mch_id>" +
                "<nonce_str>" + nonce_str + "</nonce_str>" +
                "<sign>" + sign + "</sign>" +
                "<body><![CDATA[" + sysUserExt.getWxPayBody() + "]]></body>" +
                "<out_trade_no>" + tradeBase.getTradeNo() + "</out_trade_no>" +
                "<total_fee>" + packageParams.get("total_fee") + "</total_fee>" +
                "<spbill_create_ip>" + ip + "</spbill_create_ip>" +
                "<notify_url>" + callbackUrl + "</notify_url>" +
                "<trade_type>" + payType + "</trade_type>";
        if (!payType.equals("APP")) {
            postDataXML += "<openid>" + wxExt.getOpenid() + "</openid>";
        }
        if (payType.equals("MWEB")) {
            postDataXML += "<scene_info><![CDATA[{\"h5_info\":{\"type\":\"Wap\",\"wap_url\":\"https://charge.gizwits.com\",\"wap_name\":\"租赁消费\"}}]]></scene_info></xml>";
        } else {
            postDataXML += "</xml>";
        }
        logger.info("postDataXML====>>>" + postDataXML);

        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/pay/unifiedorder");
        CloseableHttpClient httpClient = HttpClients.createDefault();


        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);

        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            String responseContent = EntityUtils.toString(entity, "UTF-8");

            logger.info(responseContent);

            //将从API返回的XML数据映射到Java对象 getObjectFromXML(String xml, Class tClass)
            XStream xStreamForResponseData = new XStream();
            XStream.setupDefaultSecurity(xStreamForResponseData);
            xStreamForResponseData.allowTypes(new Class[]{TradeWeixinServiceImpl.class,WxMpPrepayIdResult.class});
            xStreamForResponseData.ignoreUnknownElements();//暂时忽略掉一些新增的字段
            xStreamForResponseData.alias("xml", WxMpPrepayIdResult.class);
            WxMpPrepayIdResult wxMpPrepayIdResult = (WxMpPrepayIdResult) xStreamForResponseData.fromXML(responseContent);
            Map<String, String> resultMap = new HashedMap();
            if (wxMpPrepayIdResult != null) {
                if (wxMpPrepayIdResult.getReturn_code().equals("FAIL")) {
                    resultMap.put("errorMessage", wxMpPrepayIdResult.getReturn_msg());
                    return resultMap;
                }

                if (payType.equals("JSAPI") || payType.equals("APP")) {
                    resultMap.put("prepayId", wxMpPrepayIdResult.getPrepay_id());
                    return resultMap;
                } else if (payType.equals("MWEB")) {
                    resultMap.put("mwebUrl", wxMpPrepayIdResult.getMweb_url());
                    return resultMap;
                }
            }
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("get wxprepayId io error!", e);
        } catch (Exception e) {
            logger.error("get wxprepayId error! ", e);
        }

        return null;
    }


    /**
     * 生成回调地址
     *
     * @param request
     * @return
     */
    private String getPayCallbackUrl(HttpServletRequest request) {
        String callbackUrl = ContextUtil.getHostWithContextPath(request);
        if (StringUtils.isBlank(SysConfigUtils.get(CommonSystemConfig.class).getWxPayCallBackUrlPath())) {
            callbackUrl += "/app/wxPay/wxPayNotify";
            logger.warn("===> 系统未配置微信支付回调URI,采用默认wxPayNotify=" + callbackUrl);
        } else {
            if (SysConfigUtils.get(CommonSystemConfig.class).getWxPayCallBackUrlPath().startsWith("/")) {
                callbackUrl += SysConfigUtils.get(CommonSystemConfig.class).getWxPayCallBackUrlPath().substring(1);
            }
        }
        return callbackUrl;
    }

    /**
     * 处理微信支付回调
     *
     * @param request
     * @param response
     */
    public void handleWxPayCallback(HttpServletRequest request, HttpServletResponse response) {
        try {
            //回调时间
            Date notifyTime = new Date();
            StringBuffer sb = new StringBuffer();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            String xmlData = sb.toString();
            logger.info("======>>> 获取微信支付回调内容: " + xmlData);

            WxMpPayCallback wxMpPayCallback = WxUtil.getJSSDKCallbackData(xmlData);
            if ("FAIL".equals(wxMpPayCallback.getResult_code())) {
                logger.error("===wxPayNotify error, callback message is:===>>>" + xmlData);
                WxUtil.responseToWx(response, "FAIL", "fail");
                return;
            }

            if (!"APP".equals(wxMpPayCallback.getTrade_type())) {
                //检查微信用户是否在系统中存在
                User user = userService.getUserByIdOrOpenidOrMobile(wxMpPayCallback.getOpenid());
                if (user == null) {
                    logger.error("===>> 微信支付回调openid[" + wxMpPayCallback.getOpenid() + "]在系统中不存在");
                    WxUtil.responseToWx(response, "FAIL", "fail");
                    return;
                }
            }

            Map<String, String> callbackDataMap = WxUtil.parseXmlToMap(xmlData);
            //根据回调
            if (!verfy(wxMpPayCallback, callbackDataMap)) {
                logger.error("===wxPayNotify error,verify not pass====>>>");
                WxUtil.responseToWx(response, "FAIL", "fail");
                return;
            } else {
                if (wxPayCallbackInnerLogic(wxMpPayCallback.getOut_trade_no(), wxMpPayCallback.getTransaction_id(), wxMpPayCallback.getTotal_fee(), wxMpPayCallback.getTime_end(), notifyTime)) {
                    logger.info("支付完成的回调：本次交易成功，交易单号:" + wxMpPayCallback.getOut_trade_no() + "交易金额为:" + wxMpPayCallback.getTotal_fee() + "交易完成时间:" + wxMpPayCallback.getTime_end());
                }
                WxUtil.responseToWx(response, "SUCCESS", "OK");
            }
        } catch (Exception ex) {
            logger.error("===> 处理微信支付回调失败，ex===>" + ex);
            ex.printStackTrace();
        }
    }

    public boolean verfy(WxMpPayCallback wxMpPayCallback, Map<String, String> callbackDataMap) {
        List<SysUserExt> list = sysUserExtService.selectList(new EntityWrapper<>());
        if (list == null || list.size() <= 0) {
            return false;
        }

        String partnerSecret = "";
        if ("APP".equals(wxMpPayCallback.getTrade_type())) {
            partnerSecret = SysConfigUtils.get(CommonSystemConfig.class).getPartnerSecret();
        }
        if (wxMpPayCallback.getSign().equals(SignUtils.createSign(callbackDataMap, partnerSecret))) {
            return true;
        }

        try {
            //由于微信验证的回调无法确定是哪个公众号,因此需要一个一个的循环验证,只要有一个验证通过就可以
            for (SysUserExt sysUserExt : list) {
                if (wxMpPayCallback.getSign().equals(SignUtils.createSign(callbackDataMap, sysUserExt.getWxPartnerSecret()))) {
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
     * 微信支付回调内部逻辑处理
     *
     * @param tradeNo
     * @param transactionId
     * @param totalFee      @return
     * @param timeEnd
     */
    @Transactional
    public boolean wxPayCallbackInnerLogic(String tradeNo, String transactionId, String totalFee, String timeEnd, Date nofifyTime) {
        //创建tradeWeixin和tradeBase
        TradeWeixin tradeWeixin = selectById(tradeNo);
        TradeBase tradeBase = tradeBaseService.selectById(tradeNo);
        if (Objects.isNull(tradeWeixin) || Objects.isNull(tradeBase)) {
            logger.error("====>>>>> 交易单tradeNo[" + tradeNo + "]在系统中未找到");
            return false;
        }
        //如果交易单的状态为SUCCESS则不执行下面的操作
        if (TradeStatus.SUCCESS.getCode().equals(tradeBase.getStatus())) {
            logger.warn("===========>交易单:" + tradeBase.getTradeNo() + "已经交易成功，此次操作不做处理");
            return false;
        }
        //修改交易单状态和回调时间
        tradeBase.setUtime(new Date());
        tradeBase.setNofifyTime(nofifyTime);
        if (!tradeBaseService.updateTradeStatus(tradeBase, TradeStatus.SUCCESS.getCode())) {
            logger.error("===============>修改交易号:" + tradeNo + "的状态失败");
        }
        //修改微信交易单的结束时间和微信自己的交易单号
        tradeWeixin.setTimeEnd(timeEnd);
        tradeWeixin.setTransactionId(transactionId);
        updateById(tradeWeixin);

        Integer orderType = tradeBase.getOrderType();
        if (TradeOrderType.CONSUME.getCode().equals(orderType)) {
            return orderBaseService.checkAndUpdateConsumeOrder(tradeBase.getOrderNo(), tradeBase.getTotalFee());
        } else if (TradeOrderType.CHARGE.getCode().equals(orderType)) {
            return userWalletChargeOrderService.checkAndUpdateChargeOrder(tradeBase.getTotalFee(), tradeBase.getOrderNo(), PayType.WEIXINPAY.getCode(), tradeBase.getTradeNo());
        } else if (TradeOrderType.CARD.getCode().equals(orderType)) {
            return userChargeCardOrderService.checkAndUpdateCardOrder(tradeBase.getOrderNo(), tradeBase.getTotalFee(), tradeNo);
        }


        return true;
    }


    /**
     * 微信订单退款
     *
     * @param orderNo
     * @return
     */
    @Override
    public void handleWxRefund(String orderNo) {
        loggerOrder.info("====>>> 订单:{},执行退款操作", orderNo);
        if (ParamUtil.isNullOrEmptyOrZero(orderNo)) {
            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
        }

        OrderBase order = orderBaseService.selectById(orderNo);
        if (!(OrderStatus.PAYED.getCode().equals(order.getOrderStatus()) || OrderStatus.REFUNDING.getCode().equals(order.getOrderStatus()))) {
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_STATUS_ERROR);
        }

        TradeBase tradeBase = tradeBaseService.selectOne(new EntityWrapper<TradeBase>()
                .eq("order_type", TradeOrderType.CONSUME.getCode())
                .eq("order_no", orderNo));
        if (Objects.isNull(tradeBase)) {
            LeaseException.throwSystemException(LeaseExceEnums.TRADE_NOT_EXIST);
        }
        TradeWeixin tradeWeiXin = selectById(tradeBase.getTradeNo());
        if (Objects.isNull(tradeWeiXin)) {
            LeaseException.throwSystemException(LeaseExceEnums.TRADE_WEIXIN_NOT_EXIST);
        }
        try {
            String result = executeWxRefund(order, tradeWeiXin);
            loggerOrder.info("====>>> 订单:{},执行退款结果:{}", orderNo, result);
            Map<String, String> resultMap = WxUtil.parseXmlToMap(result);
            if ("SUCCESS".equalsIgnoreCase(resultMap.get("result_code"))) {
                orderBaseService.updateOrderStatusAndHandle(order, OrderStatus.REFUNDED.getCode());
            } else {
                orderBaseService.updateOrderStatusAndHandle(order, OrderStatus.REFUND_FAIL.getCode());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    @Transactional
    public ResponseObject prePay(PrePayDto prePayDto, Integer userBrowserAgentType) {
        //预支付前端传过来的是order_no
        ////根据前端的得到的openid获取user
        User user = userService.getCurrentUser();
        // TODO 一个用户对多个公众号时无法唯一确定user_wx_ext是哪一个，所以下面这样取是错的
        // UserWxExt userWxExt = userWxExtService.getByOpenid(user.getOpenid());
        // if (userWxExt != null) {
        //     user.setWxId(userWxExt.getWxId());
        //     user.setOpenid(userWxExt.getOpenid());
        // }

        String orderNo;
        Double fee;
        SysUserExt sysUserExt;
        Map<String, Object> map = null;
        //根据订单号获取订单类型
        Integer orderType = LeaseUtil.getOrderType(prePayDto.getOrderNo());
        //先判断订单类型
        if (orderType.equals(TradeOrderType.CONSUME.getCode())) {//消费订单
            OrderBase orderBase = orderBaseService.selectById(prePayDto.getOrderNo());
            if (ParamUtil.isNullOrEmptyOrZero(orderBase)) {
                LeaseException.throwSystemException(LeaseExceEnums.ORDER_DONT_EXISTS);
            }
            map = prePayForConsumeOrder(prePayDto.getSno(), user.getId(), prePayDto.getPayType(), prePayDto.getOrderNo());
        } else if (orderType.equals(TradeOrderType.CHARGE.getCode())) {//充值订单
            if (!ParamUtil.isNullOrEmptyOrZero(prePayDto.getSno())) {
                map = prePayForChargeOrder(user, prePayDto.getOrderNo(), prePayDto.getSno());
            } else {
                map = prePayForChargeOrder(user, prePayDto.getOrderNo());
            }
        }  else {
            loggerOrder.error("订单号：" + prePayDto.getOrderNo() + "的订单的订单类型不存在");
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_TYPE_NOT_EXIST);
        }

        orderNo = String.valueOf(map.get("orderNo"));
        fee = Double.valueOf(map.get("fee") + "");
        sysUserExt = (SysUserExt) map.get("sysUserExt");

        //获取微信支付的回调
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String callbackUrl = getPayCallbackUrl(request);

        TradeBase tradeBase = tradeBaseService.createTrade(orderNo, fee, callbackUrl, PayType.WEIXINPAY.getCode(), orderType);
        //创建tradeWeixin
        createTradeWeixin(tradeBase, sysUserExt);
        if (prePayDto.getPayType() == null) {
            logger.info("prePayDto.getPayType()=" + prePayDto.getPayType() + ",设置默认的 PayType.WX_JSAPI");
            prePayDto.setPayType(PayType.WX_JSAPI.getCode());
        }
        //不同支付类型参数获取
        if (prePayDto.getPayType().equals(PayType.WX_JSAPI.getCode())) {

            return getWxJsapipayInfo(orderNo, fee, user, sysUserExt, tradeBase);
        } else if (prePayDto.getPayType().equals(PayType.WX_H5.getCode())) {

            return getWxH5Payinfo(orderNo, fee, user, sysUserExt, tradeBase);
        } else if (prePayDto.getPayType().equals(PayType.WX_APP.getCode())) {

            return getWxAppPayInfo(orderNo, fee, user, sysUserExt, tradeBase);
        } else {
            LeaseException.throwSystemException(LeaseExceEnums.PAYTYPE_NULL);
            return null;
        }
    }

    @Override
    @Transactional
    public ResponseObject cardRechargePrePay(PrePayDto prePayDto, Integer userBrowserAgentType) {
        // 根据openid获取user
        User user = userService.getCurrentUser();
        // 根据订单号判断订单类型
        Integer orderType = LeaseUtil.getOrderType(prePayDto.getOrderNo());
        Map<String, Object> map = null;
        if (orderType.equals(TradeOrderType.CARD.getCode())) { // 只处理充值卡订单
            map = prePayForCardOrder(user, prePayDto.getOrderNo());
        } else {
            loggerOrder.error("订单号：" + prePayDto.getOrderNo() + "的订单的订单类型不存在");
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_TYPE_NOT_EXIST);
        }

        String orderNo = String.valueOf(map.get("orderNo"));
        Double fee = Double.valueOf(map.get("fee") + "");
        SysUserExt sysUserExt = (SysUserExt) map.get("sysUserExt");

        //获取微信支付的回调
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String callbackUrl = getPayCallbackUrl(request);

        TradeBase tradeBase = tradeBaseService.createTrade(orderNo, fee, callbackUrl, PayType.WEIXINPAY.getCode(), orderType);
        //创建tradeWeixin
        createTradeWeixin(tradeBase, sysUserExt);

        // 使用 微信公众号
        return getWxJsapipayInfo(orderNo, fee, user, sysUserExt, tradeBase);

    }

    private Map<String, Object> prePayForCardOrder(User user, String orderNo) {
        SysUserExt sysUserExt = sysUserExtService.selectOne(new EntityWrapper<SysUserExt>().eq("wx_id", user.getWxId()));
        if (Objects.isNull(sysUserExt)) {
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_NOT_IN_OPERATOR);
        }
        UserChargeCardOrder cardOrder = userChargeCardOrderService.selectById(orderNo);
        if (Objects.isNull(cardOrder)) {
            LeaseException.throwSystemException(LeaseExceEnums.CHARGE_ORDER_NOT_EXIST);
        }

        //将充值卡订单状态修改为支付中
        userChargeCardOrderService.updateCardOrderStatus(orderNo, OrderStatus.PAYING.getCode());

        //保存到map返回到外面使用
        Map<String, Object> map = new HashMap<>();
        map.put("orderNo", cardOrder.getOrderNo());
        map.put("fee", cardOrder.getMoney());
        map.put("sysUserExt", sysUserExt);
        return map;
    }

    private Map<String, Object> prePayForConsumeOrder(String sno, Integer userId, Integer payType, String orderNo) {

        //支付前再次检查订单情况
        Map<String, Object> result = orderBaseService.checkBeforeOrder(sno, ThirdPartyUserType.WEIXIN.getCode());

        Boolean canRent = (Boolean) result.get("canRenew");

        OrderBase orderBase = orderBaseService.getOrderBaseByOrderNo(orderNo);
        if (orderBase == null) {
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_DONT_EXISTS);
        }

        //查看该用户是否有使用中的订单
//        List<OrderBase> unfinishOrderList = orderBaseService.findByUserIdAndStatus(userId, OrderStatus.USING.getCode());
//        if (unfinishOrderList != null && unfinishOrderList.size() > 0) {
//            LeaseException.throwSystemException(LeaseExceEnums.HAS_UNFINISH_ORDER);
//        }

        orderBase.setPayType(payType);
        orderBase.setUtime(new Date());
        orderBaseService.updateById(orderBase);


       /* Device device = deviceService.getDeviceInfoBySno(sno);
        if (Objects.isNull(device) || DeviceStatus.USING.getCode().equals(device.getWorkStatus()) || DeviceStatus.OFFLINE.getCode().equals(device.getOnlineStatus())) {
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_INUSING_FAIL);
        }*/
        //判断设备是否可用被租赁
        deviceService.checkDeviceIsRenting(sno, canRent);
        //获取设备对应的sysUserExt
        SysUserExt sysUserExt = deviceService.getWxConfigByDeviceId(sno);
        if (Objects.isNull(sysUserExt)) {
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_NOT_IN_OPERATOR);
        }

        //兼容支付中也可以支付
        if (orderBase.getOrderStatus().equals(OrderStatus.PAYING.getCode())) {
            orderBase.setOrderStatus(OrderStatus.INIT.getCode());
        }
        //修改订单状态为支付中
        orderBaseService.updateOrderStatusAndHandle(orderBase, OrderStatus.PAYING.getCode());
        //保存到map返回到外面使用
        Map<String, Object> map = new HashMap<>();
        map.put("orderNo", orderBase.getOrderNo());
        map.put("fee", orderBase.getAmount());
        map.put("sysUserExt", sysUserExt);
        return map;
    }


    /**
     * 不带设备序列的充值下单
     *
     * @param user
     * @param orderNo
     * @return
     */
    private Map<String, Object> prePayForChargeOrder(User user, String orderNo) {
        // TODO 因为一个用户对应多个公众号，这里无法确定是哪个公众号，外部传进来的wx_id也具有不确定性。
        SysUserExt sysUserExt = sysUserExtService.selectOne(new EntityWrapper<SysUserExt>().eq("wx_id", user.getWxId()));
        if (Objects.isNull(sysUserExt)) {
            LeaseException.throwSystemException(LeaseExceEnums.WEIXIN_ID_IS_NULL);
        }

        UserWalletChargeOrder userWalletChargeOrder = userWalletChargeOrderService.selectById(orderNo);
        if (Objects.isNull(userWalletChargeOrder)) {
            LeaseException.throwSystemException(LeaseExceEnums.CHARGE_ORDER_NOT_EXIST);
        }
        //将订单状态修改为支付中
        userWalletChargeOrderService.updateChargeOrderStatus(orderNo, UserWalletChargeOrderType.PAYING.getCode());

        //保存到map返回到外面使用
        Map<String, Object> map = new HashMap<>();
        map.put("orderNo", userWalletChargeOrder.getChargeOrderNo());
        map.put("fee", userWalletChargeOrder.getFee());
        map.put("sysUserExt", sysUserExt);
        return map;
    }

    /**
     * 带设备序列号充值下单
     *
     * @param user
     * @param orderNo
     * @param sno
     * @return
     */
    private Map<String, Object> prePayForChargeOrder(User user, String orderNo, String sno) {
        SysUserExt sysUserExt = deviceService.getWxConfigByDeviceId(sno);
        if (Objects.isNull(sysUserExt)) {
            LeaseException.throwSystemException(LeaseExceEnums.WEIXIN_ID_IS_NULL);
        }
        UserWalletChargeOrder userWalletChargeOrder = userWalletChargeOrderService.selectById(orderNo);
        if (Objects.isNull(userWalletChargeOrder)) {
            LeaseException.throwSystemException(LeaseExceEnums.CHARGE_ORDER_NOT_EXIST);
        }

        //将订单状态修改为支付中
        userWalletChargeOrderService.updateChargeOrderStatus(orderNo, UserWalletChargeOrderType.PAYING.getCode());

        //保存到map返回到外面使用
        Map<String, Object> map = new HashMap<>();
        map.put("orderNo", userWalletChargeOrder.getChargeOrderNo());
        map.put("fee", userWalletChargeOrder.getFee());
        map.put("sysUserExt", sysUserExt);
        return map;
    }

    public boolean queryOrderPayStatus(String orderNo) {
        if (ParamUtil.isNullOrEmptyOrZero(orderNo)) {
            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
        }
        OrderBase orderBase = orderBaseService.getOrderBaseByOrderNo(orderNo);
        if (orderBase == null) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        if (!orderBase.getOrderStatus().equals(OrderStatus.PAYING.getCode())) {
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_STATUS_ERROR);
        }
       /* //检查用户是否有使用中的订单
        if (orderBaseService.getUsingOrderByUserIdentify(orderBase.getUserId() + "") != null) {
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_IN_USING_REPEAT);
        }*/

        SysUserExt sysUserExt = deviceService.getWxConfigByDeviceId(orderBase.getSno());
        if (sysUserExt == null) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }

        //订单已支付,修改订单状态为已支付
        if (executeWxOrderQuery(orderBase, sysUserExt)) {
            orderBaseService.updateOrderStatusAndHandle(orderBase, OrderStatus.PAYED.getCode());
            return true;
            //订单未支付,修改为失败状态
        } else {
            orderBase.setOrderStatus(OrderStatus.FAIL.getCode());
            orderBase.setUtime(new Date());
            orderBaseService.updateById(orderBase);
            return false;
        }
    }

    public boolean executeWxOrderQuery(OrderBase orderBase, SysUserExt sysUserExt) {

        if (orderBase.getPayType().equals(PayType.WX_H5.getCode())
                || orderBase.getPayType().equals(PayType.WX_JSAPI.getCode())) {
            TradeWeixin tradeWeixin = tradeWeixinDao.selectLastTrade(orderBase.getOrderNo());

            String nonce_str = System.currentTimeMillis() + "";
            SortedMap<String, String> packageParams = new TreeMap<>();
            packageParams.put("appid", sysUserExt.getWxAppid());
            packageParams.put("mch_id", sysUserExt.getWxParenterId());
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("out_trade_no", tradeWeixin.getTradeNo());

            String sign = SignUtils.createSign(packageParams, sysUserExt.getWxPartnerSecret());
            String postDataXML = "<xml>" +
                    "<appid>" + sysUserExt.getWxAppid() + "</appid>" +
                    "<mch_id>" + sysUserExt.getWxParenterId() + "</mch_id>" +
                    "<nonce_str>" + nonce_str + "</nonce_str>" +
                    "<sign>" + sign + "</sign>" +
                    "<out_refund_no>" + tradeWeixin.getTradeNo() + "</out_refund_no>" +
                    "</xml>";

            HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/pay/orderquery");
            CloseableHttpClient httpClient = HttpClients.createDefault();

            //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
            StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
            httpPost.addHeader("Content-Type", "text/xml");
            httpPost.setEntity(postEntity);
            try {
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity entity = response.getEntity();

                String responseContent = EntityUtils.toString(entity, "UTF-8");

                logger.info(responseContent);

                //将从API返回的XML数据映射到Java对象 getObjectFromXML(String xml, Class tClass)
                XStream xStreamForResponseData = new XStream();
                XStream.setupDefaultSecurity(xStreamForResponseData);
                xStreamForResponseData.allowTypes(new Class[]{TradeWeixinServiceImpl.class,WxMpQueryResult.class});
                xStreamForResponseData.ignoreUnknownElements();//暂时忽略掉一些新增的字段
                xStreamForResponseData.alias("xml", WxMpQueryResult.class);
                WxMpQueryResult wxMpQueryResult = (WxMpQueryResult) xStreamForResponseData.fromXML(responseContent);

                if (wxMpQueryResult != null) {
                    if (wxMpQueryResult.getReturn_code().equals("FAIL")) {
                        LeaseException.throwSystemException(LeaseExceEnums.WX_ORDER_QUERY_FAIL);
                    } else {
                        if (wxMpQueryResult.getResult_code().equals("SUCCESS")) {
                            if (wxMpQueryResult.getTrade_state().equals("NOTPAY")) {
                                return false;
                            } else return wxMpQueryResult.getTrade_state().equals("SUCCESS");
                        }
                    }

                }
                return false;
            } catch (IOException e) {
                e.printStackTrace();
                logger.error("get wxprepayId io error!", e);
            } catch (Exception e) {
                logger.error("get wxprepayId error! ", e);
            }

        }

        return false;
    }

    private String executeWxRefund(OrderBase order, TradeWeixin tradeWeixin) throws Exception {
        //fixme getWxConfigByDeviceId 找出来的是微信公众号对应的商户号信息，微信app支付对应的商户号再系统配置里面（不合理）
        SysUserExt sysUserExt = deviceService.getWxConfigByDeviceId(order.getSno());

        if (PayType.WX_APP.getCode() == order.getPayType().intValue()) {
            CommonSystemConfig config = SysConfigUtils.get(CommonSystemConfig.class);
            String partnerId = config.getPartnerId();
            String partnerSecret = config.getPartnerSecret();
            String appId = config.getAppId();

            sysUserExt.setWxParenterId(partnerId);
            sysUserExt.setWxPartnerSecret(partnerSecret);
            sysUserExt.setWxAppid(appId);
            sysUserExt.setWxCertPath(sysUserExt.getWxAppCertPath());
        }

        if (sysUserExt == null) {
            logger.error("找不到订单{}对应的设备{}的微信配置", order.getOrderNo(), order.getSno());
            order.setRefundResult(LeaseExceEnums.ORDER_REFUND_FAIL_WITH_INCOMPLATE_INFORMATION.getMessage());
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_REFUND_FAIL_WITH_INCOMPLATE_INFORMATION);
        }
        return executeRefund(order, tradeWeixin, sysUserExt);
    }

    private String executeRefund(OrderBase order, TradeWeixin tradeWeixin, SysUserExt sysUserExt) throws Exception {
        Integer totalFee = (int) Math.round((order.getAmount() * 100));
        Integer refundFee = totalFee;
        String outRefundNo = order.getOrderNo() + "00";
        String transaction_id = tradeWeixin.getTransactionId();
        String nonce_str = System.currentTimeMillis() + "";


        SortedMap<String, String> packageParams = new TreeMap<>();
        packageParams.put("appid", sysUserExt.getWxAppid());
        packageParams.put("mch_id", sysUserExt.getWxParenterId());
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("out_refund_no", outRefundNo);
        packageParams.put("transaction_id", transaction_id);

        packageParams.put("total_fee", totalFee.toString());
        packageParams.put("refund_fee", refundFee.toString());

        String sign = SignUtils.createSign(packageParams, sysUserExt.getWxPartnerSecret());
        //将要提交给API的数据对象转换成XML格式数据Post给API
        //默认采用未结算资金退款
        String postDataXML = "<xml>" +
                "<appid>" + sysUserExt.getWxAppid() + "</appid>" +
                "<mch_id>" + sysUserExt.getWxParenterId() + "</mch_id>" +
                "<nonce_str>" + nonce_str + "</nonce_str>" +
                "<sign>" + sign + "</sign>" +
                "<transaction_id>" + transaction_id + "</transaction_id>" +
                "<out_refund_no>" + outRefundNo + "</out_refund_no>" +
                "<total_fee>" + totalFee + "</total_fee>" +
                "<refund_fee>" + refundFee + "</refund_fee>" +
                "</xml>";

        logger.info("postDataXML: {}", postDataXML);
        String wxCertPath = sysUserExt.getWxCertPath();
        if (StringUtils.isBlank(wxCertPath)) {
            order.setRefundResult(LeaseExceEnums.CERT_LOSE.getMessage());
            LeaseException.throwSystemException(LeaseExceEnums.CERT_LOSE);
        }
        String result = HttpUtil.executeBySslPost("https://api.mch.weixin.qq.com/secapi/pay/refund"
                , postDataXML
                , wxCertPath, sysUserExt.getWxParenterId().trim());

        if (ParamUtil.isNullOrEmptyOrZero(result)) {
            // 如果未结算资金退款失败，采用可用余额进行退款
            String refundAccount = "REFUND_SOURCE_RECHARGE_FUNDS";
            postDataXML = "<xml>" +
                    "<appid>" + sysUserExt.getWxAppid() + "</appid>" +
                    "<mch_id>" + sysUserExt.getWxParenterId() + "</mch_id>" +
                    "<nonce_str>" + nonce_str + "</nonce_str>" +
                    "<sign>" + sign + "</sign>" +
                    "<transaction_id>" + transaction_id + "</transaction_id>" +
                    "<out_refund_no>" + outRefundNo + "</out_refund_no>" +
                    "<total_fee>" + totalFee + "</total_fee>" +
                    "<refund_fee>" + refundFee + "</refund_fee>" +
                    "<refund_account>" + refundAccount + "</refund_fee>" +
                    "</xml>";
            logger.info("postDataXML: {}", postDataXML);

            result = HttpUtil.executeBySslPost("https://api.mch.weixin.qq.com/secapi/pay/refund"
                    , postDataXML
                    , wxCertPath, sysUserExt.getWxParenterId().trim());
            if (ParamUtil.isNullOrEmptyOrZero(result)) {
                order.setRefundResult(LeaseExceEnums.ORDER_REFUND_FAIL_WITH_RESULT_ID_EMPTY.getMessage());
                LeaseException.throwSystemException(LeaseExceEnums.ORDER_REFUND_FAIL_WITH_RESULT_ID_EMPTY);
            }
        }
        return result;
    }

    @Override
    public void createTradeWeixin(TradeBase tradeBase, SysUserExt sysUserExt) {
        //创建tradeWeixin
        TradeWeixin tradeWeixin = new TradeWeixin();
        tradeWeixin.setTradeNo(tradeBase.getTradeNo());
        tradeWeixin.setMchId(sysUserExt.getWxParenterId());
        tradeWeixin.setAppid(sysUserExt.getWxAppid());
        tradeWeixin.setBody(sysUserExt.getWxPayBody());
        baseMapper.inserOnDunplicateKey(tradeWeixin);
    }

    @Override
    public void refund(OrderBase orderBase, TradeBase tradeBase) {
        logger.info("[refund].orderNo={},tradeNo={}", orderBase.getOrderNo(), tradeBase.getTradeNo());
        TradeWeixin tradeWeiXin = tradeWeixinDao.selectById(tradeBase.getTradeNo());
        if (Objects.isNull(tradeWeiXin)) {
            LeaseException.throwSystemException(LeaseExceEnums.TRADE_WEIXIN_NOT_EXIST);
        }

        String result = null;
        try {
            result = executeWxRefund(orderBase, tradeWeiXin);
        } catch (Exception e) {
            logger.error("tradeNo=" + tradeBase.getTradeNo(), e);
            LeaseException.throwSystemException(LeaseExceEnums.REFUND_FAIL,e.getMessage());
        }
        loggerOrder.info("[refund].orderNo={},tradeNo={},response={}", orderBase.getOrderNo(), tradeBase.getTradeNo(), result);
        Map<String, String> resultMap = WxUtil.parseXmlToMap(result);
        if (resultMap == null || !"SUCCESS".equalsIgnoreCase(resultMap.get("result_code"))) {
            logger.error("退款失败，原因：" + resultMap.get("err_code_des"));
            LeaseException.throwSystemException(LeaseExceEnums.REFUND_FAIL, resultMap.get("err_code_des"));
        }
    }

    @Override
    public void handleChargeRefund(String chargeOrderNo) {
        logger.info("====>>> 押金订单:{},开始执行退款", chargeOrderNo);
        UserWalletChargeOrder chargeOrder = userWalletChargeOrderService.selectById(chargeOrderNo);
        if (Objects.isNull(chargeOrder)) {
            LeaseException.throwSystemException(LeaseExceEnums.CHARGE_ORDER_NOT_EXIST);
        }

        if (!(OrderStatus.PAYED.getCode().equals(chargeOrder.getStatus()) || OrderStatus.REFUNDING.getCode().equals(chargeOrder.getStatus()))) {
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_STATUS_ERROR);
        }

        TradeBase tradeBase = tradeBaseService.selectOne(new EntityWrapper<TradeBase>()
                .eq("order_type", TradeOrderType.CHARGE.getCode())
                .eq("order_no", chargeOrder.getChargeOrderNo()));
        if (Objects.isNull(tradeBase)) {
            LeaseException.throwSystemException(LeaseExceEnums.TRADE_NOT_EXIST);
        }
        TradeWeixin tradeWeiXin = selectById(tradeBase.getTradeNo());
        if (Objects.isNull(tradeWeiXin)) {
            LeaseException.throwSystemException(LeaseExceEnums.TRADE_WEIXIN_NOT_EXIST);
        }
        try {
            OrderBase order = new OrderBase();
            order.setOrderNo(chargeOrder.getChargeOrderNo());
            order.setOrderStatus(chargeOrder.getStatus());
            order.setAmount(chargeOrder.getFee());
            SysUserExt sysUserExt = sysUserExtService.getLatestSysUserExt();
            String result = executeRefund(order, tradeWeiXin, sysUserExt);
            logger.info("====>>> 押金订单:{},执行退款结果:{}", chargeOrder, result);
            Map<String, String> resultMap = WxUtil.parseXmlToMap(result);
            if ("SUCCESS".equalsIgnoreCase(resultMap.get("result_code"))) {
                userWalletChargeOrderService.updateChargeOrderStatus(chargeOrder, OrderStatus.REFUNDED.getCode());
                //更新押金钱包金额
                UserWallet userWallet = userWalletService.selectUserWallet(chargeOrder.getUserId(), WalletEnum.DEPOSIT.getCode());
                if (!ParamUtil.isNullOrEmptyOrZero(userWallet) && userWallet.getMoney().compareTo(chargeOrder.getFee()) >= 0) {
                    logger.info("更新钱包余额 userWallet：[],balance :[]", userWallet.getId(), userWallet.getMoney());
                    userWallet.setMoney(userWallet.getMoney() - chargeOrder.getFee());
                    userWallet.setUtime(new Date());
                    userWalletService.updateById(userWallet);
                }
            } else {
                userWalletChargeOrderService.updateChargeOrderStatus(chargeOrder, OrderStatus.REFUND_FAIL.getCode());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
