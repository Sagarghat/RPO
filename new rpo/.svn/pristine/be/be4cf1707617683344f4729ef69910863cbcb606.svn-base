package com.ojas.rpo.security.dao.location;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Location;
import com.ojas.rpo.security.entity.Response;

/**
 * 
 * @author Jyothi.Gurijala
 *
 */
public class JpaLocationDao extends JpaDao<Location, Long> implements LocationDao {
	public JpaLocationDao() {
		super(Location.class);
	}

	

	@Override
	@Transactional
	public List<Location> findById(Long id, Long regId) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Location> cq = cb.createQuery(Location.class);
		Root<Location> location = cq.from(Location.class);
		Predicate predicate = cb.and(cb.equal(location.get("id"), id), cb.equal(location.get("registrationId"), regId));
		cq.where(predicate);
		TypedQuery<Location> createQuery = getEntityManager().createQuery(cq);
		return createQuery.getResultList();

	}

	@Override
	public Response findAllLocations(Integer pageNo, Integer pageSize, Long regId) {
		
		
		Response response = null;
		Integer totalRecords = 0;
		Integer startingRow = 0;
		if (pageNo == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNo - 1) * pageSize);
		}
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Location> cq = cb.createQuery(Location.class);
		Root<Location> location = cq.from(Location.class);
		Predicate predicate = cb.equal(location.get("registrationId"), regId);
		cq.where(predicate);
		TypedQuery<Location> query = getEntityManager().createQuery(cq);
		int size = query.getResultList().size();
		List<Location> resultList = query.setFirstResult(startingRow).setMaxResults(pageSize).getResultList();
		if (resultList.isEmpty()) {
			
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {
			totalRecords = size;
			Integer totalPages = 1;
			if (totalRecords == 0) {
				response = new Response();
				response.setTotalPages(0);
			} else {
				totalPages = totalRecords / pageSize;

				totalPages = (totalRecords % pageSize > 0) ? totalPages + 1 : totalPages;

			}

			response = new Response(ExceptionMessage.StatusSuccess, resultList);
			response.setTotalPages(totalPages);
			response.setTotalRecords(totalRecords);
			return response;
		}

	}

 
} 
