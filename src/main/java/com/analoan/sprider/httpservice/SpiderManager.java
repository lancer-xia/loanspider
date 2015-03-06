/**
 * @Company:
 * @Author: lancer
 * @Date: 15-2-15 下午6:34
 * @Version: 1.0
 */
package com.analoan.sprider.httpservice;

import com.analoan.sprider.entity.WebConfig;
import com.analoan.sprider.httpservice.impl.DefaltThread;
import com.analoan.sprider.responsehandler.ResponseFactory;
import com.analoan.sprider.responsehandler.ResponseHandlerManager;
import com.analoan.sprider.responsehandler.SpiderRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

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

    static final ResponseFactory factory = ResponseHandlerManager.getInstance();

    static final RequestConfig rConfig = RequestConfig.custom().
            setSocketTimeout(1000 * 10).
            setConnectTimeout(1000 * 10).
            build();

    /**
     * 获得一个爬虫连接
     * @return
     */
    public static CloseableHttpClient getHttpClient() {
        // 异常处理机制
        SpiderRequestRetryHandler retryHandler = new SpiderRequestRetryHandler();
        return HttpClients.custom().setRetryHandler(retryHandler).build();
    }

    /**
     * 初始化爬虫线程对象
     * @param config
     * @return
     */
    public static Thread getThread(WebConfig config) {
        // 控制不正确的网站配置信息
        if (null == config ||
                null == config.getCommtype() || "".equals(config.getCommtype()) ||
                null == config.getParsercfg() || "".equals(config.getParsertype())) {
            return null;
        }

        // 初始化httpclient
        CloseableHttpClient client = getHttpClient();

        // 初始化回调解析器
        ResponseHandler handler = factory.createResponseHandler(config.getParsertype(), config.getParsercfg());

        // 请求方式
        HttpRequestBase httpRequest = null;
        if (RequestType.POST.equals(config.getCommtype())) {
            httpRequest = new HttpPost(config.getWeburl());
        } else {
            httpRequest = new HttpGet(config.getWeburl());
        }
        httpRequest.setConfig(rConfig);
        return new DefaltThread(client, handler, httpRequest);
    }

}
