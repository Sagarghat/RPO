package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.InterviewDetails.InterviewDetailsDao;
import com.ojas.rpo.security.dao.addClientContact.AddContactDao;
import com.ojas.rpo.security.dao.candidate.CandidatelistDao;
import com.ojas.rpo.security.dao.interviewfeedback.InterviewFeedbackDao;
import com.ojas.rpo.security.dao.typeofprocess.ProcessDao;
import com.ojas.rpo.security.dao.user.UserDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.IntMapper;
import com.ojas.rpo.security.entity.InterviewDetails;
import com.ojas.rpo.security.entity.InterviewMapper;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.service.InterviewDetailsService;
import com.ojas.rpo.security.util.InterviewDetailsValidation;
import com.ojas.rpo.security.util.InterviewSearch;
import com.ojas.rpo.security.util.OutlookMailSender;

@Component
@Path("/interviewDetails")
public class InterviewDetailsResource {

	@Autowired
	private AddContactDao addContactDao;
	@Autowired
	private InterviewDetailsDao interviewDetailsDao;
	@Autowired
	private InterviewDetailsService interviewService;

	@Autowired
	private InterviewFeedbackDao interviewfeedbackDao;

	@Autowired
	private ProcessDao processDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	OutlookMailSender mailSender;

	@Autowired
	private CandidatelistDao dao;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(IntMapper mapper) {

		Response response = new Response();

		if (mapper != null) {
			response = InterviewDetailsValidation.validateIinterview(mapper);
			if (response.getStatus() == ExceptionMessage.Accepted) {
				response = interviewService.createInterview(mapper);
				if (response.getResult()==null) {
					response.setRes("candidate is not selected in previous interview or  not shortlisted ");
					response.setConflict(HttpStatus.CONFLICT);
				} 
				return response;
			} else
				return response;

		} else {
			response.setConflict(HttpStatus.CONFLICT);
			response.setRes("Data is empty");
			response.setStatus(ExceptionMessage.DataIsEmpty);
			return response;
		}

	}

