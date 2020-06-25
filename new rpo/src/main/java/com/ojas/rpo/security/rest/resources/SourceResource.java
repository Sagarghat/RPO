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
import com.ojas.rpo.security.dao.source.SourceDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;
import com.ojas.rpo.security.entity.Source;

@Component
@Path("/source")
public class SourceResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SourceDao sourceDao;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/findAll/{pageNum}/{pageSize}")
	public @ResponseBody Response getAll(@PathParam("pageNum") Integer pageNum,
			@PathParam("pageSize") Integer pagesize) {
		this.logger.info("inside getAll()");

		Response response = null;
		Integer totalRecords = 0;
		Integer startingRow = 0;
		if (pageNum == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNum - 1) * pagesize);
		}

		List<Source> source = this.sourceDao.findSources(startingRow, pagesize);
		if (source == null) {
			return new Response(ExceptionMessage.DataIsEmpty, source);
		} else {
			totalRecords = sourceDao.findAll().size();
			Integer totalPages = 1;
			if (totalRecords == 0) {
				response = new Response();
				response.setTotalPages(0);
			} else {
				totalPages = totalRecords / pagesize;

				totalPages = (totalRecords % pagesize > 0) ? totalPages + 1 : totalPages;

			}

			response = new Response(ExceptionMessage.StatusSuccess);
			response.setTotalPages(totalPages);
			response.setTotalRecords(totalRecords);
			response.setResult(source);
			return response;
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response getById(@PathParam("id") Long id) {
		this.logger.info("inside getById(id)");
		Source source = this.sourceDao.find(id);
		Source s = new Source();
		if (source != null) {
			s.setId(source.getId());
			s.setSourceName(source.getSourceName());
			return new Response(ExceptionMessage.StatusSuccess, s);

		}

		else
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(Source source) {
		try {
			this.logger.info("inside post method");

			if (source != null && !source.getSourceName().trim().isEmpty()) {

				source = this.sourceDao.save(source);

				return new Response(ExceptionMessage.StatusSuccess, source);

			} else {

				return new Response(ExceptionMessage.DataIsEmpty);
			}
		} catch (Exception e) {
			return new Response(ExceptionMessage.DuplicateRecord);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response update(@PathParam("id") Long id, Source source) {
		source.setId(id);
		if (source == null || source.getSourceName().trim().isEmpty()) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {
			try {
				source = this.sourceDao.save(source);
				return new Response(ExceptionMessage.StatusSuccess, source);
			} catch (Exception e) {
				return new Response(ExceptionMessage.DuplicateRecord);
			}
		}

	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/delete/{id}")
	public @ResponseBody Response delete(@PathParam("id") Long id, Source source) {
		this.logger.info("inside delete method");
		source.setId(id);
		if (this.sourceDao.delete(id) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else
			return new Response(ExceptionMessage.StatusSuccess, source);

	}

}
