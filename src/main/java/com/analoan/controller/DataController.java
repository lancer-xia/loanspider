/**
 * @Company:
 * @Author: lancer
 * @Date: 15-2-5 下午4:32
 * @Version: 1.0
 */
package com.analoan.controller;

import com.analoan.entity.UserInfo;
import com.analoan.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b>DataController</b>
 * <p><b>详细说明：</b></p>
 * 在这里添加详细说明
 * <p><b>修改列表：</b></p>
 * <table width="" cellspacing=1 cellpadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!--在此添加修改列表，参考第一行内容-->
 * <tr bgcolor="#CCCCFF"><td>1</td><td>lancer</td><td>15-2-5 下午4:32</td><td>新建内容</td></tr>
 */

@EnableAutoConfiguration
@RestController
@RequestMapping("/data")
public class DataController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping("/{id}")
    private String view(@PathVariable("id") int id) {
        UserInfo userInfo = userRepository.findOne(id);
        if (null == userInfo) {
            userRepository.findAll();
        }
        return userInfo.toString();
    }


}
