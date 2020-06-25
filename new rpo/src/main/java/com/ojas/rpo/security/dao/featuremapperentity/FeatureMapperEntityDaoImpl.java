package com.ojas.rpo.security.dao.featuremapperentity;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.FeatureMapperEntity;

public class FeatureMapperEntityDaoImpl extends JpaDao<FeatureMapperEntity, Long> implements FeatureMapperEntityDao {

	public FeatureMapperEntityDaoImpl( ) {
		super(FeatureMapperEntity.class);
	}

	

}
