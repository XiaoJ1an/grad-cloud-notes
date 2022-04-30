package com.university.gradcloudnotes.entity.request;

import lombok.Data;

@Data
public class LoginRequest {
    /**用户名*/
    private String userName;
    /**密码*/
    private String password;
    //手机号码
    private String phoneNo;
    //验证码
    private String verCode;
}
