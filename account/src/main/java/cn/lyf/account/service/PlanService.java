package cn.lyf.account.service;

import cn.lyf.account.bean.Plan;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlanService {
    /**
     * 创建计划
     * @param plan
     * @return
     */
    @Transactional
    Boolean creatPlan(Plan plan);

    /**
     * 分页查询计划列表
     * @param start
     * @param limit
     * @return
     */
    List<Plan> findAllPlanByPage(Integer start,Integer limit,Integer userId);

    /**
     * 获取计划列表总条数
     * @return
     */
    int getTotal(Integer userId);

    /**
     * 修改计划根据Id
     * @param plan
     * @return
     */
    @Transactional
    Boolean updatePlanById(Plan plan);
}
