package com.university.gradcloudnotes.service;

import com.university.gradcloudnotes.entity.request.NoteRequest;
import com.university.gradcloudnotes.entity.response.NoteResponse;
import com.university.gradcloudnotes.rest.NoteController;
import com.university.gradcloudnotes.utils.GetReturn;
import com.university.gradcloudnotes.utils.PubFun;
import com.university.gradcloudnotes.utils.UUIDUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NoteService {

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    /**新增笔记*/
    public NoteResponse addNotes(NoteRequest noteRequest) {
        /**判断是否是要新增*/
        if(StringUtils.isNotBlank(noteRequest.getNoteId())) {
            logger.info("笔记id不为空，不适用新增方法！");
            return GetReturn.getReturn("400", "笔记id不为空，不适用新增方法！", null);
        }
        /**生成笔记id*/
        noteRequest.setNoteId(UUIDUtil.getUUID());
        /**补充时间字段*/
        noteRequest.setMakeDate(PubFun.getCurrentDate());
        noteRequest.setMakeTime(PubFun.getCurrentTime());
        noteRequest.setMakeDate(PubFun.getCurrentDate());
        noteRequest.setMakeTime(PubFun.getCurrentTime());
        /**保存数据*/
        //TODO
        return null;
    }


}
