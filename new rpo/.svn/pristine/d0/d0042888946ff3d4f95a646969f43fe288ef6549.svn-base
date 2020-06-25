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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.location.ServicesDao;
import com.ojas.rpo.security.entity.BillingModel;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;
import com.ojas.rpo.security.entity.Services;

@Component
@Path("/services")
public class ServicesResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ServicesDao servicesDao;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("findAll/{pageNum}/{pageSize}")
	public @ResponseBody Response getdata(@PathParam("pageNum") Integer pageNum,
			@PathParam("pageSize") Integer pagesize) {
		this.logger.info("list()");
		Response response = null;
		Integer totalRecords = 0;
		Integer startingRow = 0;
		if (pageNum == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNum - 1) * pagesize);
		}
		List<Services> add = this.servicesDao.findAll(startingRow, pagesize);
		if (add == null) {
			return new Response(ExceptionMessage.DataIsEmpty, add);
		}
		totalRecords = servicesDao.findAll().size();
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
		response.setResult(add);
		return response;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response read(@PathParam("id") Long id) {
		this.logger.info("read(id)");
		Services services = this.servicesDao.find(id);
		if (services == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, services);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(Services services) {

		this.logger.info("create(): " + services);

		if (services != null && !services.getServiceName().trim().isEmpty()) {
			try {
				Services services2 = this.servicesDao.save(services);
				return new Response(ExceptionMessage.StatusSuccess, services2);
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
	public @ResponseBody Response update(@PathParam("id") Long id, Services services) {

		services.setId(id);
		this.logger.info("update(): " + services);
		if (services == null || services.getServiceName().trim().isEmpty()) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {
			try {
				services = this.servicesDao.save(services);
				return new Response(ExceptionMessage.StatusSuccess, services);
			} catch (Exception e) {
				return new Response(ExceptionMessage.DuplicateRecord);
			}
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/delete/{id}")
	public void delete(@PathParam("id") Long id, Services services) {
		services.setId(id);
		this.logger.info("read(id)");

		this.servicesDao.delete(id);
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
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response delete(@PathParam("id") Long id) {
		Services services = this.servicesDao.find(id);
		if (services != null) {
			int i = this.servicesDao.deleteById(id);
			if (i == 0)
				return new Response(ExceptionMessage.Exception);
			else
				return new Response(ExceptionMessage.StatusSuccess, services);
		} else
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);

	}
}
