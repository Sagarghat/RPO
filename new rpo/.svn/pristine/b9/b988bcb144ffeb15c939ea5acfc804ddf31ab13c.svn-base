package com.ojas.rpo.security.rest.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.reports.ReportsDao;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.Reports;
import com.ojas.rpo.security.util.ValidateReports;

@Component
@Path("/reports")
public class ReportsResource {

	@Autowired
	private ReportsDao reportsDao;     

	@POST
	@Path("/reports")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response getScreenRejectVsSubmissionstToClient(Reports reports) {

		Response validateReports = ValidateReports.validateReports(reports);
		if (validateReports == null) {

			return reportsDao.getRecruiterReports(reports);
		} else {
			return validateReports;
		}

	}

}
