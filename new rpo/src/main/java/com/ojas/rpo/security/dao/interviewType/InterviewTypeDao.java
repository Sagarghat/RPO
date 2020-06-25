package com.ojas.rpo.security.dao.interviewType;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.InterviewType;

public interface InterviewTypeDao extends Dao<InterviewType,Long> {
	
	public List<InterviewType> findAllData(Integer pageNo, Integer pageSize);
}
