package com.university.gradcloudnotes.rest;

import com.university.gradcloudnotes.entity.request.MessageRequest;
import com.university.gradcloudnotes.entity.response.UniversalResponse;
import com.university.gradcloudnotes.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/cloud", produces = "application/json;charset=utf-8")
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;

    @PostMapping("/message/sendMessage")
    public UniversalResponse sendMessage(@RequestBody MessageRequest messageRequest) {
        logger.info("调用发送短信接口入参messageRequest={}", messageRequest);
        return messageService.sendMessage(messageRequest);
    }

}
