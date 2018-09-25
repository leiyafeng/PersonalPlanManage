package cn.lyf.account.controller;

import cn.lyf.account.bean.Student;
import cn.lyf.account.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
