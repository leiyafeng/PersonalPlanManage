package cn.lyf.account.service.impl;

import cn.lyf.account.bean.Plan;
import cn.lyf.account.dao.plan.PlanDao;
import cn.lyf.account.service.PlanService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
}
