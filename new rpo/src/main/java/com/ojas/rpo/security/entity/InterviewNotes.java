package com.ojas.rpo.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "interviewNotes")
public class InterviewNotes implements com.ojas.rpo.security.entity.Entity {

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
	private Long interviewId;

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

	public Long getInterviewId() {
		return interviewId;
	}

	public void setInterviewId(Long interviewId) {
		this.interviewId = interviewId;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
