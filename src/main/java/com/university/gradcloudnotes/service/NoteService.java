package com.university.gradcloudnotes.service;

import com.university.gradcloudnotes.entity.request.NoteRequest;
import com.university.gradcloudnotes.entity.response.UniversalResponse;
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

import java.text.ParseException;
import java.util.*;

@Service
public class NoteService {

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    @Autowired
    private CnNoteRepository cnNoteRepository;

    /**新增笔记*/
    public UniversalResponse addNotes(NoteRequest noteRequest) {
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

    public UniversalResponse updateNoteType(List<String> noteIds, String type) throws Exception {
        if(noteIds == null || noteIds.size() <= 0 || StringUtils.isBlank(type))
            return GetReturn.getReturn("400", "传入参数有空值，请检查！", null);
        if(noteIds.size() == 1) {/**只是对单个笔记进行修改状态*/
            System.out.println(noteIds.get(0));
            Optional<CnNote> note = cnNoteRepository.findById(noteIds.get(0));
            if(note.isPresent()) {
                CnNote cnNote = note.get();
                /**对查询出的数据进行判断*/
                if("2".equals(cnNote.getType()))
                    return GetReturn.getReturn("400", "此笔记已完成，无法修改状态！", null);
                if("0".equals(cnNote.getState()))
                    return GetReturn.getReturn("400", "此笔记已删除，无法修改状态！", null);
                if("1".equals(cnNote.getType())) {/**对代办中的笔记进行状态修改判断*/
                    if(!Arrays.asList("2", "4", "5").stream().anyMatch(e -> e.equals(type)))
                        return GetReturn.getReturn("400", "代办中的笔记不允许修改成除已完成、已撤销、长期任务外的其他类型！", null);
                    /**更新类型*/
                    updateNote(cnNote, type);
                    return GetReturn.getReturn("200", "更新成功！", null);
                }
                if("3".equals(cnNote.getType())) {/**对未完成的笔记进行状态修改判断*/
                    if(!Arrays.asList("2", "4", "5").stream().anyMatch(e -> e.equals(type)))
                        return GetReturn.getReturn("400", "未完成的笔记不允许修改成除已完成、已撤销、长期任务外的其他类型！", null);
                    /**更新类型*/
                    updateNote(cnNote, type);
                    return GetReturn.getReturn("200", "更新成功！", null);
                }
                if("4".equals(cnNote.getType())) {/**对已撤销的笔记进行状态修改判断*/
                    if(!Arrays.asList("1", "3").stream().anyMatch(e -> e.equals(type)))
                        return GetReturn.getReturn("400", "已撤销的笔记不允许修改成除代办中、未完成外的其他类型！", null);
                    /**对推送日期进行判断*/
                    String finalType = type;
                    int compare = checkPushDateAndTime(cnNote);
                    if(compare == 0 || compare == -1) finalType = "3"; /**日期相等、推送日期已过*/
                    if(compare == 1) finalType = "1"; /**推送日期未到*/
                    /**更新类型*/
                    updateNote(cnNote, finalType);
                    return GetReturn.getReturn("200", "更新成功！", null);
                }
            }
            return GetReturn.getReturn("400", "无可查询数据！", null);
        }
        if(noteIds.size() > 0) {/**说明是要批量取回或者批量完成*/
            Iterable<CnNote> cnNotes = cnNoteRepository.findAllById(noteIds);
            List<CnNote> cnNoteList = new ArrayList<>();
            cnNotes.forEach(single->{cnNoteList.add(single);});
            /**对传入的type进行校验*/
            if("QH".equals(type)) {/**说明是要批量取回*/
                /**校验这些id对应的笔记是否都为4*/
                boolean match = cnNoteList.stream().allMatch(e -> "4".equals(e.getType()));
                if(!match) return GetReturn.getReturn("400", "不是所有的笔记都是已撤销的状态，不允许批量取回！", null);
                /**校验推送日期和推送时间*/
                cnNoteList.forEach(e -> {
                    String pushType = type;
                    int result = checkPushDateAndTime(e);
                    if(result == 0 || result == -1) pushType = "3"; /**日期相等、推送日期已过*/
                    if(result == 1) pushType = "1"; /**推送日期未到*/
                    /**更新类型*/
                    updateNote(e, pushType);
                });
                return GetReturn.getReturn("200", "批量取回成功！", null);
            }
            if("2".equals(type)) {/**说明是批量完成*/
                /**校验id是否为1或者3*/
                boolean match = cnNoteList.stream().anyMatch(e -> ("1".equals(e.getType()) || "3".equals(e.getType())));
                if(!match) return GetReturn.getReturn("400", "不是所有的笔记都是代办中或者未完成，不能做批量完成的操作！", null);
                /**批量完成*/
                cnNoteList.forEach(e -> {
                    /**更新类型*/
                    updateNote(e, type);
                });
                return GetReturn.getReturn("200", "批量完成成功！", null);
            }
            return GetReturn.getReturn("400", "暂不支持除批量取回、完成的其他操作！", null);
        }
        return GetReturn.getReturn("400", "updateNoteType异常！", null);
    }

    private int checkPushDateAndTime(CnNote cnNote) {
        try {
            String dateStr = cnNote.getPushDate() + " " + cnNote.getPushTime();
            Date date = PubFun.toDate(dateStr);
            int compare = date.compareTo(new Date());
            return compare;
        } catch (ParseException e) {
            logger.info("校验推送日期和推送时间发生异常！e={}", e);
            return -2;
        }
    }

    /**更新笔记类型的公用方法*/
    private void updateNote(CnNote cnNote, String type) {
        cnNote.setType(type);
        cnNote.setModifyDate(PubFun.getCurrentDate());
        cnNote.setModifyTime(PubFun.getCurrentTime());
        cnNoteRepository.save(cnNote);
    }

}
