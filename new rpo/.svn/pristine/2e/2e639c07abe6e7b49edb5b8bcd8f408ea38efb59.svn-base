package com.ojas.rpo.security.rest.resources;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.JsonViews;
import com.ojas.rpo.security.dao.InterviewDetails.InterviewDetailsDao;
import com.ojas.rpo.security.dao.assignedrecruiters.AssingnedRecruitersDao;
import com.ojas.rpo.security.dao.bdmreqdtls.BdmReqDao;
import com.ojas.rpo.security.dao.candidate.CandidatelistDao;
import com.ojas.rpo.security.dao.candidateReqMapping.CandidateReqMappingDao;
import com.ojas.rpo.security.dao.user.UserDao;
import com.ojas.rpo.security.entity.BdmReq;
import com.ojas.rpo.security.entity.BdmReqSearch;
import com.ojas.rpo.security.entity.Candidate;
import com.ojas.rpo.security.entity.CandidateData;
import com.ojas.rpo.security.entity.CandidateMapping;
import com.ojas.rpo.security.entity.CandidateMappingRequest;
import com.ojas.rpo.security.entity.CandidateReqMappingMapper;
import com.ojas.rpo.security.entity.Client;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.InterviewDetails;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Role;
import com.ojas.rpo.security.entity.User;
import com.ojas.rpo.security.util.AssignedRecruitersMapper;
import com.ojas.rpo.security.util.CandidateStatusCounts;
import com.ojas.rpo.security.util.EmailEntity;
import com.ojas.rpo.security.util.OutlookMailSender;
import com.ojas.rpo.security.util.ReadPropertiesFile;

@Component
@Path("/candidatereqmapping")
public class CandidateReqMappingResource {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CandidateReqMappingDao candidateReqMappingDao;
	@Autowired
	private CandidatelistDao candidateDao;
	@Autowired
	private OutlookMailSender mailSender;	
	@Autowired
	private BdmReqDao bdmReqDao;
	@Autowired
	private AssingnedRecruitersDao assingnedRecruitersDao;

	
	

