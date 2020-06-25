package com.ojas.rpo.security.rest.resources;

import java.io.IOException;

import java.util.List;
import java.util.Random;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.user.UserDao;
import com.ojas.rpo.security.entity.CompanyReg;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;
import com.ojas.rpo.security.entity.User;
import com.ojas.rpo.security.registration.RegistrationDAO;
import com.ojas.rpo.security.util.EmailEntity;
import com.ojas.rpo.security.util.OutlookMailSender;
import com.ojas.rpo.security.util.ReadPropertiesFile;

@Component
@Path("/companyreg")
public class CompanyRegistraionResource {
	@Autowired
	private RegistrationDAO registrationDAO;

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	OutlookMailSender mailSender;

	@Path("/registration")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registration(CompanyReg reg, @Context HttpServletRequest request) {

		if (registrationDAO.findByEmailId(reg.getEmailId(), reg.getCompanyName()) > 0) {
			return new Response(ExceptionMessage.EmailExist);
		}
		CompanyReg result = null;
		User user = null;
		String password = null;
		try {

			Random random = new Random();
			int val = random.nextInt();
			password = new String();
			password = Integer.toHexString(val);
			reg.setDomain(reg.getCompanyName());
			// reg.getUser().setPassword(this.passwordEncoder.encode(password));

			result = registrationDAO.save(reg);

			user = new User();
			user.setFirstName(reg.getCompanyName());
			user.setLastName(reg.getCompanyName());
			user.setEmail(reg.getEmailId());
			user.setPassword(this.passwordEncoder.encode(password));
			user.setName(reg.getEmailId());
			user.setDomin(reg.getCompanyName());
			user.setRole("SUPERADMIN");
			//user.setStatus("Active");// need to comment after activation logic completed
			user.addRole(Role.SUPERADMIN);
			user.setRegistrationId(result.getId());
			user = userDao.save(user);
			if (user == null) {
				return new Response(ExceptionMessage.DataIsNotSaved);
			}

		} catch (PersistenceException pe) {
			pe.printStackTrace();
			return new Response(ExceptionMessage.Exception, "500",
					"Unable to Register User. Enter Unique name or email or mobile.");
		} catch (Exception e) {
			e.printStackTrace();
			return new Response(ExceptionMessage.Exception, "500",
					"Unable to Register User due to follwing Error : " + e.getMessage());
		}

		EmailEntity emailEntity = ReadPropertiesFile.readConfig();// to
																	// read
																	// config
																	// file
		emailEntity.setMessagesubject("Registration Successful Done");// add
																		// subject
		emailEntity.setTo(reg.getEmailId());

		String mes = "<i>Welcome</i><B>" + "  " + reg.getCompanyName() + ",</B><br><br>"
				+ "You can use the following credentials to access the <B>" + "RPO" + "</B> portal<br>"
				+ "<a href=http://192.168.1.137/#/reg/activate/" + user.getId()
				// + "<a href=" + getAppUrl(request) + "/rest/companyreg/activate/" +
				// result.getRegistrationId()
				+ ">Click Here to Activate</a><br><br>" + "Your username is :" + reg.getEmailId() + "<br>"
				+ "Your password is : " + password + " <br> \" Company Domain is :" + reg.getCompanyName() + " <br>"
				+ "<b>Thanks & Regards</b><br>" + "<img src= \"cid:image\" alt=" + "Logo" + " width=" + "160" + " "
				+ "height=" + " " + "80>";
		System.out.println("mail mes :: " + mes);
		emailEntity.setLogoImagePath(getAppUrl(request) + "/images/ojas-logo.png");
		emailEntity.setMessageBody(mes);
		mailSender.sendMail(emailEntity);

		return new Response(ExceptionMessage.StatusSuccess, reg);
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response getCompanyDetails(@PathParam("id") Long id) throws IOException {

		List<CompanyReg> companyDetails = this.registrationDAO.findByRegId(id);

		if (companyDetails == null) {
			return new Response(ExceptionMessage.DataIsEmpty, companyDetails);
		}

		return new Response(ExceptionMessage.StatusSuccess, companyDetails);

	}

	@POST
	@Path("/activate/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response activateUser(@PathParam("id") Long registrationId) {
		Response response = this.registrationDAO.updateStatusById(registrationId);
		if (response == null) {
			return new Response(ExceptionMessage.DataIsNotSaved);
		}
		return new Response(ExceptionMessage.StatusSuccess, response);
	}

	private String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

}
