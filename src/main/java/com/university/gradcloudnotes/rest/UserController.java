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
        try {
            UniversalResponse response = userService.login(loginRequest);
            return response;
        } catch (Exception e) {
            logger.info("调用service方法发生异常！e={}", e);
            return GetReturn.getReturn("400", "调用service方法发生异常！", null);
        }
    }

    /**用户详细信息保存和修改*/
    @PostMapping("/user/saveUserInfo")
    public UniversalResponse saveUserInfo(@RequestBody RegisterRequest registerRequest) {
        try {
            UniversalResponse response = userService.saveUserInfo(registerRequest);
            return  response;
        } catch (Exception e) {
            logger.info("调用service方法发生异常！e={}", e);
            return GetReturn.getReturn("400", "调用service方法发生异常！", null);
        }
    }


}
