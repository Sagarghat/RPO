package com.ojas.rpo.security.dao.interviewType;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.InterviewType;

public class JpaInterviewTypeDao extends JpaDao<InterviewType, Long> implements InterviewTypeDao {

	public JpaInterviewTypeDao() {
		super(InterviewType.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.rpo.security.dao.JpaDao#findAll()
	 */
	@Override
	@Transactional
	public InterviewType save(InterviewType entity) {
		return this.getEntityManager().merge(entity);
	}

	@Override
	@Transactional
	public List<InterviewType> findAllData(Integer pageNo, Integer pageSize) {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<InterviewType> criteriaQuery = builder.createQuery(InterviewType.class);
		Root<InterviewType> root = criteriaQuery.from(InterviewType.class);
		criteriaQuery.orderBy(builder.asc(root.get("id")));
		TypedQuery<InterviewType> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		typedQuery.setFirstResult(pageNo);
		typedQuery.setMaxResults(pageSize);
		return typedQuery.getResultList();

	}

}
