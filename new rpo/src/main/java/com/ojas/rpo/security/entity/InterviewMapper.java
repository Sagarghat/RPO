package com.ojas.rpo.security.entity;

import java.util.Date;
import java.util.List;

public class InterviewMapper {
	
	private Long id;

	private InterviewType interviewName;

	private Client client;

	private List<Candidate> candidate;

	private BdmReq bdmReq;

	private AddContact interviewer;

	private User interviewOwner;
	
	private String fromtime;
	
	private String toTime;
	private AssesmentName assesmentName;

	private String interviewLocation;

	private String scheduleComments;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	private Date interviewFrom;
	private Date interviewEnd;

	private Remainder remainder;

	private String attachement;

	private Long registrationId;
	private String status;
	
	
	public AddContact getInterviewer() {
		return interviewer;
	}

	public void setInterviewer(AddContact interviewer) {
		this.interviewer = interviewer;
	}

	public String getFromtime() {
		return fromtime;
	}

	public void setFromtime(String fromtime) {
		this.fromtime = fromtime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setInterviewOwner(User interviewOwner) {
		this.interviewOwner = interviewOwner;
	}


	public InterviewType getInterviewName() {
		return interviewName;
	}

	public void setInterviewName(InterviewType interviewName) {
		this.interviewName = interviewName;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	
	public List<Candidate> getCandidate() {
		return candidate;
	}

	public void setCandidate(List<Candidate> candidate) {
		this.candidate = candidate;
	}

	public BdmReq getBdmReq() {
		return bdmReq;
	}

	public void setBdmReq(BdmReq bdmReq) {
		this.bdmReq = bdmReq;
	}
	public User getInterviewOwner() {
		return interviewOwner;
	}

	public AssesmentName getAssesmentName() {
		return assesmentName;
	}

	public void setAssesmentName(AssesmentName assesmentName) {
		this.assesmentName = assesmentName;
	}

	public String getInterviewLocation() {
		return interviewLocation;
	}

	public void setInterviewLocation(String interviewLocation) {
		this.interviewLocation = interviewLocation;
	}

	public String getScheduleComments() {
		return scheduleComments;
	}

	public void setScheduleComments(String scheduleComments) {
		this.scheduleComments = scheduleComments;
	}

	public Date getInterviewFrom() {
		return interviewFrom;
	}

	public void setInterviewFrom(Date interviewFrom) {
		this.interviewFrom = interviewFrom;
	}

	public Date getInterviewEnd() {
		return interviewEnd;
	}

	public void setInterviewEnd(Date interviewEnd) {
		this.interviewEnd = interviewEnd;
	}

	public Remainder getRemainder() {
		return remainder;
	}

	public void setRemainder(Remainder remainder) {
		this.remainder = remainder;
	}

	public String getAttachement() {
		return attachement;
	}

	public void setAttachement(String attachement) {
		this.attachement = attachement;
	}

	public Long getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(Long registrationId) {
		this.registrationId = registrationId;
	}

	public InterviewMapper() {
		super();
	}

}
