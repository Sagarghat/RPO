package com.ojas.rpo.security.dao.assesmentname;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.AssesmentName;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;

public class JpaAssesmentNameDao extends JpaDao<AssesmentName, Long> implements AssesmentNameDao {

	public JpaAssesmentNameDao() {
		super(AssesmentName.class);
	}

	@Override
	@Transactional
	public AssesmentName save(AssesmentName entity) {
		return this.getEntityManager().merge(entity);
	}

	@Override
	public Response getAll(Integer pageNo, Integer pageSize, Long regId) {

		Response r = new Response();
		int startingRow = 0;
		int totalRecords = 0;
		int totalPages = 0;
		if (pageNo == 1) {
			startingRow = 0;
		} else {
			startingRow = pageNo + 1;
		}

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<AssesmentName> cq = cb.createQuery(AssesmentName.class);
		Root<AssesmentName> root = cq.from(AssesmentName.class);
		Predicate p = cb.equal(root.get("registrationId"), regId);
		cq.where(p);
		TypedQuery<AssesmentName> typedQuery = getEntityManager().createQuery(cq);
		List<AssesmentName> list = typedQuery.getResultList();

		if (list.isEmpty()) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {
			totalRecords = list.size();
			typedQuery.setFirstResult(startingRow);
			typedQuery.setMaxResults(pageSize);
			List<AssesmentName> list2 = typedQuery.getResultList();
			totalPages = ((totalRecords % pageSize) > 1) ? totalRecords / pageSize + 1 : totalRecords / pageSize;
			r.setTotalPages(totalPages);
			r.setResult(list2);
			r.setStatus(ExceptionMessage.StatusSuccess);
			return r;
		}
	}

	@Override
	public AssesmentName getById(Long id, Long regId) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<AssesmentName> cq = cb.createQuery(AssesmentName.class);
		Root<AssesmentName> root = cq.from(AssesmentName.class);
		Predicate p = cb.and(cb.equal(root.get("id"), id), cb.equal(root.get("registrationId"), regId));
		cq.where(p);
		List<AssesmentName> list = getEntityManager().createQuery(cq).getResultList();
		if (list.isEmpty())
			return null;
		else {
			return list.get(0);
		}
	}

	@Override
	public boolean check(AssesmentName name) {
		Query query = getEntityManager().createNativeQuery("select id from  AssesmentName where registrationId =" + " "
				+ name.getRegistrationId() + " and  assesmentName = '" + name.getAssesmentName()+"'");
		List resultList = query.getResultList();
		if (resultList.isEmpty())
			return true; 
		else
			return false;

	}

}
