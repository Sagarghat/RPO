package com.ojas.rpo.security.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@javax.persistence.Entity
public class AssesmentName implements Entity {

	@Id
	@GeneratedValue
	private Long id;
	private String assesmentName;
	private Long registrationId;
	
	

	@Override
	public String toString() {
		return "AssesmentName [id=" + id + ", assesmentName=" + assesmentName + ", registrationId=" + registrationId
				+ "]";
	}

	public Long getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(Long registrationId) {
		this.registrationId = registrationId;
	}

	public String getAssesmentName() {
		return assesmentName;
	}

	public String getAssessmentName() {
		return assesmentName;
	}

	public void setAssesmentName(String assessmentName) {
		this.assesmentName = assessmentName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return id;
	}

}
