package com.university.gradcloudnotes.entity.response;

import lombok.Data;

@Data
public class MessageResponse {
    /**状态码 0 表示成功*/
    private String code;
    /**状态码描述*/
    private String msg;
}
