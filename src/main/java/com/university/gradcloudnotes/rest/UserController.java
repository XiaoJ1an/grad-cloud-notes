package com.university.gradcloudnotes.rest;

import com.university.gradcloudnotes.entity.request.LoginRequest;
import com.university.gradcloudnotes.entity.request.RegisterRequest;
import com.university.gradcloudnotes.entity.response.UniversalResponse;
import com.university.gradcloudnotes.service.UserService;
import com.university.gradcloudnotes.utils.GetReturn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/cloud", produces = "application/json;charset=utf-8")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**用户注册接口*/
    @PostMapping("/user/register")
    public UniversalResponse register(@RequestParam String userName, @RequestParam String password) {
        logger.info("用户注册入参为：userName={}, password={}", userName, password);
        UniversalResponse response = null;
        try {
            response = userService.register(userName, password);
            return response;
        } catch (Exception e) {
            logger.info("调用service方法出现异常！e={}", e);
            return GetReturn.getReturn("400", "新用户注册异常！", null);
        }
    }

    /**用户登录接口*/
    @PostMapping("/user/login")
    public UniversalResponse login(@RequestBody LoginRequest loginRequest) {
        logger.info("用户登录接口入参为loginRequest={}", loginRequest);
        try {
            return userService.login(loginRequest);
        } catch (Exception e) {
            logger.info("调用service方法发生异常！e={}", e);
            return GetReturn.getReturn("400", "用户登录接口调用方法发生异常！", null);
        }
    }

    /**用户详细信息保存和修改*/
    @PostMapping("/user/saveUserInfo")
    public UniversalResponse saveUserInfo(@RequestBody RegisterRequest registerRequest) {
        logger.info("用户详细信息保存和修改接口入参registerRequest={}", registerRequest);
        try {
            return userService.saveUserInfo(registerRequest);
        } catch (Exception e) {
            logger.info("调用service方法发生异常！e={}", e);
            return GetReturn.getReturn("400", "用户详细信息保存和修改接口调用方法发生异常！", null);
        }
    }

    /**用户详细信息展示*/
    @PostMapping("/user/showUserInfo")
    public UniversalResponse showUserInfo(@RequestParam String userId) {
        logger.info("用户详细信息展示接口入参userId={}", userId);
        try {
            return userService.showUserInfo(userId);
        } catch (Exception e) {
            logger.info("调用service方法发生异常！e={}", e);
            return GetReturn.getReturn("400", "用户详细信息展示接口调用方法发生异常！", null);
        }
    }


}
