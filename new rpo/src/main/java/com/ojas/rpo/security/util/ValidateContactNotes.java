package com.ojas.rpo.security.util;

import com.ojas.rpo.security.entity.ContactNotes;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;

public class ValidateContactNotes {

	public static Response validateContactNotes(ContactNotes contactNotes) {
		if (contactNotes.getRegistrationId() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "RegistrationId must required");
		else if (contactNotes.getContactId() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "contactId must required");
		else if (contactNotes.getNoteType() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "noteType must required");
		else if (contactNotes.getNoteData() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "noteDate must required");
		else
			return null;

	}
}
