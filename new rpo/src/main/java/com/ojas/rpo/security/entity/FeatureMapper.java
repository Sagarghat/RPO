package com.ojas.rpo.security.entity;

import java.util.List;

public class FeatureMapper {

	private Long userId;
	private Long featureId;
	
	private List<Permissions> permissionId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getFeatureId() {
		return featureId;
	}

	public void setFeatureId(Long featureId) {
		this.featureId = featureId;
	}

	public List<Permissions> getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(List<Permissions> permissionId) {
		this.permissionId = permissionId;
	}

}
