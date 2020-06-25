
package com.ojas.rpo.security.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "interviewdetails")
@javax.persistence.Entity
public class InterviewDetails implements Entity {
	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String status;
	@ManyToOne
	private InterviewType interviewName;
	@ManyToOne
	private Client client;
	@ManyToOne
	private Candidate candidate;
	@ManyToOne
	private BdmReq bdmReq;

	@ManyToOne
	private AddContact interviewer;
	@ManyToOne
	private User interviewOwner;

	@ManyToOne
	private AssesmentName assesmentName;

	private String interviewLocation;

	private String scheduleComments;

	private String interviewFrom;
	private String interviewEnd;

	private String fromtime;
	private String toTime;
	@ManyToOne
	private Remainder remainder;

	private String attachement;

	private String fileName;
	@ManyToOne
	private InterviewRound round;

	public InterviewRound getRound() {
		return round;
	}

	public void setRound(InterviewRound round) {
		this.round = round;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "reg_id")
	private Long registrationId;

	public InterviewDetails() {
		super();
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

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

	public Long getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(Long registrationId) {
		this.registrationId = registrationId;
	}

	public String getScheduleComments() {
		return scheduleComments;
	}

	public void setScheduleComments(String scheduleComments) {
		this.scheduleComments = scheduleComments;
	}

	public BdmReq getBdmReq() {
		return bdmReq;
	}

	public void setBdmReq(BdmReq bdmReq) {
		this.bdmReq = bdmReq;
	}

	public String getAttachement() {
		return attachement;
	}

	public void setAttachement(String attachement) {
		this.attachement = attachement;
	}

	public Remainder getRemainder() {
		return remainder;
	}

	public void setRemainder(Remainder remainder) {
		this.remainder = remainder;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public User getInterviewOwner() {
		return interviewOwner;
	}

	public void setInterviewOwner(User interviewOwner) {
		this.interviewOwner = interviewOwner;
	}

	public String getInterviewLocation() {
		return interviewLocation;
	}

	public void setInterviewLocation(String interviewLocation) {
		this.interviewLocation = interviewLocation;
	}

	public AssesmentName getAssesmentName() {
		return assesmentName;
	}

	public void setAssesmentName(AssesmentName assesmentName) {
		this.assesmentName = assesmentName;
	}

	public String getInterviewFrom() {
		return interviewFrom;
	}

	public void setInterviewFrom(String interviewFrom) {
		this.interviewFrom = interviewFrom;
	}

	public String getInterviewEnd() {
		return interviewEnd;
	}

	public void setInterviewEnd(String interviewEnd) {
		this.interviewEnd = interviewEnd;
	}

	@Override
	public String toString() {
		return "InterviewDetails [id=" + id + ", status=" + status + ", interviewName=" + interviewName + ", client="
				+ client + ", candidate=" + candidate + ", bdmReq=" + bdmReq + ", interviewer=" + interviewer
				+ ", interviewOwner=" + interviewOwner + ", assesmentName=" + assesmentName + ", interviewLocation="
				+ interviewLocation + ", scheduleComments=" + scheduleComments + ", interviewFrom=" + interviewFrom
				+ ", interviewEnd=" + interviewEnd + ", fromtime=" + fromtime + ", toTime=" + toTime + ", remainder="
				+ remainder + ", attachement=" + attachement + ", fileName=" + fileName + ", round=" + round
				+ ", registrationId=" + registrationId + "]";
	}
	
	
	 

}