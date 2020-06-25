package com.ojas.rpo.security.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@javax.persistence.Entity
public class InterviewRound implements Entity {
	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = true, nullable = false)
	private String level;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public InterviewRound() {
		super();
	}
	
}
