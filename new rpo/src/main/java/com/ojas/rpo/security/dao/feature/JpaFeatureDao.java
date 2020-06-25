package com.ojas.rpo.security.dao.feature;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.AddContact;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Feature;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.FeatureSearch;

public class JpaFeatureDao extends JpaDao<Feature, Long> implements FeatureDao {

	public JpaFeatureDao() {
		super(Feature.class);
	}

	@Override
	@Transactional
	public Feature save(Feature entity) {
		return this.getEntityManager().merge(entity);
	}

	@Override
	public Response findAll(Integer pageNo, Integer pageSize, Long regId) {
		Response response = null;
		Integer totalRecords = 0;
		Integer totalPages = 1;
		Integer startingRow = 0;

		if (pageNo == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNo - 1) * pageSize);
		}

		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Feature> criteriaQuery = builder.createQuery(Feature.class);
		Root<Feature> root = criteriaQuery.from(Feature.class);
		Predicate predicate = builder.equal(root.get("registrationId"), regId);
		criteriaQuery.where(predicate);
		TypedQuery<Feature> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		List<Feature> list = typedQuery.getResultList();

		List<Feature> resultList = typedQuery.setFirstResult(startingRow).setMaxResults(pageSize).getResultList();
		if (list == null || list.isEmpty()) {
			response = new Response(ExceptionMessage.DataIsEmpty);
		} else {
			totalRecords = list.size();
			totalPages = 1;
			if (totalRecords == 0) {
				response = new Response();
				response.setTotalPages(0);
			} else {
				totalPages = totalRecords / pageSize;
				totalPages = (totalRecords % pageSize > 0) ? totalPages + 1 : totalPages;
			}
			response = new Response(ExceptionMessage.StatusSuccess);
		}
		response.setTotalPages(totalPages);
		response.setTotalRecords(totalRecords);
		response.setResult(resultList);
		return response;
	}

	@Override
	public List<Feature> getFeaturesByRole(FeatureSearch fearchSearch) {
		List<Object[]> featureList = null;
		Query query = null;
		Feature feature = null;
		StringBuilder sql = new StringBuilder();
		List<Feature> list = new ArrayList<Feature>();
		if (fearchSearch.getRole().equalsIgnoreCase("SUPERADMIN") || fearchSearch.getRole().equalsIgnoreCase("ADMIN")) {
			sql.append(" select f.id,f.featureName from feature f ");

		} else {
			sql.append(" select f.id,f.featureName from feature f inner join userfeatures u on f.id = u.featureId "
					+ " inner join user usr on usr.id=u.userId where usr.id=" + fearchSearch.getUserId()
					+ " and usr.reg_id=" + fearchSearch.getRegId());
		}
		try {
			query = getEntityManager().createNativeQuery(sql.toString());
			featureList = query.getResultList();
			for (Object[] objects : featureList) {
				feature = new Feature();
				if (objects[0] != null)
					feature.setId(Long.valueOf(objects[0] + ""));
				if (objects[1] != null)
					feature.setFeatureName(String.valueOf(objects[1] + ""));
				list.add(feature);
			}
		} catch (Exception e) {

		}
		return list;
	}

	@Override
	public List<Feature> find(Long id, Long regId) {
		CriteriaBuilder cb = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Feature> cq = cb.createQuery(Feature.class);
		Root<Feature> root = cq.from(Feature.class);
		Predicate predicate = cb.and(cb.equal(root.get("id"), id), cb.equal(root.get("registrationId"), regId));
		cq.where(predicate);
		TypedQuery<Feature> query = this.getEntityManager().createQuery(cq);

		return query.getResultList();
	}

}
