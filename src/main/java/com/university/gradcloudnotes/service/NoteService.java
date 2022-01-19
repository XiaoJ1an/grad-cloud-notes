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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    public UniversalResponse updateNoteType(List<String> noteIds, String type) throws ParseException {
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
                    String dateStr = cnNote.getPushDate() + " " + cnNote.getPushTime();
                    Date date = PubFun.toDate(dateStr);
                    int compare = date.compareTo(new Date());
                    String finalType = type;
                    if(compare == 0) finalType = "3"; /**日期相等*/
                    if(compare == 1) finalType = "1"; /**推送日期未到*/
                    if(compare == -1) finalType = "3"; /**推送日期已过*/
                    /**更新类型*/
                    updateNote(cnNote, finalType);
                    return GetReturn.getReturn("200", "更新成功！", null);
                }
            }
            return GetReturn.getReturn("400", "无可查询数据！", null);
        }
        //todo
        return GetReturn.getReturn("400", "暂不支持多id", null);
    }

    /**更新笔记类型的公用方法*/
    private void updateNote(CnNote cnNote, String type) {
        cnNote.setType(type);
        cnNote.setModifyDate(PubFun.getCurrentDate());
        cnNote.setModifyTime(PubFun.getCurrentTime());
        cnNoteRepository.save(cnNote);
    }

}
