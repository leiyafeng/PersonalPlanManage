package cn.lyf.account.controller.progress;

import cn.lyf.account.interceptor.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/progress")
@Auth
public class ProgressController {

    @RequestMapping("/")
    public String progressIndex(){
        return "/workbench/progress/progressIndex";
    }
}
