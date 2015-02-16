/**
 * @Company:
 * @Author: lancer
 * @Date: 15-2-16 下午5:17
 * @Version: 1.0
 */
package com.analoan.sprider.httpservice;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

/**
 * <b>HttpRequestRetryHandler</b>
 * <p><b>详细说明：</b></p>
 * 在这里添加详细说明
 * <p><b>修改列表：</b></p>
 * <table width="" cellspacing=1 cellpadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!--在此添加修改列表，参考第一行内容-->
 * <tr bgcolor="#CCCCFF"><td>1</td><td>lancer</td><td>15-2-16 下午5:17</td><td>新建内容</td></tr>
 */
public class SpiderRequestRetryHandler implements org.apache.http.client.HttpRequestRetryHandler {

    @Override
    public boolean retryRequest(IOException e, int i, HttpContext httpContext) {

        System.out.println("重连接次数：" + i);

        if (i >= 5) {//如果连接次数超过5次，就不进行重复连接
            return false;
        }
        if (e instanceof InterruptedIOException) {//io操作中断
            return false;
        }
        if (e instanceof UnknownHostException) {//未找到主机
            // Unknown host
            return false;
        }
        if (e instanceof ConnectTimeoutException) {//连接超时
            return true;
        }
        if (e instanceof SSLException) {
            // SSL handshake exception
            return false;
        }

        HttpClientContext clientContext = HttpClientContext.adapt(httpContext);
        HttpRequest request = clientContext.getRequest();
        boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);

        if (idempotent) {
            // Retry if the request is considered idempotent
            return true;
        }
        return false;
    }
}
