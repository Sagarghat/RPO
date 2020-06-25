package com.ojas.rpo.security.rest.resources;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ojas.rpo.security.dao.InterviewDetails.InterviewDetailsDao;
import com.ojas.rpo.security.dao.bdmreqdtls.BdmReqDao;
import com.ojas.rpo.security.dao.candidate.CandidatelistDao;
import com.ojas.rpo.security.dao.candidateReqMapping.CandidateReqMappingDao;
import com.ojas.rpo.security.dao.interviewfeedback.InterviewFeedbackDao;
import com.ojas.rpo.security.dao.user.UserDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.InterviewDetails;
import com.ojas.rpo.security.entity.InterviewFeedback;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.OutlookMailSender;

@Component
@Path("/interviewfeedback")
public class InterviewFeedbackResource {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private InterviewFeedbackDao interviewfeedbackDao;

	@Autowired
	OutlookMailSender mailSender;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private BdmReqDao bdmReqDao;
	@Autowired
	private InterviewDetailsDao intdao;

	@Autowired
	private CandidatelistDao candidateDao;

	private CandidateReqMappingDao mappingdao;

	@Autowired
	private UserDao userDao;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public Response saveFeedBack(InterviewFeedback feedback) {

		feedback.setDate(new Date());
		Long id = interviewfeedbackDao.getFeedbackIdByIntId(feedback.getInterviewId());
		if (id != null) {
			feedback.setId(id);
		}
			InterviewDetails interviewDetails = intdao.find(feedback.getInterviewId());
			interviewDetails.setStatus(feedback.getStatus());
			intdao.save(interviewDetails);
			if(feedback.getcStatus().getId()==null) {
				feedback.setcStatus(interviewDetails.getCandidate().getCandidateStatusMaster());
			}
			InterviewFeedback interviewFeedback = interviewfeedbackDao.save(feedback);
			
			interviewfeedbackDao.updateCandidateMapping(interviewFeedback);
		return new Response(ExceptionMessage.StatusSuccess, interviewFeedback);

	}
	
	

}
