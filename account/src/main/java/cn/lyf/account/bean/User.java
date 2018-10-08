package cn.lyf.account.bean;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Integer id;
    private String userAccount;
    private String password;
    private String userName;
    private Date lastLogin;
    private Date creatTime;
    private Date updateTime;
}
