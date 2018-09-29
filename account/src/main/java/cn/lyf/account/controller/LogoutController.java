package cn.lyf.account.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

import static cn.lyf.account.util.Constants.USER_SESSION_KEY;

@Slf4j
@Controller
public class LogoutController {

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        //清除session返回首页
        request.getSession().removeAttribute(USER_SESSION_KEY);
        log.info("退出系统！");
        return "/login";
    }
}
