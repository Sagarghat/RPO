package com.ojas.rpo.security.util;

import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;

public class ValidateReports {
	private ValidateReports() {

	}

	public static Response validateReports(Reports reports) {
		Response response = new Response();
		if (reports.getRegistrationId() == null)
			response = new Response(ExceptionMessage.DataIsEmpty, "RegistrationId must Required");

		if (reports.getRole() == null || reports.getRole().isEmpty())
			response = new Response(ExceptionMessage.DataIsEmpty, "Role must Required");
		if (reports.getRoleUserId() == null)
			response = new Response(ExceptionMessage.DataIsEmpty, "Role UserId must Required");
		if (reports.getSubmissionCondition() == null || reports.getSubmissionCondition().isEmpty())
			response = new Response(ExceptionMessage.DataIsEmpty, " First(Submission) condition must Required");
		if (reports.getRejectedCondition() == null || reports.getRejectedCondition().isEmpty())
			response = new Response(ExceptionMessage.DataIsEmpty, " Second(Rejections) condition must Required");

		return response;

	}

}
