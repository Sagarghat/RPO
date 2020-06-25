package com.ojas.rpo.security.dao.permissions;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.Permissions;
import com.ojas.rpo.security.util.PermissionSearch;

public interface PermissionsDao extends Dao<Permissions, Long> {

	List<Permissions> findAllPermissions(Integer startingRow, Integer pageSize);
	List<Permissions> getPermissionsByRole(PermissionSearch permissionSearch);

}
