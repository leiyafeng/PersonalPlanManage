package cn.lyf.account.dao;

import cn.lyf.account.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

@Mapper
public interface UserDao {

    /**
     * 根据账号密码查询用户
     * @param userAccount
     * @param password
     * @return
     */
    @Select("select id as id,user_account as userAccount , user_password as password , " +
            "user_name as userName , last_login as lastLogin, creat_time as creatTime , update_time as updateTime" +
            " from t_user where user_account = #{userAccout} and user_password = #{password}")
    User getUserByUserAccountAndPassword(@Param("userAccout") String userAccount,@Param("password") String password);

    /**
     * 更新密码
     * @param id
     * @param newPwd
     * @return
     */
    @Update("update t_user set user_password=#{password} where id=#{id}")
    Integer changePassword(@Param("id") Integer id,@Param("password") String newPwd);

    /**
     * 修改最近登录时间
     * @param id
     * @param date
     * @return
     */
    @Update("update t_user set last_login = #{date} where id=#{id}")
    Integer changeLastLoginTime(@Param("id") Integer id , @Param("date") String date );
}
