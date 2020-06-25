package com.ojas.rpo.security.dao.interviewnotes;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.InterviewNotes;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.NotesSearch;

public interface InterviewNotesDao extends Dao<InterviewNotes, Long> {

	InterviewNotes save(InterviewNotes interviewNotes);

	Response deleteById(Long id);

	Response getAllInterviews(NotesSearch notesSearch);

}
