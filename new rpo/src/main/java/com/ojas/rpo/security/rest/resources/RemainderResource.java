package com.ojas.rpo.security.rest.resources;

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

import com.ojas.rpo.security.dao.remainder.RemainderDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Remainder;
import com.ojas.rpo.security.entity.Response;

@Component
@Path("/remainderResource")
public class RemainderResource {
	@Autowired
	private RemainderDao remainderDao;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(Remainder list) {
		System.out.println("==========inside post method====RPO");
		
		/*
		 * int key = list.getTime();
		 * 
		 * switch (key) { case 0: list.setDesc("NONE"); break; case 60:
		 * list.setDesc("1 hr before"); break;
		 * 
		 * case 120: list.setDesc("2 hrs before"); break;
		 * 
		 * default: list.setDesc(list.getTime()+" before"); break; }
		 */
		if (this.remainderDao.save(list) == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		} else
			return new Response(ExceptionMessage.StatusSuccess, list);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAll() {

		return new Response(ExceptionMessage.Accepted, remainderDao.findAll());

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response read(@PathParam("id") Long id) {

		Remainder list = this.remainderDao.find(id);
		if (list == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, list);
	}

}
