package com.ojas.rpo.security.dao.location;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Skill;

public interface SkillDao extends Dao<Skill, Long> {

	void updateSkillsByCandidateId(Long id, List<Skill> skills);

	Response findAllSkills(Integer startingRow, Integer pagesize, Long regId);

	List<Skill> find(Long id, Long regId);
}
