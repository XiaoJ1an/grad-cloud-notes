package com.university.gradcloudnotes.service;

import com.university.gradcloudnotes.entity.request.NoteRequest;
import com.university.gradcloudnotes.entity.response.NoteResponse;
import com.university.gradcloudnotes.jpa.CnNote;
import com.university.gradcloudnotes.repository.CnNoteRepository;
import com.university.gradcloudnotes.rest.NoteController;
import com.university.gradcloudnotes.utils.GetReturn;
import com.university.gradcloudnotes.utils.PubFun;
import com.university.gradcloudnotes.utils.UUIDUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    @Autowired
    private CnNoteRepository cnNoteRepository;

    /**新增笔记*/
    public NoteResponse addNotes(NoteRequest noteRequest) {
        /**判断是否是要新增*/
        if(StringUtils.isNotBlank(noteRequest.getNoteId())) {
            logger.info("笔记id不为空，不适用新增方法！");
            return GetReturn.getReturn("400", "笔记id不为空，不适用新增方法！", null);
        }
        /**补充时间字段*/
        noteRequest.setMakeDate(PubFun.getCurrentDate());
        noteRequest.setMakeTime(PubFun.getCurrentTime());
        noteRequest.setModifyDate(PubFun.getCurrentDate());
        noteRequest.setModifyTime(PubFun.getCurrentTime());
        /**属性复制*/
        CnNote cnNote = new CnNote();
        BeanUtils.copyProperties(noteRequest, cnNote);
        /**生成笔记id*/
        cnNote.setId(UUIDUtil.getUUID());
        /**保存数据*/
        cnNoteRepository.save(cnNote);
        return GetReturn.getReturn("200", "笔记信息保存成功！", null);
    }


}
