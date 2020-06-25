package com.ojas.rpo.security.util;

import com.ojas.rpo.security.entity.ClientNotes;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;

public class ValidateClientNotes {

	public static Response validateClientNotes(ClientNotes clientNotes) {
		if (clientNotes.getRegistrationId() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "RegistrationId must required");
		else if (clientNotes.getClientId() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "ClientId must required");
		else if (clientNotes.getNoteType() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "noteType must required");
		else if (clientNotes.getNoteData() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "noteDate must required");
		else
			return null;

	}

}
