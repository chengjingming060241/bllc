package com.gizwits.lease.trade.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.entity.SysUserExt;
import com.gizwits.boot.sys.service.SysUserExtService;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.DateKit;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.boot.utils.SysConfigUtils;
import com.gizwits.lease.app.utils.LeaseUtil;
import com.gizwits.lease.config.CommonSystemConfig;
import com.gizwits.lease.constant.*;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.install.entity.InstallFeeOrder;

import com.gizwits.lease.order.dto.PrePayDto;
import com.gizwits.lease.order.entity.OrderBase;
import com.gizwits.lease.order.service.OrderBaseService;
import com.gizwits.lease.trade.dao.TradeAlipayDao;
import com.gizwits.lease.trade.entity.TradeAlipay;
import com.gizwits.lease.trade.entity.TradeBase;
import com.gizwits.lease.trade.service.TradeAlipayService;
import com.gizwits.lease.trade.service.TradeBaseService;
import com.gizwits.lease.user.entity.User;
import com.gizwits.lease.user.service.UserService;
import com.gizwits.lease.wallet.entity.UserWalletChargeOrder;
import com.gizwits.lease.wallet.service.UserWalletChargeOrderService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 支付宝交易表 服务实现类
 * </p>
 *
 * @author yinhui
 * @since 2017-08-15
 */
@Service
public class TradeAlipayServiceImpl extends ServiceImpl<TradeAlipayDao, TradeAlipay> implements TradeAlipayService {

    private static Logger logger = LoggerFactory.getLogger("PAY_LOGGER");

    @Autowired
    private TradeAlipayDao tradeAlipayDao;

    @Autowired
    private TradeBaseService tradeBaseService;

    @Autowired
    private OrderBaseService orderBaseService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private UserService userService;

    @Autowired
    private SysUserExtService sysUserExtService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private UserWalletChargeOrderService userWalletChargeOrderService;


    @Override
    public TradeAlipay selectLastTrade(String orderNo) {
        if (ParamUtil.isNullOrEmptyOrZero(orderNo))
            return null;
        return tradeAlipayDao.selectLastTrade(orderNo);
    }


    /**
     * 支付宝预支付
     *
     * @param prePayDto
     * @param browserAgentType
     * @return
     */
    @Override
    @Transactional
    public String prePay(PrePayDto prePayDto, Integer browserAgentType) {
        //根据订单号获取订单类型
        Integer orderType = LeaseUtil.getOrderType(prePayDto.getOrderNo());

        User user = userService.getCurrentUser();
        if (Objects.isNull(user)) {
            LeaseException.throwSystemException(LeaseExceEnums.USER_DONT_EXISTS);
        }
        logger.info("orderType = " + orderType);

        String orderNo = null;
        BigDecimal fee = null;
        SysUserExt sysUserExt = null;
        String subject = null;
        String body = null;

        //判断订单类型
        if (orderType.equals(TradeOrderType.CONSUME.getCode())) {//消费订单
            OrderBase orderBase = orderBaseService.selectById(prePayDto.getOrderNo());
            if (Objects.isNull(orderBase)) {
                logger.info("订单不存在 orderNo = " + prePayDto.getOrderNo());
                LeaseException.throwSystemException(LeaseExceEnums.ORDER_DONT_EXISTS);
            }
            Map<String, Object> result = orderBaseService.checkBeforeOrder(orderBase.getSno(), browserAgentType);
            Boolean canRenew = (Boolean) result.get("canRenew");
            //判断订单、设备、用户
            orderNo = orderBase.getOrderNo();
            fee = BigDecimal.valueOf(orderBase.getAmount());
            sysUserExt = (SysUserExt) result.get("sysUserExt");
            subject = orderBase.getServiceModeName();
            body = orderBase.getMac();
            // #16948 支付前判断设备状态
           /* Device device = deviceService.getDeviceInfoBySno(prePayDto.getSno());
            if (Objects.isNull(device) || DeviceStatus.USING.getCode().equals(device.getWorkStatus()) || DeviceStatus.OFFLINE.getCode().equals(device.getOnlineStatus())) {
                LeaseException.throwSystemException(LeaseExceEnums.DEVICE_INUSING_FAIL);
            }*/
            deviceService.checkDeviceIsRenting(orderBase.getSno(), canRenew);

            orderBase.setPayType(prePayDto.getPayType());
            orderBase.setUtime(new Date());
            orderBaseService.updateOrderStatusAndHandle(orderBase, OrderStatus.PAYING.getCode());
        } else if (orderType.equals(TradeOrderType.CHARGE.getCode())) {//充值订单
            UserWalletChargeOrder chargeOrder = userWalletChargeOrderService.selectOne(new EntityWrapper<UserWalletChargeOrder>().eq("charge_order_no", prePayDto.getOrderNo()));
            if (Objects.isNull(chargeOrder)) {
                LeaseException.throwSystemException(LeaseExceEnums.ORDER_DONT_EXISTS);
            }
            chargeOrder.setUtime(new Date());
            userWalletChargeOrderService.updateChargeOrderStatus(chargeOrder, UserWalletChargeOrderType.PAYING.getCode());

            orderNo = chargeOrder.getChargeOrderNo();
            fee = BigDecimal.valueOf(chargeOrder.getFee());
            sysUserExt = deviceService.getWxConfigByDeviceId(prePayDto.getSno());
            subject = chargeOrder.getWalletType() + "";
            body = chargeOrder.getWalletName();
        }  else {
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_DONT_EXISTS);
        }

