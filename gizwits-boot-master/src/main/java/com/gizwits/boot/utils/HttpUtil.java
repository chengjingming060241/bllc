package com.gizwits.boot.utils;

import com.gizwits.boot.model.HttpRespObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Map;

import javax.net.ssl.SSLContext;

/**
 * Created by zhl on 2017/6/28.
 */
public class HttpUtil {

    private static int timeOut = 30000;
    private static Logger logger = Logger.getLogger(HttpUtil.class);

    public static HttpRespObject sendPost(String uri, String constant,
                                          Map<String, String> headers) {
        int statusCode = 0;
        String rspBody = "";
        CloseableHttpClient client = SSLUtil.createSSLClientDefault();
        HttpPost post = new HttpPost(uri);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(10000).setConnectTimeout(timeOut)
                .setSocketTimeout(40000).build();
        post.setConfig(requestConfig);
        if (headers != null && headers.size() > 0) {
            post.setHeaders(assemblyHeader(headers));
        }
        InputStream in = null;
        CloseableHttpResponse response = null;
        try {
            StringEntity sEntity = new StringEntity(constant,"utf-8");
            sEntity.setContentType("application/json");
            sEntity.setContentEncoding("utf-8");
            post.setEntity(sEntity);
            response = client.execute(post);
            statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            in = entity.getContent();
            rspBody = inputStream2Str(in);
            post.abort();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                statusCode = -1;
                rspBody = e.getMessage();
                logger.error(e.getMessage(), e);
            }
        }
        HttpRespObject respObject = handleRsp(statusCode, uri, rspBody, "Post");
        return respObject;
    }


    /**
     * http 请求之Get，支持http和https两种。<br>
     * 需要传入uri和headers<br>
     * headers 为一个Map<String,String>, 如果请求中不需要header则传入null<br>
     * 返回HttpRespObject，HttpRespObject中包含是否请求成功(success)、statusCode和请求返回内容(
     * constant)
     *
     * @param uri
     * @param headers
     * @return
     */
    public static HttpRespObject sendGet(String uri, Map<String, String> headers) {
        String rspBody = "响应超时";
        int statusCode = 0;
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(10000).setConnectTimeout(timeOut)
                .setSocketTimeout(40000).build();
		/*
		 * CloseableHttpClient client = null; if(StringUtils.startsWith(uri,
		 * "https://")){ client = SSLUtil.createSSLClientDefault(); }else { //
		 * client = HttpClients.createDefault();
		 * PoolingHttpClientConnectionManager pm = new
		 * PoolingHttpClientConnectionManager(); pm.setDefaultMaxPerRoute(200);
		 * pm.setMaxTotal(200); client =
		 * HttpClients.custom().setConnectionManager(pm).build(); }
		 */
        CloseableHttpClient client = SSLUtil.createSSLClientDefault();
        HttpGet get = new HttpGet(uri);
        get.setConfig(requestConfig);
        if (headers != null && headers.size() > 0) {
            get.setHeaders(assemblyHeader(headers));
        }
        CloseableHttpResponse response = null;
        InputStream in = null;
        try {
            response = client.execute(get);
            statusCode = response.getStatusLine().getStatusCode();
			/*
			 * HttpEntity entity = response.getEntity(); in =
			 * entity.getContent();
			 */
            rspBody = EntityUtils.toString(response.getEntity(), "utf8");
            get.abort();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);

        } finally {
            try {
                EntityUtils.consume(response.getEntity());
                if (response != null) {
                    response.close();
                }
                if (client != null) {
                    client.close();
                }
            } catch (IOException e) {
                statusCode = -1;
                rspBody = e.getMessage();
                logger.error(e.getMessage(), e);
            }
        }

        HttpRespObject respObject = handleRsp(statusCode, uri, rspBody, "Get");
        return respObject;
    }

    /**
     * GET 请求
     * @param url
     * @return
     */
    public static String executeGet(String url) {
        try {
            HttpGet httpGet = new HttpGet(url);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse;
            httpResponse = httpClient.execute(httpGet);
            String resultContent = new Utf8ResponseHandler()
                    .handleResponse(httpResponse);
            return resultContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * POST 请求
     * @param url
     * @param body
     * @return
     */
    public static String executePost(String url, String body) {
        try {
            HttpPost httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(body, "UTF-8");
            httpPost.setEntity(entity);
            HttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = httpClient.execute(httpPost);
            String resultContent = new Utf8ResponseHandler()
                    .handleResponse(httpResponse);
            logger.info("result=" + resultContent);
            return resultContent;
        } catch (Exception e) {
            System.out.println(e.getMessage()+e.getCause()+e.getStackTrace());
            throw new RuntimeException(e);
        }
    }


    /**
     * 带证书的POST请求
     * @param url 请求url
     * @param body post消息体
     * @param certPath 证书绝对路径 PKCS12
     * @param certKey 证书密钥
     * @return
     * @throws IOException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public static String httpPostWithCert(String url, String body,String certPath,String certKey) throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(certPath));//加载本地的证书进行https加密传输
        try {
            keyStore.load(instream, certKey.toCharArray());//设置证书密码
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, certKey.toCharArray())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        HttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        //根据默认超时限制初始化requestConfig
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(30000).build();

        String result = null;
        HttpPost httpPost = new HttpPost(url);
        //解决XStream对出现双下划线的bug
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        //将要提交给API的数据对象转换成XML格式数据Post给API
        /*String postDataXML = xStreamForRequestPostData.toXML(xmlObj);*/
        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(body, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);
        //设置请求器的配置
        httpPost.setConfig(requestConfig);
        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, "UTF-8");
        } catch (ConnectionPoolTimeoutException e) {
            e.printStackTrace();
        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpPost.abort();
        }

        return result;
    }



    public static String executeBySslPost(String url, String body,String certificatePath,String password) throws Exception {
        String result = "";
        //商户id
        //指定读取证书格式为PKCS12
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        //读取本机存放的PKCS12证书文件
        FileInputStream instream = new FileInputStream(new File(certificatePath));
        try {
            //指定PKCS12的密码(商户ID)
            keyStore.load(instream, password.toCharArray());
        } finally {
            instream.close();
        }
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, password.toCharArray()).build();
        //指定TLS版本
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"}, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        //设置httpclient的SSLSocketFactory
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        try {
            HttpPost httppost = new HttpPost(url);
            StringEntity reqEntity = new StringEntity(body, "UTF-8");
            httppost.setEntity(reqEntity);

            System.out.println("Executing request: " + httppost.getRequestLine());
            CloseableHttpResponse response = null;
            try {
                response = httpclient.execute(httppost);
                result = EntityUtils.toString(response.getEntity(),"UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } finally {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    /**
     * utf-8编码
     */
    static class Utf8ResponseHandler implements ResponseHandler<String> {
        public String handleResponse(final HttpResponse response)
                throws IOException {
            final StatusLine statusLine = response.getStatusLine();
            final HttpEntity entity = response.getEntity();
            if (statusLine.getStatusCode() >= 300) {
                EntityUtils.consume(entity);
                throw new HttpResponseException(statusLine.getStatusCode(),
                        statusLine.getReasonPhrase());
            }
            return entity == null ? null : EntityUtils
                    .toString(entity, "UTF-8");
        }

    }

    /**
     * InputStream to string
     *
     * @param is
     * @return
     */
    private static String inputStream2Str(InputStream is) {
        BufferedReader in = new BufferedReader(new InputStreamReader(is));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
        } catch (IOException e) {
            logger.error("===read input stream to string error===>>>",e);
        }
        return buffer.toString();
    }

    /**
     * map to header[]
     *
     * @param headers
     * @return
     */
    public static Header[] assemblyHeader(Map<String, String> headers) {
        Header[] allHeader = new BasicHeader[headers.size()];
        int i = 0;
        for (String str : headers.keySet()) {
            allHeader[i] = new BasicHeader(str, headers.get(str));
            i++;
        }
        return allHeader;
    }

    private static HttpRespObject handleRsp(int statusCode, String uri,
                                            String content, String method) {
        boolean success = false;
        switch (statusCode) {
            case 0:
                logger.error("请求api：" + uri + " 时响应超时[" + method + "]，超时时间="
                        + timeOut);
                break;
            case 201:
            case 202:
            case 203:
            case 200:
                success = true;
                break;
            default:
                logger.warn("请求api：" + uri + " 时异常[" + method + "]，statusCode="
                        + statusCode + "响应内容=" + content);
                break;
        }
        return new HttpRespObject(success, statusCode, content);
    }

}
