package cn.lyf.account.service.impl;

import cn.lyf.account.bean.Plan;
import cn.lyf.account.dao.plan.PlanDao;
import cn.lyf.account.service.PlanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public int getTotal(Integer userId) {
        Map<String, Object > reqMap = new HashMap<>();
        reqMap.put("userId",userId);
        return planDao.getTotal(reqMap);
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
}
