package com.ojas.rpo.security.rest.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.ojas.rpo.security.dao.notes.NotesDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Notes;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.NotesSearch;

@Component
@Path("/notes")
public class NotesResource {

	@Autowired
	private NotesDao notesDao;

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response createNote(Notes notes) {

		Notes savedNote = this.notesDao.save(notes);
		if (savedNote != null)
			return new Response(ExceptionMessage.StatusSuccess, savedNote,
					"Note saved for the requirement" + notes.getRequirementId());

		return new Response(ExceptionMessage.DataIsNotSaved, "note is not saved");

	}

	@POST
	@Path("/search")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response getAllNotes(NotesSearch notesSearch) {

		return this.notesDao.getAllNotes(notesSearch);

	}

	@POST
	@Path("/delete/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response deleteById(@PathParam("id") Integer id) {
		
		return this.notesDao.deleteById(id);

	}

}
