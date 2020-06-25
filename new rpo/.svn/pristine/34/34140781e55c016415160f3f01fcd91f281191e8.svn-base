package com.ojas.rpo.security.dao.Qualification;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.AddQualification;

public class JpaQualificationDao extends JpaDao<AddQualification, Long> implements QualificationDao {
	public JpaQualificationDao() {
		super(AddQualification.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AddQualification> findAll(Integer registrationId) {
		List<AddQualification> addQualification = null;
		try {
			final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
			final CriteriaQuery<AddQualification> criteriaQuery = builder.createQuery(AddQualification.class);

			Root<AddQualification> root = criteriaQuery.from(AddQualification.class);
			criteriaQuery.select(root).where(builder.equal(root.get("registrationId"), registrationId));

			addQualification = this.getEntityManager().createQuery(criteriaQuery).getResultList();
			return addQualification;
		} catch (Exception e) {
			return addQualification;
		}
	}

	@Override
	@Transactional
	public void updateQualificationsByCandidateId(Long id, List<AddQualification> education, Integer registrationId) {
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
	@Transactional(readOnly = true)
	public List<AddQualification> findAllAddQualification(Integer startingRow, Integer pagesize,
			Integer registrationId) {

		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<AddQualification> criteriaQuery = builder.createQuery(AddQualification.class);

		Root<AddQualification> root = criteriaQuery.from(AddQualification.class);
		criteriaQuery.select(root).where(builder.equal(root.get("registrationId"), registrationId));
		TypedQuery<AddQualification> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		typedQuery.setFirstResult(startingRow).setMaxResults(pagesize).getResultList().toString();
		return typedQuery.getResultList();
	}

	@Override
	public AddQualification find(Long id, Integer registrationId) {

		try {
			CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
			CriteriaQuery<AddQualification> createQuery = criteriaBuilder.createQuery(AddQualification.class);
			Root<AddQualification> from = createQuery.from(AddQualification.class);

			//Predicate and = criteriaBuilder.and(criteriaBuilder.equal(criteriaBuilder.equal(from.get("id"), id),
				//	criteriaBuilder.equal(from.get("registrationId"), registrationId)));
			Predicate and = criteriaBuilder.and(criteriaBuilder.equal(from.get("id"), id), criteriaBuilder.equal(from.get("registrationId"), registrationId));
			
			createQuery.where(and);
			return this.getEntityManager().createQuery(createQuery).getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
