package com.university.gradcloudnotes.rest;

import com.alibaba.fastjson.JSON;
import com.university.gradcloudnotes.entity.request.NoteRequest;
import com.university.gradcloudnotes.entity.response.UniversalResponse;
import com.university.gradcloudnotes.service.NoteService;
import com.university.gradcloudnotes.utils.GetReturn;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping(path = "api/cloud", produces = "application/json;charset=utf-8")
public class NoteController {

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    @Autowired
    private NoteService noteService;

    /**新增笔记*/
    @ResponseBody
    @PostMapping("/notes/addNotes")
    public UniversalResponse addNotes(@RequestBody NoteRequest noteRequest) {
        logger.info("新增笔记接口入参noteRequest={}", JSON.toJSONString(noteRequest));
        /**数据非空校验*/
        if(StringUtils.isBlank(noteRequest.getTitle()))
            return GetReturn.getReturn("400", "笔记标题为空！", null);
        if(StringUtils.isBlank(noteRequest.getContent()))
            return GetReturn.getReturn("400", "笔记内容为空！", null);
        if(StringUtils.isBlank(noteRequest.getType()))
            return GetReturn.getReturn("400", "笔记类型为空！", null);
        if("5".equals(noteRequest.getType())) {
            if(StringUtils.isBlank(noteRequest.getGroupId()))
                return GetReturn.getReturn("400", "笔记分组为空！", null);
        }
        if(StringUtils.isBlank(noteRequest.getUserId()))
            return GetReturn.getReturn("400", "笔记所有者为空！", null);
        if(StringUtils.isBlank(noteRequest.getPushDate()))
            return GetReturn.getReturn("400", "笔记推送日期为空！", null);
        if(StringUtils.isBlank(noteRequest.getPushTime()))
            return GetReturn.getReturn("400", "笔记推送时间为空！", null);
        /**调用service方法*/
        try {
            UniversalResponse universalResponse = noteService.addNotes(noteRequest);
            logger.info("新增笔记service返回数据为：noteResponse={}", universalResponse);
            return universalResponse;
        } catch (Exception e) {
            logger.info("调用service方法出现异常！e={}", e);
            return GetReturn.getReturn("400", "新增笔记异常！", null);
        }
    }

    /**修改笔记类型*/
    @ResponseBody
    @PostMapping("/notes/updateNoteType")
    public UniversalResponse updateNoteType(@RequestParam List<String> noteIds, @RequestParam String type) throws Exception {
        try {
            return noteService.updateNoteType(noteIds, type);
        } catch (ParseException e) {
            return GetReturn.getReturn("400", "调用service方法发生异常！", null);
        }
    }

    /**查询各个类型的笔记*/
    @ResponseBody
    @GetMapping("/notes/queryNotes")
    public UniversalResponse queryNotes(@RequestParam String userId, @RequestParam String noteType) {
        try {
            logger.info("查询各个类型的笔记接口入参：userId={}, noteType={}", userId, noteType);
            UniversalResponse response = noteService.queryNotes(userId, noteType);
            return response;
        } catch (Exception e) {
            logger.info("调用查询各个类型的笔记接口service发生异常！e={}",e);
            return GetReturn.getReturn("400", "调用查询各个类型的笔记接口service发生异常！", null);
        }
    }

    /**删除及批量删除笔记接口*/
    @ResponseBody
    @PostMapping("/notes/deleteNotes")
    public UniversalResponse deleteNotes(@RequestParam List<String> noteIds) {
        logger.info("删除笔记接口入参noteIds={}", noteIds);
        try {
            UniversalResponse response = noteService.deleteNotes(noteIds);
            return response;
        } catch (Exception e) {
            logger.info("调用service发生异常！e={}", e);
            return GetReturn.getReturn("400", "删除及批量删除笔记接口异常！", null);
        }
    }

}
