package cn.lyf.account.service.impl;

import cn.lyf.account.bean.User;
import cn.lyf.account.dao.UserDao;
import cn.lyf.account.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
        User user = userDao.getUserByUserAccountAndPassword(userAccount,password);
        return user;
    }

    /**
     * 修改用户密码
     * @param oldPwd
     * @param newPwd
     * @param confirmPwd
     * @return
     */
    @Override
    public Boolean changePassword(String oldPwd, String newPwd, String confirmPwd) {
        //1.判断旧密码是否匹配
        return null;
    }
}
