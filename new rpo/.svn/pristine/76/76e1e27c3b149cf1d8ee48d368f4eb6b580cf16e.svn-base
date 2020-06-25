package com.ojas.rpo.security.dao.Qualification;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.AddQualification;

public interface QualificationDao extends Dao<AddQualification, Long> {

	void updateQualificationsByCandidateId(Long id, List<AddQualification> education, Integer registrationId);

	List<AddQualification> findAllAddQualification(Integer startingRow, Integer pagesize, Integer registrationId);

	List<AddQualification> findAll(Integer registrationId);

	AddQualification find(Long id, Integer registrationId);

}
