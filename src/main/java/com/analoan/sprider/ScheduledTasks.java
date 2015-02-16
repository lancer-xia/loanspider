/**
 * @Company:
 * @Author: lancer
 * @Date: 15-2-10 下午4:09
 * @Version: 1.0
 */
package com.analoan.sprider;

import com.analoan.sprider.entity.WebConfig;
import com.analoan.sprider.service.WebConfigRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * <b>test</b>
 * <p><b>详细说明：</b></p>
 * 在这里添加详细说明
 * <p><b>修改列表：</b></p>
 * <table width="" cellspacing=1 cellpadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!--在此添加修改列表，参考第一行内容-->
 * <tr bgcolor="#CCCCFF"><td>1</td><td>lancer</td><td>15-2-10 下午4:09</td><td>新建内容</td></tr>
 */
@Component
@Configurable
@EnableScheduling
public class ScheduledTasks {

    @Autowired
    WebConfigRepository repository;

//    private static Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final Log logger = LogFactory.getLog(ScheduledTasks.class);

//    @Scheduled(fixedRate = 1000 * 30)
//    public void reportCurrentTime() {
//        System.out.println("Scheduling Tasks Examples: The time is now " + dateFormat().format(new Date()));
//    }

    //每1分钟执行一次
    @Scheduled(cron = "0 */1 *  * * * ")
    public void reportCurrentByCron() {
        List<WebConfig> wcList = repository.findAll();
        logger.debug(wcList.size());
    }

    private SimpleDateFormat dateFormat() {
        return new SimpleDateFormat("HH:mm:ss");
    }
}

