package com.ojas.rpo.security.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "CandidateJobTitle")
public class CandidateJobTitle implements com.ojas.rpo.security.entity.Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	private Long id;
	@Column
	private String currentJobTittle;

	public CandidateJobTitle() {
		super();
	}

	public CandidateJobTitle(Long id, String currentJobTittle) {
		super();
		this.id = id;
		this.currentJobTittle = currentJobTittle;
	}

	public String getCurrentJobTittle() {
		return currentJobTittle;
	}

	public void setCurrentJobTittle(String currentJobTittle) {
		this.currentJobTittle = currentJobTittle;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {

		return this.id;
	}

}
