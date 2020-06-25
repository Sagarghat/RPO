package com.ojas.rpo.security.util;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;

public class ValidateBdm {
	public static Response validateBdm(BdmUpdate bdmUpdate) {
		if (bdmUpdate.getClientId() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "clientId must not be null");
		if (bdmUpdate.getRegistrationId() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "registrion id must not be null");
		if (bdmUpdate.getBdmId() == null)
			return new Response(ExceptionMessage.DataIsEmpty, "registrion id must not be null");

		return null;

	}

}
