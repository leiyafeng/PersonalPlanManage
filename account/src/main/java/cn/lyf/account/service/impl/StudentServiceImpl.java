package cn.lyf.account.service.impl;

import cn.lyf.account.bean.Student;
import cn.lyf.account.dao.StudentDao;
import cn.lyf.account.service.StudentService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class StudentServiceImpl implements StudentService {


    @Resource
    private StudentDao studentDao;
    @Override
    public Student getStudentById(Integer id) {

         return studentDao.getStudentById(id);
    }
}
