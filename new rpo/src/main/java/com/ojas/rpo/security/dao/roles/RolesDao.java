package com.ojas.rpo.security.dao.roles;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.Roles;

public interface RolesDao extends Dao<Roles, Long> {

	public List<Roles> findAll(Integer pageNo, Integer pageSize);


}
