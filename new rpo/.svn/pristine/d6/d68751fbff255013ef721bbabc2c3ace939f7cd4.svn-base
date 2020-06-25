package com.ojas.rpo.security.dao.sendemail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MimeType;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.EmailEntity;
import com.ojas.rpo.security.util.ReadPropertiesFile;
import com.ojas.rpo.security.util.ValidateEmail;

@Repository
public class MailSenderImpl implements MailSender {
	private static SimpleDateFormat iCalendarDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmm'00'");
	@Value("${excelFile}")
	private String excelFilePath;
	MimeMessage mimeMessage = null;
	EmailEntity entity1 = new EmailEntity();

	public MailSenderImpl() {
		super();
		Properties props = new Properties();
		entity1 = ReadPropertiesFile.readConfig();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "outlook.office365.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(entity1.getFrom(), entity1.getPassword());
			}

		});
		mimeMessage = new MimeMessage(session);
		try {
			mimeMessage.setFrom(new InternetAddress(entity1.getFrom()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Response sendMail(EmailEntity emailEntity) throws Exception {
		File file = null;
		BodyPart calendarPart = new MimeBodyPart();
		StringBuilder pro = new StringBuilder(excelFilePath);
		Response emailValidationResponse = ValidateEmail.validateEmail(emailEntity);
		if (emailValidationResponse == null) {
			if (emailEntity.getTo() != null && emailEntity.getMessageBody() != null) {
				final String emailBody = emailEntity.getMessageBody();
				Multipart multipart = new MimeMultipart();
				// for cc
				if (emailEntity.getCc() != null && !emailEntity.getCc().isEmpty()) {
					if (emailEntity.getCc().indexOf(',') > 0) {
						mimeMessage.setRecipients(Message.RecipientType.CC, InternetAddress.parse(emailEntity.getCc()));
					} else {
						mimeMessage.setRecipient(Message.RecipientType.CC, new InternetAddress(emailEntity.getCc()));
					}
				}
				// for Bcc
				if (emailEntity.getBcc() != null && !emailEntity.getBcc().isEmpty()) {
					if (emailEntity.getBcc().indexOf(',') > 0) {
						mimeMessage.setRecipients(Message.RecipientType.BCC,
								InternetAddress.parse(emailEntity.getBcc()));
					} else {
						mimeMessage.setRecipient(Message.RecipientType.BCC, new InternetAddress(emailEntity.getBcc()));
					}
				}
				// for sender
				if (emailEntity.getTo() != null && !emailEntity.getTo().isEmpty()) {
					if (emailEntity.getTo().indexOf(',') > 0) {
						mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailEntity.getTo()));
					} else {
						mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailEntity.getTo()));
					}
				}
				// Adding Attachement to the Mail
				MimeBodyPart attachmentPart = new MimeBodyPart();
				if (emailEntity.getFileAttachement() != null && !emailEntity.getFileAttachement().isEmpty()
						&& emailEntity.getFileType() != null) {
					pro.append("candidateResume");
					int index = emailEntity.getFileType().lastIndexOf(".");
					String extension = emailEntity.getFileType().substring(index, emailEntity.getFileType().length());
					file = new File(pro + extension);
					try (FileOutputStream fos = new FileOutputStream(file);) {
						byte[] decoder = Base64.getDecoder().decode(emailEntity.getFileAttachement());
						fos.write(decoder);
					} catch (Exception e) {
						e.printStackTrace();
					}
					attachmentPart.setDataHandler(new DataHandler(new FileDataSource(file)));
					attachmentPart.setFileName("candidateResume." + emailEntity.getFileType());
					multipart.addBodyPart(attachmentPart);

				}
				if (emailEntity.getStartDate() != null && emailEntity.getEndDate() != null) {
					if (emailEntity.getLocation() == null)
						return new Response(ExceptionMessage.DataIsEmpty, "Location must required");
					if (emailEntity.getRemainder() == 0)
						return new Response(ExceptionMessage.DataIsEmpty, "Remainder must required");
					StringBuilder buffer = new StringBuilder("BEGIN:VCALENDAR\n" + "PRODID: BCP - Meeting\n"
							+ "VERSION:2.0\n" + "METHOD:REQUEST\n" + "BEGIN:VEVENT\n" + "DTSTART:"
							+ iCalendarDateFormat.format(emailEntity.getStartDate()) + "\n" + "DTEND:"
							+ iCalendarDateFormat.format(emailEntity.getEndDate()) + "\n" + "ORGANIZER:MAILTO:"
							+ emailEntity.getFrom() + "\n" + "UID:" + emailEntity.getTo() + "\n" + "LOCATION:"
							+ emailEntity.getLocation() + "\n" + "TRANSP:OPAQUE\n" + "SEQUENCE:0\n" + "DTSTAMP:"
							+ iCalendarDateFormat.format(new Date()) + "\n" + "CATEGORIES:Meeting\n"
							+ "DESCRIPTION:This the description of the meeting.\n\n" + "SUMMARY:"
							+ emailEntity.getMessagesubject() + "\n" + "PRIORITY:5\n" + "CLASS:PUBLIC\n"
							+ "BEGIN:VALARM\n" + "TRIGGER;RELATED=START:PT" + emailEntity.getRemainder() + "M\n"
							+ "REPEAT:2\n" + "DURATION:PT15M\n" + "TRIGGER:-PT30M\n" + "ACTION:DISPLAY\n"
							+ "DESCRIPTION:Reminder\n" + "END:VALARM\n" + "END:VEVENT\n" + "END:VCALENDAR");
					calendarPart.setHeader("Content-Class", "urn:content-  classes:calendarmessage");
					calendarPart.setHeader("Content-ID", "calendar_message");
					calendarPart.setDataHandler(
							new DataHandler(new ByteArrayDataSource(buffer.toString(), "text/calendar")));
					multipart.addBodyPart(calendarPart);
				}
				MimeBodyPart textPart = new MimeBodyPart();
				textPart.setContent(emailBody, "text/html");

				multipart.addBodyPart(textPart);
				mimeMessage.setContent(multipart);
				if (emailEntity.getMessagesubject() != null)
					mimeMessage.setSubject(emailEntity.getMessagesubject());
				Transport.send(mimeMessage);
				return new Response(ExceptionMessage.StatusSuccess, "mail Sended to " + emailEntity.getTo());
			}
			return new Response(ExceptionMessage.DataIsEmpty, "details must require");
		}
		return emailValidationResponse;

	}

}
