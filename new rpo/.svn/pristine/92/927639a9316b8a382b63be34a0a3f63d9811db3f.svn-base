package com.ojas.rpo.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "assignedRecruiters")
public class AssignedRecruiters implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private Long recruiterId;
	@Column
	private Long requirementId;
	@Column
	private Long clientId;
	@Column
	private Long userId;
	@Column
	private Date assignedDate;

	@Column
	private Long registrationId;

	public Long getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(Long registrationId) {
		this.registrationId = registrationId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRecruiterId() {
		return recruiterId;
	}

	public void setRecruiterId(Long recruiterId) {
		this.recruiterId = recruiterId;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(Date assignedDate) {
		this.assignedDate = assignedDate;
	}

	@Override
	public Long getId() {
		return this.id;
	}

}
