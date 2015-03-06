package com.analoan.sprider.httpservice.impl;

import com.analoan.sprider.exception.ExceptionHandler;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * <b>AbstractThread</b>
 * <p><b>详细说明：</b></p>
 * 在这里添加详细说明
 * <p><b>修改列表：</b></p>
 * <table width="" cellspacing=1 cellpadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!--在此添加修改列表，参考第一行内容-->
 * <tr bgcolor="#CCCCFF"><td>1</td><td>lancer</td><td>15-3-6 上午11:40</td><td>新建内容</td></tr>
 */
public abstract class AbstractThread extends Thread {
    final CloseableHttpClient httpClient;
    final HttpContext context;
    final HttpRequestBase httpRequest;
    final ResponseHandler handler;

    public AbstractThread (CloseableHttpClient client, ResponseHandler handler, HttpRequestBase httpRequest) {
        this.httpClient = client;
        this.handler = handler;
        this.httpRequest = httpRequest;
        this.context = HttpClientContext.create();
        // 设置线程运行时异常处理机制
        this.setUncaughtExceptionHandler(new ExceptionHandler());
    }

    @Override
    public void run() {
        try {
            // 设置回调类型
            Object object = httpClient.execute(httpRequest, handler, context);
            System.out.println(object);
        } catch (ClientProtocolException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {

        }
    }
}
