/**
 * @Company:
 * @Author: lancer
 * @Date: 15-2-16 上午11:17
 * @Version: 1.0
 */
package com.analoan.sprider.httpservice.impl;

import com.analoan.sprider.exception.ExceptionHandler;
import com.analoan.sprider.responsehandler.parser.ResponseJsonHandler;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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
public class DefaltThread extends AbstractThread {

    public DefaltThread(CloseableHttpClient client, ResponseHandler handler, HttpRequestBase httpRequest) {
        super(client, handler, httpRequest);
    }

}
