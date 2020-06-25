package com.ojas.rpo.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "interviewfeedback")
@javax.persistence.Entity
public class InterviewFeedback implements Entity {
	@Id
	@GeneratedValue
	private Long id;

	public void setId(Long id) {
		this.id = id;
	}

	@Column
	private Date date;

	@Column
	private Long requirementId;

	@Column
	private Long candidateid;

	@Column
	private Long interviewId;

	private String status;
	private String comments;
	
	@ManyToOne
	private CandidateStatusMaster cStatus;
	
	private boolean flag;

	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Long requirementId) {
		this.requirementId = requirementId;
	}

	public Long getCandidateid() {
		return candidateid;
	}

	public void setCandidateid(Long candidateid) {
		this.candidateid = candidateid;
	}

	public Long getInterviewId() {
		return interviewId;
	}

	public void setInterviewId(Long interviewId) {
		this.interviewId = interviewId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public CandidateStatusMaster getcStatus() {
		return cStatus;
	}

	public void setcStatus(CandidateStatusMaster cStatus) {
		this.cStatus = cStatus;
	}

	@Override
	public Long getId() {
		return id;
	}

} 
