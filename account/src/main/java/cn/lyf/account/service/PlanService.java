package cn.lyf.account.service;

import cn.lyf.account.dto.QueryPlanListDTO;
import cn.lyf.account.po.Plan;
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
    int getTotal(QueryPlanListDTO queryPlanListDTOber);

    /**
     * 修改计划根据Id
     * @param plan
     * @return
     */
    @Transactional
    Boolean updatePlanById(Plan plan);

    /**
     * 根据条件查询计划列表
     * @param queryPlanListDTOber
     * @return
     */
    List<Plan> findPlanByOptions(QueryPlanListDTO queryPlanListDTOber);
}
