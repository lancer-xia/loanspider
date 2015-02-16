/**
 * @Company:
 * @Author: lancer
 * @Date: 15-2-16 上午11:00
 * @Version: 1.0
 */
package com.analoan.sprider.vo;

import com.analoan.sprider.Spider;
import com.analoan.sprider.entity.WebConfig;

/**
 * <b>SpiderVO</b>
 * <p><b>详细说明：</b></p>
 * 在这里添加详细说明
 * <p><b>修改列表：</b></p>
 * <table width="" cellspacing=1 cellpadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!--在此添加修改列表，参考第一行内容-->
 * <tr bgcolor="#CCCCFF"><td>1</td><td>lancer</td><td>15-2-16 上午11:00</td><td>新建内容</td></tr>
 */
public class SpiderVO implements Spider {

    private String id;

    private String name;

    private String type;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public void fetch(WebConfig wc) {

    }
}
