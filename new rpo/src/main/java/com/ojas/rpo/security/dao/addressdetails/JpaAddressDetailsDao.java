package com.ojas.rpo.security.dao.addressdetails;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.AddressDetails;

public class JpaAddressDetailsDao extends JpaDao<AddressDetails, Long> implements AddressDetailsDao {

	public JpaAddressDetailsDao() {
		super(AddressDetails.class);

	}

	@Override
	@Transactional(readOnly = true)
	public List<AddressDetails> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<AddressDetails> criteriaQuery = builder.createQuery(AddressDetails.class);
		Root<AddressDetails> root = criteriaQuery.from(AddressDetails.class);
		criteriaQuery.orderBy(builder.desc(root.get("id")));
		TypedQuery<AddressDetails> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	public List<AddressDetails> findBycpId(Long cpid) {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<AddressDetails> criteriaQuery = builder.createQuery(AddressDetails.class);
		Root<AddressDetails> root = criteriaQuery.from(AddressDetails.class);
		Predicate p = builder.equal(root.get("cid"), cpid);
		criteriaQuery.where(p);
		criteriaQuery.orderBy(builder.desc(root.get("id")));
		TypedQuery<AddressDetails> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	@Transactional
	public Boolean deleteByClientId( Long addressId) {
		Boolean isDeleted = false;
		Query query = this.getEntityManager().createNativeQuery("delete from addressdetails where  id=?");
		query.setParameter(1, addressId);
		int count = query.executeUpdate();
		if (count != 0)
			isDeleted = true;
		return isDeleted;
	}
}
