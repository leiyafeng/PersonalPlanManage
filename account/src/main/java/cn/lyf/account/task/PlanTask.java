package cn.lyf.account.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PlanTask {

    @Scheduled(cron = "0 0 12 * * ?")//每天12点触发定时任务
    public  void planStatusMonitor(){
        //查询和数据库所有计划，判断结束时间是否过期
    }
}
