package com.university.gradcloudnotes.rest;

import com.alibaba.fastjson.JSON;
import com.university.gradcloudnotes.entity.request.NoteRequest;
import com.university.gradcloudnotes.entity.response.NoteResponse;
import com.university.gradcloudnotes.service.NoteService;
import com.university.gradcloudnotes.utils.GetReturn;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/cloud", produces = "application/json;charset=utf-8")
public class NoteController {

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    @Autowired
    private NoteService noteService;

    /**新增笔记*/
    @ResponseBody
    @PostMapping("/notes/addNotes")
    public NoteResponse addNotes(NoteRequest noteRequest) {
        logger.info("新增笔记接口入参noteRequest={}", JSON.toJSONString(noteRequest));
        /**数据非空校验*/
        if(StringUtils.isBlank(noteRequest.getNoteTitle()))
            return GetReturn.getReturn("400", "笔记标题为空！", null);
        if(StringUtils.isBlank(noteRequest.getNoteContent()))
            return GetReturn.getReturn("400", "笔记内容为空！", null);
        if(StringUtils.isBlank(noteRequest.getNoteType()))
            return GetReturn.getReturn("400", "笔记类型为空！", null);
        if(StringUtils.isBlank(noteRequest.getGroupId()))
            return GetReturn.getReturn("400", "笔记分组为空！", null);
        /**调用service方法*/
        try {
            NoteResponse noteResponse = noteService.addNotes(noteRequest);
            logger.info("新增笔记service返回数据为：noteResponse={}", noteResponse);
            return noteResponse;
        } catch (Exception e) {
            logger.info("调用service方法出现异常！e={}", e);
            return GetReturn.getReturn("400", "新增笔记异常！", null);
        }
    }

}
