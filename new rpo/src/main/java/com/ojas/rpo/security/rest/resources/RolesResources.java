package com.ojas.rpo.security.rest.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.roles.RolesDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Roles;
import com.ojas.rpo.security.entity.Skill;

@Component
@Path("/roles")
public class RolesResources {

	@Autowired
	private RolesDao rolesdao;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(Roles roles) {
		Response response = new Response();
		try {
			if (roles != null && !roles.getName().trim().isEmpty()) {
				Roles role = rolesdao.save(roles);

				response = new Response(ExceptionMessage.StatusSuccess, role);
			} else {
				response = new Response(ExceptionMessage.DataIsEmpty);
			}
		} catch (Exception e) {
			response = new Response(ExceptionMessage.DuplicateRecord);
		}
		return response;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response read(@PathParam("id") Long id) {
		Roles roles = this.rolesdao.find(id);
		if (roles == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, roles);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/findAll/{pageNo}/{pageSize}")
	public @ResponseBody Response findAllData(@PathParam("pageNo") Integer pageNo,
			@PathParam("pageSize") Integer pageSize) {
		Response response = null;
		Integer totalRecords = 0;
		Integer startingRow = 0;
		if (pageNo == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNo - 1) * pageSize);
		}
		List<Roles> findAll = rolesdao.findAll(startingRow, pageSize);
		if (findAll == null) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {
			totalRecords = rolesdao.findAll().size();
			Integer totalPages = 1;
			if (totalRecords == 0) {
				response = new Response();
				response.setTotalPages(0);
			} else {
				totalPages = totalRecords / pageSize;

				totalPages = (totalRecords % pageSize > 0) ? totalPages + 1 : totalPages;

			}

			response = new Response(ExceptionMessage.StatusSuccess, findAll);
			response.setTotalPages(totalPages);
			response.setTotalRecords(totalRecords);
			return response;
		}

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response update(@PathParam("id") Long id, Roles roles) {
		roles.setId(id);
		if (roles == null || roles.getName().trim().isEmpty()) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {
			try {
				roles = this.rolesdao.save(roles);
				return new Response(ExceptionMessage.StatusSuccess, roles);
			} catch (Exception e) {
				return new Response(ExceptionMessage.DuplicateRecord);
			}
		}
	}

}
