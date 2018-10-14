package cn.lyf.account.dao.plan;

import cn.lyf.account.bean.Plan;

public interface PlanDao {
    /**
     * 创建计划
     * @param plan
     * @return
     */
    int insertPlan(Plan plan);
}
