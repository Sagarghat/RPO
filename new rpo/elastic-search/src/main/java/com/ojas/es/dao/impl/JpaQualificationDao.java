package com.ojas.es.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.es.entity.AddQualification;

public class JpaQualificationDao extends JpaDao<AddQualification, Long> implements QualificationDao {
	public JpaQualificationDao() {
		super(AddQualification.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AddQualification> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<AddQualification> criteriaQuery = builder.createQuery(AddQualification.class);

		Root<AddQualification> root = criteriaQuery.from(AddQualification.class);
		criteriaQuery.orderBy(builder.desc(root.get("date")));

		TypedQuery<AddQualification> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	@Transactional
	public void updateQualificationsByCandidateId(Long id, List<AddQualification> education) {
		if (null != education) {

			Query qualDeleteQuery = this.getEntityManager()
					.createNativeQuery(" DELETE FROM candidateeducationmap WHERE candidate_ID = ?");
			qualDeleteQuery.setParameter(1, id);
			qualDeleteQuery.executeUpdate();
			for (AddQualification addQualification : education) {
				Query addQualQuery = this.getEntityManager().createNativeQuery(
						" INSERT INTO candidateeducationmap(candidate_ID,education_ID) values(?,?) ");
				addQualQuery.setParameter(1, id);
				addQualQuery.setParameter(2, addQualification.getId());
				addQualQuery.executeUpdate();
			}
		}

	}

	@Override
	public Long getRoleByName(String degree) {
		Query q = getEntityManager().createNativeQuery(
				"select id, qualificationName  from addqualification where qualificationName= '" + degree + "'");

		Map<String, Integer> map = new HashMap<String, Integer>();
		List<Object[]> results = q.getResultList();

		if (results != null) {
			for (Object obj[] : results) {
				return Long.parseLong(obj[0] + "");
			}

		}
		return 0L;
	}

}
