package com.university.gradcloudnotes.entity.response;

import lombok.Data;

@Data
public class NoteResponse {
    /**状态码*/
    private String code;
    /**返回消息*/
    private String message;
    /**返回数据*/
    private Object data;
}
