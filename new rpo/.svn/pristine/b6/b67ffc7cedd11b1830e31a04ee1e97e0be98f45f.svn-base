package com.ojas.rpo.security.rest.resources;

import java.util.List;

import javax.ws.rs.Consumes;
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

import com.ojas.rpo.security.dao.feature.FeatureDao;
import com.ojas.rpo.security.dao.menu.MenuDao;
import com.ojas.rpo.security.dao.permissions.PermissionsDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Menu;
import com.ojas.rpo.security.entity.Response;

@Component
@Path("/menu")
public class MenuResource {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MenuDao menuDao;

	@Autowired
	private PermissionsDao permissionsDao;

	@Autowired
	private FeatureDao featureDao;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/save")
	public @ResponseBody Response create(Menu menu) {
		System.out.println("==========inside post method====RPO");
		// menu.setId(menu.getId());
		this.logger.info("create(): " + menu);
		Response response = null;
		try {

			boolean b = this.menuDao.chekduplicate(menu.getMenuName());
			if (b) {
				response = new Response(ExceptionMessage.DuplicateRecord, menu.getMenuName());
				response.setRes("menuName already exist !! ");
			}
			if (menu.getMenuName() != null && menu.getId() == null) {
				Menu save = this.menuDao.save(menu);
				response = new Response(ExceptionMessage.StatusSuccess, save);
			} else if (menu.getMenuName() != null && menu.getId() != null) {
				Menu save1 = this.menuDao.save(menu);
				response = new Response(ExceptionMessage.StatusSuccess, save1);
			} else {
				response = new Response(ExceptionMessage.DataIsNotSaved);
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
		this.logger.info("read(id)");
		Menu find = this.menuDao.find(id);
		if (find == null) {
			return new Response(ExceptionMessage.UnRegistrationUser);
		} else
			return new Response(ExceptionMessage.StatusSuccess, find);
	}

	@GET
	@Path("/findAll/{pageNum}/{pageSize}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response findAll(@PathParam("pageNum") Integer pageNum,
			@PathParam("pageSize") Integer pagesize) {
		Response response = null;
		Integer totalRecords = 0;
		Integer startingRow = 0;
		if (pageNum == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNum - 1) * pagesize);
		}
		List<Menu> findAll1 = menuDao.findAllMenu(startingRow, pagesize);
		if (null != findAll1) {
			response = new Response(ExceptionMessage.StatusSuccess, findAll1);

			totalRecords = findAll1.size();
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
			response.setResult(findAll1);
		} else {
			response = new Response(ExceptionMessage.DataIsEmpty);
		}

		return response;
	}

}
