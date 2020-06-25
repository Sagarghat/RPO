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

import com.ojas.rpo.security.dao.plans.PlansDAO;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Plans;
import com.ojas.rpo.security.entity.PlansVO;
import com.ojas.rpo.security.entity.Response;

@Component
@Path("/plans")
public class PlansResource {

	@Autowired
	private PlansDAO plansDAO;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("findAll/{pageNum}/{pageSize}/{regId}")
	public @ResponseBody Response getPlansList(@PathParam("pageNum") Integer pageNum,
			@PathParam("pageSize") Integer pagesize, @PathParam("regId") Long regId) {

		return plansDAO.findAllPlans(pageNum, pagesize, regId);

	}

	@Path("/{id}/{regId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response getUserPlans(@PathParam("id") Long id, @PathParam("regId") Long regisrtationId) {

		List<Plans> plans = plansDAO.findById(id, regisrtationId);
		if (plans.isEmpty()) {
			return new Response(ExceptionMessage.DataIsEmpty, plans);
		}

		return new Response(ExceptionMessage.StatusSuccess, plans.get(0));
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(Plans plans) {
		try {
			if (plans != null && plans.getNoOfEmails() != null && plans.getNoOfUsers() != null
					&& plans.getPlanType() != null && plans.getPrice() != 0 && plans.getTalentFlag() != 0) {

				plans = this.plansDAO.save(plans);
				return new Response(ExceptionMessage.StatusSuccess, plans);

			} else {

				return new Response(ExceptionMessage.DataIsEmpty);
			}
		} catch (Exception e) {
			return new Response(ExceptionMessage.DuplicateRecord);
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response update(@PathParam("id") Long id, Plans plans) {
		plans.setPlanId(id);
		if (plans == null || plans.getNoOfEmails() == null || plans.getNoOfUsers() == null
				|| plans.getPlanType() == null || plans.getPrice() == 0 || plans.getTalentFlag() == 0) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {
			try {
				plans = this.plansDAO.save(plans);
				return new Response(ExceptionMessage.StatusSuccess, plans);
			} catch (Exception e) {
				return new Response(ExceptionMessage.DuplicateRecord);
			}
		}
	}

}
