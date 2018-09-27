package cn.lyf.account.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/plan")
public class PlanController {

    @RequestMapping("/")
    public String planIndex(){
        return "/workbench/plan/planIndex";
    }
}
