package cn.lyf.account.dao;

import cn.lyf.account.po.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    User findUserById(Integer id);

    /**
     * 根据user查询用户
     * @param user
     * @return
     */
    User findUserByUser(User user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUserByUser(User user);
}
