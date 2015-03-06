/**
 * @Company:
 * @Author: lancer
 * @Date: 15-2-10 下午4:09
 * @Version: 1.0
 */
package com.analoan.sprider;

import com.analoan.sprider.entity.WebConfig;
import com.analoan.sprider.httpservice.SpiderManager;
import com.analoan.sprider.service.WebConfigRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    private static final Log logger = LogFactory.getLog(ScheduledTasks.class);

    @Autowired
    WebConfigRepository repository;

    ExecutorService executor = Executors.newCachedThreadPool();


    //每1分钟执行一次
    @Scheduled(cron = "0 */1 *  * * * ")
    public void reportCurrentByCron() {
        Thread thread = null;
        // 查询到配置信息
        List<WebConfig> configList = repository.findAll();
        for (int i = 0; i < configList.size(); i++) {
            thread = SpiderManager.getThread(configList.get(i));
            executor.submit(thread);
            if (i % 200 == 0) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logger.info(e);
                }
            }
        }
    }

}

