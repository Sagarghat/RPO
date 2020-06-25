package com.ojas.rpo.security.util;

import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;

public class ValidateRequruiters {

	public static Response validateRecruiters(AssignedRecruitersMapper mapper) {

		if (mapper.getRecruiterIdsList().length == 0) {
			return new Response(ExceptionMessage.DataIsEmpty, "recruiter details must require");
		}
		if (mapper.getClientId() == null) {
			return new Response(ExceptionMessage.DataIsEmpty, "Client id must required");
		}
		if (mapper.getRequirementId() == null) {
			return new Response(ExceptionMessage.DataIsEmpty, "requritement id must required");
		}
		if(mapper.getAssignedDate()==null) {
			return new Response(ExceptionMessage.DataIsEmpty, "Assigning datae must required");
		}
			
		if (mapper.getUserId() == null) {
			return new Response(ExceptionMessage.DataIsEmpty, "user id must required");
		}

		return null;

	}

}
