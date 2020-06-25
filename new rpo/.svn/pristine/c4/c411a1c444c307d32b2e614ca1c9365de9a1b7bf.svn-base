package com.ojas.rpo.security.util;

import org.springframework.http.HttpStatus;

import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;

public class ValidateEmail {

	public static Response validateEmail(EmailEntity emailEntity) {

		if (emailEntity.getTo() == null) {
			return new Response(HttpStatus.CONFLICT, " TO address Must require");
		}


		if (emailEntity.getFileAttachement() != null) {
			if (emailEntity.getFileType() == null) {
				return new Response(ExceptionMessage.DataIsEmpty, "File type mandatory for the File Attachement");
			}
		}

		


		return null;

	}
}
