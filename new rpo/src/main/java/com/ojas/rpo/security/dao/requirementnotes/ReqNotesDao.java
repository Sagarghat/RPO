package com.ojas.rpo.security.dao.requirementnotes;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.RequirementNotes;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.NotesSearch;

public interface ReqNotesDao extends Dao<RequirementNotes, Long> {
	
	RequirementNotes save(RequirementNotes requirementNotes);
	Response deleteById(Long id);

	Response getAllRequirementNotes(NotesSearch notesSearch);

}
