package com.ojas.rpo.security.rest.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.candidatenotes.CandidateNotesDao;
import com.ojas.rpo.security.entity.CandidateNotes;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.NotesSearch;
import com.ojas.rpo.security.util.ValidateCandidateNotes;

@Component
@Path("/candidatenotes")
public class CandidateNotesResource {

	@Autowired
	private CandidateNotesDao candidateNotesDao;

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response save(CandidateNotes candidateNotes) {
		Response response = ValidateCandidateNotes.validateCandidateNotes(candidateNotes);
		if (response == null) {
			CandidateNotes savedCandiatenotes = this.candidateNotesDao.save(candidateNotes);
			if (savedCandiatenotes != null)
				response = new Response(ExceptionMessage.StatusSuccess, savedCandiatenotes, "data stored to database");
			else
				response = new Response(ExceptionMessage.DataIsNotSaved, "data not stored");

		}
		return response;

	}

	@DELETE
	@Path("/delete/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response delteClientNoteById(@PathParam("id") Long id) {

		return this.candidateNotesDao.deleteById(id);

	}

	@POST
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response getAllNotes(NotesSearch notesSearch) {

		return this.candidateNotesDao.getAllContactNotes(notesSearch);

	}
}
