package com.ojas.rpo.security.dao.candidatenotes;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.CandidateNotes;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.NotesSearch;

public interface CandidateNotesDao  extends Dao<CandidateNotes, Long>{

	CandidateNotes save( CandidateNotes candidatNotes);	

	Response deleteById(Long id);

	Response getAllContactNotes(NotesSearch notesSearch);

}
