package cn.lyf.account.controller.login;


import cn.lyf.account.bean.User;
import cn.lyf.account.dao.UserDao;
import cn.lyf.account.interceptor.Auth;
import cn.lyf.account.service.UserService;
import cn.lyf.account.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static cn.lyf.account.util.Constants.USER_SESSION_KEY;


@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    UserService userService;
    @Resource
    private UserDao userDao;



    /**
     * 登录
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public Map<String,Object> login(@RequestParam ("userAccount") String userAccount,
                                    @RequestParam ("password") String password,
                                    HttpServletRequest request){
        //1.获取参数
        //2.调service，根据userAccount和password查询用户是否存在
        Map<String,Object> model = new HashMap<>();
        if(StringUtils.isBlank(userAccount) || StringUtils.isBlank(password) ){
            model.put("success",false);
            model.put("message","请填写账号和密码");
        }else{
            User user = userService.getUserByUserAccountAndPassword(userAccount,password);
            if(user == null){
                //3.用户不存在，返回用户或密码错误
                model.put("success",false);
                model.put("message","用户名或密码错误");
            }else{
                //4.用户存在，将user放入session中，返回个人主页
                request.getSession().setAttribute(USER_SESSION_KEY,user);
                //5.更新用户最近登录时间
                String date = DateUtils.getStringDate();
                userDao.changeLastLoginTime(user.getId(),date);
                log.info("user="+user);
                log.info("userSession="+request.getSession().getAttribute("user"));
                model.put("success",true);
            }
        }
        return model;
    }

    /**
     * 修改密码
     * @param oldPwd
     * @param newPwd
     * @param confirmPwd
     * @param request
     */
    @Auth
    @RequestMapping("/changePassword")
    @ResponseBody
    public Map<String,Object> changePassword(@RequestParam("oldPwd") String oldPwd,
                               @RequestParam("newPwd") String newPwd,
                               @RequestParam("confirmPwd") String confirmPwd,
                               HttpServletRequest request){
        //1.判断旧密码是否匹配
        Map<String,Object> map = new HashMap<>();
        User user = (User) request.getSession().getAttribute(USER_SESSION_KEY);
        if(!user.getPassword().equals(oldPwd)){
            map.put("success",false);
            map.put("msg","请输入正确的旧密码");
            return map;
        }
        //2.判断新密码两次输入是否一致
        if(StringUtils.isNotBlank(newPwd) && StringUtils.isNotBlank(confirmPwd)){
            if(newPwd.equals(confirmPwd)){
                //3.修改密码操作
                Boolean b  = userService.changePassword(user.getId() , newPwd);
                if(b){
                    map.put("success",true);
                    map.put("msg","修改成功，请重新登陆！");
                    return map;
                }else{
                    map.put("success",false);
                    map.put("msg","修改失败，请重试！");
                    return map;
                }
            }else{
                map.put("success",false);
                map.put("msg","输入的新密码和确认密码不一致！");
                return map;
            }
        }else{
            map.put("success",false);
            map.put("msg","输入的新密码或确认密码不能为空！");
            return map;
        }

    }


}
