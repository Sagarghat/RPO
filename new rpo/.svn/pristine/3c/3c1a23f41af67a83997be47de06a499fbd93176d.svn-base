package com.ojas.rpo.security.rest.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.JsonViews;
import com.ojas.rpo.security.dao.feature.FeatureDao;
import com.ojas.rpo.security.dao.featuremapperentity.FeatureMapperEntityDao;
import com.ojas.rpo.security.dao.user.UserDao;
import com.ojas.rpo.security.entity.AddContact;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Feature;
import com.ojas.rpo.security.entity.FeatureMapper;
import com.ojas.rpo.security.entity.FeatureMapperEntity;
import com.ojas.rpo.security.entity.Permissions;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;
import com.ojas.rpo.security.entity.User;
import com.ojas.rpo.security.entity.UserSearch;
import com.ojas.rpo.security.util.EmailEntity;
import com.ojas.rpo.security.util.OutlookMailSender;
import com.ojas.rpo.security.util.ReadPropertiesFile;
import com.ojas.rpo.security.util.UserFeatureMapper;

@Component
@Path("/Reg")
public class UserRegistrationResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserDao userDao;

	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	OutlookMailSender mailSender;
	@Autowired
	private FeatureMapperEntityDao feaDao;
	@Autowired
	private FeatureDao featureDao;

	@POST
