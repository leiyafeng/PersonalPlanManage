package cn.lyf.account.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    /**
     * 进入首页（登录页）
     * @return
     */
    @RequestMapping("/index")
    public String index(){
        return "login";
    }
}
