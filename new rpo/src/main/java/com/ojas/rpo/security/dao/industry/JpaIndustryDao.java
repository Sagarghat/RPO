package com.ojas.rpo.security.dao.industry;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.Client;
import com.ojas.rpo.security.entity.Industry;

@Repository
public class JpaIndustryDao extends JpaDao<Industry, Long> implements IndustryDao {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public JpaIndustryDao() {
		super(Industry.class);
	}

	@Override
	public Industry save(Industry entity) {
		logger.info("Industry saving");

		return this.getEntityManager().merge(entity);
	}

	@Override
	public Industry find(Long id) {
		if (id != 0)
			return this.getEntityManager().find(Industry.class, id);
		else
			return null;
	}

	
	@Override
	public List<Industry> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Industry> criteriaQuery = builder.createQuery(Industry.class);

		Root<Industry> root = criteriaQuery.from(Industry.class);
		criteriaQuery.orderBy(builder.desc(root.get("id")));

		TypedQuery<Industry> typedQuery = this.getEntityManager().createQuery(criteriaQuery);

		return typedQuery.getResultList();

	}

}
