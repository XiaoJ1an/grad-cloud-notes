package com.university.gradcloudnotes.repository;

import com.university.gradcloudnotes.jpa.CnNote;
import org.springframework.data.repository.CrudRepository;

public interface CnNoteRepository extends CrudRepository<CnNote, String> {
}
