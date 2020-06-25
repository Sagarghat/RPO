package com.ojas.rpo.security.util;

import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.RequirementNotes;
import com.ojas.rpo.security.entity.Response;

public class ValidateReqnotes {

	
	public static Response validateReqnotes(RequirementNotes requirementNotes) {
		if (requirementNotes.getRegistrationId() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "RegistrationId must required");
		else if (requirementNotes.getRecruiterId() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "requirementId must required");
		else if (requirementNotes.getNoteType() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "noteType must required");
		else if (requirementNotes.getNoteData() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "noteDate must required");
		else
			return null;

	}
	
}
