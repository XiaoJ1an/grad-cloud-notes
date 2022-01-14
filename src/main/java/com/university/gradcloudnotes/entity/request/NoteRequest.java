package com.university.gradcloudnotes.entity.request;

import lombok.Data;

@Data
public class NoteRequest {
    /**笔记id*/
    private String noteId;
    /**笔记标题*/
    private String noteTitle;
    /**笔记内容*/
    private String noteContent;
    /**笔记类型*/
    private String noteType;
    /**笔记所属组*/
    private String groupId;
    /**创建日期*/
    private String makeDate;
    /**创建时间*/
    private String makeTime;
    /**修改日期*/
    private String modifyDate;
    /**修改时间*/
    private String modifyTime;

}
