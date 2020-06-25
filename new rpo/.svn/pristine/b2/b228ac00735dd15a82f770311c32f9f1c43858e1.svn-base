package com.ojas.rpo.security.dao.source;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.Plans;
import com.ojas.rpo.security.entity.Source;

public class SourceDaoImpl extends JpaDao<Source, Long> implements SourceDao{

	public SourceDaoImpl() {
		super(Source.class);
	}
	
	
	
	@Override
	@Transactional(readOnly = true)
	public List<Source> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Source> criteriaQuery = builder.createQuery(Source.class);

		Root<Source> root = criteriaQuery.from(Source.class);

		TypedQuery<Source> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}



	@Override
	public List<Source> findSources(Integer startingRow, Integer pagesize) {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Source> criteriaQuery = builder.createQuery(Source.class);

		Root<Source> root = criteriaQuery.from(Source.class);

		TypedQuery<Source> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		typedQuery.setFirstResult(startingRow).setMaxResults(pagesize).getResultList().toString();
		return typedQuery.getResultList();
	}
}
