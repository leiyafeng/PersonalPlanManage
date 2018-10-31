package cn.lyf.account.controller.plan;

import cn.lyf.account.bean.Plan;
import cn.lyf.account.bean.User;
import cn.lyf.account.dto.AjaxResponseDTO;
import cn.lyf.account.interceptor.Auth;
import cn.lyf.account.service.PlanService;
import cn.lyf.account.util.DateUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
        plan.setCreatTime(new Date());
        //1.1判断参数
        if(StringUtils.isBlank(plan.getGoal())){
            //1.1.1计划名称为空
            ajaxResponseDTO.buildError("亲，计划名称不能为空哦",null);
        }else if(null == plan.getPlanPriority()){
            //1.1.2优先级为空
            ajaxResponseDTO.buildError("亲，请选择优先级啦！",null);
        }else{
            if(null != plan.getPlanBegainTime() && null != plan.getPlanEndTime()){
                //1.1.3开始时间都不为空时，开始时间不能大于结束时间
                String begain = DateUtils.dateToString(plan.getPlanBegainTime(),DateUtils.YYYYMMDD);
                String end = DateUtils.dateToString(plan.getPlanEndTime(),DateUtils.YYYYMMDD);
                if(Integer.valueOf(begain)>Integer.valueOf(end)){
                    ajaxResponseDTO.buildError("亲，计划开始时间不能大于结束时间哦",null);
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
                        ajaxResponseDTO.buildSucess(null,"耶，创建计划成功啦！");
                        log.info("创建计划成功");
                    }else{
                        ajaxResponseDTO.buildError("创建计划失败啦，请稍后再试下吧！",null);
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
                    ajaxResponseDTO.buildSucess(null,"耶，创建计划成功啦！");
                    log.info("创建计划成功");
                }else{
                    ajaxResponseDTO.buildError("创建计划失败啦，请稍后再试下吧！",null);
                    log.error("创建计划失败");
                }
            }
        }
        String json = JSONObject.toJSONString(ajaxResponseDTO);
        return json;
    }

    /**
     * 查询计划列表
     * @param pageSize
     * @param pageNumber
     * @return
     */
    @RequestMapping("/queryPlanList")
    @ResponseBody
    public String queryPlanList(Integer pageSize, Integer pageNumber,HttpServletRequest request){
        log.info("进入分页查询计划列表页面,获取的limit="+pageSize+"offset="+pageNumber);
        User user = (User) request.getSession().getAttribute(USER_SESSION_KEY);
        Integer userId = user.getId();
        List<Plan> planList = planService.findAllPlanByPage(pageSize,pageNumber,userId);
        log.info("分页查询计划列表结果是："+planList);
        //获取列表总条数
        Plan p = new Plan();
        p.setUserId(userId);
        int total = planService.getTotal(p);
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",planList);
        return  JSON.toJSONString(map);
    }

    @RequestMapping("/modifyPlan")
    @ResponseBody
    public String modifyPlan(@RequestBody Plan plan ){
        log.info("进入修改计划controller-----获取到plan是"+plan);
        AjaxResponseDTO ajaxResponseDTO = new AjaxResponseDTO();
        try{
            //1.判断参数
            //1.0.1 计划名称，优先级不能为空
            if(StringUtils.isBlank(plan.getGoal()) || null == plan.getPlanPriority()){
                ajaxResponseDTO.setCode(2);
                ajaxResponseDTO.setMsg("亲，计划名称和计划优先级都不可以为空哦");
            }else{
                //1.1.1判断开始时间不能小于结束时间
                //  begainDate = DateUtils.dateToString(plan.getPlanBegainTime(),DateUtils.YYYYMMDD);
                //String  endDate = DateUtils.dateToString(plan.getPlanEndTime(),DateUtils.YYYYMMDD);
                Boolean b = DateUtils.compareDate(plan.getPlanBegainTime(),plan.getPlanEndTime(),DateUtils.YYYYMMDD);
                if(b){
                    //结束时间小于开始时间，不符合规定
                    ajaxResponseDTO.setCode(2);
                    ajaxResponseDTO.setMsg("亲，计划开始时间不能大于计划结束时间哦！");
                }else{
                    //时间符合规定，修改计划
                    //判断结束时间是否小于当前时间
                    Boolean b1 = DateUtils.compareDate(new Date(),plan.getPlanEndTime(),DateUtils.YYYYMMDD);
                    if(b1){
                        //结束时间小于当前时间，更改计划状态为已经结束
                        plan.setPlanStatus(1);
                    }
                    //1.2.1 修改计划
                    Boolean res = planService.updatePlanById(plan);
                    //1.2.2 判断修改是否成功
                    if(res){
                        ajaxResponseDTO.setCode(1);
                        ajaxResponseDTO.setMsg("耶，修改成功啦！祝您计划顺利！");
                    }else{
                        ajaxResponseDTO.setCode(2);
                        ajaxResponseDTO.setMsg("啊哦，修改出了点问题呢，请再重新试一下吧！");
                    }
                }
            }
        }catch (Exception e){
            log.error("修改计划异常",e);
            ajaxResponseDTO.setMsg("不好啦，粗大问题啦，修改失败啦，请稍后再来修改吧");
        }
        log.info("修改计划结束，修改结果是："+JSONObject.toJSONString(ajaxResponseDTO));
        return JSONObject.toJSONString(ajaxResponseDTO);

    }

    @RequestMapping("/discardPlan")
    @ResponseBody
    public String discardPlan(@RequestBody String plans){
        log.info("进入废弃计划controller-----获取到plan数组是"+plans);
        AjaxResponseDTO ajaxResponseDTO = new AjaxResponseDTO();
        try{
            //1.1 遍历数组，循环更改计划状态
            JSONArray plan = JSONArray.parseArray(plans);
            //判断是否存在已过期计划
            for(int j=0;j<plan.size();j++){
                Map<String,Object> m = (Map)plan.get(j);
                Long l = (Long) m.get("planEndTime");
                Date d = new Date(l);
                Boolean b = DateUtils.compareDate(new Date(),d,DateUtils.YYYYMMDD);
                if(b){
                    //过期计划
                    ajaxResponseDTO.setCode(2);
                    ajaxResponseDTO.setMsg("计划名字为<span style='color:red'>"+m.get("goal")+"</span>的计划已经过期啦，不能废弃！");
                    return JSONObject.toJSONString(ajaxResponseDTO);
                }
            }
            //不存在过期计划时，重新遍历更改状态
            for(int i=0;i<plan.size();i++){
                Map<String,Object> m = (Map)plan.get(i);
                Plan p = new Plan();
                p.setId((Integer) m.get("id"));
                p.setPlanStatus(4);
                //1.1.1 修改计划
                Boolean res = planService.updatePlanById(p);
                //1.1.2 判断修改是否成功
                if(!res){
                    throw new Exception("废弃计划失败了");
                }
            }
            ajaxResponseDTO.setCode(1);
            ajaxResponseDTO.setMsg("耶，废弃计划成功啦！祝您计划顺利！");
        }catch (Exception e){
            log.error("废弃计划异常",e);
            ajaxResponseDTO.setCode(2);
            ajaxResponseDTO.setMsg("啊哦，废弃计划出了点问题呢，请再重新试一下吧！");
        }
        log.info("废弃计划结束，废弃结果是："+JSONObject.toJSONString(ajaxResponseDTO));
        return JSONObject.toJSONString(ajaxResponseDTO);
    }

    @RequestMapping("/queryPlanByOptions")
    @ResponseBody
    public String queryPlanByOptions(String goal,Integer planPriority,Integer planStatus,
                                     Integer pageSize, Integer pageNumber, HttpServletRequest request){
        log.info("进入根据条件查询计划controller，获取到的参数是【goal="+goal+",planPriority="+planPriority+",planStatus="+
                planStatus+"pagesize="+pageSize+"pageNumber="+pageNumber+"】");
        User user = (User) request.getSession().getAttribute(USER_SESSION_KEY);
        Map<String,Object> map = new HashMap<>();
        List<Plan> plans = new ArrayList<>();
        try{
            Integer userId = user.getId();
            Plan p = new Plan();
            p.setUserId(userId);
            p.setGoal(goal);
            p.setPlanPriority(planPriority);
            p.setPlanStatus(planStatus);
            //查询计划列表
            plans =  planService.findPlanByOptions(p,pageSize,pageNumber);
            //查询计划条数
            int total = planService.getTotal(p);
            map.put("total",total);
            map.put("rows",plans);


        }catch (Exception e){
            log.error("根据条件查询计划异常",e);
        }
        log.info("查询结果是："+JSON.toJSONString(plans));
        return  JSON.toJSONString(map);
    }

}
