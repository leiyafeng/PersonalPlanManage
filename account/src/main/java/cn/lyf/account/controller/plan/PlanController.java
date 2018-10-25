package cn.lyf.account.controller.plan;

import cn.lyf.account.bean.Plan;
import cn.lyf.account.bean.User;
import cn.lyf.account.dto.AjaxResponseDTO;
import cn.lyf.account.interceptor.Auth;
import cn.lyf.account.service.PlanService;
import cn.lyf.account.util.DateUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.lyf.account.util.Constants.USER_SESSION_KEY;

@Slf4j
@Controller
@Auth
@RequestMapping("/plan")
public class PlanController {
    @Autowired
    private PlanService planService;

    /**
     * 计划管理首页
     * @return
     */
    @RequestMapping("/")
    public String planIndex(){
        return "/workbench/plan/planIndex";
    }

    /**
     * 创建计划
     * @param plan
     */
    @RequestMapping("/creatPlan")
    @ResponseBody
    public String creatPlan(@RequestBody Plan plan,HttpServletRequest request){
        log.info("进入创建计划controller");
        AjaxResponseDTO ajaxResponseDTO = new AjaxResponseDTO();
        User user = (User) request.getSession().getAttribute(USER_SESSION_KEY);
        Integer userId = user.getId();
        plan.setUserId(userId);

        //1.1判断参数
        if(StringUtils.isBlank(plan.getGoal())){
            //1.1.1计划名称为空
            ajaxResponseDTO.buildError("计划名称不能为空",null);
        }else if(null == plan.getPlanPriority()){
            //1.1.2优先级为空
            ajaxResponseDTO.buildError("请选择优先级！",null);
        }else{
            if(null != plan.getPlanBegainTime() && null != plan.getPlanEndTime()){
                //1.1.3开始时间都不为空时，开始时间不能大于结束时间
                String begain = DateUtils.dateToString(plan.getPlanBegainTime(),DateUtils.YYYYMMDD);
                String end = DateUtils.dateToString(plan.getPlanEndTime(),DateUtils.YYYYMMDD);
                if(Integer.valueOf(begain)>Integer.valueOf(end)){
                    ajaxResponseDTO.buildError("计划开始时间不能大于结束时间",null);
                }else{
                    //2.1添加计划状态
                    if(plan.getPlanBegainTime() != null){
                        //进行中状态
                        plan.setPlanStatus(2);
                    }else{
                        //未开始状态
                        plan.setPlanStatus(6);
                    }
                    //2.2调service创建plan
                    Boolean b = planService.creatPlan(plan);
                    if(b){
                        ajaxResponseDTO.buildSucess(null,"创建计划成功");
                        log.info("创建计划成功");
                    }else{
                        ajaxResponseDTO.buildError("创建计划失败，请稍后再试！",null);
                        log.error("创建计划失败");
                    }
                }
            }else{
                //2.1添加计划状态
                if(plan.getPlanBegainTime() != null){
                    //进行中状态
                    plan.setPlanStatus(2);
                }else{
                    //未开始状态
                    plan.setPlanStatus(6);
                }
                //2.2调service创建plan
                Boolean b = planService.creatPlan(plan);
                if(b){
                    ajaxResponseDTO.buildSucess(null,"创建计划成功");
                    log.info("创建计划成功");
                }else{
                    ajaxResponseDTO.buildError("创建计划失败，请稍后再试！",null);
                    log.error("创建计划失败");
                }
            }
        }
        String json = JSONObject.toJSONString(ajaxResponseDTO);
        return json;
    }

    @RequestMapping("/queryPlanList")
    @ResponseBody
    public String queryPlanList(Integer pageSize, Integer pageNumber){
        log.info("进入分页查询计划列表页面,获取的limit="+pageSize+"offset="+pageNumber);
        List<Plan> planList = planService.findAllPlanByPage(pageSize,pageNumber);
        log.info("分页查询计划列表结果是："+planList);
        //获取列表总条数
        int total = planService.getTotal();
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",planList);
        return  JSON.toJSONString(map);
    }

}
