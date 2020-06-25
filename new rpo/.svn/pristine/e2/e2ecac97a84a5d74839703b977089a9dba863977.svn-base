package com.ojas.rpo.security.rest.resources;

import java.util.ArrayList;
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

import com.ojas.rpo.security.dao.industry.IndustryDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Industry;
import com.ojas.rpo.security.entity.Response;

@Component
@Path("/industry")
public class IndustryResource {

	@Autowired
	private IndustryDao industryDao;

	Response response = new Response();

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(Industry industry) {

		if (industry != null) {
			Industry industry2 = this.industryDao.save(industry);
			response = new Response(ExceptionMessage.StatusSuccess, industry2, "data saved");
		} else
			response = new Response(ExceptionMessage.Bad_Request, "data must available");

		return response;

	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response getById(@PathParam("id") Long id) {
		Industry industry = new Industry();
		if (id != 0) {
			industry = this.industryDao.find(id);
		}
		if (industry != null)
			response = new Response(ExceptionMessage.StatusSuccess, industry);
		return response;

	}

	@GET
	@Path("/search/{pagesize}/{pageno}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response getAll(@PathParam("pagesize") Integer pageSize, @PathParam("pageno") Integer pageNum) {
		Integer totalPages = 1;
		Integer totalrecords = 0;

		List<Industry> industryList = new ArrayList<Industry>();

		List<Industry> trasIndustriesList = new ArrayList<Industry>();

		industryList = this.industryDao.findAll();

		totalrecords = industryList.size();
		for (int i =(pageNum-1)*pageSize ; i < (pageNum*pageSize); i++) {

			trasIndustriesList.add(industryList.get(i));

		}

		if (totalrecords == 0)
			response.setTotalPages(totalPages);
		else if (totalrecords == 1 || totalrecords <= pageSize)
			response.setTotalPages(1);
		else {
			totalPages = totalrecords / pageSize ;
			System.out.println(totalPages);
			totalPages=((totalrecords % pageSize) >0 ?totalPages+1:totalPages);

		}
		System.out.println(totalPages);
		System.out.println(industryList);

		if (!trasIndustriesList.isEmpty())

			response = new Response(ExceptionMessage.OK, trasIndustriesList);
		else {

			response = new Response(ExceptionMessage.DataIsEmpty, "data is empty");
		}
		response.setTotalPages(totalPages);
		response.setTotalRecords(totalrecords);
		return response;

	}

}
