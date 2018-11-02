package cn.lyf.account.task;

import cn.lyf.account.po.Plan;
import cn.lyf.account.service.PlanService;
import cn.lyf.account.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class PlanTask {

    @Autowired
    private PlanService planService;

    /**
     * 定时更改未开始计划状态
     */
    @Scheduled(cron="*/5 * * * * ?")//每5秒钟执行一次
    //@Scheduled(cron = "0 0 1 * * ?")//每天凌晨1点触发定时任务
    @Transactional
    public  void planStatusMonitor(){
        log.info("=============定时任务开始执行");
        //1.1 查询计划状态为6,未开始的计划
        try{
            List<Plan> plans = planService.findPlanByPlanStatus(6);
            //1.2 遍历计划根据开始时间判断是否更改进行中状态
            if(plans != null){
                log.info("查询到未开始的计划条数："+plans.size());
                int i = 0;
                for (Plan plan : plans) {
                    Date date = plan.getPlanBegainTime();
                    //和当前时间进行比较
                    Boolean b = DateUtils.compareDate1(new Date(),date,DateUtils.YYYYMMDD);
                    if(b){
                        //更改状态为进行中
                        Plan p = new Plan();
                        p.setId(plan.getId());
                        p.setPlanStatus(2);
                        planService.updatePlanById(p);
                        i++;
                    }
                }
                log.info("更改了未开始计划的条数是："+i);
            }
        }catch (Exception e){
            log.error("定时更改未开始计划状态异常",e);
        }
        log.info("定时任务结束执行=====================");
    }

    /**
     * 定时更改到期计划状态，申请延期
     */
    @Scheduled(cron="*/5 * * * * ?")//每5秒钟执行一次
    @Transactional
    public void planStatusDelay(){
    log.info("执行了延期状态更改开始==========");
        try{
            //1.1 查询计划状态为进行中2，和延期5计划列表
            List<Plan> plans1 = planService.findPlanByPlanStatus(2);
            List<Plan> plans2 = planService.findPlanByPlanStatus(5);
            //1.2 遍历计划 ，更改状态
            log.info("查询到进行中状态计划条数是:"+plans1.size()+"==========查询到延期状态计划条数是:"+plans2.size());
            if(plans1 != null){
                //进行中计划遍历
                for (Plan plan:plans1){
                    //进行中计划,比较结束时间和当前时间大小
                    Boolean b = DateUtils.compareDate(new Date(),plan.getPlanEndTime(),DateUtils.YYYYMMDD);
                    int i = 0;
                    if(b){
                        //当前时间大于结束时间更改状态延期,进行延期
                        Plan p = new Plan();
                        p.setId(plan.getId());
                        p.setPlanStatus(5);
                        //结束日期在当前日期基础上加1天
                        Date endTime = DateUtils.nextDay(new Date());
                        p.setPlanEndTime(endTime);
                        //延期次数加一，进行中计划延期次数默认为0
                        p.setPlanDelayCount(1);
                        planService.updatePlanById(p);
                        i++;
                    }
                    log.info("更改了进行中计划的条数是："+i);
                }
            }
            if(plans2!=null){
                //延期计划遍历
                int i= 0;
                for(Plan plan:plans2){
                    //比较结束时间和当前时间大小
                    Boolean b = DateUtils.compareDate(new Date(),plan.getPlanEndTime(),DateUtils.YYYYMMDD);
                    if(b){
                        Plan p = new Plan();
                        p.setId(plan.getId());
                        //当前时间大于结束时间,进行延期申请
                        if(plan.getPlanDelayCount()>=3){
                            //延期申请超过3次，直接改为结束未完成状态
                            p.setPlanStatus(3);
                        }else{
                            //结束日期在当前日期基础上加1天
                            Date endTime = DateUtils.nextDay(new Date());
                            p.setPlanEndTime(endTime);
                            //延期次数加一，进行中计划延期次数默认为0
                            p.setPlanDelayCount(plan.getPlanDelayCount()+1);
                        }
                        planService.updatePlanById(p);
                        i++;
                    }
                }
                log.info("更改了延期计划的条数是："+i);
            }

        }catch (Exception e){
            log.error("定时更改进行中和延期计划状态异常",e);
        }
        log.info("执行了延期状态更改结束==========");

    }
    //@Scheduled(cron="*/5 * * * * ?")//每5秒钟执行一次
    public void test(){
        System.out.println("执行了一次定时任务==========");
    }
}
