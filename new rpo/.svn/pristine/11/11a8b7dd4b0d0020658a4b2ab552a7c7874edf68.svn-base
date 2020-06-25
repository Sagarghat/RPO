package com.ojas.rpo.security.dao.feature;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.Feature;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.FeatureSearch;

public interface FeatureDao extends Dao<Feature, Long> {

	Response findAll(Integer startingRow, Integer pageSize, Long regId);
	List<Feature> getFeaturesByRole(FeatureSearch fearchSearch);

	List<Feature> find(Long id, Long regId);


}
