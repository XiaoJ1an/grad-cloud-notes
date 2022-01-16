package com.university.gradcloudnotes.entity.request;

import lombok.Data;

@Data
public class NoteRequest {
    /**笔记id*/
    private String noteId;
    /**笔记标题*/
    private String title;
    /**笔记内容*/
    private String content;
    /**笔记类型*/
    private String type;
    /**笔记状态*/
    private String state;
    /**笔记所属组*/
    private String groupId;
    /**笔记所属者*/
    private String userId;
    /**推送日期*/
    private String pushDate;
    /**推送时间*/
    private String pushTime;
    /**创建日期*/
    private String makeDate;
    /**创建时间*/
    private String makeTime;
    /**修改日期*/
    private String modifyDate;
    /**修改时间*/
    private String modifyTime;

}
