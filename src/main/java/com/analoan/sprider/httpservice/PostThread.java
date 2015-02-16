/**
 * @Company:
 * @Author: lancer
 * @Date: 15-2-16 上午11:17
 * @Version: 1.0
 */
package com.analoan.sprider.httpservice;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * <b>GetThread</b>
 * <p><b>详细说明：</b></p>
 * 在这里添加详细说明
 * <p><b>修改列表：</b></p>
 * <table width="" cellspacing=1 cellpadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!--在此添加修改列表，参考第一行内容-->
 * <tr bgcolor="#CCCCFF"><td>1</td><td>lancer</td><td>15-2-16 上午11:17</td><td>新建内容</td></tr>
 */
public class PostThread extends Thread {

    private final CloseableHttpClient httpClient;
    private final HttpContext context;
    private final HttpPost httpPost;

    public PostThread(CloseableHttpClient httpClient, HttpPost httpPost) {
        this.httpClient = httpClient;
        this.context = HttpClientContext.create();
        this.httpPost = httpPost;
    }

    public void init() {
        //构建请求配置
        RequestConfig config = RequestConfig.
                custom().
//                setAuthenticationEnabled().
//                setCircularRedirectsAllowed().
//                setConnectionRequestTimeout().
//                setCookieSpec().
//                setExpectContinueEnabled().
//                setLocalAddress().
//                setMaxRedirects().
//                setRedirectsEnabled().
//                setRelativeRedirectsAllowed().
//                setStaleConnectionCheckEnabled().
//                setTargetPreferredAuthSchemes().
                setSocketTimeout(1000 * 10).
                setConnectTimeout(1000 * 10).
                build();
        httpPost.setConfig(config);

    }

    @Override
    public void run() {
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost, context);
            HttpEntity entity = response.getEntity();
            System.out.println(entity.getContentType());
            System.out.println(entity.getContentEncoding());
            System.out.println(entity.getContentLength());
            System.out.println(entity.getContent());
        } catch (ClientProtocolException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (null != response) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
