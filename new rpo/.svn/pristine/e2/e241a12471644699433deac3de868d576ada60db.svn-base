package com.ojas.rpo.security.rest.resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.bdmreqdtls.BdmReqDao;
import com.ojas.rpo.security.entity.BdmReq;
import com.ojas.rpo.security.entity.BdmReqSearch;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.BdmUpdate;
import com.ojas.rpo.security.util.ValidateBdm;
import com.sun.media.jfxmedia.Media;

@Path("/Bdmrequire")
@Component
public class BdmRequirement {

	@Autowired
	private BdmReqDao bdmReqDao;

	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(BdmReq bdmReq, @Context HttpServletRequest request) {
		if (bdmReq != null) {
			bdmReq = bdmReqDao.save(bdmReq);
			if (bdmReq == null) {
				return new Response(ExceptionMessage.Bad_Request, "Error Occur During the updating the requirement");
			}
		}
		return new Response(ExceptionMessage.StatusSuccess, HttpStatus.OK);

	}

	@POST
	@Path("/getRequirementById")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response getRequirementById(BdmReqSearch bdmReqSearch, @Context HttpServletRequest request) {

		BdmReq requirement = null;

		if (bdmReqSearch != null) {

			requirement = this.bdmReqDao.getRequirementById(bdmReqSearch);
			if (requirement == null) {
				return new Response(ExceptionMessage.Bad_Request, "Error Occur During the updating the requirement");
			}

		}
		return new Response(ExceptionMessage.StatusSuccess, requirement);

	}

	@POST

	@Path("/getAllRequirementsByRegId")

	@Produces(MediaType.APPLICATION_JSON)

	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response getAllRequirementsByRegId(BdmReqSearch bdmReqSearch,@Context HttpServletRequest request) {

		Response reponse = this.bdmReqDao.getAllRequirementsByRegId(bdmReqSearch);

		return reponse;

	}

	/*
	 * @GET
	 * 
	 * @Path("/findAll")
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public @ResponseBody Response findAll()
	 * { return new Response(ExceptionMessage.StatusSuccess, bdmReqDao.findAll()); }
	 */

	@POST
	@Path("/bdmupdate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response bdmUpdate(BdmUpdate bdmUpdate) {

		Response response = ValidateBdm.validateBdm(bdmUpdate);
		if (response == null) {
			response = this.bdmReqDao.updateReqDetails(bdmUpdate);
		}
		return response;

	}

}
