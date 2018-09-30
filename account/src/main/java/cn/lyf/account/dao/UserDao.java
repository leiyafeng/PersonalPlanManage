package cn.lyf.account.dao;

import cn.lyf.account.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserDao {

    /**
     * 根据账号密码查询用户
     * @param userAccount
     * @param password
     * @return
     */
    @Select("select id as id,user_account as userAccount , user_password as password , " +
            "user_name as userName , creat_time as creatTime , update_time as updateTime" +
            " from t_user where user_account = #{userAccout} and user_password = #{password}")
    User getUserByUserAccountAndPassword(@Param("userAccout") String userAccount,@Param("password") String password);

    @Update("update t_user set user_password=#{password} where id=#{id}")
    Integer changePassword(@Param("id") Integer id,@Param("password") String newPwd);
}
