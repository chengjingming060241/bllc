package com.gizwits.boot.utils;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Utils - http client
 *
 * @author lilh
 * @date 2017/7/4 11:42
 */
public final class HttpRequestUtils {

    //todo 重试，容错机制有待完善

    private static Logger logger = LoggerFactory.getLogger(HttpRequestUtils.class);

    private HttpRequestUtils() {
    }

    /**
     * post请求
     */
    public static String post(String url, Map<String, String> headers, String jsonBody) {
        Assert.hasText(url);
        HttpPost method = new HttpPost(url);
        addHeader(method, headers);
        addBody(method, jsonBody);
        return getResult(method);
    }

    /**
     * get请求
     */
    public static String get(String url, Map<String, String> headers) {
        Assert.hasText(url);
        HttpGet method = new HttpGet(url);
        addHeader(method, headers);
        return getResult(method);
    }

    private static String getResult(final HttpUriRequest method) {
        CloseableHttpClient httpClient = getHttpClient();
        String result = null;
        HttpResponse response = null;
        try {
            response = httpClient.execute(method);
            if (isSuccess(response)) {
                result = EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            logger.error("post请求失败" + method.getURI().toString(), e);
        } finally {
            HttpClientUtils.closeQuietly(response);
            HttpClientUtils.closeQuietly(httpClient);
        }
        return result;
    }

    private static CloseableHttpClient getHttpClient() {
        return HttpClients.createDefault();
    }

    private static boolean isSuccess(HttpResponse response) {
        return response.getStatusLine().getStatusCode() == 200 || response.getStatusLine().getStatusCode() == 201;
    }

    private static void addBody(HttpPost method, String jsonBody) {
        if (StringUtils.isNotEmpty(jsonBody)) {
            StringEntity body = new StringEntity(jsonBody, "utf-8");
            body.setContentType("application/json");
            body.setContentEncoding("utf-8");
            method.setEntity(body);
        }
    }

    private static void addHeader(HttpRequestBase method, Map<String, String> headers) {
        headers.forEach(method::addHeader);
    }

}
