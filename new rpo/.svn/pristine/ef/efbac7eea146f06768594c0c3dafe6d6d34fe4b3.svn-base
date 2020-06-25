package com.ojas.rpo.security.rest.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.sendemail.MailSender;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.EmailEntity;
import com.ojas.rpo.security.util.ValidateEmail;

@Path("/email")
@Component
public class EmialSendResource {

	@Autowired
	private MailSender javaMailSender;

	@POST
	@Path("/sendEmail")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response sendingEmail(EmailEntity emailEntity) throws Exception {
		System.out.println("Welcome to email Sending");
		if (emailEntity.getTo() != null) {
			if (emailEntity.getMessageBody() != null && !emailEntity.getMessageBody().isEmpty()) {
				Response sendMailResponse = javaMailSender.sendMail(emailEntity);
				return sendMailResponse;
			} else {
				return new Response(ExceptionMessage.DataIsEmpty, "please enter the MessageBody");
			}
		} else {
			return new Response(ExceptionMessage.DataIsEmpty, "To address must required");
		}

	}

	
}