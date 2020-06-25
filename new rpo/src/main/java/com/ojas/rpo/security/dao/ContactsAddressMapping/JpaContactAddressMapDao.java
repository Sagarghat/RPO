package com.ojas.rpo.security.dao.ContactsAddressMapping;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.AddressDetails;
import com.ojas.rpo.security.entity.ContactAddressDetails;

public class JpaContactAddressMapDao extends JpaDao<ContactAddressDetails, Long> implements ContactAddressMapDao {

	public JpaContactAddressMapDao() {
		super(ContactAddressDetails.class);

	}

	

	@Override
	public List<ContactAddressDetails> findBycpId(Long cpid) {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<ContactAddressDetails> criteriaQuery = builder.createQuery(ContactAddressDetails.class);
		Root<ContactAddressDetails> root = criteriaQuery.from(ContactAddressDetails.class);
		Predicate p = builder.equal(root.get("cid"), cpid);
		criteriaQuery.where(p);
		criteriaQuery.orderBy(builder.desc(root.get("id")));
		TypedQuery<ContactAddressDetails> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	@Transactional
	public Boolean deleteById(Long addressId) {
		Boolean isDeleted = false;
		Query query = this.getEntityManager().createNativeQuery("delete from contactaddressdetails where  id=?");
		query.setParameter(1, addressId);
		int count = query.executeUpdate();

		if (count != 0)
			isDeleted = true;

		return isDeleted;
	}



	@Override
	public List<ContactAddressDetails> findById(Long id) {
		Query query = this.getEntityManager().createNativeQuery("select * from contactaddressdetails where  id=?");
		query.setParameter(1, id);
	  

		return query.getResultList();
	}

}
