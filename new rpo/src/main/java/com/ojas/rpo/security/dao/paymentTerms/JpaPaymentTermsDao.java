package com.ojas.rpo.security.dao.paymentTerms;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.PaymentTerms;

public class JpaPaymentTermsDao extends JpaDao<PaymentTerms, Long> implements PaymentTermsDao {
	public JpaPaymentTermsDao() {
		super(PaymentTerms.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.rpo.security.dao.JpaDao#findAll()
	 */
	/*
	 * @Override
	 * 
	 * @Transactional(readOnly = true) public List<PaymentTerms> findAll() { final
	 * CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder(); final
	 * CriteriaQuery<PaymentTerms> criteriaQuery =
	 * builder.createQuery(PaymentTerms.class);
	 * 
	 * Root<PaymentTerms> root = criteriaQuery.from(PaymentTerms.class);
	 * 
	 * TypedQuery<PaymentTerms> typedQuery =
	 * this.getEntityManager().createQuery(criteriaQuery); return
	 * typedQuery.getResultList(); }
	 */

	@Override
	public List<PaymentTerms> findAll(Integer startingRow, Integer pagesize, Integer registrationId) {
		List<PaymentTerms> resultList = null;
		try {
			final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
			final CriteriaQuery<PaymentTerms> criteriaQuery = builder.createQuery(PaymentTerms.class);
			
			Root<PaymentTerms> root = criteriaQuery.from(PaymentTerms.class);
			criteriaQuery.select(root).where(builder.equal(root.get("registrationId"), registrationId));
			TypedQuery<PaymentTerms> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
			typedQuery.setFirstResult(startingRow).setMaxResults(pagesize).getResultList().toString();
			resultList = typedQuery.getResultList();
			return resultList;
		} catch (Exception e) {
			return resultList;
		}
	}

	@Override
	public List<PaymentTerms> findAll(Integer registrationId) {
		List<PaymentTerms> resultList = null;
		try {
			CriteriaBuilder criteriaBuilder = this.getEntityManager().getCriteriaBuilder();
			CriteriaQuery<PaymentTerms> createQuery = criteriaBuilder.createQuery(PaymentTerms.class);
			Root<PaymentTerms> from = createQuery.from(PaymentTerms.class);
			createQuery.select(from).where(criteriaBuilder.equal(from.get("registrationId"), registrationId));
			resultList = this.getEntityManager().createQuery(createQuery).getResultList();
			return resultList;
		} catch (Exception e) {
			return resultList;
		}
	}

	@Override
	public PaymentTerms find(Long id, Integer registrationId) {
		try {
			CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
			CriteriaQuery<PaymentTerms> createQuery = cb.createQuery(PaymentTerms.class);
			Root<PaymentTerms> from = createQuery.from(PaymentTerms.class);
			Predicate and = cb.and(cb.equal(from.get("id"), id), cb.equal(from.get("registrationId"), registrationId));
			createQuery.where(and);
			return this.getEntityManager().createQuery(createQuery).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
