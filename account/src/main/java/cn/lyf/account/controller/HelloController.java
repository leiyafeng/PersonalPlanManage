package cn.lyf.account.controller;

import cn.lyf.account.bean.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class HelloController {
    @Autowired
    private StudentService studentService;


    @RequestMapping("/getStudent")
    public String getStudent(){
        System.out.println("请求到此");
        Student stu = studentService.getStudentById(1);
        //model.addAttribute(stu);
        if(stu !=null){


            System.out.println("stu="+stu.getName());
        }
        return stu.getName();
    }
}