        TradeBase tradeBase = tradeBaseService.createTrade(orderNo, fee.doubleValue(), sysUserExt.getAlipayNotifyUrl(), PayType.ALIPAY.getCode(), orderType);
        TradeAlipay tradeAlipay = new TradeAlipay();
        tradeAlipay.setSubject(subject);
        tradeAlipay.setAppid(sysUserExt.getAlipayAppid());
        tradeAlipay.setTradeNo(tradeBase.getTradeNo());
        insert(tradeAlipay);

        if (browserAgentType.equals(ThirdPartyUserType.APP.getCode())) {
            return executePayForApp(sysUserExt, tradeBase.getTradeNo(), fee, subject);
        } else {
            return executePay(sysUserExt, tradeBase.getTradeNo(), fee, subject, body, user.getAlipayUnionid());
        }
    }

    private String executePayForApp(SysUserExt sysUserExt, String tradeNo, BigDecimal fee, String subject) {
        logger.info("executePayForApp...");
        if (Objects.isNull(sysUserExt)) {
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_INUSING_FAIL);
        }

        if (ParamUtil.isNullOrEmptyOrZero(sysUserExt.getAlipayAppid()) ||
                ParamUtil.isNullOrEmptyOrZero(sysUserExt.getAlipayPrivateKey()) ||
                ParamUtil.isNullOrEmptyOrZero(sysUserExt.getAlipayPublicKey()) ||
                ParamUtil.isNullOrEmptyOrZero(sysUserExt.getAlipayNotifyUrl())) {
            LeaseException.throwSystemException(LeaseExceEnums.ALIPAY_PARAM_IS_NULL);
        }

        /**
         * 支付宝订单签名
         */

        //获得初始化的AlipayClient
        AlipayClient alipayClient = getAlipayClient(sysUserExt);
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setSubject(subject);
        model.setOutTradeNo(tradeNo);
        BigDecimal totalAmount = fee.setScale(2, BigDecimal.ROUND_HALF_UP);
        model.setTotalAmount(totalAmount.toString());
        model.setProductCode("QUICK_MSECURITY_PAY");
        request.setBizModel(model);
        request.setNotifyUrl(sysUserExt.getAlipayNotifyUrl());

        try {
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            logger.info("Alipay response body: " + response.getBody());

            if (response.isSuccess()) {
                return response.getBody();
            } else {
                logger.info("Alipay sign is failure, reason code: " + response.getCode() + " reason :" + response.getMsg());
                return response.getBody();
            }
        } catch (AlipayApiException e) {
            logger.error("Alipay sign is failure!");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取当面付的二维码
     *
     * @param sysUserExt
     * @param
     * @param fee
     * @param subject
     * @param storeId
     * @return
     */
    @Override
    public String precrate(SysUserExt sysUserExt, String orderNo, BigDecimal fee, String subject, String storeId) {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = getAlipayClient(sysUserExt);
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();//创建API对应的request类
        request.setBizContent("{" +
                "    \"out_trade_no\":\"" + orderNo + "\"," +
                "    \"total_amount\":\"" + fee + "\"," +
                "    \"subject\":\"" + subject + "\"," +
                "    \"store_id\":\"" + storeId + "\"," +
                "    \"timeout_express\":\"90m\"}");//设置业务参数
        AlipayTradePrecreateResponse response = null;
        try {
            response = alipayClient.execute(request);
            //根据response中的结果继续业务逻辑处理
            if (response.isSuccess()) {
                return response.getQrCode();
            } else {
                logger.info("alipayClient.pageExecute请求失败!");
                LeaseException.throwSystemException(LeaseExceEnums.ALIPAY_PAY_FAILUR);
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public String executePay(SysUserExt sysUserExt, String tradeNo, BigDecimal fee, String subject, String body, String buyerId) {
        logger.info("executePay...");
        if (Objects.isNull(sysUserExt)) {
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_INUSING_FAIL);
        }

        //获得初始化的AlipayClient
        AlipayClient alipayClient = getAlipayClient(sysUserExt);


        AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
        request.setReturnUrl(sysUserExt.getAlipayReturnUrl());
        request.setNotifyUrl(sysUserExt.getAlipayNotifyUrl());
        request.setBizContent("{" +
                "\"out_trade_no\":\"" + tradeNo + "\"," +
                "\"total_amount\":\"" + fee.toString() + "\"," +
                "\"subject\":\"" + subject + "\"," +
                "\"buyer_id\":\"" + buyerId + "\"," +
                "\"body\":\"" + body + "\"" +
                "  }");
        AlipayTradeCreateResponse response = null;
        try {
            response = alipayClient.execute(request);
            if (response.isSuccess()) {
                return response.getTradeNo();
            } else {
                logger.info("alipayClient.pageExecute请求失败!" + response.getSubMsg());
                LeaseException.throwSystemException(LeaseExceEnums.ALIPAY_PAY_FAILUR);
            }
        } catch (AlipayApiException e) {
            logger.info("alipayClient.pageExecute请求失败!");
            e.printStackTrace();
            LeaseException.throwSystemException(LeaseExceEnums.ALIPAY_PAY_FAILUR);
        }

        return null;
    }

    /**
     * 支付回调
     */
    @Override
    public void alipayNotify(HttpServletRequest request, HttpServletResponse response) {
        logger.info("支付宝支付完成回调...");
        try {
            Map<String, String> params = new HashMap<String, String>();
            Map<String, String[]> requestParams = request.getParameterMap();
            for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
                String name = iter.next();
                String[] values = requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i]
                            : valueStr + values[i] + ",";
                }
                params.put(name, valueStr);
            }
            logger.info("parame = " + params);
            SysUserExt sysUserExt = verifySignature(params);
            if (Objects.nonNull(sysUserExt)) {
                //商户订单号
                String out_trade_no = request.getParameter("out_trade_no");
                //支付宝交易号
                String alipay_trade_no = request.getParameter("trade_no");
                logger.info("支付宝交易号：" + alipay_trade_no);
                //交易状态
                String trade_status = request.getParameter("trade_status");
                //appId
                String appId = request.getParameter("app_id");
                logger.info("交易状态：" + trade_status + ",appId:" + appId);
                //判断appId是否是商户appId
                if (!Objects.equals(appId, sysUserExt.getAlipayAppid())) {
                    response.getWriter().write("fail");
                    return;
                }
                //交易金额
                Double totalAmount = Double.parseDouble(request.getParameter("total_amount"));
                //收款支付宝账号对应的支付宝唯一用户号
                String sellerId = request.getParameter("seller_id");
                //支付时间
                String time = params.get("gmt_payment");

                if (alipayCallbackInnerLogic(sysUserExt, out_trade_no, totalAmount, trade_status, appId, alipay_trade_no, sellerId, time)) {
                    response.getWriter().write("success");
                } else {
                    response.getWriter().write("fail");
                }

            } else {
                logger.info("sysUserExt null");
                response.getWriter().write("fail");
                return;
            }
        } catch (Exception e) {
            logger.info("parame 解析报错!");
            e.printStackTrace();
        }
    }

    private SysUserExt verifySignature(Map<String, String> params) {
        String trade_no = params.get("out_trade_no");
        String app_id = params.get("app_id");
        TradeBase tradeBase = tradeBaseService.selectByTradeNo(trade_no);
        if (ParamUtil.isNullOrEmptyOrZero(tradeBase)) {
            logger.info("验签失败 tradeBase null , no = " + trade_no);
            return null;
        }
        SysUserExt sysUserExt = sysUserExtService.selectOne(new EntityWrapper<SysUserExt>()
                .eq("is_deleted", DeleteStatus.NOT_DELETED.getCode())
                .eq("alipay_appid", app_id));

        if (sysUserExt == null) {
            logger.info("验签失败 sysuserext null, appId = " + app_id);
            return null;
        }
        try {
            //由于微信验证的回调无法确定是哪个公众号,因此需要一个一个的循环验证,只要有一个验证通过就可以
            if (StringUtils.isNotBlank(sysUserExt.getAlipayAppid())
                    && StringUtils.isNotBlank(sysUserExt.getAlipayPublicKey())
                    && AlipaySignature.rsaCheckV1(params, sysUserExt.getAlipayPublicKey(),
                    SysConfigUtils.get(CommonSystemConfig.class).getAlipaySignCharset(), SysConfigUtils.get(CommonSystemConfig.class).getAlipaySignType())) {
                return sysUserExt;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LeaseException.throwSystemException(LeaseExceEnums.ALIPAY_NOTIFY_FAILUR);
            logger.error("===> 支付宝Token失败");
            return null;
        }
        return null;
    }

    /**
     * 回调内部逻辑处理
     */
    private boolean alipayCallbackInnerLogic(SysUserExt sysUserExt, String tradeNo, Double fee, String tradeStatus, String appId, String alipay_trade_no, String sellerId, String time) {
        logger.warn("===========>交易单:" + tradeNo + "回调内部逻辑处理");
        String notify_url = sysUserExt.getAlipayNotifyUrl();
        TradeAlipay tradeAlipay = selectById(tradeNo);
        TradeBase tradeBase = tradeBaseService.selectById(tradeNo);
        if (Objects.isNull(tradeAlipay) || Objects.isNull(tradeBase)) {
            logger.error("====>>>>> 交易单tradeNo[" + tradeNo + "]在系统中未找到");
            return false;
        }
        //如果交易单的状态为SUCCESS则不执行下面的操作
        if (TradeStatus.SUCCESS.getCode().equals(tradeBase.getStatus())) {
            logger.warn("===========>交易单:" + tradeBase.getTradeNo() + "已经交易成功，此次操作不做处理");
            return false;
        }
        tradeBase.setUtime(new Date());
        tradeBase.setNotifyUrl(notify_url);
        tradeBase.setNofifyTime(DateKit.formatString2DateByDateTimePattern(time));
        if (!tradeBaseService.updateTradeStatus(tradeBase, TradeStatus.SUCCESS.getCode())) {
            logger.error("===============>修改交易号:" + tradeNo + "的状态失败");
        }

        tradeAlipay.setAlipayId(alipay_trade_no);
        tradeAlipay.setAppid(appId);
        tradeAlipay.setSellerId(sellerId);
        tradeAlipay.setTradeStatus(tradeStatus);
        updateById(tradeAlipay);

        Integer orderType = LeaseUtil.getOrderType(tradeBase.getOrderNo());
        logger.info("处理订单类型 orderType ：" + orderType);
        //处理不同类型订单
        if (TradeOrderType.CONSUME.getCode().equals(orderType)) {
            if (!orderBaseService.checkAndUpdateConsumeOrder(tradeBase.getOrderNo(), fee)) {
                logger.info("消费订单处理失败 orderNo :[]", tradeBase.getOrderNo());
                return false;
            }
        } else if (TradeOrderType.CHARGE.getCode().equals(orderType)) {
            if (!userWalletChargeOrderService.checkAndUpdateChargeOrder(fee, tradeBase.getOrderNo(), PayType.WEIXINPAY.getCode(), tradeBase.getTradeNo())) {
                logger.info("充值订单处理失败 orderNo :[]", tradeBase.getOrderNo());
                return false;
            }
        }

        return true;
    }

    public boolean executePayStatusQuery(OrderBase orderBase) {
        if (orderBase.getPayType().equals(PayType.ALIPAY.getCode())) {
            TradeAlipay tradeAlipay = selectLastTrade(orderBase.getOrderNo());

            SysUserExt sysUserExt = deviceService.getWxConfigByDeviceId(orderBase.getSno());
            if (Objects.isNull(sysUserExt)) {
                LeaseException.throwSystemException(LeaseExceEnums.DEVICE_INUSING_FAIL);
            }

            //获得初始化的AlipayClient
            AlipayClient alipayClient = getAlipayClient(sysUserExt);

            //设置请求参数
            AlipayTradeQueryRequest alipayRequest = new AlipayTradeQueryRequest();

            //商户订单号，商户网站订单系统中唯一订单号
            alipayRequest.setBizContent("{\"out_trade_no\":\"" + tradeAlipay.getTradeNo() + "\"}");

            String result = "";
            try {
                result = alipayClient.execute(alipayRequest).getBody();
            } catch (AlipayApiException e) {
                e.printStackTrace();
                LeaseException.throwSystemException(LeaseExceEnums.ALIPAY_PAY_FAILUR);
            }

            if (ParamUtil.isNullOrEmptyOrZero(result)) {
                LeaseException.throwSystemException(LeaseExceEnums.ALIPAY_ORDER_QUERY_FAIL);
            }
            JSONObject jsonObject = JSON.parseObject(result);
            JSONObject body = jsonObject.getJSONObject("alipay_trade_query_response");
            if (body.containsKey("code") && !body.getString("code").equals("10000")) {
                LeaseException.throwSystemException(LeaseExceEnums.ALIPAY_ORDER_QUERY_FAIL);
            }
            if (body.containsKey("trade_status") && body.getString("trade_status").equals("TRADE_SUCCESS")) {
                return true;
            }
        }

        return false;
    }

    /**
     * 支付宝退款  此方法未经生产环境测试,如要使用请自测 Joey
     *
     * @param tradeNo 支付的时候对应的交易号
     * @return
     */
    @Override
    public AlipayTradeRefundResponse refund(OrderBase orderBase, String tradeNo) {
        if (ParamUtil.isNullOrEmptyOrZero(tradeNo)) {
            LeaseException.throwSystemException(LeaseExceEnums.TRADE_NO_LOSE);
        }
        TradeBase tradeBase = tradeBaseService.selectByTradeNo(tradeNo);
        if (Objects.isNull(tradeBase)) {
            LeaseException.throwSystemException(LeaseExceEnums.TRADE_BASE_NOT_EXIST);
        }
        SysUserExt sysUserExt = deviceService.getWxConfigByDeviceId(orderBase.getMac());
        if (Objects.isNull(sysUserExt)) {
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_INUSING_FAIL);
        }
        TradeAlipay tradeAlipay = selectById(tradeBase.getTradeNo());
        if (Objects.isNull(tradeAlipay)) {
            LeaseException.throwSystemException(LeaseExceEnums.TRADE_BASE_NOT_EXIST);
        }
        if (StringUtils.isEmpty(tradeAlipay.getAlipayId()) && StringUtils.isEmpty(tradeNo)) {
            logger.warn("====支付宝退款,原商户交易单号和支付宝交易号不能同时为空======");
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_REFUND_FAIL_WITH_INCOMPLATE_ALIPAY_INFORMATION);
        }

        return baseRefund(tradeAlipay.getAlipayId(), tradeNo, tradeBase.getTotalFee(), "正常退款", sysUserExt);
    }


    /**
     * 单笔转账到支付宝账户接口
     */
    @Override
    public void transfer(String orderNo, String subject, Double amount, String account, String realName, Integer sysUserId) {
        // 查找支付宝配置
        SysUser sysUser = sysUserService.selectById(sysUserId);
        String[] parentIds = (sysUser.getTreePath() + sysUser.getId()).split(",");
        SysUserExt alipayConfig = sysUserExtService.selectOne(new EntityWrapper<SysUserExt>().in("sys_user_id", parentIds)
                .isNotNull("alipay_appid").orderBy("sys_user_id", false).last("limit 1"));
        if (alipayConfig == null) {
            LeaseException.throwSystemException(LeaseExceEnums.ALIPAY_PARAM_IS_NULL);
        }
        //创建跟踪单
        TradeBase tradeBase = tradeBaseService.createTrade(orderNo, amount, alipayConfig.getAlipayNotifyUrl(), PayType.ALIPAY.getCode(), TradeOrderType.REFUND.getCode());
        TradeAlipay tradeAlipay = new TradeAlipay();
        tradeAlipay.setSubject(subject);
        tradeAlipay.setAppid(alipayConfig.getAlipayAppid());
        tradeAlipay.setTradeNo(tradeBase.getTradeNo());
        insert(tradeAlipay);

        // 调用打款接口

        //获得初始化的AlipayClient
        AlipayClient alipayClient = getAlipayClient(alipayConfig);
        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"out_biz_no\":\"").append(tradeBase.getTradeNo()).append("\",");
        sb.append("\"payee_type\":\"ALIPAY_LOGONID\",");
        sb.append("\"payee_account\":\"").append(account).append("\",");
        if (StringUtils.isNotBlank(realName)) {
            sb.append("\"payee_real_name\":\"").append(realName).append("\"");
        }
        sb.append("\"amount\":\"").append(amount).append("\"");
        sb.append("  }");
        request.setBizContent(sb.toString());
        try {
            AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
            logger.info("单笔转账到支付宝账户接口: Alipay response body: " + response.getBody());

            if (!response.isSuccess()) {
                logger.info("Alipay transfer is failure, reason code: " + response.getCode() + " reason :" + response.getSubMsg());
                LeaseException.throwSystemException(LeaseExceEnums.REFUND_FAIL, response.getSubMsg());
            }
        } catch (AlipayApiException e) {
            logger.error("Alipay transfer is failure!", e);
            LeaseException.throwSystemException(LeaseExceEnums.REFUND_FAIL, e.getMessage());
        }
    }

    @Override
    public void refund(OrderBase orderBase, TradeBase tradeBase) {
//        SysUserExt sysUserExt = deviceService.getWxConfigByDeviceId(orderBase.getSno());
//        if(Objects.isNull(sysUserExt)){
//            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_INUSING_FAIL);
//        }
//
//        //获得初始化的AlipayClient
//        AlipayClient alipayClient = getAlipayClient(sysUserExt);
//
//        //验证参数
//        TradeAlipay tradeAlipay = selectById(tradeBase.getTradeNo());
//        if(Objects.isNull(tradeAlipay)){
//            LeaseException.throwSystemException(LeaseExceEnums.TRADE_BASE_NOT_EXIST);
//        }
//
//        //设置请求参数
//        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
//
//        //支付宝交易号
//        String trade_no = tradeAlipay.getAlipayId();
//        //支付宝交易金额
//        String refund_amount = tradeBase.getTotalFee().toString();
//        request.setBizContent("{" +
//                "\"out_trade_no\":\"" + tradeBase.getTradeNo() + "\"," +
//                "\"trade_no\":\"" + trade_no + "\"," +
//                "\"refund_amount\":" + refund_amount + "," +
//                "\"refund_reason\":\"正常退款\"," +
//                "  }");
//
//        //请求
//        AlipayTradeRefundResponse response = null;
//        try {
//            response = alipayClient.execute(request);
//            logger.info("支付宝退款结果："+response.getBody());
//            if(!response.isSuccess())
//                LeaseException.throwSystemException(LeaseExceEnums.ORDER_REFUND_FAIL);
//        } catch (AlipayApiException e) {
//            logger.error("支付宝退款报错", e);
//            LeaseException.throwSystemException(LeaseExceEnums.ORDER_REFUND_FAIL);
//        }

        AlipayTradeRefundResponse response = refund(orderBase, tradeBase.getTradeNo());

    }

    private AlipayClient getAlipayClient(SysUserExt sysUserExt) {
        //获得初始化的AlipayClient
        CommonSystemConfig commonSystemConfig = SysConfigUtils.get(CommonSystemConfig.class);
        return new DefaultAlipayClient(commonSystemConfig.getAlipayGetwayUrl(),
                sysUserExt.getAlipayAppid(), sysUserExt.getAlipayPrivateKey(), "json",
                commonSystemConfig.getAlipaySignCharset(), sysUserExt.getAlipayPublicKey(), commonSystemConfig.getAlipaySignType());
    }


    /**
     * 基础退款操作
     *
     * @param tradeNo
     * @param outTradeNo
     * @param refundMoney
     * @param refundMsg
     * @param sysUserExt
     * @return
     */
    @Override
    public AlipayTradeRefundResponse baseRefund(String tradeNo, String outTradeNo, Double refundMoney, String refundMsg, SysUserExt sysUserExt) {
        if (StringUtils.isEmpty(tradeNo) && StringUtils.isEmpty(outTradeNo)) {
            logger.warn("====支付宝退款,原商户交易单号和支付宝交易号不能同时为空======");
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_REFUND_FAIL_WITH_INCOMPLATE_ALIPAY_INFORMATION);
        }

        if (Objects.isNull(refundMoney) || refundMoney.equals(0.00D)) {
            logger.warn("====退款金额为:{},不能执行退款操作===", refundMoney);
            return null;
        }
        if (Objects.isNull(sysUserExt) || Objects.isNull(sysUserExt.getAlipayAppid())
                || Objects.isNull(sysUserExt.getAlipayPrivateKey()) || Objects.isNull(sysUserExt.getAlipayPublicKey())) {
            logger.warn("====执行支付宝退款,参数配置SysUserExt为空===");
            return null;
        }

        AlipayClient alipayClient = new DefaultAlipayClient(SysConfigUtils.get(CommonSystemConfig.class).getAlipayGetwayUrl(),
                sysUserExt.getAlipayAppid(),
                sysUserExt.getAlipayPrivateKey(),
                "json",
                SysConfigUtils.get(CommonSystemConfig.class).getAlipaySignCharset(),
                sysUserExt.getAlipayPublicKey(),
                SysConfigUtils.get(CommonSystemConfig.class).getAlipaySignType());

        StringBuffer stringBuffer = new StringBuffer("{");
        if (!StringUtils.isEmpty(tradeNo)) {
            stringBuffer.append("\"trade_no\":\"").append(tradeNo).append("\",");
        }
        if (!StringUtils.isEmpty(outTradeNo)) {
            stringBuffer.append("\"out_trade_no\":\"").append(outTradeNo).append("\",");
        }
        stringBuffer.append("\"refund_amount\":").append(refundMoney).append(",");
        if (StringUtils.isEmpty(refundMsg)) {
            stringBuffer.append("\"refund_reason\":\"").append("正常退款").append("\"}");
        } else {
            stringBuffer.append("\"refund_reason\":\"").append(refundMsg).append("\"}");
        }
        logger.debug("====退款参数为:{}", stringBuffer.toString());
        //设置请求参数
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizContent(stringBuffer.toString());
        //请求
        AlipayTradeRefundResponse response = null;
        try {
            response = alipayClient.execute(request);
            logger.info("支付宝退款结果：" + response.getBody());
        } catch (AlipayApiException e) {
            logger.error("支付宝退款报错", e);
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_REFUND_FAIL, e.getMessage());
        }

        return response;
    }


    private void createTradeAlipay(TradeBase tradeBase, SysUserExt sysUserExt, String subject) {
        TradeAlipay tradeAlipay = new TradeAlipay();
        tradeAlipay.setSubject(subject);
        tradeAlipay.setAppid(sysUserExt.getAlipayAppid());
        tradeAlipay.setTradeNo(tradeBase.getTradeNo());
        insertOrUpdate(tradeAlipay);
    }
}
