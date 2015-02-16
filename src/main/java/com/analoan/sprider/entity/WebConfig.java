/**
 * @Company:
 * @Author: lancer
 * @Date: 15-2-5 下午4:34
 * @Version: 1.0
 */
package com.analoan.sprider.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <b>UserInfo</b>
 * <p><b>详细说明：</b></p>
 * 在这里添加详细说明
 * <p><b>修改列表：</b></p>
 * <table width="" cellspacing=1 cellpadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!--在此添加修改列表，参考第一行内容-->
 * <tr bgcolor="#CCCCFF"><td>1</td><td>lancer</td><td>15-2-5 下午4:34</td><td>新建内容</td></tr>
 */

@Entity
@Table(name = "i_web_cfg")
public class WebConfig implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer webid;

    @Column(nullable = false)
    String weburl;

    @Column(nullable = false)
    String commtype;

    @Column(nullable = false)
    String commpara;

    @Column(nullable = false)
    String logintype;

    @Column(nullable = false)
    String loginname;

    @Column(nullable = false)
    String loginpass;

    @Column(nullable = false)
    String logincode;

    @Column(nullable = false)
    String spidercount;

    @Column(nullable = false)
    String httpinterval;

    @Column(nullable = false)
    String parsertype;

    @Column(nullable = false)
    String parsercfg;

    @Column(nullable = false)
    Timestamp createdate;

    @Column(nullable = false)
    Timestamp updatedate;

    public Integer getWebid() {
        return webid;
    }

    public void setWebid(Integer webid) {
        this.webid = webid;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    public String getCommtype() {
        return commtype;
    }

    public void setCommtype(String commtype) {
        this.commtype = commtype;
    }

    public String getCommpara() {
        return commpara;
    }

    public void setCommpara(String commpara) {
        this.commpara = commpara;
    }

    public String getLogintype() {
        return logintype;
    }

    public void setLogintype(String logintype) {
        this.logintype = logintype;
    }

    public String getLoginname() {
        return loginname;
    }

    public void setLoginname(String loginname) {
        this.loginname = loginname;
    }

    public String getLoginpass() {
        return loginpass;
    }

    public void setLoginpass(String loginpass) {
        this.loginpass = loginpass;
    }

    public String getLogincode() {
        return logincode;
    }

    public void setLogincode(String logincode) {
        this.logincode = logincode;
    }

    public String getSpidercount() {
        return spidercount;
    }

    public void setSpidercount(String spidercount) {
        this.spidercount = spidercount;
    }

    public String getHttpinterval() {
        return httpinterval;
    }

    public void setHttpinterval(String httpinterval) {
        this.httpinterval = httpinterval;
    }

    public String getParsertype() {
        return parsertype;
    }

    public void setParsertype(String parsertype) {
        this.parsertype = parsertype;
    }

    public String getParsercfg() {
        return parsercfg;
    }

    public void setParsercfg(String parsercfg) {
        this.parsercfg = parsercfg;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    public Timestamp getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Timestamp updatedate) {
        this.updatedate = updatedate;
    }
}
