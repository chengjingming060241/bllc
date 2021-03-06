//package com.gizwits.lease.user.service.impl;
//
//import com.alipay.api.AlipayApiException;
//import com.alipay.api.AlipayClient;
//import com.alipay.api.AlipayConstants;
//import com.alipay.api.DefaultAlipayClient;
//import com.alipay.api.internal.util.AlipaySignature;
//import com.alipay.api.request.AlipayFundTransOrderQueryRequest;
//import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
//import com.alipay.api.request.AlipaySystemOauthTokenRequest;
//import com.alipay.api.response.*;
//import com.baomidou.mybatisplus.mapper.EntityWrapper;
//import com.gizwits.boot.sys.entity.SysUserExt;
//import com.gizwits.boot.sys.service.SysUserExtService;
//import com.gizwits.boot.utils.ParamUtil;
//import com.gizwits.boot.utils.SysConfigUtils;
//import com.gizwits.lease.alipay.AlipayUserinfoShareRequest;
//import com.gizwits.lease.benefit.entity.OrderShareProfit;
//import com.gizwits.lease.benefit.entity.ShareBenefitSheet;
//
//
//import com.gizwits.lease.config.CommonSystemConfig;
//import com.gizwits.lease.constant.OrderStatus;
//import com.gizwits.lease.constant.PayType;
//import com.gizwits.lease.constant.ThirdPartyUserType;
//import com.gizwits.lease.device.service.DeviceService;
//import com.gizwits.lease.enums.ShareBenefitSheetStatusType;
//import com.gizwits.lease.enums.SheetOrderStatusType;
//import com.gizwits.lease.exceptions.LeaseExceEnums;
//import com.gizwits.lease.exceptions.LeaseException;
//import com.gizwits.lease.order.dto.AlipayOrderDto;
//import com.gizwits.lease.order.dto.PrePayDto;
//import com.gizwits.lease.order.entity.OrderBase;
//import com.gizwits.lease.order.service.OrderBaseService;
//import com.gizwits.lease.redis.RedisService;
//import com.gizwits.lease.trade.service.TradeAlipayService;
//import com.gizwits.lease.user.entity.User;
//import com.gizwits.lease.user.service.UserAlipayService;
//import com.gizwits.lease.user.service.UserService;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.PrintWriter;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * Created by yinhui on 2017/8/15.
// */
//@Service
//public class UserAlipayServiceImpl implements UserAlipayService {
//    private static Logger logger = LoggerFactory.getLogger("PAY_LOGGER");
//    private static Logger loggerOrder = LoggerFactory.getLogger("ORDER_LOGGER");
//    private final static String ALIPAYGATEWAYURL = "https://openapi.alipay.com/gateway.do";
//
//    @Autowired
//    private OrderBaseService orderBaseService;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private DeviceService deviceService;
//
//    @Autowired
//    private TradeAlipayService tradeAlipayService;
//
//    @Autowired
//    private SysUserExtService sysUserExtService;
//
//    @Autowired
//    private RedisService redisService;
//
//
//
//
//    public void verifyAlipay(HttpServletResponse response, HttpServletRequest request) {
//        Map<String, String> params = new HashMap<String, String>();
//        String responseMsg = "";
//        if (null != request) {
//            Set<String> paramsKey = request.getParameterMap().keySet();
//            for (String key : paramsKey) {
//                params.put(key, request.getParameter(key));
//            }
//        }
//        try {
//            //2. 验证签名
//            SysUserExt currentUseExt = verifySignature(params);
//            //SysUserExt currentUseExt = sysUserExtService.selectById(58);
//
//            System.out.println(currentUseExt.getAlipayAppid());
//
//            if (Objects.isNull(currentUseExt)) {
//                return;
//            }
//
//            StringBuilder builder = new StringBuilder();
//            builder.append("<success>").append(Boolean.TRUE.toString()).append("</success>");
//            builder.append("<biz_content>").append(currentUseExt.getAlipaySelfPublicKey())
//                    .append("</biz_content>");
//            responseMsg = builder.toString();
//
//            System.out.println(responseMsg);
//
//            responseMsg = encryptAndSign(responseMsg,
//                    currentUseExt.getAlipayPublicKey(),
//                    currentUseExt.getAlipayPrivateKey(), SysConfigUtils.get(CommonSystemConfig.class).getAlipaySignCharset(),
//                    false, true, SysConfigUtils.get(CommonSystemConfig.class).getAlipaySignType());
//            System.out.println(responseMsg);
//            response.reset();
//            response.setContentType("text/xml;charset=GBK");
//            PrintWriter printWriter = response.getWriter();
//            printWriter.print(responseMsg);
//            response.flushBuffer();
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    private SysUserExt verifySignature(Map<String, String> params) {
//        List<SysUserExt> list = sysUserExtService.selectList(new EntityWrapper<>());
//        if (list == null || list.size() <= 0) {
//            return null;
//        }
//        try {
//            //由于微信验证的回调无法确定是哪个公众号,因此需要一个一个的循环验证,只要有一个验证通过就可以
//            for (SysUserExt sysUserExt : list) {
//                if (StringUtils.isNotBlank(sysUserExt.getAlipayAppid())
//                        && StringUtils.isNotBlank(sysUserExt.getAlipayPublicKey())
//                        && AlipaySignature.rsaCheckV2(params, sysUserExt.getAlipayPublicKey(),
//                        SysConfigUtils.get(CommonSystemConfig.class).getAlipaySignCharset(), SysConfigUtils.get(CommonSystemConfig.class).getAlipaySignType())) {
//                    return sysUserExt;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("===> 支付宝Token失败");
//            return null;
//        }
//        return null;
//    }
//
//
//    public static String encryptAndSign(String bizContent, String alipayPublicKey, String cusPrivateKey, String charset,
//                                        boolean isEncrypt, boolean isSign, String signType) throws AlipayApiException {
//        StringBuilder sb = new StringBuilder();
//        if (com.alipay.api.internal.util.StringUtils.isEmpty(charset)) {
//            charset = AlipayConstants.CHARSET_GBK;
//        }
//        sb.append("<?xml version=\"1.0\" encoding=\"" + charset + "\"?>");
//        System.out.println("=======" + isEncrypt);
//        if (isEncrypt) {// 加密
//            sb.append("<alipay>");
//            String encrypted = AlipaySignature.rsaEncrypt(bizContent, alipayPublicKey, charset);
//            sb.append("<response>" + encrypted + "</response>");
//            sb.append("<encryption_type>AES</encryption_type>");
//            if (isSign) {
//                String sign = AlipaySignature.rsaSign(encrypted, cusPrivateKey, charset, signType);
//                sb.append("<sign>" + sign + "</sign>");
//                sb.append("<sign_type>");
//                sb.append(signType);
//                sb.append("</sign_type>");
//            }
//            sb.append("</alipay>");
//        } else if (isSign) {// 不加密，但需要签名
//
//            sb.append("<alipay>");
//            sb.append("<response>" + bizContent + "</response>");
//            String sign = AlipaySignature.rsaSign(bizContent, cusPrivateKey, charset, signType);
//            sb.append("<sign>" + sign + "</sign>");
//            sb.append("<sign_type>");
//            sb.append(signType);
//            sb.append("</sign_type>");
//            sb.append("</alipay>");
//        } else {// 不加密，不加签
//            sb.append(bizContent);
//        }
//        System.out.println("=======" + sb.toString());
//        return sb.toString();
//    }
//
//
//    public boolean queryOrderPayStatus(String orderNo) {
//        if (ParamUtil.isNullOrEmptyOrZero(orderNo)) {
//            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
//        }
//        OrderBase orderBase = orderBaseService.getOrderBaseByOrderNo(orderNo);
//        if (orderBase == null) {
//            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
//        }
//        if (!orderBase.getOrderStatus().equals(OrderStatus.PAYING.getCode())) {
//            LeaseException.throwSystemException(LeaseExceEnums.ORDER_STATUS_ERROR);
//        }
//        /*//检查用户是否有使用中的订单
//        if(orderBaseService.getUsingOrderByUserIdentify(orderBase.getUserId()+"")!=null){
//            LeaseException.throwSystemException(LeaseExceEnums.ORDER_IN_USING_REPEAT);
//        }*/
//
//        SysUserExt sysUserExt = deviceService.getWxConfigByDeviceId(orderBase.getSno());
//        if (sysUserExt == null) {
//            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
//        }
//        //订单已支付,修改订单状态为已支付
//        if (tradeAlipayService.executePayStatusQuery(orderBase)) {
//            orderBaseService.updateOrderStatusAndHandle(orderBase, OrderStatus.PAYED.getCode());
//            return true;
//            //订单未支付,修改为失败状态
//        } else {
//            orderBase.setOrderStatus(OrderStatus.FAIL.getCode());
//            orderBase.setUtime(new Date());
//            orderBaseService.updateById(orderBase);
//            return false;
//        }
//    }
//
//    @Override
//    public String signByAlipaysOrder(AlipayOrderDto alipayOrderDto) {
//        if (ParamUtil.isNullOrEmptyOrZero(alipayOrderDto.getOrderNo())) {
//            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
//        }
//        //调用预支付，生成追踪单，改变订单状态
//        PrePayDto prePayDto = new PrePayDto();
//        prePayDto.setOrderNo(alipayOrderDto.getOrderNo());
//        prePayDto.setPayType(PayType.ALIPAY.getCode());
//        return tradeAlipayService.prePay(prePayDto, ThirdPartyUserType.APP.getCode());
//    }
//
//    /**
//     * 支付宝分润(单笔转账到支付宝账户)
//     *
//     * @param orderShareProfit
//     * @param sysUserExt
//     * @return
//     */
//    @Override
//    public AlipayFundTransToaccountTransferResponse shareBenefitSheetByAlipay(OrderShareProfit orderShareProfit, SysUserExt sysUserExt) {
//        //查找厂商信息
//        SysUserExt manufacturer = sysUserExtService.getLatestSysUserExt();
//        if (ParamUtil.isNullOrEmptyOrZero(manufacturer)){
//            logger.warn("========支付宝参数配置厂商SysUserExt配置异常");
//            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
//        }
//        AlipayFundTransToaccountTransferResponse response = transforToAccount("LOGINID", sysUserExt.getAlipayAccount(), sysUserExt.getAlipayAccountName(), orderShareProfit.getTradeNo(), orderShareProfit.getShareMoney().doubleValue(), "分润", manufacturer);
//        return response;
//    }
//
//
//
//    /**
//     * 单笔转账到个人
//     *
//     * @param payeeType
//     * @param payeeAccount
//     * @param accountRealname
//     * @param tradeNo
//     * @param money
//     * @param message
//     * @param sysUserExt
//     * @return
//     */
//    @Override
//    public AlipayFundTransToaccountTransferResponse transforToAccount(String payeeType, String payeeAccount, String accountRealname, String tradeNo, Double money, String message, SysUserExt sysUserExt) {
//        if (StringUtils.isEmpty(payeeAccount) || StringUtils.isEmpty(payeeType) || StringUtils.isEmpty(tradeNo) || Objects.isNull(money) || Objects.isNull(sysUserExt)) {
//            logger.warn("===执行单笔转账到个人方法参数异常:payeeType:{},payeeAccount:{},tradeNo:{},money:{},sysUserExt:{}===", payeeType, payeeAccount, tradeNo, money, sysUserExt.toString());
//            return null;
//        }
//        if (Objects.isNull(sysUserExt) || Objects.isNull(sysUserExt.getAlipayAppid())
//                || Objects.isNull(sysUserExt.getAlipayPrivateKey()) || Objects.isNull(sysUserExt.getAlipayPublicKey())) {
//            logger.warn("====支付宝参数配置SysUserExt配置异常:{}===", sysUserExt.toString());
//            return null;
//        }
//        AlipayClient alipayClient = new DefaultAlipayClient(SysConfigUtils.get(CommonSystemConfig.class).getAlipayGetwayUrl(),
//                sysUserExt.getAlipayAppid(),
//                sysUserExt.getAlipayPrivateKey(),
//                "json",
//                SysConfigUtils.get(CommonSystemConfig.class).getAlipaySignCharset(),
//                sysUserExt.getAlipayPublicKey(),
//                SysConfigUtils.get(CommonSystemConfig.class).getAlipaySignType());
//        StringBuffer stringBuffer = new StringBuffer("{");
//        stringBuffer.append("\"out_biz_no\":\"").append(tradeNo).append("\",");
//        if ("USERID".equalsIgnoreCase(payeeType)) {
//            stringBuffer.append("\"payee_type\":\"").append("ALIPAY_USERID").append("\",");
//        } else if ("LOGINID".equalsIgnoreCase(payeeType)) {
//            stringBuffer.append("\"payee_type\":\"").append("ALIPAY_LOGONID").append("\",");
//        } else {
//            logger.warn("====执行单笔转账到个人参数payeeType的值只能是USERID和LOGINID,传递参数payeeType:{}===", payeeType);
//            return null;
//        }
//        stringBuffer.append("\"payee_account\":\"").append(payeeAccount).append("\",");
//        if (!StringUtils.isEmpty(accountRealname)) {
//            stringBuffer.append("\"payee_real_name\":\"").append(accountRealname).append("\",");
//        }
//        stringBuffer.append("\"amount\":").append(money).append(",");
//        stringBuffer.append("\"remak\":\"").append("单笔到个人_").append(message).append("\"}");
//
//        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
//        request.setBizContent(stringBuffer.toString());
//        //请求
//        AlipayFundTransToaccountTransferResponse response = null;
//        try {
//            response = alipayClient.execute(request);
//        } catch (AlipayApiException e) {
//            LeaseException.throwSystemException(LeaseExceEnums.ORDER_REFUND_FAIL);
//        }
//        logger.info("支付宝单笔转账到个人：\n" + "parames = \n" + stringBuffer.toString() + "\nresponse = \n" + response.toString());
//        return response;
//    }
//
//    /**
//     * 查询转账订单接口
//     *
//     * @param shareBenefitSheet
//     * @param sysUserExt
//     * @return
//     */
//    @Override
//    public String queryShareBenefitSheetStatus(ShareBenefitSheet shareBenefitSheet, SysUserExt sysUserExt) {
//        AlipayClient alipayClient = new DefaultAlipayClient(ALIPAYGATEWAYURL, sysUserExt.getAlipayAppid(), sysUserExt.getAlipayPrivateKey(), "json", "utf-8", sysUserExt.getAlipayPublicKey(), "RSA2");
//        AlipayFundTransOrderQueryRequest request = new AlipayFundTransOrderQueryRequest();
//        request.setBizContent("{" +
//                "\"out_biz_no\":" + shareBenefitSheet.getTradeNo() + "\"," +
//                "\"order_id\":" + shareBenefitSheet.getPaymentNo() + "\"" +
//                "  }");
//        try {
//            AlipayFundTransOrderQueryResponse response = alipayClient.execute(request);
//            if (response.isSuccess()) {
//                return response.getBody();
//            } else {
//                return response.getBody();
//            }
//        } catch (AlipayApiException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    /**
//     * 用户授权后获取用户信息并返回相应参数到cookie中
//     *
//     * @param deviceId
//     * @param authCode
//     * @param response
//     */
//    public void getAndSaveUserinfo(String deviceId, String authCode, HttpServletResponse response) {
//        try {
//
//            SysUserExt sysUserExt = deviceService.getWxConfigByDeviceId(deviceId);
//            if (Objects.isNull(sysUserExt)) {
//                LeaseException.throwSystemException(LeaseExceEnums.DEVICE_CONTROL_FAIL);
//                return;
//            }
//
//
//            AlipayClient alipayClient = new DefaultAlipayClient(SysConfigUtils.get(CommonSystemConfig.class).getAlipayGetwayUrl(),
//                    sysUserExt.getAlipayAppid(), sysUserExt.getAlipayPrivateKey(), "json",
//                    SysConfigUtils.get(CommonSystemConfig.class).getAlipaySignCharset(), sysUserExt.getAlipayPublicKey(), SysConfigUtils.get(CommonSystemConfig.class).getAlipaySignType());
//            AlipaySystemOauthTokenRequest oauthTokenRequest = new AlipaySystemOauthTokenRequest();
//            oauthTokenRequest.setCode(authCode);
//            oauthTokenRequest.setGrantType(SysConfigUtils.get(CommonSystemConfig.class).getAlipayGrantType());
//            AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(oauthTokenRequest);
//            User user = null;
//            //成功获得authToken
//            if (null != oauthTokenResponse && oauthTokenResponse.isSuccess()) {
//                //4. 利用authToken获取用户信息
//                AlipayUserinfoShareRequest userinfoShareRequest = new AlipayUserinfoShareRequest();
//                AlipayUserUserinfoShareResponse userinfoShareResponse = alipayClient.execute(
//                        userinfoShareRequest, oauthTokenResponse.getAccessToken());
//
//                //成功获得用户信息
//                if (null != userinfoShareResponse && userinfoShareResponse.isSuccess()) {
//                    //这里仅是简单打印， 请开发者按实际情况自行进行处理
//                    loggerOrder.info("获取用户信息成功：" + userinfoShareResponse.getBody());
//                    user = userService.addUserByAlipay(userinfoShareResponse, sysUserExt);
//                } else {
//                    //这里仅是简单打印， 请开发者按实际情况自行进行处理
//                    loggerOrder.info("获取用户信息失败");
//                }
//            } else {
//                //这里仅是简单打印， 请开发者按实际情况自行进行处理
//                loggerOrder.info("authCode换取authToken失败");
//            }
//
//            Cookie deviceIdCookie = new Cookie("sno", deviceId);
//            deviceIdCookie.setPath("/");
//            response.addCookie(deviceIdCookie);
//            Cookie bindCookie = new Cookie("isBind", "0");
//            bindCookie.setPath("/");
//            response.addCookie(bindCookie);
//            Cookie _mobileCookie = new Cookie("mobile", "");//用户手机号
//            _mobileCookie.setPath("/");
//            response.addCookie(_mobileCookie);
//
//            if (user != null) {
//                Cookie openid = new Cookie("openid", user.getAlipayUnionid());
//                openid.setPath("/");
//                response.addCookie(openid);
//                //将token放入缓存
//                String accessToken = UUID.randomUUID().toString();
//                redisService.cacheAppUser(accessToken, user);
//                Cookie cookie9 = new Cookie("accessToken", accessToken);
//                cookie9.setPath("/");
//                response.addCookie(cookie9);
//                Cookie blackCookie = new Cookie("isBlack", user.getStatus()+"");//覆盖黑名单
//                blackCookie.setPath("/");
//                response.addCookie(blackCookie);
//            }
//
////            if (user != null && LeaseExceEnums.USER_IN_BLACK.getCode().equals(user.getStatus())) {
////                Cookie blackCookie = new Cookie("isBlack", "2");//覆盖黑名单
////                blackCookie.setPath("/");
////                response.addCookie(blackCookie);
////            } else {
////                Cookie blackCookie = new Cookie("isBlack", "1");//覆盖黑名单
////                blackCookie.setPath("/");
////                response.addCookie(blackCookie);
////            }
//            if (user != null && !ParamUtil.isNullOrEmptyOrZero(user.getMobile())) {
//                Cookie mobileCookie = new Cookie("mobile", user.getMobile());//用户手机号
//                mobileCookie.setPath("/");
//                response.addCookie(mobileCookie);
//                bindCookie = new Cookie("isBind", "1"); //是否绑定
//                bindCookie.setPath("/");
//                response.addCookie(bindCookie);
//            }
//
//            if (deviceService.checkDeviceIsInOperator(deviceId)) {
//                Cookie operatorCookie = new Cookie("isOperator", "0");//设备是否投入运营
//                operatorCookie.setPath("/");
//                response.addCookie(operatorCookie);
//            } else {
//                Cookie operatorCookie = new Cookie("isOperator", "1");//设备是否投入运营
//                operatorCookie.setPath("/");
//                response.addCookie(operatorCookie);
//            }
//
//            response.sendRedirect("/");
//            response.getWriter().flush();
//            response.getWriter().close();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//}
