package com.ojas.rpo.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Experiencedetails")
public class Experiencedetails implements com.ojas.rpo.security.entity.Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@Column
	private String companyname;

	@Column
	private String occupation;

	@Column
	private Date durationFormDate;
	@Column
	private Date durationToDate;

	@Column
	private Boolean currentWorkingHere;

	@Column(columnDefinition = "LONGTEXT")
	private String workExperience;

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public Date getDurationFormDate() {
		return durationFormDate;
	}

	public void setDurationFormDate(Date durationFormDate) {
		this.durationFormDate = durationFormDate;
	}

	public Date getDurationToDate() {
		return durationToDate;
	}

	public void setDurationToDate(Date durationToDate) {
		this.durationToDate = durationToDate;
	}

	public Boolean getCurrentWorkingHere() {
		return currentWorkingHere;
	}

	public void setCurrentWorkingHere(Boolean currentWorkingHere) {
		this.currentWorkingHere = currentWorkingHere;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWorkExperience() {
		return workExperience;
	}

	public void setWorkExperience(String workExperience) {
		this.workExperience = workExperience;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
