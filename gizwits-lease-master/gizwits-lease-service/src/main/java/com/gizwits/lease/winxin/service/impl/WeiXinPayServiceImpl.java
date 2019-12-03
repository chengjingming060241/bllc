package com.gizwits.lease.winxin.service.impl;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import com.github.wxpay.sdk.WXPayUtil;
import com.gizwits.boot.sys.entity.SysUserExt;
import com.gizwits.boot.utils.*;
import com.gizwits.lease.benefit.entity.ShareBenefitSheet;
import com.gizwits.lease.constant.*;
import com.gizwits.lease.event.ShareBenefitPayRecordEvent;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.util.WxUtil;
import com.gizwits.lease.utils.http.HttpClient;
import com.gizwits.lease.wallet.entity.UserWalletChargeOrder;
import com.gizwits.lease.wallet.service.UserWalletChargeOrderService;
import com.gizwits.lease.winxin.service.WeixinPayService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WeiXinPayServiceImpl implements WeixinPayService {

    private static Logger logger = LoggerFactory.getLogger("WeiXinPayServiceImpl");

    private static String WXTransferURL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

    @Autowired
    private UserWalletChargeOrderService userWalletChargeOrderService;


    @Value("#{cronConfig.getAppid()}")
    private String appid;

    @Value("#{cronConfig.getPartner()}")
    private String partner;

    @Value("#{cronConfig.getPartnerkey()}")
    private String partnerkey;

    @Value("#{cronConfig.getPath()}")
    private String path;

    @Override
    public Map createNative(String out_trade_no, String total_fee) {

        //1.参数封装
        Map param=new HashMap();
        param.put("appid", appid);//公众账号ID
        param.put("mch_id", partner);//商户
        param.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
        param.put("body", "卡券代充");
        param.put("out_trade_no", out_trade_no);//交易订单号
        param.put("total_fee",total_fee );//金额（分）
        param.put("spbill_create_ip", "127.0.0.1");
        param.put("notify_url", "http://www.d-health.cn/");
        param.put("trade_type", "NATIVE");//交易类型

        try {
            String xmlParam = WXPayUtil.generateSignedXml(param, partnerkey);
            System.out.println("请求的参数："+xmlParam);

            //2.发送请求
            HttpClient httpClient=new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            httpClient.setHttps(true);
            httpClient.setXmlParam(xmlParam);
            httpClient.post();

            //3.获取结果
            String xmlResult = httpClient.getContent();

            Map<String, String> mapResult = WXPayUtil.xmlToMap(xmlResult);
            System.out.println("微信返回结果"+mapResult);
            Map map=new HashMap<>();
            map.put("code_url", mapResult.get("code_url"));//生成支付二维码的链接
            map.put("out_trade_no", out_trade_no);
            map.put("total_fee", total_fee);
            return map;

        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap();
        }

    }

    @Override
    public Map queryPayStatus(String out_trade_no) {
        //1.封装参数
        Map param=new HashMap();
        param.put("appid", appid);
        param.put("mch_id", partner);
        param.put("out_trade_no", out_trade_no);
        param.put("nonce_str", WXPayUtil.generateNonceStr());
        try {
            String xmlParam = WXPayUtil.generateSignedXml(param, partnerkey);
            //2.发送请求
            HttpClient httpClient=new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            httpClient.setHttps(true);
            httpClient.setXmlParam(xmlParam);
            httpClient.post();

            //3.获取结果
            String xmlResult = httpClient.getContent();
            Map<String, String> map = WXPayUtil.xmlToMap(xmlResult);
            System.out.println("调动查询API返回结果："+xmlResult);

            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean scanningRefund(String out_trade_no) {
        logger.info("====>>> 代充订单:{},开始执行退款", out_trade_no);
        boolean flag = false;
        UserWalletChargeOrder chargeOrder = userWalletChargeOrderService.selectById(out_trade_no);
        if (Objects.isNull(chargeOrder)) {
            LeaseException.throwSystemException(LeaseExceEnums.CHARGE_ORDER_NOT_EXIST);
        }

        try {
            String result = executeRefund(chargeOrder);
            Map<String, String> resultMap = WxUtil.parseXmlToMap(result);
            logger.info("====>>> 代充订单:{},执行退款结果:{}", chargeOrder.getChargeOrderNo(), resultMap.get("err_code_des").toString());

            if ("SUCCESS".equalsIgnoreCase(resultMap.get("result_code").toString())) {
                userWalletChargeOrderService.updateChargeOrderStatus(chargeOrder, UserWalletChargeOrderType.Refund_Successful.getCode());
                flag = true;
            } else {
                userWalletChargeOrderService.updateChargeOrderStatus(chargeOrder,UserWalletChargeOrderType.Refund_Failed.getCode());
                return flag;
            }
        } catch (Exception ex) {
            logger.info("====>>> 代充订单:{},开始执行退款失败", out_trade_no);
            ex.printStackTrace();
        }
        return flag;
    }


    private String executeRefund(UserWalletChargeOrder chargeOrder) throws Exception {
        Integer totalFee = (int) Math.round((chargeOrder.getFee() * 100));
        Integer refundFee = totalFee;
        String outRefundNo = chargeOrder.getChargeOrderNo() + "00";
        String transaction_id = chargeOrder.getTransactionId();
        String nonce_str = System.currentTimeMillis() + "";

        SortedMap<String, String> packageParams = new TreeMap<>();
        packageParams.put("appid", appid);//公众账号ID
        packageParams.put("mch_id", partner);//商户
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("out_refund_no", outRefundNo);
        packageParams.put("transaction_id", transaction_id);

        packageParams.put("total_fee", totalFee.toString());
        packageParams.put("refund_fee", refundFee.toString());

        //将要提交给API的数据对象转换成XML格式数据Post给API
         //默认采用未结算资金退款
        String postDataXML = WXPayUtil.generateSignedXml(packageParams, partnerkey);
        System.out.println("executeRefund请求的参数："+postDataXML);
        File file = new File(this.getClass().getClassLoader().getResource(path).getFile());
        String wxCertPath = file.getPath();

        if (StringUtils.isBlank(wxCertPath)) {
            chargeOrder.setStatus(UserWalletChargeOrderType.CHARGE_FAIL.getCode());
            LeaseException.throwSystemException(LeaseExceEnums.CERT_LOSE);
        }
        String result = HttpUtil.executeBySslPost("https://api.mch.weixin.qq.com/secapi/pay/refund"
                , postDataXML
                , wxCertPath, partner.trim());

        if (ParamUtil.isNullOrEmptyOrZero(result)) {
            // 如果未结算资金退款失败，采用可用余额进行退款
            String refundAccount = "REFUND_SOURCE_RECHARGE_FUNDS";
            packageParams.put("refundAccount",refundAccount);
            String postDataXMLs = WXPayUtil.generateSignedXml(packageParams, partnerkey);

            result = HttpUtil.executeBySslPost("https://api.mch.weixin.qq.com/secapi/pay/refund"
                    , postDataXMLs
                    , wxCertPath, partner.trim());
            //失败
            if (ParamUtil.isNullOrEmptyOrZero(result)) {

                LeaseException.throwSystemException(LeaseExceEnums.ORDER_REFUND_FAIL_WITH_RESULT_ID_EMPTY);
            }
        }
        return result;
    }

    /**
     * 分润
     *
     * @param shareBenefitSheet
     * @param ip
     * @return
     * @throws IOException
     */
    public String postShareBenefitOrder(ShareBenefitSheet shareBenefitSheet, String ip, SysUserExt sysUserExt, Integer actionUserId) throws Exception {
        Integer totalFee = new BigDecimal(shareBenefitSheet.getShareMoney()).multiply(new BigDecimal(100)).intValue();
        String nonce_str = System.currentTimeMillis() + "";

        SortedMap<String, String> packageParams = new TreeMap<>();
        packageParams.put("mch_appid", appid);
        packageParams.put("mchid", partner);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("partner_trade_no", shareBenefitSheet.getTradeNo());
        packageParams.put("openid", shareBenefitSheet.getReceiverOpenid());
        packageParams.put("re_user_name", shareBenefitSheet.getReceiverName());
        packageParams.put("check_name", "FORCE_CHECK");
        packageParams.put("amount", totalFee.toString());
        packageParams.put("desc", "分润");
        packageParams.put("spbill_create_ip", ip);

        //将要提交给API的数据对象转换成XML格式数据Post给API
        String postDataXML = WXPayUtil.generateSignedXml(packageParams, partnerkey);
        System.out.println("executeRefund请求的参数："+postDataXML);

        File file = new File(this.getClass().getClassLoader().getResource(path).getFile());
        String wxCertPath = file.getPath();

        //分润记录
        CommonEventPublisherUtils.publishEvent(new ShareBenefitPayRecordEvent("ExecutingShare", shareBenefitSheet, postDataXML, actionUserId));
        String resultStr = HttpUtil.httpPostWithCert(WXTransferURL, postDataXML, wxCertPath, partner.trim());
        CommonEventPublisherUtils.publishEvent(new ShareBenefitPayRecordEvent("ExecutedShare", shareBenefitSheet, resultStr, actionUserId));

        return resultStr;
    }
}

