/**
 * @Company:
 * @Author: lancer
 * @Date: 15-2-15 下午6:26
 * @Version: 1.0
 */
package com.analoan.sprider;

/**
 * <b>Spider</b>
 * <p><b>详细说明：</b></p>
 * 在这里添加详细说明
 * <p><b>修改列表：</b></p>
 * <table width="" cellspacing=1 cellpadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!--在此添加修改列表，参考第一行内容-->
 * <tr bgcolor="#CCCCFF"><td>1</td><td>lancer</td><td>15-2-15 下午6:26</td><td>新建内容</td></tr>
 */
public interface Spider {

    public String getId();

    public String getName();

    public SpiderCfg getSpiderCfg();

    public SpiderWebCfg getFetchWebCfg();

    public void fetch();
}
