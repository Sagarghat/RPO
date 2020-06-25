package com.ojas.rpo.security.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.ojas.rpo.security.transfer.Qualification;

@Entity
@Table(name = "EducationalDetails")
public class EducationalDetails implements com.ojas.rpo.security.entity.Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@Column(insertable = true, nullable = false)
	private String nameOfSchool;
	@ManyToOne
	private AddQualification degree;
	@Column
	private Date durationFormDate;
	@Column
	private Date durationToDate;
	@Column
	private Boolean currentPursuing;

	public String getNameOfSchool() {
		return nameOfSchool;
	}

	public void setNameOfSchool(String nameOfSchool) {
		this.nameOfSchool = nameOfSchool;
	}

	public AddQualification getDegree() {
		return degree;
	}

	public void setDegree(AddQualification degree) {
		this.degree = degree;
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

	public Boolean getCurrentPursuing() {
		return currentPursuing;
	}

	public void setCurrentPursuing(Boolean currentPursuing) {
		this.currentPursuing = currentPursuing;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return this.id;
	}

}
