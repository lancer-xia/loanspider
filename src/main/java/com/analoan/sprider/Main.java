/**
 * @Company:
 * @Author: lancer
 * @Date: 15-2-16 下午3:31
 * @Version: 1.0
 */
package com.analoan.sprider;

/**
 * <b>Main</b>
 * <p><b>详细说明：</b></p>
 * 在这里添加详细说明
 * <p><b>修改列表：</b></p>
 * <table width="" cellspacing=1 cellpadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!--在此添加修改列表，参考第一行内容-->
 * <tr bgcolor="#CCCCFF"><td>1</td><td>lancer</td><td>15-2-16 下午3:31</td><td>新建内容</td></tr>
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.*;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;

import org.apache.http.*;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.*;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ConnectionRequest;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.cookie.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.impl.cookie.*;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class Main {


//    public static void main(String[] args) {
//
//        try {
//            test1();
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }


    /**
     * 测试1： 构建复杂uri，这种方式会很方便的设置多个参数；
     * <p/>
     * HttpClients类是client的具体一个实现类；
     * <p/>
     * URIBuilder包含：协议，主机名，端口（可选），资源路径，和多个参数（可选）
     */
    private static void test1() {

        CloseableHttpClient client = HttpClients.createDefault();

        URI uri = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("webservice.webxml.com.cn")
                    .setPath("/WebServices/MobileCodeWS.asmx/getDatabaseInfo")
                    .setParameter("", "")//这里可以设置多个参数
                    .setParameter("", "")
                    .setParameter("", "")
                    .setParameter("", "")
                    .build();
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }

        //http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getDatabaseInfo
        HttpGet get = new HttpGet(uri);
        try {
            CloseableHttpResponse response = client.execute(get);

            if (response.getStatusLine().getStatusCode() == 200) {


                System.out.println(EntityUtils.toString(response.getEntity()));

                //以下这种方式读取流也可以，只不过用EntityUtils会更方便
                /*InputStream is = response.getEntity().getContent();
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				byte[] buffer = new byte[1024];
				int len=-1;
				while((len = is.read(buffer))!=-1){
					os.write(buffer,0,len);
				}
				os.close();
				is.close();
				System.out.println(os.size()+new String(os.toByteArray(),"utf-8"));*/
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * 为uri进行加密，并进行表单提交；
     * <p/>
     * 许多应用需要模拟提交的HTML表单的处理，例如，在
     * 为了登录到Web应用程序或提交的输入数据。 HttpClient提供的实体类
     * UrlEncodedFormEntity可以实现该过程；
     */
    private static void test2() {

        CloseableHttpClient client = HttpClients.createDefault();

        HttpPost httppost = new HttpPost("http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getMobileCodeInfo");

        //构建请求参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        list.add(new BasicNameValuePair("mobileCode", "110"));
        list.add(new BasicNameValuePair("userID", ""));

        //构建url加密实体，并以utf-8方式进行加密；
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, Consts.UTF_8);
        httppost.setEntity(entity);

        try {
            CloseableHttpResponse response = client.execute(httppost);

            if (response.getStatusLine().getStatusCode() == 200) {

                //org.apache.http.util.EntityUtils类可以快速处理服务器返回实体对象
                System.out.println(EntityUtils.toString(response.getEntity()));

            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    /**
     * 以回调方式处理返回结果
     * <p/>
     * 处理响应的最简单和最方便的方法是通过使用ResponseHandler的
     * 接口。用户不必担心连接管理的问题。当使用一个
     * ResponseHandler的时候，无论是否请求执行成功或导致异常，HttpClient将会自动释放连接。
     */
    private static void test3() {

        CloseableHttpClient client = HttpClients.createDefault();

        //==============================================================
        ResponseHandler<Object> handler = new ResponseHandler<Object>() {
            @Override
            public Object handleResponse(final HttpResponse response) throws IOException {

                HttpEntity entity = response.getEntity();

                if (entity == null) {
                    throw new ClientProtocolException("返回结果为空");
                }

                if (response.getStatusLine().getStatusCode() == 200) {

                    //获取返回结果的字符集 如：utf-8  gbk，并以这种字符集来读取流信息
                    ContentType contentType = ContentType.getOrDefault(entity);
                    Charset charset = contentType.getCharset();

                    InputStreamReader reader = new InputStreamReader(entity.getContent(), charset);
                    BufferedReader br = new BufferedReader(reader);
                    StringBuffer sb = new StringBuffer();
                    char[] buffer = new char[1024];
                    while (br.read(buffer) != -1) {
                        sb.append(new String(buffer));
                    }

                    return sb.toString();
                }

                return null;

            }
        };
        //===================================================================

        URI uri = null;//构建uri实体
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("webservice.webxml.com.cn")
                    .setPath("/WebServices/MobileCodeWS.asmx/getDatabaseInfo")
                    .setParameter("", "")
                    .setParameter("", "")
                    .setParameter("", "")
                    .build();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        HttpPost post = new HttpPost(uri);

        try {

            //handler回调
            Object obj = client.execute(post, handler);

            System.out.println("返回结果：" + obj);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 设置长连接策略，根据服务器的约束或者客户端的约束来设置长连接的时长；
     */
    private static void test4() {

        ConnectionKeepAliveStrategy strategy = new DefaultConnectionKeepAliveStrategy() {

            /**
             * 服务器端配置（以tomcat为例）：keepAliveTimeout=60000，表示在60s内内，服务器会一直保持连接状态。
             * 也就是说，如果客户端一直请求服务器，且间隔未超过60s，则该连接将一直保持，如果60s内未请求，则超时。
             *
             * getKeepAliveDuration返回超时时间；
             */
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {


                //如果服务器指定了超时时间，则以服务器的超时时间为准
                HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        try {
                            System.out.println("服务器指定的超时时间：" + value + " 秒");
                            return Long.parseLong(value) * 1000;
                        } catch (NumberFormatException ignore) {
                            ignore.printStackTrace();
                        }
                    }
                }


                long keepAlive = super.getKeepAliveDuration(response, context);

                // 如果服务器未指定超时时间，则客户端默认30s超时
                if (keepAlive == -1) {
                    keepAlive = 30 * 1000;
                }

                return keepAlive;

		        /*如果访问webservice.webxml.com.cn主机，则超时时间5秒，其他主机超时时间30秒
		        HttpHost host = (HttpHost) context.getAttribute(HttpClientContext.HTTP_TARGET_HOST);
		        if ("webservice.webxml.com.cn".equalsIgnoreCase(host.getHostName())) {
		        	keepAlive =  10 * 1000;
		        } else {
		        	keepAlive =  30 * 1000;
		        }*/


            }
        };


        CloseableHttpClient client = HttpClients.custom().setKeepAliveStrategy(strategy).build();

        URI uri = null;//构建uri实体
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("webservice.webxml.com.cn")
                    .setPath("/WebServices/MobileCodeWS.asmx/getDatabaseInfo")
                    .setParameter("", "")
                    .build();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        HttpPost post = new HttpPost(uri);

        try {

            CloseableHttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(response.getEntity());
                System.out.println("返回的结果=====" + result);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**
     * 构建复杂请求体
     * <p/>
     * RequestConfig将会保存在context上下文中，并会在连续的请求中进行传播（来自官方文档）；
     */
    private void test5() {


        CloseableHttpClient client = HttpClients.createDefault();

        //构建请求配置
        RequestConfig config = RequestConfig.
                custom().
                setSocketTimeout(1000 * 10).
                setConnectTimeout(1000 * 10).
                build();
        //==============================================================
        ResponseHandler<Object> handler = new ResponseHandler<Object>() {//回调
            @Override
            public Object handleResponse(final HttpResponse response) throws IOException {

                HttpEntity entity = response.getEntity();

                if (entity == null) {
                    throw new ClientProtocolException("返回结果为空");
                }

                if (response.getStatusLine().getStatusCode() == 200) {
                    ContentType contentType = ContentType.getOrDefault(entity);
                    Charset charset = contentType.getCharset();

                    InputStreamReader reader = new InputStreamReader(entity.getContent(), charset);
                    BufferedReader br = new BufferedReader(reader);
                    StringBuffer sb = new StringBuffer();
                    char[] buffer = new char[1024];
                    while (br.read(buffer) != -1) {
                        sb.append(new String(buffer));
                    }

                    return sb.toString();
                }

                return null;

            }
        };
        //===================================================================

        URI uri = null;
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("webservice.webxml.com.cn")
                    .setPath("/WebServices/MobileCodeWS.asmx/getDatabaseInfo")
                    .setParameter("", "")
                    .setParameter("", "")
                    .setParameter("", "")
                    .build();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        HttpPost post = new HttpPost(uri);

        post.setConfig(config);//设置请求配置

        try {
            Object obj = client.execute(post, handler);

            System.out.println("返回结果：" + obj);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    /**
     * 异常恢复机制：
     * <p/>
     * HttpRequestRetryHandler连接失败后，可以针对相应的异常进行相应的处理措施；
     * HttpRequestRetryHandler接口须要用户自己实现；
     */
    private static void test6() {

        HttpRequestRetryHandler retryHandler = new HttpRequestRetryHandler() {

            /**
             * exception异常信息；
             * executionCount：重连次数；
             * context：上下文
             */
            @Override
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {

                System.out.println("重连接次数：" + executionCount);

                if (executionCount >= 5) {//如果连接次数超过5次，就不进行重复连接
                    return false;
                }
                if (exception instanceof InterruptedIOException) {//io操作中断
                    return false;
                }
                if (exception instanceof UnknownHostException) {//未找到主机
                    // Unknown host
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {//连接超时
                    return true;
                }
                if (exception instanceof SSLException) {
                    // SSL handshake exception
                    return false;
                }
                HttpClientContext clientContext = HttpClientContext.adapt(context);

                HttpRequest request = clientContext.getRequest();

                boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);

                if (idempotent) {
                    // Retry if the request is considered idempotent
                    return true;
                }
                return false;
            }
        };


        CloseableHttpClient client = HttpClients.custom().setRetryHandler(retryHandler).build();

        RequestConfig config = RequestConfig.
                custom().
                setSocketTimeout(1000 * 10).
                setConnectTimeout(1000 * 10).
                build();

        HttpPost post = new HttpPost("http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getDatabaseInfo");

        post.setConfig(config);

        try {
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();

            ContentType contentType = ContentType.getOrDefault(entity);
            Charset charset = contentType.getCharset();

            InputStreamReader reader = new InputStreamReader(entity.getContent(), charset);
            BufferedReader br = new BufferedReader(reader);
            StringBuffer sb = new StringBuffer();
            char[] buffer = new char[1024];
            while (br.read(buffer) != -1) {
                sb.append(new String(buffer));
            }

            System.out.println("返回的结果：" + sb.toString());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * HTTP请求过滤器，在执行请求之前拦截HttpRequest 和 HttpContext；
     */
    private static void test7() throws ClientProtocolException, IOException {

        HttpRequestInterceptor requestInterceptor = new HttpRequestInterceptor() {
            /**
             * 处理请求：
             * 如果是客户端：这个处理在发送请求之前执行；
             * 如果是服务器：这个处理在接收到请求体之前执行；
             */
            public void process(final HttpRequest request, final HttpContext context) throws HttpException, IOException {
                AtomicInteger count = (AtomicInteger) context.getAttribute("count");
                request.addHeader("count", Integer.toString(count.getAndIncrement()));
            }
        };


        CloseableHttpClient httpclient = HttpClients.
                custom().
                addInterceptorLast(requestInterceptor).
                build();


        AtomicInteger count = new AtomicInteger(1);
        HttpClientContext context = HttpClientContext.create();
        context.setAttribute("count", count);
        HttpGet httpget = new HttpGet("http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getDatabaseInfo");

        for (int i = 0; i < 10; i++) {
            CloseableHttpResponse response = httpclient.execute(httpget, context);
            try {

                HttpEntity entity = response.getEntity();
//				System.out.println(EntityUtils.toString(entity));

            } finally {
                response.close();
            }
        }
    }


    /**
     * httpclient会自动处理重定向；
     * <p/>
     * 301 永久重定向,告诉客户端以后应从新地址访问.
     * 302 作为HTTP1.0的标准,以前叫做Moved Temporarily ,现在叫Found. 现在使用只是为了兼容性的  处理,包括PHP的默认Location重定向用的也是302.
     * 但是HTTP 1.1 有303 和307作为详细的补充,其实是对302的细化
     * 303：对于POST请求，它表示请求已经被处理，客户端可以接着使用GET方法去请求Location里的URI。
     * 307：对于POST请求，表示请求还没有被处理，客户端应该向Location里的URI重新发起POST请求。
     */
    private static void test8() throws ClientProtocolException, IOException,
            URISyntaxException {

        LaxRedirectStrategy redirectStrategy = new LaxRedirectStrategy();
        CloseableHttpClient httpclient = HttpClients.custom().setRedirectStrategy(redirectStrategy).build();

        HttpClientContext context = HttpClientContext.create();
        HttpGet httpget = new HttpGet("http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getDatabaseInfo");
        CloseableHttpResponse response = httpclient.execute(httpget, context);
        try {
            HttpHost target = context.getTargetHost();
            List<URI> redirectLocations = context.getRedirectLocations();
            URI location = URIUtils.resolve(httpget.getURI(), target, redirectLocations);
            System.out.println("Final HTTP location: " + location.toASCIIString());
        } finally {
            response.close();
        }
    }

//=======================================第二章================================================

    /**
     * 多线程并发访问服务器
     *
     * 每次http连接需要三次握手，开销很大，http/1.1默认支持连接复用；
     *
     * PoolingHttpClientConnectionManager 允许管理器限制最大连接数 ，还允许每个路由器针对某个主机限制最大连接数。
     *
     * 如下：setDefaultMaxPerRoute(3)之后，每次并发3个访问，所以打印输出是每次输出三个"test",验证了http连接管理器生效；
     *
     */
    private static void test9() throws InterruptedException,ExecutionException, IOException {

        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(200);//设置最大连接数200
        connManager.setDefaultMaxPerRoute(3);//设置每个路由默认连接数
        HttpHost host = new HttpHost("webservice.webxml.com.cn");//针对的主机
        connManager.setMaxPerRoute(new HttpRoute(host), 5);//每个路由器对每个服务器允许最大5个并发访问

        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connManager).build();

        String[] urisToGet = {
                "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getDatabaseInfo",
                "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getDatabaseInfo",
                "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getDatabaseInfo",
                "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getDatabaseInfo",
                "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getDatabaseInfo",
                "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getDatabaseInfo",
                "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getDatabaseInfo",
                "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getDatabaseInfo",
                "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getDatabaseInfo",
                "http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getDatabaseInfo" };

        GetThread[] threads = new GetThread[urisToGet.length];

        for (int i = 0; i < threads.length; i++) {
            HttpGet httpget = new HttpGet(urisToGet[i]);
            threads[i] = new GetThread(httpClient, httpget);
        }

        for (int j = 0; j < threads.length; j++) {
            threads[j].start();
        }

        for (int j = 0; j < threads.length; j++) {
            threads[j].join();
        }


    }

    static class GetThread extends Thread {
        private final CloseableHttpClient httpClient;
        private final HttpContext context;
        private final HttpGet httpget;

        public GetThread(CloseableHttpClient httpClient, HttpGet httpget) {
            this.httpClient = httpClient;
            this.context = HttpClientContext.create();
            this.httpget = httpget;
        }

        @Override
        public void run() {
            try {
                CloseableHttpResponse response = httpClient.execute(httpget, context);
                try {
                    HttpEntity entity = response.getEntity();
                    if(entity!=null){
                        System.out.println("test");
                    }
                } finally {
                    response.close();
                }
            } catch (ClientProtocolException ex) {
            } catch (IOException ex) {
            }
        }
    }


    /**
     *  清空失效连接：
     *
     * 	连接的有效性检测是所有连接池都面临的一个通用问题，大部分HTTP服务器为了控制资源开销，并不会
     永久的维护一个长连接，而是一段时间就会关闭该连接。放回连接池的连接，如果在服务器端已经关闭，客
     户端是无法检测到这个状态变化而及时的关闭Socket的。这就造成了线程从连接池中获取的连接不一定是有效的。
     这个问题的一个解决方法就是在每次请求之前检查该连接是否已经存在了过长时间，可能已过期。
     但是这个方法会使得每次请求都增加额外的开销。HTTP Client4.0的HttpClientConnectionManager 提供了
     closeExpiredConnections()方法和closeIdleConnections()方法来解决该问题。
     前一个方法是清除连接池中所有过期的连接，至于连接什么时候过期可以设置，设置方法将在下面提到，
     而后一个方法则是关闭一定时间空闲的连接，可以使用一个单独的线程完成这个工作。
     */
    private static void test10(){

        HttpClientConnectionManager manager = new BasicHttpClientConnectionManager();

        new IdleConnectionMonitorThread(manager).start();//启动线程，5秒钟清空一次失效连接

        CloseableHttpClient client = HttpClients.custom().setConnectionManager(manager).build();

        URI uri = null;//构建uri实体
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("webservice.webxml.com.cn")
                    .setPath("/WebServices/MobileCodeWS.asmx/getDatabaseInfo")
                    .setParameter("", "")
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        HttpPost post = new HttpPost(uri);

        try {
            client.execute(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //这个线程负责使用连接管理器清空失效连接和过长连接
    private static class IdleConnectionMonitorThread extends Thread {

        private final HttpClientConnectionManager connMgr;
        private volatile boolean shutdown;

        public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
            super();
            this.connMgr = connMgr;
        }

        @Override
        public void run() {
            try {
                while (!shutdown) {
                    synchronized (this) {
                        wait(5000);
                        System.out.println("清空失效连接...");
                        // 关闭失效连接
                        connMgr.closeExpiredConnections();
                        //关闭空闲超过30秒的连接
                        connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
                    }
                }
            } catch (InterruptedException ex) {
            }
        }

        public void shutdown() {
            shutdown = true;
            synchronized (this) {
                notifyAll();
            }
        }
    }


    /**
     *
     * http长连接策略：
     * 可以根据须要定制所须要的长连接策略，可根据服务器指定的超时时间，也可根据主机名自己指定超时时间；
     */
    private static void test11(){

        //参考第一章的DefaultConnectionKeepAliveStrategy类
        ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
            public long getKeepAliveDuration(HttpResponse response,HttpContext context) {
                // 遍历response的header
                HeaderElementIterator it = new BasicHeaderElementIterator(response.headerIterator(HTTP.CONN_KEEP_ALIVE));

                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {//如果头部包含timeout信息，则使用
                        try {
                            //超时时间设置为服务器指定的值
                            return Long.parseLong(value) * 1000;
                        } catch (NumberFormatException ignore) {
                        }
                    }
                }
                //获取主机
                HttpHost target = (HttpHost) context.getAttribute(HttpClientContext.HTTP_TARGET_HOST);
                if ("webservice.webxml.com.cn".equalsIgnoreCase(target.getHostName())) {
                    // 如果访问webservice.webxml.com.cn主机则设置长连接时间为5秒
                    return 5* 1000;
                } else {
                    // 其他为30秒
                    return 30 * 1000;
                }
            }
        };
        CloseableHttpClient client = HttpClients.custom().setKeepAliveStrategy(myStrategy).build();


        URI uri = null;//构建uri实体
        try {
            uri = new URIBuilder()
                    .setScheme("http")
                    .setHost("webservice.webxml.com.cn")
                    .setPath("/WebServices/MobileCodeWS.asmx/getDatabaseInfo")
                    .setParameter("", "")
                    .build();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        HttpPost post = new HttpPost(uri);

        try {

            CloseableHttpResponse response =  client.execute(post);
            if(response.getStatusLine().getStatusCode()==200){
                String result = EntityUtils.toString(response.getEntity());
                System.out.println("返回的结果====="+result);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private static void test12() throws IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

        /**
         * Http连接使用java.net.Socket类来传输数据。这依赖于ConnectionSocketFactory接口来创建、初始化和连接socket。
         */
        HttpClientContext clientContext = HttpClientContext.create();
        PlainConnectionSocketFactory sf = PlainConnectionSocketFactory.getSocketFactory();//Plain：简洁的
        Socket socket = sf.createSocket(clientContext);
        HttpHost target = new HttpHost("localhost");
        InetSocketAddress remoteAddress = new InetSocketAddress(InetAddress.getByAddress(new byte[] { 127, 0, 0, 1}), 80);
        sf.connectSocket(1000, socket, target, remoteAddress, null,clientContext);


        //创建通用socket工厂
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();

        //Creates default SSL context based on system properties(HttpClient没有自定义任何加密算法。它完全依赖于Java加密标准)
        SSLContext sslcontext = SSLContexts.createSystemDefault();

        //创建ssl socket工厂
        LayeredConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                SSLConnectionSocketFactory.STRICT_HOSTNAME_VERIFIER);

        //自定义的socket工厂类可以和指定的协议（Http、Https）联系起来，用来创建自定义的连接管理器。
        Registry<ConnectionSocketFactory> r = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", plainsf)
                .register("https", sslsf)
                .build();
        HttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(r);
        CloseableHttpClient client = HttpClients.
                custom().
                setConnectionManager(cm).
                build();


        ////////////////////////////////////////////////////////////
        //自定义SSLContext
//		KeyStore myTrustStore = null;
//		SSLContext sslContext = SSLContexts.custom()
//				.useTLS()   //安全传输层协议（TLS）用于在两个通信应用程序之间提供保密性和数据完整性。该协议由两层组成： TLS 记录协议（TLS Record）和 TLS 握手协议（TLS Handshake）。
//				.useSSL()
//				.loadTrustMaterial(myTrustStore)
//				.build();
//		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
        ////////////////////////////////////////////////////////////////////


    }

    /**
     * HttpClient代理服务器配置
     *
     * 使用代理服务器最简单的方式就是，指定一个默认的proxy参数。
     * 我们也可以让HttpClient去使用jre的代理服务器。
     * 又或者，我们也可以手动配置RoutePlanner，这样就可以完全控制Http路由的过程。
     */
    private static void test13(){

        //1、使用默认的代理
        HttpHost proxy = new HttpHost("8.8.8.8", 8080);
        DefaultProxyRoutePlanner routePlanner1 = new DefaultProxyRoutePlanner(proxy);
        CloseableHttpClient httpclient1 = HttpClients.
                custom()
                .setRoutePlanner(routePlanner1)
                .build();

        //2、使用jre的代理
        SystemDefaultRoutePlanner routePlanner2 = new SystemDefaultRoutePlanner(ProxySelector.getDefault());
        CloseableHttpClient httpclient2 = HttpClients.
                custom()
                .setRoutePlanner(routePlanner2)
                .build();


        //3、自定义代理
        HttpRoutePlanner routePlanner3 = new HttpRoutePlanner() {
            public HttpRoute determineRoute(HttpHost target,HttpRequest request,HttpContext context) throws HttpException {

                return new HttpRoute(
                        target,
                        null,
                        new HttpHost("8.8.8.8", 8080),
                        "https".equalsIgnoreCase(target.getSchemeName())
                );
            }
        };
        CloseableHttpClient httpclient3 = HttpClients.
                custom()
                .setRoutePlanner(routePlanner3)
                .build();

    }


    //=========================== 第三章 =================================================

    /**
     * 最初，Http被设计成一个无状态的协议。
     * 网景(netscape)公司，最先在他们的产品中支持http状态管理，并且制定了一些专有规范。
     * 但是，现在多数的应用的状态管理机制都在使用网景公司的规范，而网景的规范和官方规定是不兼容的。
     */
    private static void test14(){

        //=====================================================
        //网景版本的Cookie ，网景版本是0 ，标准版本是1
        BasicClientCookie netscapeCookie = new BasicClientCookie("name", "value");
        netscapeCookie.setVersion(0);
        /**
         * 跨域访问设置domain方式： “.” + other.com，这样在 other.com域下可以获取cookie
         * 如果在同一应用中则设置：
         */
        netscapeCookie.setDomain(".other.com");
        /**
         * 可以在tomcat webapps文件夹下的所有应用共享此cookie
         * cookie.setPath("/webapp_b/")只是webapp_b应用才能获取此cookie
         */
        netscapeCookie.setPath("/");


        //=====================================================
        //下面是标准版的cookie（版本为1）
        BasicClientCookie stdCookie = new BasicClientCookie("name", "value");
        stdCookie.setVersion(1);
        stdCookie.setDomain(".mycompany.com");
        stdCookie.setPath("/");
        stdCookie.setSecure(true);
        // 服务器发送过来的属性都必须保留
        stdCookie.setAttribute(ClientCookie.VERSION_ATTR, "1");
        stdCookie.setAttribute(ClientCookie.DOMAIN_ATTR, ".mycompany.com");


        //=====================================================
        //创建了Set-Cookie2兼容cookie。
        BasicClientCookie2 cookie2 = new BasicClientCookie2("name", "value");
        cookie2.setVersion(1);
        cookie2.setDomain(".mycompany.com");
        cookie2.setPorts(new int[] {80,8080});
        cookie2.setPath("/");
        cookie2.setSecure(true);
        cookie2.setAttribute(ClientCookie.VERSION_ATTR, "1");
        cookie2.setAttribute(ClientCookie.DOMAIN_ATTR, ".mycompany.com");
        cookie2.setAttribute(ClientCookie.PORT_ATTR, "80,8080");



        //=====================================================
        //自己实现CookieSpec接口，然后创建一个CookieSpecProvider接口来新建、
        //初始化自定义CookieSpec接口，最后把CookieSpecProvider注册到HttpClient中。
        //一旦我们注册了自定义策略，就可以像其他标准策略一样使用了。
        CookieSpecProvider easySpecProvider = new CookieSpecProvider() {
            public CookieSpec create(HttpContext context) {
                return new BrowserCompatSpec() {
                    @Override
                    public void validate(Cookie cookie, CookieOrigin origin)
                            throws MalformedCookieException {
                        // Oh, I am easy
                    }
                };
            }
        };

        Registry<CookieSpecProvider> r = RegistryBuilder
                .<CookieSpecProvider> create()
                        //最佳适配标准工厂
                .register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
                        //BrowserCompatSpecFactory 浏览器兼容标准工厂
                .register(CookieSpecs.BROWSER_COMPATIBILITY,new BrowserCompatSpecFactory())
                .register("easy", easySpecProvider)
                .build();

        RequestConfig requestConfig = RequestConfig.custom()
                .setCookieSpec("easy")
                .build();

        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCookieSpecRegistry(r)
                .setDefaultRequestConfig(requestConfig)
                .build();

    }



    /**
     * cookie持久化
     */
    private static void test15(){

        BasicCookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie("name", "value");
        cookie.setVersion(0);
        cookie.setDomain(".mycompany.com");
        cookie.setPath("/");
        //BasicCookieStore中的Cookie，当载体对象被当做垃圾回收掉后，就会丢失。
        cookieStore.addCookie(cookie);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCookieStore(cookieStore) //设置cookie，供服务器使用
                .build();
    }



    /**
     *  通过HttpClientContext 设置cookie 以及cookie标准
     *
     *  CookieSpec对象 代表实际的Cookie规范。
     CookieOrigin对象 代表实际的origin server的详细信息。
     CookieStore对象  ，这个属性集中的值会取代默认值。

     我们也可以在不同的线程中使用不同的执行上下文。
     我们在http请求层指定的cookie规范集和cookie store会覆盖在http Client层级的默认值。
     */
    private static void test16() throws ClientProtocolException, IOException{

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext context = HttpClientContext.create();
        BasicCookieStore cookieStore = new BasicCookieStore();
        //cookieStore 存放cookie.............

        Registry<CookieSpecProvider> r = RegistryBuilder
                .<CookieSpecProvider> create()
                        //最佳适配标准工厂
                .register(CookieSpecs.BEST_MATCH, new BestMatchSpecFactory())
                        //BrowserCompatSpecFactory 浏览器兼容标准工厂
                .register(CookieSpecs.BROWSER_COMPATIBILITY,new BrowserCompatSpecFactory())
                .build();

        context.setCookieSpecRegistry(r);
        context.setCookieStore(cookieStore);

        HttpGet httpget = new HttpGet("http://www.baidu.com");
        httpclient.execute(httpget, context);
        // Cookie origin details
        CookieOrigin cookieOrigin = context.getCookieOrigin();
        // Cookie spec used
        CookieSpec cookieSpec = context.getCookieSpec();

    }

    /**
     * HTTP认证：
     *
     * HTTPclient支持http标准认证，也支持其他认证，如NTLM和SPNEGO。
     */
    private static void test17(){

        //最简单的明文用户名 密码认证。
        UsernamePasswordCredentials creds = new UsernamePasswordCredentials("user", "pwd");
        System.out.println(creds.getUserPrincipal().getName());
        System.out.println(creds.getPassword());

        /**
         * NTCredentials是微软的windows系统使用的一种凭据，包含username、password，还包括一系列其他的属性，
         * 比如用户所在的域名。在Microsoft Windows的网络环境中，同一个用户可以属于不同的域，所以他也就有不同的凭据。
         * workstation:本机的计算机名
         */
        NTCredentials ntcreds = new NTCredentials("user", "pwd", "workstation", "domain");
        System.out.println(ntcreds.getUserPrincipal().getName());//输出   DOMAIN/user
        System.out.println(ntcreds.getPassword());


    }

    /**
     * 凭证提供者（CredentialsProvider）内含一套特定的凭证，须要哪种凭证时，CredentialsProvider就能获得对应的凭证。
     * 获取凭证的时候，可以模糊的指定主机名、端口号、realm和认证方案。
     * CredentialsProvider会筛选出一个最佳匹配方案。
     */
    private static void test18() {

        CredentialsProvider credsProvider = new BasicCredentialsProvider();

        credsProvider.setCredentials(new AuthScope("somehost",AuthScope.ANY_PORT),
                new UsernamePasswordCredentials("u1", "p1"));
        credsProvider.setCredentials(new AuthScope("somehost", 8080),
                new UsernamePasswordCredentials("u2", "p2"));
        credsProvider.setCredentials(new AuthScope("otherhost", 8080,AuthScope.ANY_REALM, "ntlm"),
                new UsernamePasswordCredentials("u3", "p3"));

        System.out.println(credsProvider.getCredentials(new AuthScope(
                "somehost", 80, "realm", "basic")));
        System.out.println(credsProvider.getCredentials(new AuthScope(
                "somehost", 8080, "realm", "basic")));
        System.out.println(credsProvider.getCredentials(new AuthScope(
                "otherhost", 8080, "realm", "basic")));
        System.out.println(credsProvider.getCredentials(new AuthScope(
                "otherhost", 8080, null, "ntlm")));

        /**
         *   输出：
         *  [principal: u1]
         [principal: u2]
         null
         [principal: u3]
         */
    }



    private static void test19() throws ClientProtocolException, IOException {

//		CloseableHttpClient httpclient = HttpClients.createDefault();
//		HttpClientContext context = HttpClientContext.create();
//
//		HttpHost targetHost = new HttpHost("webservice.webxml.com.cn", 80, "http");
//
//		//认证提供者
//		CredentialsProvider credsProvider = new BasicCredentialsProvider();
//		credsProvider.setCredentials(new AuthScope(targetHost.getHostName(),targetHost.getPort()),
//									 new UsernamePasswordCredentials("root", "root"));
//
//		//基础认证缓存
//		AuthCache authCache = new BasicAuthCache();
//
//		context.setCredentialsProvider(credsProvider);
//		context.setAuthCache(authCache);
//
//		HttpGet httpget = new HttpGet("/WebServices/MobileCodeWS.asmx/getDatabaseInfo");
//
//		CloseableHttpResponse response = httpclient.execute(targetHost,httpget, context);
//
//		AuthState proxyAuthState = context.getProxyAuthState();
//
//		System.out.println("Proxy auth state: " + proxyAuthState.getState());
//		System.out.println("Proxy auth scheme: "+ proxyAuthState.getAuthScheme());
//		System.out.println("Proxy auth credentials: "+ proxyAuthState.getCredentials());
//		AuthState targetAuthState = context.getTargetAuthState();
//		System.out.println("Target auth state: " + targetAuthState.getState());
//		System.out.println("Target auth scheme: "+ targetAuthState.getAuthScheme());
//		System.out.println("Target auth credentials: "+ targetAuthState.getCredentials());
        /**
         * 	Proxy auth state: UNCHALLENGED
         Proxy auth scheme: null
         Proxy auth credentials: null
         Target auth state: UNCHALLENGED
         Target auth scheme: null
         Target auth credentials: null
         */
    }


    /**
     * HttpClientContext 设置抢先认证  和  认证提供者：
     *
     * 在HTTP中，基本认证是一种用来允许Web浏览器或其他客户端程序在请求时提供以用户名和口令形式的凭证。
     *
     * 一般http basic认证，首先登录服务器， 然后服务器会返回401码让客户端输入用户名和密码，客户端把用户名密码进行BASE64加密，
     *
     * 最后把加密后的用户名和密码发送给服务器进行验证。
     *
     * 抢先验证则省略了前几部，直接发送BASE64位密文，进行验证，但存在风险，慎用！
     */
    private static void test20() throws ClientProtocolException, IOException {


        HttpClientContext context = HttpClientContext.create();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //配置主机 ， 端口可随意填写
        HttpHost targetHost = new HttpHost("webservice.webxml.com.cn", 80, "http");
        //认证提供者
        CredentialsProvider credsProvider = new BasicCredentialsProvider();

        credsProvider.setCredentials(
                new AuthScope(targetHost.getHostName(),targetHost.getPort()),
                new UsernamePasswordCredentials("username", "password"));

        AuthCache authCache = new BasicAuthCache();

        BasicScheme basicAuth = new BasicScheme();
        authCache.put(targetHost, basicAuth);

        //提前填充认证信息缓存到上下文中，这样，以这个上下文执行的方法，就会使用抢先认证。可能会出错
        context.setAuthCache(authCache);
        context.setCredentialsProvider(credsProvider);

        HttpGet httpget = new HttpGet("/WebServices/MobileCodeWS.asmx/getDatabaseInfo");

        CloseableHttpResponse response = httpclient.execute(targetHost,httpget, context);
        try {
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity);
            System.out.println(result);

        } finally {
            response.close();
        }
    }



    /**
     * NTLM连接认证：
     *
     * windows提供的一套安全、复杂的、有状态的协议。
     *
     * 相比Basic和Digest认证，NTLM认证要明显需要更多的计算开销，性能影响也比较大。
     *
     * 也就是说，NTLM连接一旦建立，用户的身份就会在其整个生命周期和它相关联。
     */
    private static void test21() throws ClientProtocolException, IOException {

        // 确保使用同一个上下文
        HttpClientContext context = HttpClientContext.create();
        CloseableHttpClient httpclient = HttpClients.createDefault();

        CredentialsProvider credsProvider = new BasicCredentialsProvider();
        //构建详细nt认证信息：NTCredentials 参数1：用户名  2：密码  3：本机名 3：domain域
        credsProvider.setCredentials(new AuthScope("somehost",AuthScope.ANY_PORT) ,
                new NTCredentials("username", "password", "myworkstation", "domain"));

        context.setCredentialsProvider(credsProvider);

        HttpHost target = new HttpHost("somehost", 80, "http");

        HttpGet httpget = new HttpGet("/");

        CloseableHttpResponse response1 = httpclient.execute(target, httpget, context);

        try {
            HttpEntity entity1 = response1.getEntity();
            System.out.println(EntityUtils.toString(entity1,"GB2312"));
        } finally {
            response1.close();
        }

        HttpPost httppost = new HttpPost("/");
        httppost.setEntity(new StringEntity("lots and lots of data"));
        CloseableHttpResponse response2 = httpclient.execute(target, httppost, context);
        try {
            HttpEntity entity2 = response2.getEntity();
        } finally {
            response2.close();
        }

    }

//--------------------------  快速API ---------------------------------


    /**
     * 快速api只提供最基本的功能，只用于不须要灵活扩展的场景
     */
//    private static void test22() throws ClientProtocolException, IOException{
//
//        String result = Request.Get("http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getDatabaseInfo")
//                .connectTimeout(1000)//设置服务器请求超时
//                .socketTimeout(1000)//设置服务器相应超时
//                .execute()
//                .returnContent()
//                .asString();
//
//        String result2 = Request.Post("http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getDatabaseInfo")
//                .useExpectContinue()
//                .version(HttpVersion.HTTP_1_1)
//                .bodyString("参数", ContentType.DEFAULT_TEXT)
//                .execute()
//                .returnContent()
//                .asString();
//
//        //提交HTML表单 ，并保存返回结果
//        Request.Post("http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getDatabaseInfo")
//                .addHeader("X-Custom-header", "stuff") // 表单头
//                .viaProxy(new HttpHost("myproxy", 8080))  // 设置代理
//                .bodyForm(Form.form()   //表单
//                        .add("mobileCode", "12345")
//                        .add("userID", "123456")
//                        .build())
//                .execute()
//                .saveContent(new File("result.txt"));
//
//        System.out.println(result);
//        System.out.println(result2);
//
//    }
//
//
//    /**
//     * 利用Executor 快速开发；
//     * 如果需要在指定的安全上下文中执行某些请求，我们也可以直接使用Exector，
//     * 这时候用户的认证信息就会被缓存起来，以便后续的请求使用。
//     */
//    private static void test23() throws ClientProtocolException, IOException{
//
//
//        Executor executor = Executor.newInstance()
//                .auth(new HttpHost("somehost",8080), "username", "password")//添加认证
//                .authPreemptive(new HttpHost("somehost", 8080));  //使用抢先认证
//
//        executor.execute(Request.Get("http://somehost/"))//执行get请求
//                .returnContent().asString();
//
//        executor.execute(Request.Post("http://somehost/") //执行post请求
//                .useExpectContinue()
//                .bodyString("Important stuff", ContentType.DEFAULT_TEXT))
//                .returnContent().asString();
//
//    }
//
//
//
//    /**
//     *
//     * 快速响应处理
//     *
//     * 利用request快速发送get请求，并用ResponseHandler 回调返回结果；
//     */
//    private static void test24() throws ClientProtocolException, IOException {
//
//        Object result = Request .Get("http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getDatabaseInfo")
//                .execute().handleResponse(new ResponseHandler<Object>() {
//
//                    public Object handleResponse(final HttpResponse response) throws IOException {
//
//                        StatusLine statusLine = response.getStatusLine();
//
//                        if(statusLine.getStatusCode()==200){
//
//                            HttpEntity entity = response.getEntity();
//                            if (entity != null) {
//                                String str = EntityUtils.toString(entity);
//                                return str;
//                            }
//                        }
//
//                        return null;
//                    }
//
//                });
//
//
//        if (result != null) {
//            System.out.println(">>>>>>"+result);
//        }
//
//    }

    /**
     * httpclient缓存机制
     */
//    private static void test25() throws ClientProtocolException, IOException {
//
//
//        CacheConfig cacheConfig = CacheConfig.custom()
//                .setMaxCacheEntries(1000)  // 最大缓存1000个object
//                .setMaxObjectSize(8192)  // 字节
//                .build();
//
//        RequestConfig requestConfig = RequestConfig.custom()
//                .setConnectTimeout(30000)  // 请求超时时间
//                .setSocketTimeout(30000)  // 响应超时时间
//                .build();
//
//        // 有缓存的HttpClient继承了非缓存HttpClient的所有配置项和参数
//        CloseableHttpClient cachingClient = CachingHttpClients.custom()
//                .setCacheConfig(cacheConfig)
//                .setDefaultRequestConfig(requestConfig)
//                .build();
//
//        // 继承  HttpClientContext
//        HttpCacheContext context = HttpCacheContext.create();
//
//        HttpGet httpget = new HttpGet("http://www.mydomain.com/content/");
//
//        cachingClient.execute(httpget, context);
//        CacheResponseStatus responseStatus = context.getCacheResponseStatus();
//
//        /**
//         * 这个缓存请求多次  依然是打印 “ 该响应来自上行服务器~~ ” ，不是想要的结果
//         * 这里有几个疑问：缓存机制是否须要客户端和服务器共同处理
//         * 另外，是否这个缓存配置没生效，难道官网代码有误？
//         */
//        switch (responseStatus) {
//            case CACHE_HIT:
//                System.out.println("该相应由缓存生成 并没有向上行发送请求~~");
//                break;
//            case CACHE_MODULE_RESPONSE:
//                System.out.println("该响应直接由缓存生成~~");
//                break;
//            case CACHE_MISS:
//                System.out.println("该响应来自上行服务器~~");
//                break;
//            case VALIDATED:
//                System.out.println("该响应是从缓存中生成 并验证是来自源服务器~");
//                break;
//        }
//
//    }

    /**
     *
     */
    private static void test26() throws ClientProtocolException, IOException{
//		CloseableHttpClient httpclient = HttpClients.createDefault();
//		HttpClientContext context = HttpClientContext.create();
//		HttpGet httpget = new HttpGet("http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getDatabaseInfo");
//		CloseableHttpResponse response = httpclient.execute(httpget, context);
//		try {
//		    Principal principal = context.getUserToken(Principal.class);
//		    System.out.println(principal);
//		} finally {
//		    response.close();
//		}



        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpClientContext context1 = HttpClientContext.create();
        HttpGet httpget1 = new HttpGet("http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getDatabaseInfo");
        CloseableHttpResponse response1 = httpclient.execute(httpget1, context1);
        try {
            HttpEntity entity1 = response1.getEntity();
        } finally {
            response1.close();
        }
        Principal principal = context1.getUserToken(Principal.class);

        HttpClientContext context2 = HttpClientContext.create();
        context2.setUserToken(principal);
        HttpGet httpget2 = new HttpGet("http://webservice.webxml.com.cn/WebServices/MobileCodeWS.asmx/getDatabaseInfo");
        CloseableHttpResponse response2 = httpclient.execute(httpget2, context2);
        try {
            HttpEntity entity2 = response2.getEntity();
        } finally {
            response2.close();
        }


    }




}
