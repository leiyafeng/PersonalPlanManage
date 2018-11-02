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
     * 根据条件分页查询计划列表
     * @param queryPlanListDTOber
     * @return
     */
    List<Plan> findPlanByOptions(QueryPlanListDTO queryPlanListDTOber);

    /**
     * 根据id查询计划详情
     * @param id
     * @return
     */
    Plan findPlanById(Integer id);

    /**
     * 根据计划状态查询计划列表（不分页）
     * @param planStatus
     * @return
     */
    List<Plan> findPlanByPlanStatus(Integer planStatus);

    /**
     * 申请延期
     * @param planId 计划id
     * @param days 申请的天数
     * @return
     */
    Boolean applyDelay(Integer planId , Integer days);
}
