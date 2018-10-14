package cn.lyf.account.service;

import cn.lyf.account.bean.Plan;

public interface PlanService {
    /**
     * 创建计划
     * @param plan
     * @return
     */
    Boolean creatPlan(Plan plan);
}
