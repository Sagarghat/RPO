package com.ojas.rpo.security.dao.assignedrecruiters;

import java.util.Date;
import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.AssignedRecruiters;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.AssignedRecruitersMapper;

public interface AssingnedRecruitersDao extends Dao<AssignedRecruiters, Long> {

	List<AssignedRecruiters> create(AssignedRecruitersMapper mapper);

	Response deleteRecruitersById(AssignedRecruitersMapper mapper);

	Response getByRecId(AssignedRecruitersMapper mapper);

	Response getAllRecruiters(AssignedRecruitersMapper mapper);

	Boolean checkduplicates(AssignedRecruitersMapper mapper);
	
	Date getAssignedDate(AssignedRecruitersMapper mapper);


}
