package cn.lyf.account.dao;

import cn.lyf.account.bean.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface StudentDao {

    /**
     * 根据id获取学生的信息
     * @param id
     * @return
     */
    @Select("select * from t_student where id = #{id}")
    Student getStudentById(@Param("id") Integer id);
}
