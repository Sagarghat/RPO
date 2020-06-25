package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
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
import com.ojas.rpo.security.dao.noticePeriod.NoticePeriodDao;
import com.ojas.rpo.security.entity.AddQualification;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.NoticePeriod;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;
import com.ojas.rpo.security.entity.Skill;

@Component
@Path("/noticePeriod")
public class NoticePeriodResource {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private NoticePeriodDao noticePeriodDao;

	@Autowired
	private ObjectMapper mapper;

	@GET
	@Path("/search/{pageNo}/{pageSize}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response list(@PathParam("pageSize") Integer pageSize, @PathParam("pageNo") Integer pageNo)
			throws IOException {
		this.logger.info("list()");

		Response response = null;
		Integer totalRecords = 0;
		Integer startingRow = 0;
		if (pageNo == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNo - 1) * pageSize);
		}
		List<NoticePeriod> noticePeriodList = this.noticePeriodDao.findAllLocations(startingRow,pageSize);
		if (noticePeriodList == null) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {
			totalRecords = noticePeriodDao.findAll().size();
			Integer totalPages = 1;
			if (totalRecords == 0) {
				response = new Response();
				response.setTotalPages(0);
			} else {
				totalPages = totalRecords / pageSize;

				totalPages = (totalRecords % pageSize > 0) ? totalPages + 1 : totalPages;

			}

			response = new Response(ExceptionMessage.StatusSuccess, noticePeriodList);
			response.setTotalPages(totalPages);
			response.setTotalRecords(totalRecords);
			return response;
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response read(@PathParam("id") Long id) {
		this.logger.info("read(id)");
		NoticePeriod noticePeriod = this.noticePeriodDao.find(id);
		if (noticePeriod == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, noticePeriod);
	}

	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create1(NoticePeriod noticePeriod) {
		if (noticePeriod != null && noticePeriod.getNoticePeriod() != null) {
			try {
				noticePeriod = noticePeriodDao.save(noticePeriod);
				return new Response(ExceptionMessage.StatusSuccess, noticePeriod);
			} catch (Exception e) {
				return new Response(ExceptionMessage.DuplicateRecord);
			}
		} else
			return new Response(ExceptionMessage.DataIsEmpty);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response update(@PathParam("id") Long id, NoticePeriod noticePeriod) {
		noticePeriod.setId(id);
		this.logger.info("update(): " + noticePeriod);
		if (noticePeriod == null || noticePeriod.getNoticePeriod() == null) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {
			try {
				noticePeriod = this.noticePeriodDao.save(noticePeriod);

				return new Response(ExceptionMessage.StatusSuccess, noticePeriod);
			} catch (Exception e) {
				return new Response(ExceptionMessage.DuplicateRecord);
			}
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public void delete(@PathParam("id") Long id, NoticePeriod noticePeriod) {
		noticePeriod.setId(id);
		// this.logger.info("read(id)");

		this.noticePeriodDao.delete(id);
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
