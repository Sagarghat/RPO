package com.ojas.rpo.security.rest.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.xpath.operations.String;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.assignedrecruiters.AssingnedRecruitersDao;
import com.ojas.rpo.security.dao.sendemail.MailSender;
import com.ojas.rpo.security.entity.AssignedRecruiters;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.AssignedRecruitersMapper;
import com.ojas.rpo.security.util.ValidateRequruiters;

@Component
@Path("/assignedRecruiters")
public class AssignedRecruitersResouce {

	@Autowired
	private AssingnedRecruitersDao assingnedRecruitersDao;
	private Response response;

	@POST
	@Path("/create")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(AssignedRecruitersMapper mapper) {

		response = ValidateRequruiters.validateRecruiters(mapper);
		List<AssignedRecruiters> recList = new ArrayList<>();

		if (response == null) {

			recList=this.assingnedRecruitersDao.create(mapper);

			if (!recList.isEmpty())
				response = new Response(ExceptionMessage.StatusSuccess, recList, "recruiters saved");
			else
				response = new Response(ExceptionMessage.DataIsNotSaved, "Data is not saved");
		}

		return response;
	}

	@DELETE
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response deleteRecruiters(AssignedRecruitersMapper mapper) {

		return this.assingnedRecruitersDao.deleteRecruitersById(mapper);

	}

	@POST
	@Path("/getByRecId")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response getById(AssignedRecruitersMapper mapper) {

		return this.assingnedRecruitersDao.getByRecId(mapper);

	}

	@POST
	@Path("/search")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response getAllRecruiters(AssignedRecruitersMapper mapper) {

		return this.assingnedRecruitersDao.getAllRecruiters(mapper);
	}

}
