package cn.lyf.account.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/main")
public class MainController {

    @RequestMapping("/")
    public String mianIndex(){
        return "/workbench/main/mainIndex";
    }
}
