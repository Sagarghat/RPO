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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.CandidateStatusMaster.CandidateStatusMasterDaoInf;
import com.ojas.rpo.security.entity.CandidateStatusMaster;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Source;

@Path("/addCandidateStatus")
@Component
public class CandidateStatusMasterResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	CandidateStatusMasterDaoInf candidateStatusMasterDaoInf;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(CandidateStatusMaster candidateStatusMaster) {
		try {
			this.logger.info("inside create method");

			if (candidateStatusMaster != null && !candidateStatusMaster.getStatus().isEmpty()) {

				CandidateStatusMaster save = this.candidateStatusMasterDaoInf.save(candidateStatusMaster);

				return new Response(ExceptionMessage.StatusSuccess, save);

			} else {

				return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
			}
		} catch (Exception e) {
			return new Response(ExceptionMessage.DuplicateRecord, e.getLocalizedMessage());
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response getAll() {
		this.logger.info("inside getAll()");
		List<CandidateStatusMaster> findAll = this.candidateStatusMasterDaoInf.findAll();
		if (findAll == null || findAll.isEmpty()) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

		if (!findAll.isEmpty()) {
			return new Response(ExceptionMessage.StatusSuccess, findAll);
		} else {

			return new Response(ExceptionMessage.DataIsEmpty, "200", "NOEXCEPTION", "NULL");
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response update(@PathParam("id") Long id, CandidateStatusMaster source) {
		source.setId(id);
		if (this.candidateStatusMasterDaoInf.save(source) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else
			return new Response(ExceptionMessage.StatusSuccess, source);
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/delete/{id}")
	public @ResponseBody Response delete(@PathParam("id") Long id, Source source) {
		this.logger.info("inside delete method");
		source.setId(id);
		if (this.candidateStatusMasterDaoInf.delete(id) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else
			return new Response(ExceptionMessage.StatusSuccess, source);

	}
}
