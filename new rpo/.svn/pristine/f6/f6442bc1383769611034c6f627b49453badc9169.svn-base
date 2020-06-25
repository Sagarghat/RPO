package com.ojas.rpo.security.dao.typeOfAddres;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.Plans;
import com.ojas.rpo.security.entity.TypeOfAddress;

public class JpaTypeOfAddressDao extends JpaDao<TypeOfAddress, Long>  implements TypeOfAddressDao{

	public JpaTypeOfAddressDao() {
		super(TypeOfAddress.class);
		
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<TypeOfAddress> findByBillingAddress() {
		
		
		String hql = " FROM TypeOfAddress where typeofaddress in ('Billing','Shipping')";
		Query query = getEntityManager().createQuery(hql);

		return query.getResultList();
		// return users.listIterator().previous();
		
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<TypeOfAddress> findByOfficeAddress() {
		
		
		String hql = " FROM TypeOfAddress where typeofaddress in ('Office','Residence')";
		Query query = getEntityManager().createQuery(hql);

		return query.getResultList();
		// return users.listIterator().previous();
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<TypeOfAddress> findAllTypeOfAddress(Integer startingRow, Integer pagesize) {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<TypeOfAddress> criteriaQuery = builder.createQuery(TypeOfAddress.class);

		Root<TypeOfAddress> root = criteriaQuery.from(TypeOfAddress.class);
		

		TypedQuery<TypeOfAddress> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		System.out.println(typedQuery.setFirstResult(startingRow).setMaxResults(pagesize).getResultList().toString());
		return typedQuery.getResultList();
	}
	
	

}
