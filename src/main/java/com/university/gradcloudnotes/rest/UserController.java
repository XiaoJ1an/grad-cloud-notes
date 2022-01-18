package com.university.gradcloudnotes.rest;

import com.university.gradcloudnotes.entity.request.RegisterRequest;
import com.university.gradcloudnotes.entity.response.UniversalResponse;
import com.university.gradcloudnotes.service.UserService;
import com.university.gradcloudnotes.utils.GetReturn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/cloud", produces = "application/json;charset=utf-8")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    /**用户注册接口*/
    @PostMapping("/user/register")
    public UniversalResponse register(@RequestBody RegisterRequest registerRequest) {
        logger.info("用户注册入参为：registerRequest={}", registerRequest);
        UniversalResponse response = null;
        try {
            response = userService.register(registerRequest);
            return response;
        } catch (Exception e) {
            logger.info("调用service方法出现异常！e={}", e);
            return GetReturn.getReturn("400", "新用户注册异常！", null);
        }
    }

}
