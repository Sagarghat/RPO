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
import com.ojas.rpo.security.certificatenames.CertificateTypeDao;
import com.ojas.rpo.security.entity.AddQualification;
import com.ojas.rpo.security.entity.Certificate;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;

@Component
@Path("/certificates")
public class certificateResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CertificateTypeDao certificateTypeDao;

	@Autowired
	private ObjectMapper mapper;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("findAll/{pageNo}/{pageSize}")
	public @ResponseBody Response getdata(@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize)
			throws IOException {
		Response response = null;
		Integer totalRecords = 0;
		Integer startingRow = 0;
		this.logger.info("list()");
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
		List<Certificate> certificate = this.certificateTypeDao.findAll(startingRow, pageSize);
		if (certificate == null || certificate.isEmpty()) {
			response = new Response(ExceptionMessage.DataIsEmpty);
		}
		totalRecords = certificateTypeDao.findAll().size();
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
		response.setResult(certificate);
		return response;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response read(@PathParam("id") Long id) {
		this.logger.info("read(id)");
		Certificate certificate = this.certificateTypeDao.find(id);
		if (certificate == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, certificate);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(Certificate certificate) {
		this.logger.info("create(): " + certificate);
		if (certificate != null && !certificate.getCertificationName().trim().isEmpty()) {
			try {
				certificate = this.certificateTypeDao.save(certificate);
				return new Response(ExceptionMessage.StatusSuccess, certificate);
			} catch (Exception e) {
				return new Response(ExceptionMessage.DuplicateRecord);
			}
		} else
			return new Response(ExceptionMessage.Bad_Request);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response create(@PathParam("id") Long id, Certificate certificate) {
		System.out.println("==========inside post method====RPO");
		certificate.setId(id);
		this.logger.info("update(): " + certificate);
		if (certificate == null || certificate.getCertificationName().trim().isEmpty()) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {
			try {
				certificate = this.certificateTypeDao.save(certificate);
				return new Response(ExceptionMessage.StatusSuccess, certificate);
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
