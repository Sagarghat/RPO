
package com.ojas.rpo.security.rest.resources;

import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.ojas.rpo.security.dao.candidate.CandidatelistDao;
import com.ojas.rpo.security.dao.candidateJobTitle.CandidateJobTitleDaoInf;
import com.ojas.rpo.security.dao.user.UserDao;
import com.ojas.rpo.security.entity.Candidate;
import com.ojas.rpo.security.entity.CandidateJobTitle;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.ResponseWrapper;
import com.ojas.rpo.security.entity.User;
import com.ojas.rpo.security.util.CandidateSearch;
import com.ojas.rpo.security.util.EmailEntity;
import com.ojas.rpo.security.util.MessageEntity;
import com.ojas.rpo.security.util.OutlookMailSender;
import com.ojas.rpo.security.util.ReadPropertiesFile;
import com.ojas.rpo.security.util.WhatsAppMessageSender;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Component
@Path("/addcandidate")
public class CandidateResource {

	@Value("${resumeUploadPath}")
	private String resumeUpload;
	@Autowired
	private CandidatelistDao candidateDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private OutlookMailSender mailSender;

	@Autowired
	private CandidateJobTitleDaoInf candidateJobTitleDaoInf;

	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(Candidate candidate, @Context HttpServletRequest request) {

		if (candidate == null) {
			Response response = new Response(HttpStatus.CONFLICT, ExceptionMessage.DataIsEmpty);
			response.setRes("Invalid Request ");
			return response;  
		}
		if (null == candidate.getCandidateJobTitle().getId()) {
			return new Response(HttpStatus.CONFLICT, "missing candidate applied position");
		}
		CandidateJobTitle find2 = candidateJobTitleDaoInf.find(candidate.getCandidateJobTitle().getId());
		System.out.println(find2);
		if (find2.getCurrentJobTittle().equalsIgnoreCase("fresher")
				&& (candidate.getRegistrationId() == null || candidate.getFirstName() == null
						|| !candidate.getFirstName().trim().matches("([a-zA-Z]+)") || candidate.getLastName() == null
						|| !candidate.getLastName().trim().matches("([a-zA-Z]+)") || candidate.getEmail() == null
						|| !candidate.getEmail().trim().matches("(^[A-Za-z0-9+_.-]+@(.+)$)")
						|| candidate.getMobile() == null || !candidate.getMobile().matches("((0/91)?[5-9][0-9]{9})")
						|| candidate.getPrimarySkills() == null || candidate.getPrimarySkills().isEmpty()
						|| candidate.getAddQualificationHeld() == null || candidate.getSource() == null
						|| candidate.getUser() == null || candidate.getCandidateStatusMaster() == null)
				&& (candidate.getNoticePeriod() == null && candidate.getNoticePeriod().trim().matches("(\\d{0,3})")
						&& candidate.getTypeOfNotice() == null && candidate.getTypeOfNotice().trim().isEmpty()
						&& candidate.getCurrentCompanyName() == null
						&& candidate.getCurrentCompanyName().trim().matches("([a-zA-Z0-9]+)")
						&& candidate.getCurrentCTC() == null
						&& candidate.getCurrentCTC().trim()
								.matches("(\\d{1,6}[(?:\\,\\d{0,2})?$]+[(?:\\.\\d{0,2})?$]+)")
						&& candidate.getExpectedCTC() == null && candidate.getExpectedCTC().trim()
								.matches("(\\d{1,6}[(?:\\,\\d{0,2})?$]+[(?:\\.\\d{0,2})?$]+)"))) {
			return new Response(HttpStatus.CONFLICT, "Check Mandatory Fields Fresher");

		} else if (!find2.getCurrentJobTittle().equalsIgnoreCase("fresher") && (candidate.getRegistrationId() == null
				|| candidate.getFirstName() == null || !candidate.getFirstName().trim().matches("([a-zA-Z]+)")
				|| candidate.getLastName() == null || !candidate.getLastName().trim().matches("([a-zA-Z]+)")
				|| candidate.getEmail() == null || !candidate.getEmail().trim().matches("(^[A-Za-z0-9+_.-]+@(.+)$)")
				|| candidate.getMobile() == null || !candidate.getMobile().matches("((0/91)?[5-9][0-9]{9})")
				|| candidate.getPrimarySkills() == null || candidate.getPrimarySkills().isEmpty()
				|| candidate.getAddQualificationHeld() == null || candidate.getSource() == null
				|| candidate.getUser() == null || candidate.getCandidateStatusMaster() == null
				|| candidate.getNoticePeriod() == null || !candidate.getNoticePeriod().trim().matches("(\\d+)")
				|| candidate.getTypeOfNotice() == null || candidate.getTypeOfNotice().trim().isEmpty()
				|| candidate.getCurrentCompanyName() == null || candidate.getCurrentCompanyName().trim().isEmpty()
				|| candidate.getCurrentCTC() == null || candidate.getCurrentCTC().trim().isEmpty()
				|| candidate.getExpectedCTC() == null || candidate.getExpectedCTC().trim().isEmpty())) {
			return new Response(HttpStatus.CONFLICT, "Check Mandatory Fields of Experienced");
		}
		List<String> ids = candidateDao.chekduplicate(candidate.getMobile(), candidate.getEmail(),
				candidate.getRegistrationId());

		Candidate find = null;

		if (candidate.getId() != null) {

			find = candidateDao.find(candidate.getId());
			Iterator<String> iterator = ids.iterator();

			while (iterator.hasNext()) {
				if (iterator.next().equalsIgnoreCase(find.getEmail())) {
					iterator.remove();
				}
			}
		}
		if (candidate.getId() == null && !ids.isEmpty()) {

			return new Response(ExceptionMessage.DuplicateRecord);

		} else if ((candidate.getId() != null && null != ids && !ids.isEmpty()) && ids.contains(candidate.getEmail())) {
			return new Response(ExceptionMessage.DuplicateRecord);

		} else {
			try {
				if (candidate.getId() == null) {
					/**
					 * user save
					 */
					User user = null;
					try {
						String password = null;

						user = new User();
						Random random = new Random();
						int val = random.nextInt();
						password = new String();
						password = Integer.toHexString(val);

						user.setPassword(password);
						user.setName(candidate.getEmail());
						user.setRegistrationId(candidate.getRegistrationId());
						user.setFirstName(candidate.getFirstName());
						user.setLastName(candidate.getLastName());
						user.setEmail(candidate.getEmail());
						user.setContactNumber(candidate.getMobile());
						user.setDate(new Date());
						user = userDao.save(user);

					} catch (Exception e) {
						return new Response(ExceptionMessage.Bad_Request,
								"candidate details failed to save in user database");
					}

					EmailEntity emailEntity = ReadPropertiesFile.readConfig();
					emailEntity.setMessagesubject("Registration Successful Done");
					emailEntity.setTo(candidate.getEmail());
					user.setDomin("OJAS");
					String mes = "<i>Welcome</i><B>" + "  " + candidate.getFirstName() + " " + candidate.getLastName()
							+ ",</B><br><br>" + "You can use the following credentials to access the <B>" + "RPO"
							+ "</B> portal<br>" + "<a href=http://192.168.1.137/#/reg/activate/" + user.getId()
							// + "<a href=" + getAppUrl(request) + "/rest/companyreg/activate/" +
							// result.getRegistrationId()
							+ ">Click Here to Activate</a><br><br>" + "Your username is :" + user.getEmail() + "<br>"
							+ "Your password is : " + user.getPassword() + " <br> \" Company Domain is :"
							+ user.getDomin() + " <br>" + "<b>Thanks & Regards</b><br>" + "<img src= \"cid:image\" alt="
							+ "Logo" + " width=" + "160" + " " + "height=" + " " + "80>";
					System.out.println("mail mes :: " + mes);
					emailEntity.setLogoImagePath(getAppUrl(request) + "/images/ojas-logo.png");
					emailEntity.setMessageBody(mes);
					mailSender.sendMail(emailEntity);
					MessageEntity msgEntity = ReadPropertiesFile.readMessageConfig();
					msgEntity.setMsgText("Dear " + candidate.getFirstName() + " your registration has been done. ");
					WhatsAppMessageSender.sendMessage(msgEntity);

					candidate.setUser(candidate.getUser());
					candidate.setModifiedOwner(candidate.getUser());
				} else if (candidate.getId() != null && find.getUser() != null) {
					candidate.setUser(find.getUser());
					candidate.setModifiedOwner(candidate.getUser());
					candidate.setRating(find.getRating());
				}
				candidate.setLastUpdatedDate(new Date());
				candidate.setFileExtension(FilenameUtils.getExtension(candidate.getFileExtension()));
				candidate.setFileName(FilenameUtils.removeExtension(candidate.getFileName()));
				FilenameUtils.getExtension(candidate.getFileExtension());

				/**
				 * candidateSave
				 */
				Candidate candidateSave = this.candidateDao.save(candidate);
				return new Response(ExceptionMessage.StatusSuccess, candidateSave);
			} catch (Exception e) {
				return new Response(ExceptionMessage.DuplicateRecord, e.getLocalizedMessage());
			}
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public @ResponseBody Response read(@PathParam("id") Long id) {
		Candidate find = null;

		if (id != null) {
			find = candidateDao.find(id);
		}

		if (find == null || id == null) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {
			long currentTimeMillis = System.currentTimeMillis() - find.getLastUpdatedDate().getTime();
			Long days = TimeUnit.MILLISECONDS.toDays(currentTimeMillis + 0L);
			find.setSetDiffDays(days);

			return new Response(ExceptionMessage.StatusSuccess, find);
		}
	}

	@POST
	@Path("/search")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response findDataBasedOnRegId(CandidateSearch candidateSearch) {

		if (candidateSearch.getPageNum() == null || candidateSearch.getPageSize() == null
				|| candidateSearch.getRegistrationId() == null)
			return new Response(ExceptionMessage.Bad_Request, "page size or page number  or registationId missing");
		else
			return candidateDao.findAllCandidateBasedOnRegId(candidateSearch);     
	}

	@POST
	@Path("/uploadresume")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public @ResponseBody Response resumeUpload(@FormDataParam("file") InputStream file,
			@FormDataParam("file") FormDataContentDisposition fileDetails) {

		String url = "http://localhost:8081/upload";

		if (resumeUpload == null || resumeUpload.trim().length() == 0) {
			return new Response(ExceptionMessage.Bad_Request, "User Directory not found");
		}

		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool(taskExecutor());
		Future<ResponseEntity<ResponseWrapper>> submit = newCachedThreadPool.submit(() -> {

			byte[] bytes = IOUtils.toByteArray(file);

			java.nio.file.Path path = Paths.get(resumeUpload + fileDetails.getFileName());

			if (!path.getParent().toFile().exists())
				Files.createDirectories(path.getParent());

			path = Files.write(path, bytes);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA);

			LinkedMultiValueMap<String, Object> linkedMultiValueMap = new LinkedMultiValueMap<String, Object>();
			linkedMultiValueMap.add("resume", new FileSystemResource(path.toFile()));

			HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
					linkedMultiValueMap, headers);

			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<ResponseWrapper> exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity,
					ResponseWrapper.class);

			Files.deleteIfExists(path);

			return exchange;

		});

		ResponseWrapper responseEntity = null;

		try {

			responseEntity = submit.get().getBody();

		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return new Response(ExceptionMessage.Exception, e.getLocalizedMessage());

		} catch (ExecutionException e) {

			return new Response(ExceptionMessage.Exception, e.getLocalizedMessage());
		}

		return new Response(ExceptionMessage.Accepted, responseEntity);
	}

	@Bean
	private ThreadPoolTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setBeanName("resumeParser");
		threadPoolTaskExecutor.setCorePoolSize(10);
		threadPoolTaskExecutor.setMaxPoolSize(15);
		threadPoolTaskExecutor.setQueueCapacity(Integer.MAX_VALUE);
		// threadPoolTaskExecutor.newThread(new)
		threadPoolTaskExecutor.initialize();
		return threadPoolTaskExecutor;
	}

	private String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

}