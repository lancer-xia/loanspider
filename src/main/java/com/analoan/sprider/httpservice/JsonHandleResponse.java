/**
 * @Company:
 * @Author: lancer
 * @Date: 15-2-16 下午3:44
 * @Version: 1.0
 */
package com.analoan.sprider.httpservice;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.entity.ContentType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * <b>JsonHandleResponse</b>
 * <p><b>详细说明：</b></p>
 * 在这里添加详细说明
 * <p><b>修改列表：</b></p>
 * <table width="" cellspacing=1 cellpadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!--在此添加修改列表，参考第一行内容-->
 * <tr bgcolor="#CCCCFF"><td>1</td><td>lancer</td><td>15-2-16 下午3:44</td><td>新建内容</td></tr>
 */
public class JsonHandleResponse implements ResponseHandler {

    @Override
    public Object handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
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
}