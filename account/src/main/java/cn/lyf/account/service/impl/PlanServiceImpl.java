package cn.lyf.account.service.impl;

import cn.lyf.account.dao.plan.PlanDao;
import cn.lyf.account.dto.QueryPlanListDTO;
import cn.lyf.account.po.Plan;
import cn.lyf.account.service.PlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PlanServiceImpl implements PlanService {
    @Resource
    private PlanDao planDao;

    /**
     * 创建计划
     * @param plan
     * @return
     */
    @Transactional
    @Override
    public Boolean creatPlan(Plan plan) {
        int i = planDao.insertPlan(plan);
        if(i>0){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public List<Plan> findAllPlanByPage(Integer pageSize, Integer pageNumber, Integer userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("start",pageNumber);
        map.put("end",pageSize);
        map.put("userId",userId);
        List<Plan> planList = planDao.findAllPlanByPage(map);
        return planList;
    }

    /**
     * 根据userId获取计划总条数
     * @return
     */
    @Override
    public int getTotal(QueryPlanListDTO queryPlanListDTOber) {
        return planDao.getTotal(queryPlanListDTOber);
    }

    /**
     * 根据id修改计划
     * @param plan
     * @return
     */
    @Transactional
    @Override
    public Boolean updatePlanById(Plan plan) {
        Boolean flage = false;
        int a = planDao.updatePlanById(plan);
        if(a>0){
            flage = true;
        }
        return flage;
    }

    /**
     * 根据条件查询计划列表
     * @param queryPlanListDTO
     * @return
     */
    @Override
    public List<Plan> findPlanByOptions(QueryPlanListDTO queryPlanListDTO) {
        List<Plan> plans = planDao.queryPlanByOptions(queryPlanListDTO);
        return plans;
    }

    /**
     * 根据id查询计划详情
     * @param id
     * @return
     */
    @Override
    public Plan findPlanById(Integer id) {
        Plan plan = new Plan();
        plan = planDao.queryPlanById(id);
        return plan;
    }

    /**
     * 根据计划状态查询计划列表
     * @param planStatus
     * @return
     */
    @Override
    public List<Plan> findPlanByPlanStatus(Integer planStatus) {

        List<Plan> plans = planDao.queryPlanByPlanStatus(planStatus);
        return plans;
    }

    /**
     * 申请延期
     * @param planId 计划id
     * @param days 申请的天数
     * @return
     */
    @Override
    public Boolean applyDelay(Integer planId, Integer days) {
        return null;
    }
}
