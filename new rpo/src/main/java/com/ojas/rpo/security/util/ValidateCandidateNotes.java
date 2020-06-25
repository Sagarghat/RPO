package com.ojas.rpo.security.util;

import com.ojas.rpo.security.entity.CandidateNotes;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;

public class ValidateCandidateNotes {

	
	public static Response validateCandidateNotes(CandidateNotes candidateNotes) {
		if (candidateNotes.getRegistrationId() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "RegistrationId must required");
		else if (candidateNotes.getCandidateId()  == null)
			return new Response(ExceptionMessage.DataIsEmpty, "candidateId must required");
		else if (candidateNotes.getNoteType() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "noteType must required");
		else if (candidateNotes.getNoteData() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "noteDate must required");
		else
			return null;

	}
}