//	@SuppressWarnings("finally")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/adduser")
	public @ResponseBody Response create(UserFeatureMapper user, @Context HttpServletRequest request) {
		User u = null;
		Response response = null;
		try {
			if (user.getUser().getEmail() == null && user.getUser().getEmail().isEmpty())
				return new Response(ExceptionMessage.DataIsEmpty, "User email Must require");
			if (user.getUser().getContactNumber() == null && user.getUser().getContactNumber().isEmpty())
				return new Response(ExceptionMessage.DataIsEmpty, "User contact Number require");
			if (user.getUser().getFirstName() == null && user.getUser().getFirstName().isEmpty())
				return new Response(ExceptionMessage.DataIsEmpty, "User First Name Must require");
			if (user.getUser().getLastName() == null && user.getUser().getLastName().isEmpty())
				return new Response(ExceptionMessage.DataIsEmpty, "User Last  Name Must require");
			if (user.getUser().getReportingId() == null)
				return new Response(ExceptionMessage.DataIsEmpty, "Reporting Manager  Must require");
			if (user.getUser().getRegistrationId() == null)
				return new Response(ExceptionMessage.DataIsEmpty, "RegistrationId  Must require");

			if (user.getUser().getRole().equals("user")) {
				user.getUser().addRole(Role.USER);
			} else if (user.getUser().getRole().equals("admin")) {
				user.getUser().addRole(Role.ADMIN);
			} else if (user.getUser().getRole().equals("bdm")) {
				user.getUser().addRole(Role.BDM);
			} else if (user.getUser().getRole().equals("recruiter")) {
				user.getUser().addRole(Role.RECRUITER);
			} else if (user.getUser().getRole().equals("AM")) {
				user.getUser().addRole(Role.AM);
			} else if (user.getUser().getRole().equals("Lead")) {
				user.getUser().addRole(Role.TEAMLEAD);
			} else if (user.getUser().getRole().equals("Lead")) {
				user.getUser().addRole(Role.TEAMLEAD);
			} else if (user.getUser().getRole().equals("FinanceLead")) {
				user.getUser().addRole(Role.FINANCELEAD);
			} else if (user.getUser().getRole().equals("FinanceExecutive")) {
				user.getUser().addRole(Role.FINANCEEXECUTIVE);
			} else if (user.getUser().getRole().equals("Management")) {
				user.getUser().addRole(Role.MANAGEMENT);
			} else if (user.getUser().getRole().equals("MIS")) {
				user.getUser().addRole(Role.MIS);
			}

			String passString = getPassWord();
			user.getUser().setName(user.getUser().getEmail());
			user.getUser().setPassword(passwordEncoder.encode(passString));
			user.getUser().setDate(new Date());
			if (userDao.userCount(user.getUser().getRegistrationId()) != 0) {
				if (user.getFeatureMapper() == null || user.getFeatureMapper().isEmpty()) {
					u = this.userDao.save(user.getUser());
				} else {

					List<Feature> features = new ArrayList<Feature>();
					for (FeatureMapper m : user.getFeatureMapper()) {
						features.add(featureDao.find(m.getFeatureId()));

					}
					user.getUser().setFeature(features);
					u = this.userDao.save(user.getUser());

					for (FeatureMapper f : user.getFeatureMapper()) {

						f.setUserId(u.getId());

						for (Permissions permissions : f.getPermissionId()) {
							if (permissions.isFlag()) {
								FeatureMapperEntity e = new FeatureMapperEntity();
								e.setUser(u);
								e.setFeature(featureDao.find(f.getFeatureId()));
								e.setPermissions(permissions);
								FeatureMapperEntity save = feaDao.save(e);
							}
						}

					}

				}
			} else {
				return new Response(ExceptionMessage.DataLimitExceeded, user);
			}

			if (u == null) {
				response = new Response(ExceptionMessage.DataIsNotSaved);

			} else {
				sendMail(user.getUser(), request, passString);

				response = new Response(ExceptionMessage.StatusSuccess, user);

			}
		} catch (PersistenceException pe) {
			response = new Response(ExceptionMessage.Exception, "500",
					"Unable to Register User. Enter Unique name or email or mobile.");
		} catch (Exception e) {
			response = new Response(ExceptionMessage.Exception, "500",
					"Unable to Register User due to follwing Error : " + e.getMessage());
		}
		return response;

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response read(@PathParam("id") Long id) {
		this.logger.info("read(id)");

		User user = this.userDao.find(id);

		if (user == null) {

			return new Response(ExceptionMessage.UnRegistrationUser);
		} else
			return new Response(ExceptionMessage.StatusSuccess, user);
	}

	@GET
	@Path("/assign/{role}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> list1(@PathParam("role") String role) throws IOException {

		this.logger.info("list()");

		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}

		return this.userDao.findByRole(role);
	}

	@GET
	@Path("/listBDMandLead")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> listBDMandLead(@PathParam("regId") Long regId) throws IOException {

		this.logger.info("list()");

		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}

		return this.userDao.findByOnlyBdmleadRole(regId);
	}

	@GET
	@Path("/listAM/{regId}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> listAm(@PathParam("regId") Long regId) throws IOException {

		this.logger.info("list()");

		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}

		return this.userDao.findByOnlyAMRole(regId);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response list() throws IOException {
		this.logger.info("list()");

		ObjectWriter viewWriter;
		if (this.isAdmin()) {
			viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		} else {
			viewWriter = this.mapper.writerWithView(JsonViews.User.class);
		}
		// System.out.println(this.userDao.findAll());
		List<User> user = this.userDao.findAll();
		if (user == null) {
			return new Response(ExceptionMessage.DataIsEmpty);
		}

		return new Response(ExceptionMessage.StatusSuccess, user);
	}

	/*
	 * @GET
	 * 
	 * @Path("/getUserByReportingId/{id}")
	 * 
	 * @Produces(MediaType.APPLICATION_JSON) public @ResponseBody Response
	 * listByReporting(@PathParam("id") Long id) throws IOException {
	 * this.logger.info("list()");
	 * 
	 * ObjectWriter viewWriter; if (this.isAdmin()) { viewWriter =
	 * this.mapper.writerWithView(JsonViews.Admin.class); } else { viewWriter =
	 * this.mapper.writerWithView(JsonViews.User.class); } //
	 * System.out.println(this.userDao.findAll()); List<User> user =
	 * this.userDao.findByReportingId(id); if (user == null) { return new
	 * Response(ExceptionMessage.DataIsEmpty); } Date date = new Date();
	 * ListIterator litr = user.listIterator(); SimpleDateFormat formatter = new
	 * SimpleDateFormat("dd MMMM yyyy");
	 * 
	 * while (litr.hasNext()) { User user1 = (User) litr.next();
	 * user1.setDob1(formatter.format(user1.getDob() != null ? user1.getDob() :
	 * date)); user1.setDoj1(formatter.format(user1.getDoj() != null ?
	 * user1.getDoj() : date)); }
	 * 
	 * return new Response(ExceptionMessage.StatusSuccess, user); }
	 */

	private String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	/*
	 * @POST
	 * 
	 * @Produces(MediaType.APPLICATION_JSON)
	 * 
	 * @Consumes(MediaType.APPLICATION_JSON)
	 * 
	 * @Path("{id}") public @ResponseBody Response update(@PathParam("id") Long id,
	 * User list) { System.out.println("==========inside post method====RPO");
	 * list.setId(id); this.logger.info("update(): " + list);
	 * 
	 * if (this.userDao.save(list) == null) { return new
	 * Response(ExceptionMessage.DataIsNotSaved); } else return new
	 * Response(ExceptionMessage.StatusSuccess, list); }
	 */
