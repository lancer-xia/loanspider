/**
 * @Company:
 * @Author: lancer
 * @Date: 15-3-5 上午10:53
 * @Version: 1.0
 */
package com.analoan.sprider.responsehandler;

import com.analoan.sprider.responsehandler.parser.ResponseHtmlHandler;
import com.analoan.sprider.responsehandler.parser.ResponseJsonHandler;
import com.analoan.sprider.responsehandler.parser.ResponseXmlHandler;
import org.apache.http.client.ResponseHandler;

/**
 * <b>ResponseHandlerManager</b>
 * <p><b>详细说明：</b></p>
 * 在这里添加详细说明
 * <p><b>修改列表：</b></p>
 * <table width="" cellspacing=1 cellpadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!--在此添加修改列表，参考第一行内容-->
 * <tr bgcolor="#CCCCFF"><td>1</td><td>lancer</td><td>15-3-5 上午10:53</td><td>新建内容</td></tr>
 */
public class ResponseHandlerManager implements ResponseFactory {

    private static ResponseHandlerManager rm = null;

    private ResponseHandlerManager() {

    }

    /**
     * 获得单例
     * @return
     */
    public static ResponseHandlerManager getInstance() {
        if (null == rm) {
            rm = new ResponseHandlerManager();
        }
        return rm;
    }

    @Override
    public ResponseHandler createResponseHandler(String type, String mapping) {
        ResponseHandler handler = null;

        switch (ParserType.valueOf(type)) {
            case JSON:
                handler = new ResponseJsonHandler(mapping);
                break;
            case HTML :
                handler = new ResponseHtmlHandler(mapping);
                break;
            case XML:
                handler = new ResponseXmlHandler(mapping);
                break;
        }

        return handler;
    }

}
