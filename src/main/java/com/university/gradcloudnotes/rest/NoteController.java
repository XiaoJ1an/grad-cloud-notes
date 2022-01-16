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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/cloud", produces = "application/json;charset=utf-8")
public class NoteController {

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    @Autowired
    private NoteService noteService;

    /**新增笔记*/
    @ResponseBody
    @PostMapping("/notes/addNotes")
    public NoteResponse addNotes(@RequestBody NoteRequest noteRequest) {
        logger.info("新增笔记接口入参noteRequest={}", JSON.toJSONString(noteRequest));
        /**数据非空校验*/
        if(StringUtils.isBlank(noteRequest.getTitle()))
            return GetReturn.getReturn("400", "笔记标题为空！", null);
        if(StringUtils.isBlank(noteRequest.getContent()))
            return GetReturn.getReturn("400", "笔记内容为空！", null);
        if(StringUtils.isBlank(noteRequest.getType()))
            return GetReturn.getReturn("400", "笔记类型为空！", null);
        if(StringUtils.isBlank(noteRequest.getGroupId()))
            return GetReturn.getReturn("400", "笔记分组为空！", null);
        if(StringUtils.isBlank(noteRequest.getUserId()))
            return GetReturn.getReturn("400", "笔记所有者为空！", null);
        if(StringUtils.isBlank(noteRequest.getPushDate()))
            return GetReturn.getReturn("400", "笔记推送日期为空！", null);
        if(StringUtils.isBlank(noteRequest.getPushTime()))
            return GetReturn.getReturn("400", "笔记推送时间为空！", null);
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
