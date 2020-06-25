package com.ojas.rpo.security.rest.resources;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.addcandidaterating.CandidateRatingDaoInf;
import com.ojas.rpo.security.entity.CandidateRating;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.CandidateJobRatingSearch;

@Component
@Path("/addcandidaterating")
public class CandidateRatingResources {

	@Autowired
	private CandidateRatingDaoInf candidateRatingDaoInf;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(CandidateRating candidateRating) {

		Response res = null;
		if (candidateRating == null) {
			return new Response(ExceptionMessage.DataIsEmpty);
		}
		CandidateRating find = null;

		if (candidateRating.getId() != null) {
			find = candidateRatingDaoInf.find(candidateRating.getId());
			if (find.getBdmId().equals(candidateRating.getBdmId())
					&& find.getCandidateId().equals(candidateRating.getCandidateId())
					&& find.getRegistrationId().equals(candidateRating.getRegistrationId())
					&& candidateRating.getUserId().equals(find.getUserId())
					&& candidateRating.getRating().equals(find.getRating()))

				return new Response(ExceptionMessage.Bad_Request, "Record Already Exits");

		}

		if ((candidateRating.getId() == null || candidateRating.getId() != null) && (candidateRating.getRating() == null
				|| (candidateRating.getComments() == null && candidateRating.getComments().trim().isEmpty())
				|| candidateRating.getCandidateId() == null || candidateRating.getRegistrationId() == null
				|| candidateRating.getUserId() == null || candidateRating.getBdmId() == null))
			res = new Response(HttpStatus.CONFLICT, ExceptionMessage.Exception);
		else {
			candidateRating.setLastUpdateDate(new Date());
			CandidateRating save = candidateRatingDaoInf.save(candidateRating);
			candidateRatingDaoInf.setRating(candidateRating);
			res = new Response(ExceptionMessage.Accepted, save);
		}
		return res;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/search")
	public @ResponseBody Response findByRatingJobsBasedOnCIdRegId(CandidateJobRatingSearch candidateJobRatingSearch) {
		if (candidateJobRatingSearch.getBdmId() == null || candidateJobRatingSearch.getCandidateId() == null
				|| candidateJobRatingSearch.getRegistrationId() == null)
			return new Response(ExceptionMessage.Bad_Request, "candidateId or bdmid or registrationId miss");
		else
			return candidateRatingDaoInf.findByJobsWithIdrating(candidateJobRatingSearch);

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/find")
	public @ResponseBody Response findId(CandidateRating candidateRating) {
		CandidateRating find = null;
		Long id = candidateRatingDaoInf.getId(candidateRating);
		if (id == null) {
			return new Response(ExceptionMessage.Bad_Request);
		} else {
			find = candidateRatingDaoInf.find(id);
		}
		return new Response(ExceptionMessage.Accepted, find);
	}

}
