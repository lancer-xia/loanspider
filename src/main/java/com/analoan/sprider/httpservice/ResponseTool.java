/**
 * @Company:
 * @Author: lancer
 * @Date: 15-2-16 下午3:58
 * @Version: 1.0
 */
package com.analoan.sprider.httpservice;

import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * <b>ResponseTool</b>
 * <p><b>详细说明：</b></p>
 * 在这里添加详细说明
 * <p><b>修改列表：</b></p>
 * <table width="" cellspacing=1 cellpadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!--在此添加修改列表，参考第一行内容-->
 * <tr bgcolor="#CCCCFF"><td>1</td><td>lancer</td><td>15-2-16 下午3:58</td><td>新建内容</td></tr>
 */
public class ResponseTool {


    public static void redirect() throws IOException {
        LaxRedirectStrategy redirectStrategy = new LaxRedirectStrategy();
        CloseableHttpClient httpclient = HttpClients.custom().setRedirectStrategy(redirectStrategy).build();

        HttpClientContext context = HttpClientContext.create();
        HttpGet httpget = new HttpGet("http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getDatabaseInfo");
        CloseableHttpResponse response = httpclient.execute(httpget, context);
        try

        {
            HttpHost target = context.getTargetHost();
            List<URI> redirectLocations = context.getRedirectLocations();
            URI location = URIUtils.resolve(httpget.getURI(), target, redirectLocations);
            System.out.println("Final HTTP location: " + location.toASCIIString());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } finally
        {
            response.close();
        }
    }

    public static void main(String[] args) {
        try {
            redirect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
