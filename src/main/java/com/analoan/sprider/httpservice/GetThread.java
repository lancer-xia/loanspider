/**
 * @Company:
 * @Author: lancer
 * @Date: 15-2-16 上午11:17
 * @Version: 1.0
 */
package com.analoan.sprider.httpservice;

import com.analoan.sprider.exception.ExceptionHandler;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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
public class GetThread extends Thread {

    private final CloseableHttpClient httpClient;
    private final HttpContext context;
    private final HttpGet httpget;

    public GetThread(CloseableHttpClient httpClient, HttpGet httpget) {
        this.httpClient = httpClient;
        this.context = HttpClientContext.create();
        this.httpget = httpget;
        this.setUncaughtExceptionHandler(new ExceptionHandler());
    }

    @Override
    public void run() {
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpget, context);
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
