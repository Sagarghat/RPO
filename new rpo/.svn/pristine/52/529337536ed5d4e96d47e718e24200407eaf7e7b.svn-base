package com.ojas.rpo.security.util;

import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.InterviewNotes;
import com.ojas.rpo.security.entity.Response;

public class ValidateInterviewNotes {

	
	public static Response validateReqNotes(InterviewNotes interviewNotes) {
		if (interviewNotes.getRegistrationId() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "RegistrationId must required");
		else if (interviewNotes.getInterviewId() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "Interviewrd must required");
		else if (interviewNotes.getNoteType() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "noteType must required");
		else if (interviewNotes.getNoteData() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "noteDate must required");
		else
			return null;

	}
}
