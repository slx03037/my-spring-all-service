package com.shenlx.xinwen.springboot.schedule.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @program: my-spring-all-service
 * @description:
 * @author: shenlx
 * @create: 2023-02-04 17:01
 **/
@Component
public class HeartbeatJob {
    private static final Logger logger = LoggerFactory.getLogger(HeartbeatJob.class);

    /**
     * 检查状态1
     */
    @Scheduled(cron = "0 30 12 * * ?")
    public void checkState1() {
        logger.info(">>>>> cron中午12:30上传检查开始....");
        logger.info(">>>>> cron中午12:30上传检查完成....");
    }

    /**
     * 检查状态2
     */
    @Scheduled(cron = "0 0 18 * * ?")
    public void checkState2() {
        logger.info(">>>>> cron晚上18:00上传检查开始....");
        logger.info(">>>>> cron晚上18:00上传检查完成....");
    }

    /**
     * 检查状态2
     */
    @Scheduled(cron = "10 * * * * ?")
    public void checkState3() {
        logger.info(">>>>> 每10s执行一次....");
        logger.info(">>>>> 执行结束....");
    }
}
