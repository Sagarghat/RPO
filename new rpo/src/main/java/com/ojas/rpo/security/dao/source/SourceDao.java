package com.ojas.rpo.security.dao.source;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.Source;

public interface SourceDao extends Dao<Source, Long>{

	List<Source> findSources(Integer startingRow, Integer pagesize);

}
