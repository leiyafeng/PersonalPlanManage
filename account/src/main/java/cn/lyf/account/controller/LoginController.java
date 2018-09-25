package cn.lyf.account.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class LoginController {

    /**
     * 登录首页
     * @return
     */
    @GetMapping("/index")
    public String hello(){
        return "login";
    }



}
