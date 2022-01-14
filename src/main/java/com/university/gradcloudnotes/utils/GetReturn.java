package com.university.gradcloudnotes.utils;

import com.university.gradcloudnotes.entity.response.NoteResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GetReturn {
    /**返回笔记信息专用类*/
    public static NoteResponse getReturn(String code, String message, Object data) {
        NoteResponse noteResponse = new NoteResponse();
        noteResponse.setCode(code);
        noteResponse.setMessage(message);
        noteResponse.setData(data);
        return noteResponse;
    }
}
