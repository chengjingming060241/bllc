package com.gizwits.lease.util;


import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.ServiceHelper;
import cn.jiguang.common.connection.NettyHttpClient;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.ResponseWrapper;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.boot.utils.SysConfigUtils;
import com.gizwits.lease.config.CommonSystemConfig;
import io.netty.handler.codec.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.jpush.api.push.model.notification.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.Map;

/**
 * Description:极光推送api
 * User: yinhui
 * Date: 2018-01-08
 */
public  class JiGuangUtil {

    public static void main(String[] args){
//        testSendPush("推送消息","");
    }

    protected static final Logger LOG = LoggerFactory.getLogger("");

    // demo App defined in resources/jpush-api.conf
    protected static final String APP_KEY ="e1d2fac55a2f80da93cf101b";
    protected static final String MASTER_SECRET = "653afb4b9cfff6f6f827af9b";
    protected static final String GROUP_PUSH_KEY = "2c88a01e073a0fe4fc7b167c";
    protected static final String GROUP_MASTER_SECRET = "b11314807507e2bcfdeebe2e";

    public static final String TITLE = "Test from API example";
    public static final String ALERT = "Test from API Example - alert";
    public static final String MSG_CONTENT = "Test from API Example - msgContent";
    public static final String REGISTRATION_ID = "0900e8d85ef";
    public static final String TAG = "tag_api";
    public static long sendCount = 0;
    private static long sendTotalTime = 0;


    private static void sendToJiGuang(String message,String mobile,Map<String,String> map){
        buildPushObject_ios_audienceMore_messageWithExtras(message,mobile,map);
        testSendPushWithCallback(message,mobile);
    }

    public static PushPayload buildPushObject_all_alias_alert(String message,String mobile) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias("alias1"))
                .setNotification(Notification.alert(ALERT))
                .build();
    }

    // 使用 NettyHttpClient 异步接口发送请求
    public static void testSendPushWithCallback(String message,String mobile) {
        CommonSystemConfig commonSystemConfig = SysConfigUtils.get(CommonSystemConfig.class);
        String appkey = commonSystemConfig.getJiGuangAppKey();
        String appSecret = commonSystemConfig.getjiGuangAppSecret();
        ClientConfig clientConfig = ClientConfig.getInstance();
        String host = (String) clientConfig.get(ClientConfig.PUSH_HOST_NAME);
        final NettyHttpClient client = new NettyHttpClient(ServiceHelper.getBasicAuthorization(appkey, appSecret),
                null, clientConfig);
        try {
            URI uri = new URI(host + clientConfig.get(ClientConfig.PUSH_PATH));
            PushPayload payload = buildPushObject_all_alias_alert(message,mobile);
            client.sendRequest(HttpMethod.POST, payload.toString(), uri, new NettyHttpClient.BaseCallback() {
                @Override
                public void onSucceed(ResponseWrapper responseWrapper) {
                    LOG.info("Got result: " + responseWrapper.responseContent);
                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     *构建推送对象：平台是 Andorid 与 iOS，推送目标是 （"tag1" 与 "tag2" 的并集）
     * 交（"alias1" 与 "alias2" 的并集），推送内容是 - 内容为 MSG_CONTENT 的消息，
     * 并且附加字段 from = JPush。
     * @param message
     * @return
     */
    public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras(String message,String mobile,Map<String,String> map) {
        Message.Builder builder = Message.newBuilder();
        builder.setMsgContent(message);
        Iterator<String> iterator = map.keySet().iterator();
        while(iterator.hasNext()){
            String key = iterator.next();
            builder.addExtra(key,map.get(key));
        }
        if (!ParamUtil.isNullOrEmptyOrZero(mobile)){
            return PushPayload.newBuilder()
                    .setPlatform(Platform.android_ios())
                    .setAudience(Audience.newBuilder()
                            .addAudienceTarget(AudienceTarget.alias(mobile))
                            .build())
                    .setMessage(builder
                            .build())
                    .build();
        }
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.alias("alias1"))
                        .build())
                .setMessage(builder
                        .build())
                .build();
    }

    public static void testSendPush(String appKey,String appSecret,String message,String mobile,Map<String,String> map) {
        ClientConfig clientConfig = ClientConfig.getInstance();
        final JPushClient jpushClient = new JPushClient(appSecret, appKey, null, clientConfig);
        // Here you can use NativeHttpClient or NettyHttpClient or ApacheHttpClient.
        // Call setHttpClient to set httpClient,
        // If you don't invoke this method, default httpClient will use NativeHttpClient.
//        ApacheHttpClient httpClient = new ApacheHttpClient(authCode, null, clientConfig);
//        jpushClient.getPushClient().setHttpClient(httpClient);
         final PushPayload payload = buildPushObject_ios_audienceMore_messageWithExtras(message,mobile,map);

//        // For push, all you need do is to build PushPayload object.
//        PushPayload payload = buildPushObject_all_alias_alert();
        try {
            PushResult result = jpushClient.sendPush(payload);
            LOG.info("Got result - " + result);
            // 如果使用 NettyHttpClient，需要手动调用 close 方法退出进程
            // If uses NettyHttpClient, call close when finished sending request, otherwise process will not exit.
            // jpushClient.close();
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            LOG.error("Sendno: " + payload.getSendno());

        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.info("HTTP Status: " + e.getStatus());
            LOG.info("Error Code: " + e.getErrorCode());
            LOG.info("Error Message: " + e.getErrorMessage());
            LOG.info("Msg ID: " + e.getMsgId());
            LOG.error("Sendno: " + payload.getSendno());
        }
    }

}
