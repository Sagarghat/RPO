package com.ojas.rpo.security.dao.interviewround;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.InterviewRound;

public interface InterviewRoundDao extends Dao<InterviewRound,Long>{
	public List<InterviewRound> findAllData(Integer pageNo, Integer pageSize);

}
