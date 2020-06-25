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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.permissions.PermissionsDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Feature;
import com.ojas.rpo.security.entity.Permissions;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.FeatureSearch;
import com.ojas.rpo.security.util.PermissionSearch;

@Path("/permissions")
@Component
public class PermissionsResource {

	@Autowired
	private PermissionsDao permissionsDao;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(Permissions permissions) {
		try {
			if (permissions != null && !permissions.getPermissionName().trim().isEmpty()) {
				Permissions permission = this.permissionsDao.save(permissions);
				return new Response(ExceptionMessage.StatusSuccess, permission);
			} else {
				return new Response(ExceptionMessage.DataIsNotSaved);
			}
		} catch (Exception e) {
			return new Response(ExceptionMessage.Bad_Request);
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/findAll/{pageNo}/{pageSize}")
	public @ResponseBody Response getAll(@PathParam("pageNo") Integer pageNo,@PathParam("pageSize") Integer pageSize) {
		Response response = new Response();
		Integer startingRow = 0;
		Integer totalRecords = 0;
		
		if(pageNo == 1) {
			startingRow =0;
		}else {
			startingRow = ((pageNo-1) *pageSize);
		}
		
		List<Permissions> list = this.permissionsDao.findAllPermissions(startingRow,pageSize);
		if(list==null) {
			response = new Response(ExceptionMessage.DataIsEmpty);
		}else {
			Integer totalPages = 1;
			totalRecords = this.permissionsDao.findAll().size();
			if(totalRecords == 0) {
				response.setTotalPages(0);
			}
			else {
				totalPages = totalRecords / pageSize;
				totalPages = (totalRecords % pageSize) > 0 ? totalPages+1 :totalPages;
			}
			response = new Response(ExceptionMessage.StatusSuccess,list);
			response.setTotalPages(totalPages);
			response.setTotalRecords(totalRecords);
			
		}
		return response;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getById/{id}")
	public @ResponseBody Response getById(@PathParam("id") Long id) {
		Permissions find = this.permissionsDao.find(id);
		if (find != null) {
			return new Response(ExceptionMessage.StatusSuccess, find);
		} else
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response update(@PathParam("id") Long id, Permissions permissions) {
		permissions.setId(id);
		Response response = null;
		if (permissions.getId() == null || permissions.getPermissionName().trim().isEmpty()) {
			response = new Response(ExceptionMessage.DataIsEmpty);
			response.setRes("Invalid feature id");
		} else {
			try {
			 permissions = this.permissionsDao.save(permissions);

			return new Response(ExceptionMessage.StatusSuccess, permissions);
		} catch (Exception e) {
			return new Response(ExceptionMessage.DuplicateRecord);
		}
		}
		return response;
	}

	@POST
	@Path("/permissionbyrole")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)	
	public Response getPermissionsByRole(PermissionSearch permissionSearch ) {
		Response response = null;
		List<Permissions> list = permissionsDao.getPermissionsByRole(permissionSearch);
		if(list.isEmpty() || list == null) {
			response = new Response(ExceptionMessage.DataIsEmpty);
		}else {
			response = new Response(ExceptionMessage.StatusSuccess,list);
		}
		return response;
	}
	
}
