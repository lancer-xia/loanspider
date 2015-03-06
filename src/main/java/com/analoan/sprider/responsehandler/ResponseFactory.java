/**
 * @Company:
 * @Author: lancer
 * @Date: 15-3-5 上午10:57
 * @Version: 1.0
 */
package com.analoan.sprider.responsehandler;

import org.apache.http.client.ResponseHandler;

/**
 * <b>ResponseFactory</b>
 * <p><b>详细说明：</b></p>
 * 在这里添加详细说明
 * <p><b>修改列表：</b></p>
 * <table width="" cellspacing=1 cellpadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!--在此添加修改列表，参考第一行内容-->
 * <tr bgcolor="#CCCCFF"><td>1</td><td>lancer</td><td>15-3-5 上午10:57</td><td>新建内容</td></tr>
 */
public interface ResponseFactory {

    /**
     * 创建一个文本解析器
     * @param type
     * @param mapping
     * @return html json xml
     */
    public ResponseHandler createResponseHandler(String type, String mapping);
}
