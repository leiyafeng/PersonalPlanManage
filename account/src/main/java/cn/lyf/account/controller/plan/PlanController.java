package cn.lyf.account.controller.plan;

import cn.lyf.account.interceptor.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@Auth
@RequestMapping("/plan")
public class PlanController {

    @RequestMapping("/")
    public String planIndex(){
        return "/workbench/plan/planIndex";
    }
}
