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

import com.ojas.rpo.security.dao.requirementnotes.ReqNotesDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.RequirementNotes;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.NotesSearch;

@Component
@Path("/reqnotes")
public class ReqNotesResource {

	@Autowired
	private ReqNotesDao reqNotesDao;

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response save(RequirementNotes requirementNotes) {
		Response response = ValidateReqNotes.validateReqNotes(requirementNotes);
		if (response == null) {
			RequirementNotes savedReqNotes = this.reqNotesDao.save(requirementNotes);
			if (savedReqNotes != null)
				response = new Response(ExceptionMessage.StatusSuccess, savedReqNotes, "data stored to database");
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

		return this.reqNotesDao.deleteById(id);

	}

	@POST
	@Path("/search")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response getAllNotes(NotesSearch notesSearch) {
		return this.reqNotesDao.getAllRequirementNotes(notesSearch);
	}
}