	@ResponseBody
	@Path("/search")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response search(InterviewSearch search) {

		Response list = interviewDetailsDao.findDataBasedOnRegId(search);
		return list;

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response get(@PathParam("id") Long id) {

		IntMapper intMapper = interviewService.get(id);
		return new Response(ExceptionMessage.StatusSuccess, intMapper);

	}

	@POST
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(InterviewMapper interviewMapper) {
		Response response = new Response();

		if (interviewMapper != null) {
			response = InterviewDetailsValidation.validateIinterviewDetails(interviewMapper);
			if (response.getStatus() == ExceptionMessage.Accepted) {
				List<InterviewDetails> list = interviewService.createInterviewDetails(interviewMapper);
				if (list.isEmpty()) {
					response.setRes("No Candidate is selected with shortlist status");
					response.setConflict(HttpStatus.CONFLICT);
				} else {
					response.setResult(list);
					response.setStatus(ExceptionMessage.StatusSuccess);
					response.setConflict(HttpStatus.ACCEPTED);
				}
				return response;
			} else
				return response;

		} else {
			response.setConflict(HttpStatus.CONFLICT);
			response.setRes("Data is empty");
			response.setStatus(ExceptionMessage.DataIsEmpty);
			return response;
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getInterviewDetailsById/{id}/{reqId}/{userId}")
	public @ResponseBody Response getInterviewDetailsById(@PathParam("id") Long id, @PathParam("reqId") Long reqId,
			@PathParam("userId") Long userId) {

		InterviewDetails interviewDetails = this.interviewDetailsDao.getInterviewDetailsByCandidateId(id, reqId,
				userId);

		if (interviewDetails == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, interviewDetails);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response getAllInterviewDetails() {

		List<InterviewDetails> interviewDetails = this.interviewDetailsDao.findAll();

		if (interviewDetails == null) {
			return new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} else
			return new Response(ExceptionMessage.StatusSuccess, interviewDetails);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/interviewDetailslistByCandidateId/{id}/{status}/{reqId}/{userId}")
	public @ResponseBody Response interviewDetailslistByCandidateId(@PathParam("id") Long candidateId,
			@PathParam("status") String status, @PathParam("reqId") Long reqId, @PathParam("userId") Long userId)
			throws IOException {
		List<InterviewDetails> interviewDetls = this.interviewDetailsDao
				.getInterviewDetailsByCandidateIdAndStatus(candidateId, status, reqId, userId);

		if (interviewDetls == null) { // throw new WebApplicationException(404);
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {
			return new Response(ExceptionMessage.StatusSuccess, interviewDetls);
		}
	}

	private String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	@ResponseBody

	@GET

	@Path("/update")

	@Produces(MediaType.APPLICATION_JSON)

	@Consumes(MediaType.APPLICATION_JSON)

	public Response update(InterviewDetails interviewDetails) {

		InterviewDetails i = interviewDetailsDao.find(interviewDetails.getId());
		if (interviewDetails.getStatus().equalsIgnoreCase("selected")) {
			if (i.getInterviewName() != interviewDetails.getInterviewName()) {
				interviewDetails.setId(null);
			}

			InterviewDetails save = interviewDetailsDao.save(interviewDetails);
			return new Response(ExceptionMessage.StatusSuccess, save);
		} else {
			return new Response(ExceptionMessage.Exception, "unable to re-schedule this inteview");
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/emailCalender")
	public Response send() throws Exception {

		Response response = new Response();

		String sql = "select i.interviewTime,i.interviewLocation,i.address,i.nameOfRound,c.email as candidateEmail,u.email as recuriterEamil  from interviewdetails i\r\n"
				+ "join candidate c on c.id = i.candidate_id\r\n"
				+ "join candidatemapping cm on i.userId = cm.mappedUser_id\r\n"
				+ "join user u on u.id = cm.mappedUser_id";

		try {

			String from = "rpo.info@ojas-it.com";
			String to = "mahichowdary540@gmail.com";
			Properties prop = new Properties();

			prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.starttls.enable", "true");
			prop.put("mail.smtp.host", "outlook.office365.com");
			prop.put("mail.smtp.port", "587");

			Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("rpo.info@ojas-it.com", "Ojas1525");
				}
			});
			// Define message
			MimeMessage message = new MimeMessage(session);
			message.addHeaderLine("method=REQUEST");
			message.addHeaderLine("charset=UTF-8");
			message.addHeaderLine("component=VEVENT");

			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Outlook Meeting Request Using JavaMail");

			StringBuffer sb = new StringBuffer();

			StringBuffer buffer = sb.append("BEGIN:VCALENDAR\n"
					+ "PRODID:-//Microsoft Corporation//Outlook 9.0 MIMEDIR//EN\n" + "VERSION:2.0\n"
					+ "METHOD:REQUEST\n" + "BEGIN:VEVENT\n"
					+ "ATTENDEE;ROLE=REQ-PARTICIPANT;RSVP=TRUE:MAILTO:xx@xx.com\n" + "ORGANIZER:MAILTO:@xx.com\n"
					+ "DTSTART:20051208T053000Z\n" + "DTEND:20051208T060000Z\n" + "LOCATION:Conference room\n"
					+ "TRANSP:OPAQUE\n" + "SEQUENCE:0\n"
					+ "UID:040000008200E00074C5B7101A82E00800000000002FF466CE3AC5010000000000000000100\n"
					+ " 000004377FE5C37984842BF9440448399EB02\n" + "DTSTAMP:20051206T120102Z\n" + "CATEGORIES:Meeting\n"
					+ "DESCRIPTION:This the description of the meeting.\n\n" + "SUMMARY:Test meeting request\n"
					+ "PRIORITY:5\n" + "CLASS:PUBLIC\n" + "BEGIN:VALARM\n" + "TRIGGER:PT1440M\n" + "ACTION:DISPLAY\n"
					+ "DESCRIPTION:Reminder\n" + "END:VALARM\n" + "END:VEVENT\n" + "END:VCALENDAR");

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
			messageBodyPart.setHeader("Content-ID", "calendar_message");
			messageBodyPart
					.setDataHandler(new DataHandler(new ByteArrayDataSource(buffer.toString(), "text/calendar")));// very
																													// important

			// Create a Multipart
			Multipart multipart = new MimeMultipart();

			// Add part one
			multipart.addBodyPart(messageBodyPart);

			// Put parts in message
			message.setContent(multipart);

			// send message
			Transport.send(message);
			response.setErrorCode("200");
			response.setRes("Mail Calender Event Sent Successfully...");
			response.setResult(new ArrayList());
		} catch (MessagingException me) {
			me.printStackTrace();

			response.setErrorCode("409");
			response.setRes("Uanle To Send Mail Calender Event");
			response.setResult(new ArrayList());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return response;
	}

	@ResponseBody

	@GET

	@Path("findById/{id}")

	@Produces(MediaType.APPLICATION_JSON)

	@Consumes(MediaType.APPLICATION_JSON)
	public Response getInterviewDetailsById(@PathParam("id") Long id) {
		Response response = new Response();

		InterviewDetails interviewDetails = interviewDetailsDao.find(id);

		if (interviewDetails != null) {
			response.setConflict(HttpStatus.ACCEPTED);
			response.setResult(interviewDetails);
			response.setStatus(ExceptionMessage.StatusSuccess);
		} else {
			response.setConflict(HttpStatus.CONFLICT);
			response.setStatus(ExceptionMessage.DataIsEmpty);
		}
		return response;

	}

}
