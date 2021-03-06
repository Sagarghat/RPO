package com.ojas.rpo.security.dao.plans;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.Plans;
import com.ojas.rpo.security.entity.Response;

public interface PlansDAO extends Dao<Plans, Long> {
	Response findAllPlans(Integer startingRow, Integer pagesize, Long regId);

	List<Plans> findById(Long id, Long regId);
}
