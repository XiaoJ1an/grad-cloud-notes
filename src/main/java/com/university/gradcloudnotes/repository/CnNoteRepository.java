package com.university.gradcloudnotes.repository;

import com.university.gradcloudnotes.jpa.CnNote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CnNoteRepository extends CrudRepository<CnNote, String> {
    /**根据笔记ID更新状态*/
    @Query(value = " UPDATE cn_note a SET a.type = ?2 WHERE a.id = ?1  ", nativeQuery = true)
    void updateNoteType(String noteId, String type);
}
