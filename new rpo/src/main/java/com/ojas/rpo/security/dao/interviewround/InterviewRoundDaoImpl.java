package com.ojas.rpo.security.dao.interviewround;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.InterviewRound;

public class InterviewRoundDaoImpl  extends JpaDao<InterviewRound, Long>implements InterviewRoundDao  {

	public InterviewRoundDaoImpl() {
		super(InterviewRound.class);
	}

	@Override
	@Transactional
	public List<InterviewRound> findAllData(Integer pageNo, Integer pageSize) {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<InterviewRound> criteriaQuery = builder.createQuery(InterviewRound.class);
		Root<InterviewRound> root = criteriaQuery.from(InterviewRound.class);
		criteriaQuery.orderBy(builder.asc(root.get("id")));
		TypedQuery<InterviewRound> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		typedQuery.setFirstResult(pageNo);
		typedQuery.setMaxResults(pageSize);
		return typedQuery.getResultList();

	}
}
