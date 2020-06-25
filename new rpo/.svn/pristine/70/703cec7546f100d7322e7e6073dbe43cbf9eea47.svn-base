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

import com.ojas.rpo.security.dao.clientnotes.ClientNotesDao;
import com.ojas.rpo.security.entity.ClientNotes;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.NotesSearch;
import com.ojas.rpo.security.util.ValidateClientNotes;

@Component
@Path("/clientNotes")
public class ClientNotesResource {

	@Autowired
	private ClientNotesDao clientNotesDao;
	
	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response saveClientNotes(ClientNotes clientNotes) {
		Response response = ValidateClientNotes.validateClientNotes(clientNotes);
		if (response == null) {
			ClientNotes savedClientNotes = this.clientNotesDao.save(clientNotes);
			if (savedClientNotes != null)
				response = new Response(ExceptionMessage.StatusSuccess, savedClientNotes, "data stored to database");
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

		return this.clientNotesDao.deleteById(id);

	}

	@POST
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response getAllClientNotes(NotesSearch notesSearch) {

		return this.clientNotesDao.getAllClientNotes(notesSearch);

	}

}
