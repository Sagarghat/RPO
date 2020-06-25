package com.ojas.rpo.security.rest.resources;

import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.RequirementNotes;
import com.ojas.rpo.security.entity.Response;

public class ValidateReqNotes {

	
	public static Response validateReqNotes(RequirementNotes reqNotes) {
		if (reqNotes.getRegistrationId() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "RegistrationId must required");
		else if (reqNotes.getRecruiterId() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "recruiterId must required");
		else if (reqNotes.getNoteType() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "noteType must required");
		else if (reqNotes.getNoteData() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "noteDate must required");
		else
			return null;

	}

}
