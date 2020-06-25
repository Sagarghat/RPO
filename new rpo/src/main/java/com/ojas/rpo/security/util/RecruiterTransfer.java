package com.ojas.rpo.security.util;

import java.util.Date;

public class RecruiterTransfer {

	private String recruitersName;

	private String requirementName;

	private String clientName;

	private String userName;

	private Long recruiterId;

	public Long getRecruiterId() {
		return recruiterId;
	}

	public void setRecruiterId(Long recruiterId) {
		this.recruiterId = recruiterId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private Long requirementId;
	private Long clientId;

	private String email;

	private Date assignedDate;

	private String assignedBy;

	public String getAssignedBy() {
		return assignedBy;
	}

	public void setAssignedBy(String assignedBy) {
		this.assignedBy = assignedBy;
	}

	public String getRecruitersName() {
		return recruitersName;
	}

	public void setRecruitersName(String recruitersName) {
		this.recruitersName = recruitersName;
	}

	public String getRequirementName() {
		return requirementName;
	}

	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getRequirementId() {
		return requirementId;
	}

	public void setRequirementId(Long requirementId) {
		this.requirementId = requirementId;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Date getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}

}
