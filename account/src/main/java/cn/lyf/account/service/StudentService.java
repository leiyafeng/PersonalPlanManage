package cn.lyf.account.service;

import cn.lyf.account.bean.Student;

public interface StudentService {

    /**
     * 根据id获取学生信息
     * @param id
     * @return
     */
    Student getStudentById(Integer id);
}
