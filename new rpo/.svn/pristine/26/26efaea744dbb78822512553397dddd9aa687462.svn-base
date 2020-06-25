package com.ojas.rpo.security.dao.plans;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Location;
import com.ojas.rpo.security.entity.Plans;
import com.ojas.rpo.security.entity.PlansVO;
import com.ojas.rpo.security.entity.Response;



public class JpaPlansDAOImpl extends JpaDao<Plans, Long> implements PlansDAO {

	public JpaPlansDAOImpl() {
		super(Plans.class);
	}

	@Override
	@Transactional(readOnly = true)
	public Response findAllPlans(Integer pageNo, Integer pagesize ,Long regId) {
		Response response = new Response();
		Integer totalRecords = 0;
		Integer startingRow = 0;
		if (pageNo == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNo - 1) * pagesize);
		}

		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Plans> criteriaQuery = builder.createQuery(Plans.class);

		Root<Plans> root = criteriaQuery.from(Plans.class);
		Predicate p  = builder.equal(root.get("registrationId"), regId);
		criteriaQuery.where(p);
		TypedQuery<Plans> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		int size = typedQuery.getResultList().size();
		List<Plans> resultList = typedQuery.setFirstResult(startingRow).setMaxResults(pagesize).getResultList();
		if(resultList.isEmpty()) {
			return new Response(ExceptionMessage.DataIsEmpty);
		}
		else {
			totalRecords = size;
			if(totalRecords==0) {
				response.setTotalPages(0);
				
			}
			else {
				Integer totalPages = totalRecords/pagesize;
				totalPages = totalRecords%pagesize>0 ? totalPages+1 : totalPages;
				response.setTotalPages(totalPages );
			}
			return response;
		}
		
	}
	
	
	@Override
	@Transactional
	public List<Plans> findById(Long id, Long regId) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Plans> cq = cb.createQuery(Plans.class);
		Root<Plans> location = cq.from(Plans.class);
		Predicate predicate = cb.and(cb.equal(location.get("id"), id), cb.equal(location.get("registrationId"), regId));
		cq.where(predicate);
		TypedQuery<Plans> createQuery = getEntityManager().createQuery(cq);
		return createQuery.getResultList();

	}

	

}
