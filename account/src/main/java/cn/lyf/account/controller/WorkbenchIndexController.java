package cn.lyf.account.controller;

import cn.lyf.account.interceptor.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@Auth
public class WorkbenchIndexController {

    @RequestMapping("/workbench/workIndex")
    public String workIndex(){
        return "/workbench/workIndex";
    }
}
