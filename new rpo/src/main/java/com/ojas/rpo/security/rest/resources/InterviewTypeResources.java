package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
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
import com.ojas.rpo.security.dao.interviewType.InterviewTypeDao;
import com.ojas.rpo.security.entity.AddQualification;
import com.ojas.rpo.security.entity.Certificate;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.InterviewType;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;
import com.ojas.rpo.security.util.DateParsing;

@Component
@Path("/modeofInterview")
public class InterviewTypeResources {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private InterviewTypeDao interviewTypeDao;

	@Autowired
	private ObjectMapper mapper;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(InterviewType interviewType) {
		System.out.println("==========inside post method====RPO");
		this.logger.info("create(): " + interviewType);
		try {
			String s = interviewType.getModeofInterview();
			String fs = DateParsing.textConvertor(s);
			interviewType.setModeofInterview(fs);
			if (interviewType != null && !interviewType.getModeofInterview().trim().isEmpty()) {
				InterviewType save = this.interviewTypeDao.save(interviewType);

				return new Response(ExceptionMessage.StatusSuccess, save);
			} else {

				return new Response(ExceptionMessage.DataIsEmpty);
			}
		} catch (Exception e) {
			return new Response(ExceptionMessage.DuplicateRecord);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("findAll/{pageNo}/{pageSize}")
	public @ResponseBody Response getdata(@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize)
			throws IOException {
		this.logger.info("list()");
		Response response = null;
		Integer totalRecords = 0;
		Integer startingRow = 0;
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		if (pageNo == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNo - 1) * pageSize);
		}
		List<InterviewType> add = this.interviewTypeDao.findAllData(startingRow, pageSize);
		if (add == null || add.isEmpty()) {
			response = new Response(ExceptionMessage.DataIsEmpty);
		}
		totalRecords = interviewTypeDao.findAll().size();
		Integer totalPages = 1;
		if (totalRecords == 0) {
			response = new Response();
			response.setTotalPages(0);
		} else {
			totalPages = totalRecords / pageSize;
			totalPages = (totalRecords % pageSize > 0) ? totalPages + 1 : totalPages;
		}
		response = new Response(ExceptionMessage.StatusSuccess);
		response.setTotalPages(totalPages);
		response.setTotalRecords(totalRecords);
		response.setResult(add);
		return response;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response read(@PathParam("id") Long id) {
		this.logger.info("read(id)");
		InterviewType interviewType = this.interviewTypeDao.find(id);
		if (interviewType == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, interviewType);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response update(@PathParam("id") Long id, InterviewType interviewType) {
		this.logger.info("update(): " + interviewType);
		interviewType.setId(id);
		if (interviewType == null || interviewType.getModeofInterview().trim().isEmpty()) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {
			try {
				interviewType = this.interviewTypeDao.save(interviewType);
				return new Response(ExceptionMessage.StatusSuccess,interviewType);
			} catch (Exception e) {
				return new Response(ExceptionMessage.DuplicateRecord);
			}
		}
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
