package com.ojas.rpo.security.dao.contactnotes;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.ContactNotes;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.NotesSearch;

public interface ContactNotesDao extends Dao<ContactNotes, Long> {
	
	ContactNotes save( ContactNotes contactNotes);

	
	Response deleteById(Integer id);

	Response getAllContactNotes(NotesSearch notesSearch);
}
