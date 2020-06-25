package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
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
import com.ojas.rpo.security.dao.typeOfAddres.TypeOfAddressDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;
import com.ojas.rpo.security.entity.TypeOfAddress;

@Component
@Path("/typeofaddress")
public class TypeOfAddressResources {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TypeOfAddressDao typeOfAddressDao;
	@Autowired
	private ObjectMapper mapper;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response getdata() throws IOException {
		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		List<TypeOfAddress> add = this.typeOfAddressDao.findAll();
		if (add == null) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

		if (add.size() > 0) {
			return new Response(ExceptionMessage.StatusSuccess, add);
		} else {

			return new Response(ExceptionMessage.DataIsEmpty, "200", "NOEXCEPTION", "NULL");
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/billingAddress")
	public @ResponseBody Response getBillingAddressdata() throws IOException {
		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		List<TypeOfAddress> add = this.typeOfAddressDao.findByBillingAddress();
		if (add == null) {
			// throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

		if (add.size() > 0) {
			return new Response(ExceptionMessage.StatusSuccess, add);
		} else {

			return new Response(ExceptionMessage.DataIsEmpty, "200", "NOEXCEPTION", "NULL");
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/officeAddress")
	public @ResponseBody Response getOfficeAddressdata() throws IOException {
		this.logger.info("list()");
		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		List<TypeOfAddress> add = this.typeOfAddressDao.findByOfficeAddress();
		if (add == null) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else

		if (add.size() > 0) {
			return new Response(ExceptionMessage.StatusSuccess, add);
		} else {

			return new Response(ExceptionMessage.DataIsEmpty, "200", "NOEXCEPTION", "NULL");
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response read(@PathParam("id") Long id) {
		this.logger.info("read(id)");
		TypeOfAddress list = this.typeOfAddressDao.find(id);
		if (list == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, list);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response read() {
		this.logger.info("read(id)");
		List<TypeOfAddress> typeOfAddress = this.typeOfAddressDao.findAll();
		if (typeOfAddress == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, typeOfAddress);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(TypeOfAddress address) {
		this.logger.info("create(): " + address);
		if (address != null && !address.getTypeOfAddress().trim().isEmpty()) {
			try {
				address = this.typeOfAddressDao.save(address);
				return new Response(ExceptionMessage.StatusSuccess, address);
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
	public @ResponseBody Response update(@PathParam("id") Long id, TypeOfAddress typeOfAddress) {
		System.out.println("==========inside post method====RPO");
		typeOfAddress.setId(id);
		this.logger.info("update(): " + typeOfAddress);
		if (typeOfAddress == null || typeOfAddress.getTypeOfAddress().trim().isEmpty()) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {
			try {
				typeOfAddress = this.typeOfAddressDao.save(typeOfAddress);
				return new Response(ExceptionMessage.StatusSuccess, typeOfAddress);
			} catch (Exception e) {
				return new Response(ExceptionMessage.DuplicateRecord);
			}
		}
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public void active(@PathParam("id") Long id) {

		this.logger.info("delete(id)");
		TypeOfAddress list = this.typeOfAddressDao.find(id);

		this.typeOfAddressDao.save(list);
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

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("findAll/{pageNum}/{pageSize}")
	public @ResponseBody Response getTypeOfAddress(@PathParam("pageNum") Integer pageNum,
			@PathParam("pageSize") Integer pagesize) {
		Response response = null;
		Integer totalRecords = 0;
		Integer startingRow = 0;
		if (pageNum == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNum - 1) * pagesize);
		}

		List<TypeOfAddress> typeOfAddress = typeOfAddressDao.findAllTypeOfAddress(startingRow, pagesize);

		if (typeOfAddress == null) {
			return new Response(ExceptionMessage.DataIsEmpty, typeOfAddress);
		}
		totalRecords = typeOfAddressDao.findAll().size();
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
		response.setResult(typeOfAddress);
		return response;
	}
}
