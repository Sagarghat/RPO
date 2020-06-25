package com.ojas.rpo.security.dao.notes;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.Notes;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.NotesSearch;

public interface NotesDao extends Dao<Notes, Long> {

	Response getAllNotes(NotesSearch notesSearch);

	Response deleteById(Integer id);

}
