package com.university.gradcloudnotes.service;

import com.university.gradcloudnotes.entity.request.LoginRequest;
import com.university.gradcloudnotes.entity.request.RegisterRequest;
import com.university.gradcloudnotes.entity.response.UniversalResponse;
import com.university.gradcloudnotes.jpa.CnUser;
import com.university.gradcloudnotes.repository.CnUserRepository;
import com.university.gradcloudnotes.rest.UserController;
import com.university.gradcloudnotes.utils.EncryptUtil;
import com.university.gradcloudnotes.utils.GetReturn;
import com.university.gradcloudnotes.utils.PubFun;
import com.university.gradcloudnotes.utils.UUIDUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    /**密钥*/
    public static final String key = "abcdabcdabcdabcdabcdabcd";

    @Autowired
    private CnUserRepository cnUserRepository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public UniversalResponse register(RegisterRequest registerRequest) {
        /**判断用户名是否重复*/
        //TODO
        /**对密码进行加密处理*/
        String encryPasswd = EncryptUtil.getEncrypeStr(key, registerRequest.getPassword());
        logger.info("加密后的密码为：encryPasswd={}", encryPasswd);
        registerRequest.setPassword(encryPasswd);
        /**属性复制*/
        CnUser cnUser = new CnUser();
        BeanUtils.copyProperties(registerRequest, cnUser);
        /**主键生成*/
        cnUser.setId(UUIDUtil.getUUID());
        /**其他字段赋值*/
        cnUser.setMakeDate(PubFun.getCurrentDate());
        cnUser.setMakeTime(PubFun.getCurrentTime());
        cnUser.setModifyDate(PubFun.getCurrentDate());
        cnUser.setModifyTime(PubFun.getCurrentTime());
        logger.info("属性复制后的user信息为：cnUser={}", cnUser);
        /**存表*/
        cnUserRepository.save(cnUser);
        return GetReturn.getReturn("200", "用户信息保存成功！", null);
    }

    public UniversalResponse login(LoginRequest loginRequest) throws UnsupportedEncodingException {
        /**调用CustomUserDetailsService*/
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUserName());
        /**比较密码是否一致*/
        logger.info("通过安全认证框架得到的数据库密码为：pwd={}", userDetails.getPassword());
        /**密码解密*/
        String decryptStr = EncryptUtil.getDecryptStr(key, userDetails.getPassword());
        logger.info("解密后的密码pwd={}", decryptStr);
        if(decryptStr.equals(loginRequest.getPassword())) {
            /**密码一致*/
            return GetReturn.getReturn("200", "登录成功", null);
        }
        return GetReturn.getReturn("400", "登录失败", null);
    }
}
