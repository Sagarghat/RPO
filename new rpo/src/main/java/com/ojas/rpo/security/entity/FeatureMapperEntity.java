package com.ojas.rpo.security.entity;

import javax.annotation.Generated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "mapperClass")
public class FeatureMapperEntity implements Entity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne
	private User user;
	@ManyToOne
	private Feature feature;
	@ManyToOne
	private Permissions permissions;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Feature getFeature() {
		return feature;
	}

	public void setFeature(Feature feature) {
		this.feature = feature;
	}

	public Permissions getPermissions() {
		return permissions;
	}

	public void setPermissions(Permissions permissions) {
		this.permissions = permissions;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return "FeatureMapperEntity [id=" + id + ", user=" + user + ", feature=" + feature + ", permissions="
				+ permissions + "]";
	}
	
	

}
