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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.Qualification.QualificationDao;
import com.ojas.rpo.security.entity.AddQualification;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;

@Component
@Path("/Qualification")
public class AddQualificationResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private QualificationDao qualificationdao;

	@Autowired
	private ObjectMapper mapper;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("findAll/{pageNum}/{pageSize}/{registrationId}")
	public @ResponseBody Response getAddQualification(@PathParam("pageNum") Integer pageNum,
			@PathParam("pageSize") Integer pagesize, @PathParam("registrationId") Integer registrationId) {
		Response response = null;
		Integer totalRecords = 0;
		Integer startingRow = 0;
		if (pageNum == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNum - 1) * pagesize);
		}

		List<AddQualification> addQualification = qualificationdao.findAllAddQualification(startingRow, pagesize,
				registrationId);

		if (addQualification == null) {
			return new Response(ExceptionMessage.DataIsEmpty, addQualification);
		}
		totalRecords = qualificationdao.findAll(registrationId).size();
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
		response.setResult(addQualification);
		return response;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}/{registrationId}")
	public @ResponseBody Response read(@PathParam("id") Long id, @PathParam("registrationId") Integer registrationId) {
		this.logger.info("read(id)");
		AddQualification addQualification = this.qualificationdao.find(id, registrationId);
		if (addQualification == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else {
			return new Response(ExceptionMessage.StatusSuccess, addQualification);
		}
	}

/////////////////////////////////////////////////////////////////
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/save")
	public @ResponseBody Response create(AddQualification addqualification) {
		this.logger.info("create(): " + addqualification);

		if (addqualification != null && !addqualification.getqualificationName().trim().isEmpty()
				&& addqualification.getRegistrationId() != null) {
			try {
				addqualification = this.qualificationdao.save(addqualification);
				return new Response(ExceptionMessage.StatusSuccess, addqualification);
			} catch (Exception e) {
				return new Response(ExceptionMessage.DuplicateRecord);
			}

		} else {
			return new Response(ExceptionMessage.DataIsNotSaved);
		}
	}

	////////////////////////////////////////////////////////////////
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}/{registrationId}")
	public @ResponseBody Response update(@PathParam("id") Long id, @PathParam("registrationId") Integer registrationId,
			AddQualification addqualification) {
		addqualification.setId(id);
		addqualification.setRegistrationId(registrationId);
		if (addqualification == null || addqualification.getqualificationName().trim().isEmpty()
				|| addqualification.getRegistrationId() == null) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {
			try {
				addqualification = this.qualificationdao.save(addqualification);
				return new Response(ExceptionMessage.StatusSuccess, addqualification);
			} catch (Exception e) {
				return new Response(ExceptionMessage.DuplicateRecord);
			}
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}/{registrationId}")
	public void delete(@PathParam("id") Long id, @PathParam("registrationId") Integer registrationId,
			AddQualification addqualification) {
		AddQualification qualification = this.qualificationdao.find(id, registrationId);
		qualification.setId(id);
		qualification.setStatus("Inactive");
		this.qualificationdao.delete(id);
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
