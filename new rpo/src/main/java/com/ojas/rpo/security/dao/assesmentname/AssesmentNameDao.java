package com.ojas.rpo.security.dao.assesmentname;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.AssesmentName;
import com.ojas.rpo.security.entity.Response;

public interface AssesmentNameDao  extends Dao<AssesmentName, Long>{
	
	public Response getAll(Integer pageNo, Integer pageSize, Long regId);
	public AssesmentName getById(Long id, Long regId);
	
	public boolean check(AssesmentName name);
	

}
