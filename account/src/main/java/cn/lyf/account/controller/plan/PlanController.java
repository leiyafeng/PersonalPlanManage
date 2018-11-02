package cn.lyf.account.controller.plan;

import cn.lyf.account.dto.AjaxResponseDTO;
import cn.lyf.account.dto.QueryPlanListDTO;
import cn.lyf.account.interceptor.Auth;
import cn.lyf.account.po.Plan;
import cn.lyf.account.po.User;
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
        }else if(null == plan.getPlanBegainTime() || null == plan.getPlanBegainTime()){
                //1.1.3 开始时间或结束时间为空
            ajaxResponseDTO.buildError("亲，开始日期或预计结束日期不能为空啦！",null);
        }else{
            Boolean bd = DateUtils.compareDate(plan.getPlanBegainTime(),new Date(),DateUtils.YYYYMMDD);
            if(bd){
                //开始日期大于当前日期
                //未开始状态
                plan.setPlanStatus(6);
            }else{
                //进行中状态
                plan.setPlanStatus(2);
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
        String json = JSONObject.toJSONString(ajaxResponseDTO);
        return json;
    }

    /**
     * 查询计划列表
     * @param pageSize
     * @param pageNumber
     * @return
     */
    @Deprecated
    @RequestMapping("/queryPlanList")
    @ResponseBody
    public String queryPlanList(Integer pageSize, Integer pageNumber,HttpServletRequest request){
        log.info("进入分页查询计划列表页面,获取的limit="+pageSize+"offset="+pageNumber);
        User user = (User) request.getSession().getAttribute(USER_SESSION_KEY);
        Integer userId = user.getId();
        List<Plan> planList = planService.findAllPlanByPage(pageSize,pageNumber,userId);
        log.info("分页查询计划列表结果是："+planList);
        //获取列表总条数
        /*Plan p = new Plan();
        p.setUserId(userId);*/
        QueryPlanListDTO q = new QueryPlanListDTO();
        q.setUserId(userId);
        int total = planService.getTotal(q);
        Map<String,Object> map = new HashMap<>();
        map.put("total",total);
        map.put("rows",planList);
        return  JSON.toJSONString(map);
    }

    /**
     * 修改计划接口
     * @param plan
     * @return
     */
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
                if(null !=plan.getPlanBegainTime()){
                    //开始时间不为空时
                    if(null != plan.getPlanEndTime()){
                        //1.1.1判断开始时间不能小于结束时间
                        Boolean b = DateUtils.compareDate(plan.getPlanBegainTime(),plan.getPlanEndTime(),DateUtils.YYYYMMDD);
                        if(b){
                            //结束时间小于开始时间，不符合规定
                            ajaxResponseDTO.setCode(2);
                            ajaxResponseDTO.setMsg("亲，计划开始时间不能大于计划结束时间哦！");
                        }else{
                            //时间符合规定，修改计划
                            Boolean b0 = DateUtils.compareDate(new Date(),plan.getPlanEndTime(),DateUtils.YYYYMMDD);
                            if(!b0){
                                //开始时间大于当前时间

                            }
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


                }
                //1.1.1判断开始时间不能小于结束时间
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

    /**
     * 废弃计划接口
     * @param plans
     * @return
     */
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

    /**
     * 根据条件查询计划列及总条数
     * @param pageSize
     * @param pageNumber
     * @param goal
     * @param planPriority
     * @param planStatus
     * @param start1
     * @param start2
     * @param end1
     * @param end2
     * @param request
     * @return
     */
    @RequestMapping("/queryPlanByOptions")
    @ResponseBody
    public String queryPlanByOptions(Integer pageSize,Integer pageNumber,String goal,Integer planPriority,Integer planStatus,
                                     String start1,String start2,String end1,String end2, HttpServletRequest request){
        log.info("条件查询controller接收参数---------pageSize="+pageSize+"---pagenumber="+pageNumber+"--goal="+goal+"---planPriority"+planPriority+
        "----planStatus="+planStatus+"----start1="+start1+"-----start2="+start2+"----end1="+end1+"---end2="+end2);
        User user = (User) request.getSession().getAttribute(USER_SESSION_KEY);
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> resultMap = new HashMap<>();
        List<Plan> plans = new ArrayList<>();
        AjaxResponseDTO ajaxResponseDTO = new AjaxResponseDTO();
        try{
            Integer userId = user.getId();
            QueryPlanListDTO q = new QueryPlanListDTO();
            q.setUserId(userId);
            q.setPageSize(pageSize);
            q.setPageNumber(pageNumber);
            if(!StringUtils.isBlank(start1)){
                q.setStart1(DateUtils.stringToDate(start1,DateUtils.YYYY_MM_DD));
            }
            if(!StringUtils.isBlank(start2)){
                q.setStart2(DateUtils.stringToDate(start2,DateUtils.YYYY_MM_DD));
            }
            if(!StringUtils.isBlank(end1)){
                q.setEnd1(DateUtils.stringToDate(end1,DateUtils.YYYY_MM_DD));
            }
            if(!StringUtils.isBlank(end2)){
                q.setEnd2(DateUtils.stringToDate(end2,DateUtils.YYYY_MM_DD));
            }
            q.setGoal(goal);
            q.setPlanPriority(planPriority);
            q.setPlanStatus(planStatus);
            //查询计划列表
            plans =  planService.findPlanByOptions(q);
            //查询计划条数
            int total = planService.getTotal(q);
            map.put("total",total);
            map.put("rows",plans);


        }catch (Exception e){
            log.error("根据条件查询计划异常",e);
        }
        log.info("查询结果是："+JSON.toJSONString(map));
        return  JSON.toJSONString(map);
    }

}
