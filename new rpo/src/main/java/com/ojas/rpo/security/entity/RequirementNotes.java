package com.ojas.rpo.security.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "requirementNotes")
public class RequirementNotes implements Entity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String noteType;

	@Column
	private String noteData;

	@Column
	private Long registrationId;

	@Column
	private Long recruiterId;

	@Override
	public Long getId() {
		return id;
	}

	public String getNoteType() {
		return noteType;
	}

	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}

	public String getNoteData() {
		return noteData;
	}

	public void setNoteData(String noteData) {
		this.noteData = noteData;
	}

	public Long getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(Long registrationId) {
		this.registrationId = registrationId;
	}

	public Long getRecruiterId() {
		return recruiterId;
	}

	public void setRecruiterId(Long recruiterId) {
		this.recruiterId = recruiterId;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
