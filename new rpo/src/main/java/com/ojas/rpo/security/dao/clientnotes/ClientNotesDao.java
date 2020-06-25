package com.ojas.rpo.security.dao.clientnotes;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.ClientNotes;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.NotesSearch;

public interface ClientNotesDao extends Dao<ClientNotes, Long> {

	ClientNotes save(ClientNotes clientNotes);

	Response deleteById(Long id);

	Response getAllClientNotes(NotesSearch notesSearch);

}
