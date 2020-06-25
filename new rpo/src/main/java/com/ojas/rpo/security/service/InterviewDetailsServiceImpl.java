package com.ojas.rpo.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ojas.rpo.security.dao.InterviewDetails.InterviewDetailsDao;
import com.ojas.rpo.security.dao.sendemail.MailSender;
import com.ojas.rpo.security.entity.Candidate;
import com.ojas.rpo.security.entity.IntMapper;
import com.ojas.rpo.security.entity.InterviewDetails;
import com.ojas.rpo.security.entity.InterviewMapper;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.DateParsing;
import com.ojas.rpo.security.util.EmailEntity;
import com.ojas.rpo.security.util.InterviewDetailsValidation;

public class InterviewDetailsServiceImpl implements InterviewDetailsService {
	@Autowired
	private InterviewDetailsDao interviewDetailsDao;
	@Autowired
	private MailSender mailsender;
	private boolean flag = true;

	public InterviewDetailsServiceImpl() {
		super();
	}

	public Response createInterview(IntMapper mapper) {
		InterviewDetails interviewDetail = InterviewDetailsValidation.createInterview(mapper);
		List<InterviewDetails> list = new ArrayList<>();
		Response mail = new Response();
		Response rs = new Response();
		if (mapper.getDetails().getCandidate() != null) {
			if (mapper.getDetails().getCandidate().getId() != null) {
				InterviewDetails i = interviewDetailsDao.find(mapper.getDetails().getId());
				if (mapper.getDetails().getInterviewName().getId() != i.getInterviewName().getId()
						&& i.getStatus().equalsIgnoreCase("interview scheduled")) {
					mapper.getDetails().setId(null);						  
					flag = false;
				}
				if (mapper.getDetails().getRound().getId() != i.getRound().getId()
						&& mapper.getDetails().getInterviewName().getId() != i.getInterviewName().getId()
						&& !i.getStatus().equalsIgnoreCase("interview scheduled")) {

					mapper.getDetails().setId(null);
					flag = false;
				}
				mapper.getDetails().setStatus(i.getStatus());
				InterviewDetails interviewDetails = interviewDetailsDao.save(mapper.getDetails());
				list.add(interviewDetails);
				interviewDetailsDao.updateCandidateMapping(interviewDetails);
			
				EmailEntity emailEntity = new EmailEntity();
				try {
					emailEntity.setMessageBody("Hi " + interviewDetails.getCandidate().getFirstName()
							+ "\n  Congratulations!!  \n Your Resume  is ShortListed for the "
							+ interviewDetails.getInterviewName().getModeofInterview() + " " + " for  "
							+ interviewDetails.getRound().getLevel() + " at " + interviewDetails.getFromtime() + " on "
							+ interviewDetails.getInterviewFrom() + "\n Thank you " + "\n Regards");

					emailEntity.setStartDate(DateParsing.createDate(interviewDetails.getInterviewFrom(),
							interviewDetails.getFromtime()));
					emailEntity.setMessageBody("Hi " + interviewDetails.getCandidate().getFirstName()
							+ "\n  attend the next round of the interview on " + interviewDetails.getFromtime() + ""
							+ interviewDetails.getInterviewFrom() + "\n Thank you " + "\n Regards");
					emailEntity.setTo(interviewDetails.getCandidate().getEmail());
					emailEntity.setFileAttachement(interviewDetails.getAttachement());
					emailEntity.setFileType(interviewDetails.getFileName());
					emailEntity.setRemainder(interviewDetails.getRemainder().getTime());

					if (flag == false) {

						emailEntity.setMessagesubject("Next Round of interview");
					} else {
						emailEntity.setMessagesubject("Interview is re-scheduled");

					}

					mailsender.sendMail(emailEntity);
				} catch (Exception e) {
					rs.setRes("Email related Error with candidate"+interviewDetails.getCandidate().getFirstName());
					return rs;
				}
					rs.setResult(list);
			}
		} else {
			for (Candidate candidate : mapper.getCandidates()) {

				if (candidate != null) {
					interviewDetail.setCandidate(candidate);

					InterviewDetails interviewDetails = interviewDetailsDao.save(interviewDetail);
					if (interviewDetails.getId() != null)
						list.add(interviewDetails);
					interviewDetailsDao.updateCandidateMapping(interviewDetails);

					EmailEntity emailEntity = new EmailEntity();
					try {
						emailEntity.setStartDate(DateParsing.createDate(interviewDetails.getInterviewFrom(),
								interviewDetails.getFromtime()));
						emailEntity.setMessageBody("Hi "+interviewDetails.getCandidate().getFirstName()+"\n You are selected to attend"
								+ " "+interviewDetails.getRound().getLevel()+" of "+interviewDetails.getInterviewName().getModeofInterview()+" ");
						emailEntity.setTo(interviewDetails.getCandidate().getEmail());
						emailEntity.setFileAttachement(interviewDetails.getAttachement());
						emailEntity.setFileType(interviewDetails.getFileName());
						emailEntity.setRemainder(interviewDetails.getRemainder().getTime());
						emailEntity.setMessagesubject("Your resume is Shortlisted");
						emailEntity.setStartDate(DateParsing.createDate(interviewDetails.getInterviewFrom(),
								interviewDetails.getFromtime()));
						emailEntity.setEndDate(DateParsing.createDate(interviewDetails.getInterviewEnd(),
								interviewDetails.getToTime()));
						emailEntity.setLocation("OJAS");
						mailsender.sendMail(emailEntity);

					} catch (Exception e) {
						rs.setRes("Email related Error with candidate"+interviewDetails.getCandidate().getFirstName());
						return rs;
					}

				} else {

					rs.setResult(null);
					return rs;

				}

			}

			rs.setResult(list);

		}

		return rs;

	}

