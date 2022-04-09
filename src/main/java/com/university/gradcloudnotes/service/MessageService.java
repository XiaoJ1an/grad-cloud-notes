package com.university.gradcloudnotes.service;

import com.university.gradcloudnotes.entity.request.MessageRequest;
import com.university.gradcloudnotes.entity.response.MessageResponse;
import com.university.gradcloudnotes.entity.response.UniversalResponse;
import com.university.gradcloudnotes.utils.GetReturn;
import com.university.gradcloudnotes.utils.HttpUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    @Autowired
    private RedisTemplate redisTemplate;


    public UniversalResponse sendMessage(MessageRequest messageRequest) {
        /**随机生成六位 大写验证码*/
        String code = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        /**将验证码放入缓存*/
        try {
            redisTemplate.opsForValue().set(messageRequest.getMobile(), code, 60 * 5, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.info("短信验证码放入REDIS发生异常！e={}", e);
            return GetReturn.getReturn("400", "短信验证码放入REDIS发生异常！", null);
        }
        /**自定义短信内容*/
//        String messageContent = "尊敬的先生/女士，云笔记验证码为" + code + "五分钟内有效，请勿泄露给他人！";
        //===============开始拼装调用发送短信接口参数================
        String host = "https://gyytz.market.alicloudapi.com";
        String path = "/sms/smsSend";
        String method = "POST";
        String appcode = "4fa4dd8739ef4838bb6fb67d9d225f7b";
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", messageRequest.getMobile());
        querys.put("param", "**code**:" + code + ",**minute**:5");
        querys.put("smsSignId", "2e65b1bb3d054466b82f0c9d125465e2");
        querys.put("templateId", "908e94ccf08b4476ba6c876d13f084ad");
        Map<String, String> bodys = new HashMap<String, String>();
        //===============拼装调用发送短信接口参数结束================
        try {
            logger.info("调用发送短信接口入参host={},path={},method={},headers={},querys={},bodys={}", host, path, method, headers, querys, bodys);
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            logger.info("调用发送短信接口出参response={}", response.toString());
            MessageResponse messageResponse = new MessageResponse();
            messageResponse.setCode("0/1");
            messageResponse.setMsg("调用发送短信接口成功，但是不知道发送是否成功~~~");
            return GetReturn.getReturn("200", "调用发送短信接口成功！", messageResponse);
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            logger.info("调用发送短信接口发生异常！e={}", e);
            return GetReturn.getReturn("400", "调用发送短信接口发生异常！", null);
        }
    }
}
