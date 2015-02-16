/**
 * @Company:
 * @Author: lancer
 * @Date: 15-2-15 下午6:34
 * @Version: 1.0
 */
package com.analoan.sprider;

import com.analoan.sprider.entity.WebConfig;
import com.analoan.sprider.httpservice.GetThread;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.util.List;

/**
 * <b>SpiderManager</b>
 * <p><b>详细说明：</b></p>
 * 在这里添加详细说明
 * <p><b>修改列表：</b></p>
 * <table width="" cellspacing=1 cellpadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!--在此添加修改列表，参考第一行内容-->
 * <tr bgcolor="#CCCCFF"><td>1</td><td>lancer</td><td>15-2-15 下午6:34</td><td>新建内容</td></tr>
 */
public class SpiderManager {

    private static PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();

    public SpiderManager() {
        pool.setMaxTotal(200);//设置最大连接数200
        pool.setDefaultMaxPerRoute(3);//设置每个路由默认连接数
    }

    public void fetch(List<WebConfig> wcList) throws InterruptedException {
//        HttpGet httpget = null;
//        for (int i = 0; i < wcList.size(); i++) {
//
//            httpget = new HttpGet(urisToGet[i]);
//            threads[i] = new GetThread(httpClient, httpget);
//        }
//
//
//        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(pool).build();
//        HttpHost host = new HttpHost("webservice.webxml.com.cn");//针对的主机
//        pool.setMaxPerRoute(new HttpRoute(host), 5);//每个路由器对每个服务器允许最大5个并发访问
//        // URL列表数组
//        String[] urisToGet = {
//                "http://www.domain1.com/",
//                "http://www.domain2.com/",
//                "http://www.domain3.com/",
//                "http://www.domain4.com/"
//        };
//
//        // 为每个url创建一个线程，GetThread是自定义的类
//        GetThread[] threads = new GetThread[urisToGet.length];
//        for (int i = 0; i < threads.length; i++) {
//            HttpGet httpget = new HttpGet(urisToGet[i]);
//            threads[i] = new GetThread(httpClient, httpget);
//        }
//
//        // 启动线程
//        for (int j = 0; j < threads.length; j++) {
//            threads[j].start();
//        }
    }
}