	@Override
	public IntMapper get(Long id) {

		InterviewDetails intdetails = interviewDetailsDao.find(id);
		IntMapper mapper = new IntMapper();
		mapper.setDetails(intdetails);
		return mapper;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public List<InterviewDetails> createInterviewDetails(InterviewMapper mapper) {
		InterviewDetails interviewDetail = InterviewDetailsValidation.createInterviewDetails(mapper);
		List<InterviewDetails> list = new ArrayList<>();
		for (Candidate candidate : mapper.getCandidate()) {

			if (candidate != null) {
				interviewDetail.setCandidate(candidate);

				InterviewDetails interviewDetails = interviewDetailsDao.save(interviewDetail);
				list.add(interviewDetails);
				/*
				 * EmailEntity e = new EmailEntity();
				 * e.setTo(interviewDetails.getCandidate().getEmail());
				 * System.out.println(interviewDetails.getCandidate().getEmail());
				 * e.setFrom("mounika.venuvenka@ojas-it.com");
				 * 
				 * e.setMessagesubject("Interview Invitaion"); e.setMessageBody("Dear " +
				 * candidate.getFirstName() +
				 * "you are shortlisted to attend interview at the below location");
				 * e.setPassword("Mouni@1506");
				 * e.setStartDate(interviewDetails.getInterviewFrom());
				 * e.setEndDate(interviewDetails.getInterviewEnd());
				 * e.setRemainder(interviewDetail.getRemainder().getTime() + "");
				 * e.setLocation("OJAS"); try { System.out.println(mailsender + " mounika");
				 * mailsender.schedulingMail(e); } catch (Exception e1) {
				 * 
				 * System.out.println("in the catch block"); e1.printStackTrace(); }
				 */
			} else {
				return new ArrayList<>();

			}

		}
		return list;
	}

	@Override
	public InterviewMapper getById(Long id) {
		InterviewDetails interviewDetails = interviewDetailsDao.find(id);
		InterviewMapper mapper = new InterviewMapper();

		if (interviewDetails != null) {
			mapper.setAssesmentName(interviewDetails.getAssesmentName());
			mapper.setAttachement(interviewDetails.getAttachement());
			mapper.setBdmReq(interviewDetails.getBdmReq());
			mapper.setClient(interviewDetails.getClient());
			mapper.setFromtime(interviewDetails.getFromtime());
			// mapper.setInterviewEnd(interviewDetails.getInterviewEnd());
			mapper.setInterviewer(interviewDetails.getInterviewer());
			// mapper.setInterviewFrom(interviewDetails.getInterviewFrom());
			mapper.setInterviewLocation(interviewDetails.getInterviewLocation());
			mapper.setInterviewName(interviewDetails.getInterviewName());
			mapper.setInterviewOwner(interviewDetails.getInterviewOwner());
			mapper.setRegistrationId(interviewDetails.getRegistrationId());
			mapper.setRemainder(interviewDetails.getRemainder());
			mapper.setScheduleComments(interviewDetails.getScheduleComments());
			mapper.setToTime(interviewDetails.getToTime());
			mapper.setId(interviewDetails.getId());
			Candidate c = new Candidate();
			List<Candidate> clist = new ArrayList<>();
			c = interviewDetails.getCandidate();
			clist.add(c);
			mapper.setCandidate(clist);
			return mapper;
		} else
			return null;

	}

}
