package com.university.gradcloudnotes.repository;

import com.university.gradcloudnotes.jpa.CnNote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CnNoteRepository extends CrudRepository<CnNote, String> {
    /**根据笔记ID更新类型*/
    @Query(value = " UPDATE cn_note a SET a.type = ?2 WHERE a.id = ?1  ", nativeQuery = true)
    void updateNoteType(String noteId, String type);
    /**查询历史记录*/
    @Query(value = " SELECT * FROM cn_note a WHERE a.user_id = ?1 AND a.state <> '0' ORDER BY a.modify_date DESC, a.modify_time DESC ", nativeQuery = true)
    List<CnNote> getHistoryNotes(String userId);
    /**根据用户id和笔记类型查询笔记信息*/
    @Query(value = " SELECT * FROM cn_note a WHERE a.user_id = ?1 AND a.type = ?2 AND a.state <> '0' ORDER BY a.modify_date DESC, a.modify_time DESC ", nativeQuery = true)
    List<CnNote> getNotesByType(String userId, String noteType);

}
