package cn.lyf.account.service;

import cn.lyf.account.bean.User;

public interface UserService {
    /**
     * 根据账号密码查询用户
     * @param userAccount
     * @param password
     * @return
     */
    User getUserByUserAccountAndPassword(String userAccount , String password);

    /**
     * 修改用户密码
     * @param oldPwd
     * @param newPwd
     * @param confirmPwd
     * @return
     */
    Boolean changePassword(String oldPwd,String newPwd , String confirmPwd);
}
