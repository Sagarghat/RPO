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

import com.ojas.rpo.security.dao.contactnotes.ContactNotesDao;
import com.ojas.rpo.security.entity.ContactNotes;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.NotesSearch;
import com.ojas.rpo.security.util.ValidateContactNotes;

@Component
@Path("/contactnotes")
public class ContactNotesResource {

	@Autowired
	private ContactNotesDao contactNotesDao;

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response save(ContactNotes contactNotes) {
		Response response = ValidateContactNotes.validateContactNotes(contactNotes);
		if (response == null) {
			ContactNotes savedContactNotes = this.contactNotesDao.save(contactNotes);
			if (savedContactNotes != null)
				response = new Response(ExceptionMessage.StatusSuccess, savedContactNotes, "data stored to database");
			else
				response = new Response(ExceptionMessage.DataIsNotSaved, "data not stored");

		}
		return response;

	}

	@DELETE
	@Path("/delete/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response delteClientNoteById(@PathParam("id") Integer id) {

		return this.contactNotesDao.deleteById(id);
	}

	@POST
	@Path("/search")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response getAllNotes(NotesSearch notesSearch) {
		return this.contactNotesDao.getAllContactNotes(notesSearch);
	}
}
