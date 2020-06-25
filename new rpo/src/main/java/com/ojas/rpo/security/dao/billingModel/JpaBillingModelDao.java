package com.ojas.rpo.security.dao.billingModel;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.BillingModel;

/**
 * 
 * @author Jyothi.Gurijala
 *
 */
public class JpaBillingModelDao extends JpaDao<BillingModel, Long> implements BillingModelDao {
	public JpaBillingModelDao() {
		super(BillingModel.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.rpo.security.dao.JpaDao#findAll()
	 */
	@Override 
	@Transactional(readOnly = true)
	public List<BillingModel> findAllData(Integer pageNo, Integer pageSize) {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<BillingModel> criteriaQuery = builder.createQuery(BillingModel.class);

		Root<BillingModel> root = criteriaQuery.from(BillingModel.class);

		TypedQuery<BillingModel> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		typedQuery.setFirstResult(pageNo);
		typedQuery.setMaxResults(pageSize);
		return typedQuery.getResultList();
		
	
		
	}

	@Override
	@Transactional
	public int deleteById(Long id) {
		String sql = "delete from testing.billingmodel where id = "+id;
		int i = this.getEntityManager().createNativeQuery(sql).executeUpdate();
		return i;
	}

	

}
