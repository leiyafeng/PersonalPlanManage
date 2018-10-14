package cn.lyf.account.service.impl;

import cn.lyf.account.bean.User;
import cn.lyf.account.dao.UserDao;
import cn.lyf.account.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;

    /**
     * 根据账号密码查询用户
     * @param userAccount
     * @param password
     * @return
     */
    @Override
    public User getUserByUserAccountAndPassword(String userAccount, String password) {
        User user = new User();
        user.setUserAccount(userAccount);
        user.setPassword(password);
        User resUser = userDao.findUserByUser(user);

        return resUser;
    }


    /**
     * 修改用户密码
     * @param id
     * @param newPwd
     * @return
     */
    @Override
    public Boolean changePassword(Integer id, String newPwd) {
        User user = new User();
        user.setId(id);
        user.setPassword(newPwd);
        int count = userDao.updateUserByUser(user);
        if(count>0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 更新用户最近登陆时间
     * @param id
     * @param date
     */
    @Override
    public void updateLastLoginById(Integer id, Date date) {
        User user = new User();
        user.setId(id);
        user.setLastLogin(date);
        userDao.updateUserByUser(user);
    }
}
