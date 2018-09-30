package cn.lyf.account.controller.personal;

import cn.lyf.account.interceptor.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/personal")
@Auth
public class PersonalController {


    @RequestMapping("/")
    public String personalIndex(){
        log.info("进入个人主页");
        return "/workbench/personal/personal";
    }

}
