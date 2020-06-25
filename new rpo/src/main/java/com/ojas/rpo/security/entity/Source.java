package com.ojas.rpo.security.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@javax.persistence.Entity
public class Source implements Entity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2194156073437973720L;

	@Id
	@GeneratedValue
	private Long id;
	@Column(unique = true, nullable = false)
	private String sourceName;

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Source [id=" + id + ", SourceName=" + sourceName + "]";
	}
	
	
	
}
