package com.ojas.rpo.security.util;

import org.springframework.http.HttpStatus;

import com.ojas.rpo.security.entity.BdmReq;
import com.ojas.rpo.security.entity.Response;	

public class BdmReqUtil {

	public Response bdmReqUtil(BdmReq bdmReq) {

		if (bdmReq.getClient().getClientName() == null || bdmReq.getClient().getClientName().trim().isEmpty()) {
			return new Response(HttpStatus.NO_CONTENT, "Client Name Must Require");
		} else if (bdmReq.getAddContact().getLastName() == null
				|| bdmReq.getAddContact().getLastName().trim().isEmpty()) {
			return new Response(HttpStatus.NO_CONTENT, "Last Name Must Require");
		} else if (bdmReq.getNameOfRequirement() == null) {
			return new Response(HttpStatus.NO_CONTENT, "Name of Requirement Must Require");
		} else if (bdmReq.getNoOfPositions() == null) {
			return new Response(HttpStatus.NO_CONTENT, "Number of Position Must Require");
		} else if (bdmReq.getRequirementStartdate() == null) {
			return new Response(HttpStatus.NO_CONTENT, "Start Date Must Require");
		} else if (bdmReq.getRequirementEndDate() == null) {
			return new Response(HttpStatus.NO_CONTENT, "End Date Must Require");
		} else if (bdmReq.getSkills() == null || bdmReq.getSkills().isEmpty()) {
			return new Response(HttpStatus.NO_CONTENT, "Skills Must Require");
		} else if (bdmReq.getQualifications() == null || bdmReq.getQualifications().isEmpty()) {
			return new Response(HttpStatus.NO_CONTENT, "Qualification Must Require");
		} else if (bdmReq.getJobType() == null) {
			return new Response(HttpStatus.NO_CONTENT, "Job Type Must Require");
		} else if (bdmReq.getLocations().isEmpty() || bdmReq.getLocations() == null) {
			return new Response(HttpStatus.NO_CONTENT, "select atleast one location");
		} else if (bdmReq.getTotalExperience() == null) {
			return new Response(HttpStatus.NO_CONTENT, "Total Experience Must Require");
		}

		return null;
	}
}