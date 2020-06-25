package com.ojas.rpo.security.rest.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.JsonViews;
import com.ojas.rpo.security.dao.candidateJobTitle.CandidateJobTitleDaoInf;
import com.ojas.rpo.security.entity.CandidateJobTitle;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;

@Path("/addCandidateJobTitle")
@Component
public class CandidateJobTitleResource {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	CandidateJobTitleDaoInf candidateJobTitleDaoInf;

	@Autowired
	private ObjectMapper mapper;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response getAll() {
		this.logger.info("inside getAll()");

		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		List<CandidateJobTitle> findAll = this.candidateJobTitleDaoInf.findAll();
		if (findAll == null || findAll.isEmpty()) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

		if (!findAll.isEmpty()) {
			return new Response(ExceptionMessage.StatusSuccess, findAll);
		} else {

			return new Response(ExceptionMessage.DataIsEmpty, "200", "NOEXCEPTION", "NULL");
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response getById(@PathParam("id") Long id) {
		this.logger.info("inside getById(id)");
		CandidateJobTitle find = this.candidateJobTitleDaoInf.find(id);
		if (find == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, find);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(CandidateJobTitle candidateJobTitle) {
		try {
			this.logger.info("inside create method");

			if (candidateJobTitle != null && !candidateJobTitle.getCurrentJobTittle().isEmpty()) {

				CandidateJobTitle save = this.candidateJobTitleDaoInf.save(candidateJobTitle);

				return new Response(ExceptionMessage.StatusSuccess, save);

			} else {

				return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
			}
		} catch (Exception e) {
			return new Response(ExceptionMessage.DuplicateRecord, e.getLocalizedMessage());
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response update(@PathParam("id") Long id, CandidateJobTitle candidateJobTitle) {
		candidateJobTitle.setId(id);
		if (this.candidateJobTitleDaoInf.save(candidateJobTitle) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else
			return new Response(ExceptionMessage.StatusSuccess, candidateJobTitle);
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/delete/{id}")
	public @ResponseBody Response delete(@PathParam("id") Long id, CandidateJobTitle candidateJobTitle) {
		this.logger.info("inside delete method");
		candidateJobTitle.setId(id);
		if (this.candidateJobTitleDaoInf.delete(id) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else
			return new Response(ExceptionMessage.StatusSuccess, candidateJobTitle);

	}

	private boolean isAdmin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if ((principal instanceof String) && ((String) principal).equals("anonymousUser")) {
			return false;
		}
		UserDetails userDetails = (UserDetails) principal;

		return userDetails.getAuthorities().contains(Role.ADMIN);
	}

}
