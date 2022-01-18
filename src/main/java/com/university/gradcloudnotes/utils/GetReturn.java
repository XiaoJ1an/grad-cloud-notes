package com.university.gradcloudnotes.utils;

import com.university.gradcloudnotes.entity.response.UniversalResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GetReturn {
    /**返回笔记信息专用类*/
    public static UniversalResponse getReturn(String code, String message, Object data) {
        UniversalResponse universalResponse = new UniversalResponse();
        universalResponse.setCode(code);
        universalResponse.setMessage(message);
        universalResponse.setData(data);
        return universalResponse;
    }
}
