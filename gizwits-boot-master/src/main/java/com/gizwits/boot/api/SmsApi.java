package com.gizwits.boot.api;

import ch.qos.logback.core.net.SyslogOutputStream;

import com.gizwits.boot.common.MessageCodeConfig;
import com.gizwits.boot.exceptions.SysExceptionEnum;
import com.gizwits.boot.exceptions.SystemException;
import com.gizwits.boot.utils.JsonUtil;
import com.gizwits.boot.utils.SysConfigUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.boot.context.config.RandomValuePropertySource;

import java.io.IOException;

import java.net.URLEncoder;
import java.util.*;

/**
 * 短信验证
 * Created by Chloe on 2017/7/5.
 */
public class SmsApi {

    //模板发送接口的http地址
    private static String URI_TPL_SEND_SMS = "https://sms.yunpian.com/v2/sms/tpl_single_send.json";

    private static String URI_SEND_SMS = "https://sms.yunpian.com/v1/sms/send.json";

    //编码格式。发送编码格式统一用UTF-8
    private static String ENCODING = "UTF-8";

//    private static String apikey = "f2be677d325f630408a559f5c267ee6c";

    /**
     * 智能匹配模版接口
     * @param apikey
     * @param text
     * @param mobile
     * @param map
     * @return
     */
    public static String sendSms(String apikey, String text, String mobile, Map<String, String> map) {
        Map<String, String> params = new HashMap<String, String>();
        String value = RandomStringUtils.random(4, false, true);
        map.put("code", value);
        Iterator<String> iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            text = text.replaceAll("#"+key+"#",map.get(key));
        }
//       text = text.replaceAll("#code#",map.get("code"));
        params.put("apikey", apikey);
        params.put("text", text);
        params.put("mobile", mobile);
        String result = post(URI_SEND_SMS, params);
        JsonObject jsonObject = JsonUtil.getJsonObject(result);
        String code = JsonUtil.getString(jsonObject, "code");
        String detail = JsonUtil.getString(jsonObject,"detail");
        if (!Objects.equals(code, "0")) {
            throw new SystemException(code, detail);

        }
        return value;
    }

    /**
     * 享智云
     * 短线验证码模版
     */
    public static String getAplValue(String tplValue, Map<String, String> paramMap) {
        try {
            tplValue = "";
            Iterator<String> iterator = paramMap.keySet().iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                tplValue += URLEncoder.encode("#" + key + "#", ENCODING) + "="
                        + URLEncoder.encode(paramMap.get(key), ENCODING) + "&";
            }
        } catch (Exception e) {
            throw new SystemException(SysExceptionEnum.ILLEGAL_PARAM.getCode(), SysExceptionEnum.ILLEGAL_PARAM.getMessage());
        }
        if (StringUtils.isNotBlank(tplValue)) {
            return tplValue.substring(0, tplValue.length() - 1);
        }

        return tplValue;
    }

    /**
     * 机智云验证码模版
     */
    public static String getAplValue(String code) {
//         String tpl_value="【机智云】您的验证码是#code#。如非本人操作，请忽略本短信";
        String tplValue = SysConfigUtils.get(MessageCodeConfig.class).getTemplateValue();
        try {
            tplValue = URLEncoder.encode("#code#", ENCODING) + "="
                    + URLEncoder.encode(code, ENCODING);
        } catch (Exception e) {
            throw new SystemException(SysExceptionEnum.ILLEGAL_PARAM.getCode(), SysExceptionEnum.ILLEGAL_PARAM.getMessage());
        }
        return tplValue;
    }

    /**
     * 通过模板发送短信
     *
     * @param apiKey   apikey
     * @param tplId    　模板id
     * @param tplValue 　模板变量值
     * @param mobile   　接受的手机号
     * @return json格式字符串
     */

    public static String tplSendSms(String apiKey, String tplId, String mobile, String tplValue, Map<String, String> map) {
        Map<String, String> params = new HashMap<String, String>();
        String value = RandomStringUtils.random(4, false, true);
        map.put("code", value);
        String tpl_value = getAplValue(tplValue, map);
        params.put("apikey", apiKey);
        params.put("tpl_id", tplId);
        params.put("tpl_value", tpl_value);
        params.put("mobile", mobile);
        String result = post(URI_TPL_SEND_SMS, params);
        JsonObject jsonObject = JsonUtil.getJsonObject(result);
        String code = JsonUtil.getString(jsonObject, "code");
        String detail = JsonUtil.getString(jsonObject,"detail");
        if (!Objects.equals(code, "0")) {
            throw new SystemException(code, detail);

        }
        return value;
    }

    /**
     * 机智云发送短信验证码
     */
    public static String tplSendSms(String mobile) {
//        long tpl_id = 1860414;
        String tplId = SysConfigUtils.get(MessageCodeConfig.class).getTemplateId();
        String apiKey = SysConfigUtils.get(MessageCodeConfig.class).getApiKey();
        Map<String, String> params = new HashMap<String, String>();
        String value = RandomStringUtils.random(4, false, true);
        String tpl_value = getAplValue(value);
        params.put("apikey", apiKey);
        params.put("tpl_id", tplId);
        params.put("tpl_value", tpl_value);
        params.put("mobile", mobile);
        String result = post(URI_TPL_SEND_SMS, params);
        JsonObject jsonObject = JsonUtil.getJsonObject(result);
        String code = JsonUtil.getString(jsonObject, "code");
        String detail = JsonUtil.getString(jsonObject,"detail");
        if (!Objects.equals(code, "0")) {
            throw new SystemException(code, detail);

        }
        return value;
    }


    /**
     * 发送故障短信给负责人
     */
    public static String sendAlarmMessage(String sno, String name, String date, String mobile, String tplId, String tplValue, String apikey) {
        Map<String, String> params = new HashMap<String, String>();
        String tpl_value = getAlarmAplValue(tplValue, sno, name, date);
        params.put("apikey", apikey);
        params.put("tpl_id", tplId);
        params.put("tpl_value", tpl_value);
        params.put("mobile", mobile);
        return post(URI_TPL_SEND_SMS, params);
    }

    /**
     * 机智云发送故障短信给负责人
     */
    public static String sendAlarmMessage(String sno, String name, String date, String mobile) {
        Map<String, String> params = new HashMap<String, String>();
//        long tpl_id = 1877844;
        String tplId = SysConfigUtils.get(MessageCodeConfig.class).getTemplateId();
        String apiKey = SysConfigUtils.get(MessageCodeConfig.class).getApiKey();
        String tpl_value = getAlarmAplValue(sno, name, date);
        params.put("apikey", apiKey);
        params.put("tpl_id", tplId);
        params.put("tpl_value", tpl_value);
        params.put("mobile", mobile);
        return post(URI_TPL_SEND_SMS, params);
    }


    /**
     * 故障消息模版
     */
    public static String getAlarmAplValue(String tplValue, String sno, String name, String date) {
        try {
            tplValue = URLEncoder.encode("#device#", ENCODING) + "="
                    + URLEncoder.encode(sno, ENCODING) + "&"
                    + URLEncoder.encode("#symptom#", ENCODING) + "="
                    + URLEncoder.encode(name, ENCODING) + "&"
                    + URLEncoder.encode("#time#", ENCODING) + "="
                    + URLEncoder.encode(date, ENCODING);
        } catch (Exception e) {
            throw new SystemException(SysExceptionEnum.ILLEGAL_PARAM.getCode(), SysExceptionEnum.ILLEGAL_PARAM.getMessage());
        }
        return tplValue;
    }

    /**
     * 机智云故障消息模版
     */
    public static String getAlarmAplValue(String sno, String name, String date) {
//        String tpl_value = "【机智云】故障通知，#device#，故障现象：#symptom#，故障时间：#time#，请及时处理。";
        String tplValue = SysConfigUtils.get(MessageCodeConfig.class).getAlarmTemplateValue();
        try {
            tplValue = URLEncoder.encode("#device#", ENCODING) + "="
                    + URLEncoder.encode(sno, ENCODING) + "&"
                    + URLEncoder.encode("#symptom#", ENCODING) + "="
                    + URLEncoder.encode(name, ENCODING) + "&"
                    + URLEncoder.encode("#time#", ENCODING) + "="
                    + URLEncoder.encode(date, ENCODING);
        } catch (Exception e) {
            throw new SystemException(SysExceptionEnum.ILLEGAL_PARAM.getCode(), SysExceptionEnum.ILLEGAL_PARAM.getMessage());
        }
        return tplValue;
    }

    /**
     * 基于HttpClient 4.3的通用POST方法
     *
     * @param url       提交的URL
     * @param paramsMap 提交<参数，值>Map
     * @return 提交响应
     */

    public static String post(String url, Map<String, String> paramsMap) {
        CloseableHttpClient client = HttpClients.createDefault();
        String responseText = "";
        CloseableHttpResponse response = null;
        try {
            HttpPost method = new HttpPost(url);
            if (paramsMap != null) {
                List<NameValuePair> paramList = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
                    NameValuePair pair = new BasicNameValuePair(param.getKey(), param.getValue());
                    paramList.add(pair);
                }
                method.setEntity(new UrlEncodedFormEntity(paramList, ENCODING));
            }
            response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseText = EntityUtils.toString(entity, ENCODING) + "";

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return responseText.replace("\\", "");
    }
}