	@Value("${fileUploadPath}")
	private String documentsfolder;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public @ResponseBody Response create(CandidateReqMappingMapper mappingMapper, @Context HttpServletRequest request) {
		this.logger.info("save()");
		Response response = null;
		Date assignedDate = null;
		int diffDays = 0;
		if(mappingMapper.getCandidate().size() > 0 && mappingMapper.getBdmReq().size() > 0) {
			CandidateMapping candidateMapping = new CandidateMapping();
			
			//if(mappingMapper.getCandidate().size() > mappingMapper.getBdmReq().size()) {
				for(Candidate candidate:mappingMapper.getCandidate()) {
					for(BdmReq bdmReq:mappingMapper.getBdmReq()) {
						candidateMapping.setCandidate(candidate);
						candidateMapping.setBdmReq(bdmReq);
						candidateMapping.setMappedUser(mappingMapper.getMappedUser());
						candidateMapping.setRegId(mappingMapper.getRegId());
						int count = candidateReqMappingDao.checkDuplicateMapping(candidateMapping.getCandidate().getId(),
								candidateMapping.getBdmReq().getId(), candidateMapping.getMappedUser().getId(),candidateMapping.getRegId());
						
						if (count > 0) {
							response = new Response(ExceptionMessage.NameExist,"This Candidate already mapped to this requirement");
							
							return response;
						}

						if (candidateMapping != null) {
//							Candidate can = candidateDao.find(candidateMapping.getCandidate().getId());
//							candidateMapping.setCandidate(can);
							candidateMapping.setLastUpdatedDate(new Date());						
							candidateMapping.setCandidateStatus(candidateReqMappingDao.getCandidateStatusId("New"));							
							candidateMapping.setStageOfInterview(candidateReqMappingDao.getStageOfInterviewStatusId("Screening"));
							//candidateMapping.setMappedUser(candidateMapping.getMappedUser());
							AssignedRecruitersMapper assignedRecruitersMapper = new AssignedRecruitersMapper();
							assignedRecruitersMapper.setRegistrationId(candidateMapping.getRegId());
							assignedRecruitersMapper.setUserId(candidateMapping.getMappedUser().getId());
							assignedRecruitersMapper.setRequirementId(candidateMapping.getBdmReq().getId());
							
							assignedDate = assingnedRecruitersDao.getAssignedDate(assignedRecruitersMapper);
							Date date = new Date();
							if(assignedDate!=null) {
								long diff = date.getTime()-assignedDate.getTime();
								diffDays = (int) (diff / (24 * 60 * 60 * 1000));
							}						
							
							candidateMapping.setDiffOfAssignedMapperDays(diffDays);
							
							CandidateMapping result = candidateReqMappingDao.save(candidateMapping);
							if(result.getId() != null) {
								response = new Response(ExceptionMessage.OK,
										"Candidate Successfully Mapped to Requirement Id = " + result.getBdmReq().getId());
							}else {
								response = new Response(ExceptionMessage.DataIsNotSaved, "Candidate Mapping Not Changed");
							}
						}
						

						CandidateMapping cm2 = candidateReqMappingDao.findMappedCandidate(candidateMapping.getCandidate().getId(),
								candidateMapping.getBdmReq().getId(), candidateMapping.getMappedUser().getId(),candidateMapping.getRegId());

						// Mail Sending
						try {
							StringBuilder toMails = null;
							StringBuilder ccMails = null;
							StringBuilder ccMails1 = new StringBuilder();
							String clientname = null;
							if (null != cm2.getCandidate()) {
								if (null != cm2.getCandidate().getUser()) {
									toMails = new StringBuilder(cm2.getCandidate().getUser().getEmail());
								}
								if (null != cm2.getCandidate().getUser().getReportingId()) {
									toMails.append("," + cm2.getCandidate().getUser().getReportingId().getEmail());
								}
							}

							if (null != cm2.getBdmReq()) {
								Client client = cm2.getBdmReq().getClient();
								clientname = client.getClientName();
							}
							Candidate c = cm2.getCandidate();
							String candidateName = c.getFirstName() + " " + c.getLastName();
							EmailEntity emailEntity = ReadPropertiesFile.readConfig();// to read
																						// config
																						// file
							emailEntity.setMessagesubject(
									"Candidate Mapped to Requirement || Candidate Name : " + candidateName + " || Requirement : ");// add
																																	// subject
							emailEntity.setTo(toMails.toString());
							emailEntity.setCc(ccMails1.toString());
							emailEntity.setLogoImagePath(getAppUrl(request) + "/images/ojas-logo.png");

							User user = cm2.getMappedUser();
							String userName = null;
							if (null != user) {
								userName = user.getFirstName() + " " + user.getLastName();
							}
							String mes = "Dear all, <br> Please consider below candidate mapping details." + "<div>"
									+ "<table border = 1>"
									+ "<tr><th  colspan=\"2\"  style = \" background-color: #ffb84d; \">Candidate Mapping Details</th></tr>"
									+ "<tbody>" + "<tr><td><b>Candidate ID</b></td><td>" + c.getId() + "</td></tr>"
									+ "<tr><td><b>Candidate Name</b></td><td>" + candidateName + "</td></tr>"
									+ "<tr><td><b>Customer</b></td><td>" + clientname + "</td></tr>"
									+ "<tr><td><b>Requirement ID</b></td><td>" + cm2.getBdmReq().getId() + "</td></tr>"
									+ "<tr><td><b>Requirement Name</b></td><td>" + cm2.getBdmReq().getNameOfRequirement() + "</td></tr>"
									+ "<tr><td><b>Date of Mapping</b></td><td>" + LocalDate.now() + "</td></tr>"
									+ "<tr><td><b>Mapped By</b></td><td>" + userName + "</td></tr>" + "</tbody></table></div>"
									+ "<br><br><b>Thanks & Regards</b><br>" + "<img src= \"cid:image\" alt=" + " Logo Not Available "
									+ " width=" + "160" + " " + "height=" + " " + "80>";

							emailEntity.setMessageBody(mes);
							mailSender.sendMail(emailEntity);
						} catch (Exception e) {

						}
					}
				}
			//}			
			
			
			
		}else {
			return new Response(ExceptionMessage.DataIsEmpty);
		}
		
		return response;
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/reviewprofile/{candidateId}/{requirmentId}/{regId}")
	public @ResponseBody Response reviewProfile(@PathParam("candidateId") Long candidateId, @PathParam("requirmentId") Long requirementId,@PathParam("regId") Long regId) {
		Response response = null;
		if(null != candidateId && null != requirementId && null != regId ) {
			Candidate candidate = candidateDao.find(candidateId);
//			BdmReqSearch  bdmSearch = new BdmReqSearch();
//			bdmSearch.setId(requirementId);
//			bdmSearch.setRegistrationId(registrationId);
			BdmReq requirements = bdmReqDao.find(requirementId);
		
		if(null != candidate && null != requirements) {
			response = candidateReqMappingDao.compareCandidateAndRequirment(candidate,requirements,regId);
		}else {
			if(null == candidate) {
				return new Response(ExceptionMessage.ExcepcetdDataNotAvilable,"The Candidate is not mapped with id :"+candidateId);
			}
			else if(null == requirements) {
				return new Response(ExceptionMessage.ExcepcetdDataNotAvilable,"The requirement is not mapped with id :"+requirementId);
			}else {
				return new Response(ExceptionMessage.Bad_Request,"The Candidate id and requirement id both required to map ");
			}
			
		}
		
		}else {
			return new Response(ExceptionMessage.Bad_Request,"The Candidate id and requirement id both required to map ");
		}
		
		
		return response;
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updatereviewprofilestatus")
	public @ResponseBody Response reviewResume(CandidateMappingRequest candidateMapping) {
		Response response = null;
		Date lastUpdatedDate = null;
		int diffDays = 0;
		if(null == candidateMapping.getId() || null == candidateMapping.getRegId() || null == candidateMapping.getCandidateStatus()) {
			return new Response(ExceptionMessage.Bad_Request, "MappingId is required");
		}else {
			Long candidateStatusId = 0l;			
			if(candidateMapping.getCandidateStatus().equalsIgnoreCase("New") && candidateMapping.isStatusFlag()) {
				 candidateMapping.setLastUpdatedDate(new Date());
				 candidateStatusId = candidateReqMappingDao.getCandidateStatusId("Approved");				 
			}else if(candidateMapping.getCandidateStatus().equalsIgnoreCase("New") && !candidateMapping.isStatusFlag()){
				candidateMapping.setLastUpdatedDate(new Date());
				candidateStatusId = candidateReqMappingDao.getCandidateStatusId("Rejected");	
			}
			if(null == candidateMapping.getRemarks()) {
				candidateMapping.setRemarks("");
			}
			
			lastUpdatedDate = candidateReqMappingDao.getlastUpdatedDate(candidateMapping);
			Date date = new Date();
			if(lastUpdatedDate!=null) {
				long diff = date.getTime() - lastUpdatedDate.getTime();
				diffDays = (int) (diff / (24 * 60 * 60 * 1000));
			}

			
			candidateMapping.setDiffOfMappedReviewDays(diffDays);
			
			candidateMapping.setCandidateStatusId(candidateStatusId);
			response = candidateReqMappingDao.reviewProfile(candidateMapping);
		}
		return response;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/submittocustomer")
	public @ResponseBody Response submitToCustomer(CandidateMappingRequest candidateMapping) {
		Response response = null;
		if(null == candidateMapping.getId() || null == candidateMapping.getRegId()) {
			return new Response(ExceptionMessage.Bad_Request, "MappingId is required");
		}else {
			Long candidateStatusId = 0l;
			Long stageId = 0l;
			Date lastUpdatedDate = null;
			int diffDays = 0;
			lastUpdatedDate = candidateReqMappingDao.getlastUpdatedDate(candidateMapping);
			Date date = new Date();
			if(lastUpdatedDate!=null) {
				long diff = date.getTime() - lastUpdatedDate.getTime();
				diffDays = (int) (diff / (24 * 60 * 60 * 1000));
			}
			if(candidateMapping.getCandidateStatus().equals("Approved")  && candidateMapping.isStatusFlag()) {
				 candidateMapping.setLastUpdatedDate(new Date());
				 candidateStatusId = candidateReqMappingDao.getCandidateStatusId("Submit to Client");
				 stageId = candidateReqMappingDao.getStageOfInterviewStatusId("Submission");				
			}
			else if(candidateMapping.getCandidateStatus().equals("Approved")  && !candidateMapping.isStatusFlag()) {
				 candidateMapping.setLastUpdatedDate(new Date());
				 candidateStatusId = candidateReqMappingDao.getCandidateStatusId("Rejected");
				 stageId = candidateReqMappingDao.getStageOfInterviewStatusId("Screening");
			}
			else if(candidateMapping.getCandidateStatus().equals("new")){
				return new Response(ExceptionMessage.Bad_Request, "The Candidate is not mapped");
			}else if(candidateMapping.getCandidateStatus().equals("Submitted-to-client")) {
				return new Response(ExceptionMessage.Bad_Request, "The Candidate is already submitted");
			}else {
				return new Response(ExceptionMessage.Bad_Request, "The Candidate is already submitted");
			}
			
			
			//System.currentTimeMillis();
			
			
			candidateMapping.setDiffOfReviewSubmitDays(diffDays);
			candidateMapping.setDiffOfSubmitShortListedDays(diffDays);
			candidateMapping.setCandidateStatusId(candidateStatusId);
			candidateMapping.setStageOfInterview(stageId);
		 response = candidateReqMappingDao.submitToCustomer(candidateMapping);
		}
		return response;
	}
	

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/shortlistedbyclient")
	public @ResponseBody Response shortlistedByClient(CandidateMappingRequest candidateMapping) {
		Response response = null;
		if(null == candidateMapping.getId() || null == candidateMapping.getRegId()) {
			return new Response(ExceptionMessage.Bad_Request, "MappingId is required");
		}else {
			Long candidateStatusId = 0l;
			Long stageId = 0l;
			Date lastUpdatedDate = null;
			int diffDays = 0;
			lastUpdatedDate = candidateReqMappingDao.getlastUpdatedDate(candidateMapping);
			Date date = new Date();
			if(lastUpdatedDate!=null) {
				long diff = date.getTime() - lastUpdatedDate.getTime();
				diffDays = (int) (diff / (24 * 60 * 60 * 1000));
			}
			if(candidateMapping.getCandidateStatus().equalsIgnoreCase("Submit to Client") && candidateMapping.isStatusFlag()) {
				 candidateMapping.setLastUpdatedDate(new Date());
				 candidateStatusId = candidateReqMappingDao.getCandidateStatusId("Shortlisted");
				 stageId = candidateReqMappingDao.getStageOfInterviewStatusId("Submission");
			}else if(candidateMapping.getCandidateStatus().equalsIgnoreCase("Submit to Client") && !candidateMapping.isStatusFlag()) {
				 candidateMapping.setLastUpdatedDate(new Date());
				 candidateStatusId = candidateReqMappingDao.getCandidateStatusId("Rejected");
				 stageId = candidateReqMappingDao.getStageOfInterviewStatusId("Submission");
			}
			
			else if(candidateMapping.getCandidateStatus().equalsIgnoreCase("new")){
				return new Response(ExceptionMessage.Bad_Request, "The Candidate is not mapped");
			}else if(candidateMapping.getCandidateStatus().equalsIgnoreCase("Approved")) {
				return new Response(ExceptionMessage.Bad_Request, "The Candidate details not submitted to client");
			}else {
				return new Response(ExceptionMessage.Bad_Request, "The Candidate is already shortlisted");
			}
			
			candidateMapping.setDiffOfReviewSubmitDays(diffDays);
			candidateMapping.setDiffOfSubmitShortListedDays(diffDays);
			candidateMapping.setCandidateStatusId(candidateStatusId);
			candidateMapping.setStageOfInterview(stageId);
		 response = candidateReqMappingDao.submitToCustomer(candidateMapping);
		}
		return response;
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/mappings")
	public @ResponseBody Response getMappingByStatus( CandidateMappingRequest candidateMapping) {
		Response response = null;
		if(null != candidateMapping.getRegId() ) {
			response = candidateReqMappingDao.getMappingByStatus(candidateMapping);
		
		}else {
			return new Response(ExceptionMessage.Bad_Request,"Registration Id should not be null ");
		}
		
		
		return response;
	}
	
//	@GET
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("/mappinglist/{status}/{regId}")
//	public @ResponseBody Response getMappingDetails(@PathParam("status") String status, @PathParam("regId") Long regId) {
//		Response response = null;
//		if(null != regId  && null != status) {
//			response = candidateReqMappingDao.getMappingDetails(regId,status);
//		
//		}else {
//			return new Response(ExceptionMessage.Bad_Request,"Registration Id should not be null ");
//		}
//		
//		
//		return response;
//	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/countbyreq")
	public @ResponseBody Response getCountByReqId(CandidateMappingRequest candidateMapping) {
		Response response = null;
		if(null != candidateMapping.getRegId()  && null != candidateMapping.getBdmReq() ) {
			
			response = candidateReqMappingDao.getCountByReq(candidateMapping);
		
		}else {
			return new Response(ExceptionMessage.Bad_Request,"Registration Id should not be null ");
		}
		
		
		return response;
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/offerrelease")
	public @ResponseBody Response offerRelease(CandidateMappingRequest candidateMapping) {
		Response response = null;
		if(null == candidateMapping.getId() || null == candidateMapping.getRegId()) {
			return new Response(ExceptionMessage.Bad_Request, "MappingId is required");
		}else {
			Long candidateStatusId = 0l;
			Long stageId = 0l;
//			Date lastUpdatedDate = candidateReqMappingDao.getlastUpdatedDate(candidateMapping);
//			Date date = new Date();	
//			long diff = lastUpdatedDate.getTime() - date.getTime();			
//			int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
			if(candidateMapping.getStage().equalsIgnoreCase("Interveiw") && candidateMapping.getCandidateStatus().equalsIgnoreCase("Qualified") ) {
				 candidateMapping.setLastUpdatedDate(new Date());
				 candidateStatusId = candidateReqMappingDao.getCandidateStatusId("Offered");
				 stageId = candidateReqMappingDao.getStageOfInterviewStatusId("Interveiw");
			}			
			else if(candidateMapping.getStage().equalsIgnoreCase("Interveiw") && candidateMapping.getCandidateStatus().equalsIgnoreCase("Offered")){
				return new Response(ExceptionMessage.Bad_Request, "Already Offer Released For The Candidate");
			}else {
				return new Response(ExceptionMessage.Bad_Request, "Candidate is in interveiw process ");
			}
			
			candidateMapping.setDiffOfReviewSubmitDays(0);
			candidateMapping.setDiffOfSubmitShortListedDays(0);
			candidateMapping.setCandidateStatusId(candidateStatusId);
			candidateMapping.setStageOfInterview(stageId);
		 response = candidateReqMappingDao.updateOfferRelease(candidateMapping);
		}
		return response;
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/confirmofferstatus")
	public @ResponseBody Response confirmOfferStatus(CandidateMappingRequest candidateMapping) {
		Response response = null;
		if(null == candidateMapping.getId() || null == candidateMapping.getRegId()) {
			return new Response(ExceptionMessage.Bad_Request, "MappingId is required");
		}else {
			Long candidateStatusId = 0l;
			Long stageId = 0l;
//			Date lastUpdatedDate = candidateReqMappingDao.getlastUpdatedDate(candidateMapping);
//			Date date = new Date();	
//			long diff = lastUpdatedDate.getTime() - date.getTime();			
//			int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
			if(candidateMapping.getStage().equalsIgnoreCase("Interveiw") && candidateMapping.getCandidateStatus().equalsIgnoreCase("Offered") && candidateMapping.isStatusFlag()) {
				 candidateMapping.setLastUpdatedDate(new Date());
				 candidateStatusId = candidateReqMappingDao.getCandidateStatusId("Accepted");
				 stageId = candidateReqMappingDao.getStageOfInterviewStatusId("Offered");
			}else if(candidateMapping.getStage().equalsIgnoreCase("Interveiw") && candidateMapping.getCandidateStatus().equalsIgnoreCase("Offered") && !candidateMapping.isStatusFlag()) {
				 candidateMapping.setLastUpdatedDate(new Date());
				 candidateStatusId = candidateReqMappingDao.getCandidateStatusId("Rejected");
				 stageId = candidateReqMappingDao.getStageOfInterviewStatusId("Interveiw");
			}			
			else {
				return new Response(ExceptionMessage.Bad_Request, "The Candidate is already Offered");
			}
			
			candidateMapping.setDiffOfReviewSubmitDays(0);
			candidateMapping.setDiffOfSubmitShortListedDays(0);
			candidateMapping.setCandidateStatusId(candidateStatusId);
			candidateMapping.setStageOfInterview(stageId);
		 response = candidateReqMappingDao.updateOfferStatus(candidateMapping);
		}
		return response;
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/confirmonboardstatus")
	public @ResponseBody Response confirmOnboardStatus(CandidateMappingRequest candidateMapping) {
		Response response = null;
		if(null == candidateMapping.getId() || null == candidateMapping.getRegId()) {
			return new Response(ExceptionMessage.Bad_Request, "MappingId is required");
		}else {
			Long candidateStatusId = 0l;
			Long stageId = 0l;
//			Date lastUpdatedDate = candidateReqMappingDao.getlastUpdatedDate(candidateMapping);
//			Date date = new Date();	
//			long diff = lastUpdatedDate.getTime() - date.getTime();			
//			int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
			if(candidateMapping.getStage().equalsIgnoreCase("Offered") && candidateMapping.getCandidateStatus().equalsIgnoreCase("Accepted") && candidateMapping.isStatusFlag()) {
				 candidateMapping.setLastUpdatedDate(new Date());
				 candidateStatusId = candidateReqMappingDao.getCandidateStatusId("On Boarded");
				 stageId = candidateReqMappingDao.getStageOfInterviewStatusId("Hiring");
			}else if(candidateMapping.getStage().equalsIgnoreCase("Offered") && candidateMapping.getCandidateStatus().equalsIgnoreCase("Accepted") && !candidateMapping.isStatusFlag()) {
				 candidateMapping.setLastUpdatedDate(new Date());
				 candidateStatusId = candidateReqMappingDao.getCandidateStatusId("Not Joined");
				 stageId = candidateReqMappingDao.getStageOfInterviewStatusId("Offered");
			}	else {				
				return new Response(ExceptionMessage.Bad_Request, "The Candidate is already Hired");
			}
			
			candidateMapping.setDiffOfReviewSubmitDays(0);
			candidateMapping.setDiffOfSubmitShortListedDays(0);
			candidateMapping.setCandidateStatusId(candidateStatusId);
			candidateMapping.setStageOfInterview(stageId);
		 response = candidateReqMappingDao.updateOnBoardStatus(candidateMapping);
		}
		return response;
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/mappinglist")
	public @ResponseBody Response getMappingDetails(CandidateMappingRequest candidateMapping) {
		Response response = null;
		if(null != candidateMapping.getRegId()  && null != candidateMapping.getStage() ) {
			
			response = candidateReqMappingDao.getMappingDetails(candidateMapping);
		
		}else {
			return new Response(ExceptionMessage.Bad_Request,"Registration Id should not be null ");
		}
		
		
		return response;
	}
	
	

	

	private String getAppUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}
	
	

}
