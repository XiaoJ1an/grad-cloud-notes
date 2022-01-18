package com.university.gradcloudnotes.entity.request;

import lombok.Data;

@Data
public class RegisterRequest {
    /**用户名*/
    private String userName;
    /**密码*/
    private String password;
    /**真实姓名*/
    private String name;
    /**性别*/
    private String sex;
    /**出生日期*/
    private String birthday;
    /**手机号码*/
    private String phoneNo;
    /**昵称*/
    private String nickName;
    /**头像地址*/
    private String picture;
}
