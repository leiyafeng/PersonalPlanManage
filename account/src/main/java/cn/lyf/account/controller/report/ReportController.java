package cn.lyf.account.controller.report;

import cn.lyf.account.interceptor.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/report")
@Auth
public class ReportController {

    @RequestMapping("/")
    public String reportIndex(){
        return "/workbench/report/reportIndex";
    }
}
