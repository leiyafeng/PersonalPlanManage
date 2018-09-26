package cn.lyf.account.controller;


import cn.lyf.account.bean.User;
import cn.lyf.account.interceptor.Auth;
import cn.lyf.account.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;



@Slf4j
@Controller
public class LoginController {
    @Autowired
    UserService userService;


    /**
     * 登录首页
     * @return
     */
    @GetMapping("/")
    public String index(){
        return "login";
    }

    /**
     * 登录
     * @return
     */
    @RequestMapping("/login")
    public ModelAndView login(@RequestParam ("userAccount") String userAccount,
                              @RequestParam ("password") String password){
        //1.获取参数
        //2.调service，根据userAccount和password查询用户是否存在
        User user = userService.getUserByUserAccountAndPassword(userAccount,password);
        ModelAndView modelAndView =new ModelAndView();
        if(user == null){
            //3.用户不存在，返回用户或密码错误
            modelAndView.addObject("message","用户名或密码错误");
            modelAndView.setViewName("login");
        }else{
            //4.用户存在，将user放入session中，返回个人主页
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            request.getSession().setAttribute("user",user);
            modelAndView.setViewName("aa");
        }
        return modelAndView;
    }

    @Auth
    @RequestMapping("/ssaa")
    public @ResponseBody String aa(){
        return "hello,how are you ";
    }


}
