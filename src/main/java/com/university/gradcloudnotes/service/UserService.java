package com.university.gradcloudnotes.service;

import com.university.gradcloudnotes.entity.request.LoginRequest;
import com.university.gradcloudnotes.entity.request.RegisterRequest;
import com.university.gradcloudnotes.entity.response.UniversalResponse;
import com.university.gradcloudnotes.entity.response.UserResponse;
import com.university.gradcloudnotes.jpa.CnUser;
import com.university.gradcloudnotes.repository.CnUserRepository;
import com.university.gradcloudnotes.rest.UserController;
import com.university.gradcloudnotes.utils.EncryptUtil;
import com.university.gradcloudnotes.utils.GetReturn;
import com.university.gradcloudnotes.utils.PubFun;
import com.university.gradcloudnotes.utils.UUIDUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    /**密钥*/
    public static final String key = "abcdabcdabcdabcdabcdabcd";

    @Autowired
    private CnUserRepository cnUserRepository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public UniversalResponse register(String userName, String password) {
        /**判断用户名是否重复*/
        List<CnUser> users = cnUserRepository.findAllByUserName(userName);

        if(users != null && users.size() > 0) {
            return GetReturn.getReturn("400", "用户名已注册，请更换！", null);
        }
        /**对密码进行加密处理*/
        String encryPasswd = EncryptUtil.getEncrypeStr(key, password);
        logger.info("加密后的密码为：encryPasswd={}", encryPasswd);
        /**属性赋值*/
        CnUser cnUser = new CnUser();
        /**主键生成*/
        cnUser.setId(UUIDUtil.getUUID());
        cnUser.setUserName(userName);
        cnUser.setNickName(userName);
        cnUser.setPassword(encryPasswd);
        /**其他字段赋值*/
        cnUser.setMakeDate(PubFun.getCurrentDate());
        cnUser.setMakeTime(PubFun.getCurrentTime());
        cnUser.setModifyDate(PubFun.getCurrentDate());
        cnUser.setModifyTime(PubFun.getCurrentTime());
        logger.info("属性复制后的user信息为：cnUser={}", cnUser);
        /**存表*/
        cnUserRepository.save(cnUser);
        return GetReturn.getReturn("200", "用户信息保存成功！", cnUser.getId());
    }

    public UniversalResponse login(LoginRequest loginRequest) {
        /**调用CustomUserDetailsService*/
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUserName());
        /**判断是否有数据*/
        if("401".equals(userDetails.getUsername()))
            return GetReturn.getReturn("401", "用户未注册！", null);
        /**比较密码是否一致*/
        logger.info("通过安全认证框架得到的数据库密码为：pwd={}", userDetails.getPassword());
        /**密码解密*/
        String decryptStr = EncryptUtil.getDecryptStr(key, userDetails.getPassword());
        logger.info("解密后的密码pwd={}", decryptStr);
        if(decryptStr.equals(loginRequest.getPassword())) {
            /**密码一致 将用户id返回*/
            List<CnUser> users = cnUserRepository.findAllByUserName(userDetails.getUsername());
            String userId = users.get(0).getId();
            return GetReturn.getReturn("200", "登录成功！", userId);
        }
        return GetReturn.getReturn("402", "用户名和密码错误！", null);
    }

    public UniversalResponse saveUserInfo(RegisterRequest registerRequest) {
        /**通过用户id查询用户信息*/
        Optional<CnUser> user = cnUserRepository.findById(registerRequest.getUserId());
        if(user.isPresent()) {
            CnUser cnUser = user.get();
            cnUser.setName(registerRequest.getName());
            cnUser.setSex(registerRequest.getSex());
            cnUser.setBirthday(registerRequest.getBirthday());
            cnUser.setPhoneNo(registerRequest.getPhoneNo());
            cnUser.setNickName(registerRequest.getNickName());
            cnUser.setPicture(registerRequest.getPictureUrl());
            /**时间字段处理*/
            /**判断是否第一次保存*/
            if(StringUtils.isBlank(cnUser.getPhoneNo())) {/**电话号码为空说明是第一次保存*/
                cnUser.setMakeDate(PubFun.getCurrentDate());
                cnUser.setMakeTime(PubFun.getCurrentTime());
            }
            cnUser.setModifyDate(PubFun.getCurrentDate());
            cnUser.setModifyTime(PubFun.getCurrentTime());
            /**保存用户信息*/
            cnUserRepository.save(cnUser);
            return GetReturn.getReturn("200", "用户信息保存成功！", null);
        }
        return GetReturn.getReturn("400", "未查询到该用户信息！", null);
    }

    public UniversalResponse showUserInfo(String userId) {
        CnUser cnUser = cnUserRepository.findById(userId).orElse(null);
        if(!Objects.isNull(cnUser)) {/**非空校验*/
            UserResponse userResponse = new UserResponse();
            BeanUtils.copyProperties(cnUser, userResponse);
            return GetReturn.getReturn("200", "获取用户详细信息成功！", userResponse);
        }
        return GetReturn.getReturn("400", "未查询到此用户的信息！", null);
    }
}