//
//	@POST
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Path("/{id}")
//	public @ResponseBody Response update(@PathParam("id") Long id, User userList) {
//		System.out.println("==========inside post method====RPO");
//		userList.setId(id);
//		this.logger.info("update(): " + userList);
//
//		if (this.userDao.updateUserById(id, userList) == null) {
//			return new Response(ExceptionMessage.DataIsNotSaved);
//		} else
//			return new Response(ExceptionMessage.StatusSuccess, userList);
//	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/updateRegId/{id}/{regId}")
	public @ResponseBody Response updateRegId(@PathParam("id") Long id, @PathParam("regId") Long regId,
			User userUpdate) {

		Response response = null;

		userUpdate.setId(id);
		this.logger.info("update(): " + userUpdate);

		List<String> data = userDao.findActiveContactByEmail(userUpdate.getId(), userUpdate.getEmail(),
				userUpdate.getRegistrationId());
		if (userUpdate.getId() != null) {
			User find = userDao.find(userUpdate.getId());
			Iterator<String> iterator = data.iterator();

			while (iterator.hasNext()) {
				if (iterator.next().equalsIgnoreCase(find.getEmail())) {
					iterator.remove();
				}
			}
		}

		if (userUpdate.getId() != null && null != data && !data.isEmpty() && data.contains(userUpdate.getEmail())) {
			return new Response(ExceptionMessage.EmailExist);
		} else {
			try {
				userUpdate = this.userDao.save(userUpdate);
				response = new Response(ExceptionMessage.StatusSuccess, userUpdate);
			} catch (Exception e) {
				response = new Response(ExceptionMessage.EmailExist, "Duplicate");
			}

		}

		return response;

	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/changepassword/{id}")
	public @ResponseBody Response changePassword(@PathParam("id") Long id, User user1) {
		System.out.println("==========inside post method====RPO");

		User user = this.userDao.find(id);

		if (user != null) {
			user.setId(id);
			if (this.passwordEncoder.matches(user1.getPassword(), user.getPassword())) {
				user.setPassword(this.passwordEncoder.encode(user1.getNewPassword()));
				user.setNewPassword(null);
				this.userDao.save(user);
				return new Response(ExceptionMessage.StatusSuccess, user);
			} else {
				return new Response(ExceptionMessage.DataIsNotSaved);
			}

		} else {
			return new Response(ExceptionMessage.DataIsNotSaved);
		}

	}

	///////////////////////////

	@Path("/search")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response usersListByRegId1(UserSearch userSearch) {
		Response res = this.userDao.findAllUsers(userSearch);
		return res;

	}

	/////////////////////////

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public User active(@PathParam("id") Long id) {

		this.logger.info("delete(id)");
		User list = this.userDao.find(id);
		list.setStatus("inactive");
		return this.userDao.save(list);
	}

	private boolean isAdmin() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Object principal = authentication.getPrincipal();
		if ((principal instanceof String) && ((String) principal).equals("anonymousUser")) {
			return false;
		}
		UserDetails userDetails = (UserDetails) principal;

		return userDetails.getAuthorities().contains(Role.ADMIN);
	}

	@GET
	@Path("{id}/{role}")
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response getList(@PathParam("id") Long id, @PathParam("role") String role) throws IOException {

		List<User> user = this.userDao.findByRoleAndId(id, role);
		if (user == null) {
			return new Response(ExceptionMessage.DataIsEmpty, user);
		}
		return new Response(ExceptionMessage.StatusSuccess, user);

	}

	@Path("usersList/{pageNo}/{pageSize}/{regId}/{flag}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response usersList(@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize,
			@PathParam("regId") Long regId, @QueryParam("sortingOrder") String sortingOrder,
			@QueryParam("sortingField") String sortingField, @QueryParam("searchField") String searchField,
			@QueryParam("searchInput") String searchInput, @PathParam("flag") Boolean flag) {

		Response res = this.userDao.findAllUsers(pageNo, pageSize, regId, sortingOrder, sortingField, searchField,
				searchInput, flag);

		return res;

	}

	@Path("usersList/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response usersList(@PathParam("id") Long id) {
		System.out.println("sdfjsd");
		User find = userDao.find(id);
		if (null != find) {
			return new Response(ExceptionMessage.StatusSuccess, find);
		} else {
			return null;
		}
	}

	/**
	 * Newly Added method for Based on Regid
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param regId
	 * @return
	 */

	@Path("/usersListByRegId/{pageNo}/{pageSize}/{regId}/{flag}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response usersListByRegId(@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize,
			@PathParam("regId") Long regId, @PathParam("flag") Boolean flag) {
		Response res = this.userDao.findAllUsersByRegId(pageNo, pageSize, regId, flag);
		return res;

	}

	// usersListByRegIdRole

	@Path("/usersListByRegIdRole/{pageNo}/{pageSize}/{regId}/{role}/{flag}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response usersListByRegIdRole(@PathParam("pageNo") Integer pageNo, @PathParam("pageSize") Integer pageSize,
			@PathParam("regId") Long regId, @PathParam("role") String role, @PathParam("flag") boolean flag) {
		logger.info("role");
		Response res = this.userDao.usersListByRegIdRole(pageNo, pageSize, regId, role, flag);
		logger.info("role1");

		return res;

	}

	@Path("getReportingManagersList")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getReportingManagersList() {
		return this.userDao.getAllReportingManagers();

	}

	@Path("getUserNamesByRole")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUserNamesByRole(@QueryParam("role") String role, @QueryParam("regId") Long regId) {

		return this.userDao.getUserNamesByRole(role, regId);

	}

	@Path("getRecruitersByReportingId/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getRecruitersByReportingId(@PathParam("id") Long id) {
		return this.userDao.getRecruitersByReportingId(id);
	}

	public String getPassWord() {

		Random random = new Random();
		int val = random.nextInt();
		String password = new String();
		password = Integer.toHexString(val);

		return password;
	}

	public void sendMail(User user, HttpServletRequest request, String password) {

		try {

			EmailEntity emailEntity = ReadPropertiesFile.readConfig();// to

			emailEntity.setMessagesubject("Registration Successful Done");// add

			emailEntity.setTo(user.getEmail());

			String mes = "<i>Welcome</i><B>" + "  " + user.getFirstName() + ",</B><br><br>"
					+ "You can use the following credentials to access the <B>" + "RecruitEx" + "</B> portal<br>"
					+ "<a href=http://192.168.1.137/#/reg/activate/" + user.getId()
// + "<a href=" + getAppUrl(request) + "/rest/companyreg/activate/" +
// result.getRegistrationId()
					+ ">Click Here to Activate</a><br><br>" + "Your username is :" + user.getEmail() + "<br>"
					+ "Your password is : " + password + " <br> \" Company Domain is :" + user.getDomin() + " <br>"
					+ "<b>Thanks & Regards</b><br>" + "<img src= \"cid:image\" alt=" + "Logo" + " width=" + "160" + " "
					+ "height=" + " " + "80>";

			emailEntity.setLogoImagePath(getAppUrl(request) + "/images/ojas-logo.png");
			emailEntity.setMessageBody(mes);
			mailSender.sendMail(emailEntity);
		} catch (Exception e) {

		}
	}

}
