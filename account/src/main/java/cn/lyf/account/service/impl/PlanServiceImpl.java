package cn.lyf.account.service.impl;

import cn.lyf.account.bean.Plan;
import cn.lyf.account.dao.plan.PlanDao;
import cn.lyf.account.service.PlanService;
import org.springframework.stereotype.Service;

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
    public List<Plan> findAllPlanByPage(Integer pageSize, Integer pageNumber) {
        Map<String,Object> map = new HashMap<>();
        map.put("start",pageNumber);
        map.put("end",pageSize);
        List<Plan> planList = planDao.findAllPlanByPage(map);
        return planList;
    }

    @Override
    public int getTotal() {

        return planDao.getTotal();
    }
}
