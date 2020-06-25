package com.ojas.rpo.security.dao.permissions;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.Feature;
import com.ojas.rpo.security.entity.InterviewType;
import com.ojas.rpo.security.entity.Permissions;
import com.ojas.rpo.security.util.FeatureSearch;
import com.ojas.rpo.security.util.PermissionSearch;

public class JpaPermissionsDao extends JpaDao<Permissions, Long> implements PermissionsDao {

	public JpaPermissionsDao() {
		super(Permissions.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Permissions> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Permissions> criteriaQuery = builder.createQuery(Permissions.class);

		Root<Permissions> root = criteriaQuery.from(Permissions.class);
		criteriaQuery.orderBy(builder.asc(root.get("id")));

		TypedQuery<Permissions> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	public List<Permissions> findAllPermissions(Integer startingRow, Integer pageSize) {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Permissions> criteriaQuery = builder.createQuery(Permissions.class);
		Root<Permissions> root = criteriaQuery.from(Permissions.class);
		criteriaQuery.orderBy(builder.asc(root.get("id")));
		TypedQuery<Permissions> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		typedQuery.setFirstResult(startingRow);
		typedQuery.setMaxResults(pageSize);
		return typedQuery.getResultList();
	}
	
	@Override
	public List<Permissions> getPermissionsByRole(PermissionSearch permissionSearch) {
		List<Object[]> featureList = null;
		Query query = null;
		Permissions permissions = null;
		StringBuilder sql = new StringBuilder();
		List<Permissions>  list =new ArrayList<Permissions>();
		if(permissionSearch.getRole().equalsIgnoreCase("SUPERADMIN") || permissionSearch.getRole().equalsIgnoreCase("ADMIN")) {
			sql.append(" select p.id,p.permissionName,p.flag from permissions p ");
		
		}else {
			sql.append(" select p.id,p.permissionName,p.flag from permissions p"
					+ " inner join mapperclass fp on p.id=fp.permissions_id  "
				+ " inner join user usr on usr.id=fp.feature_id where usr.id="+permissionSearch.getUserId()
				+ " and usr.reg_id="+permissionSearch.getRegId()+" and fp.feature_id ="+permissionSearch.getFeatureId());
		}
		try {
			query = getEntityManager().createNativeQuery(sql.toString());			
			featureList = query.getResultList();
			for (Object[] objects : featureList) {
				permissions = new Permissions();
				if (objects[0] != null)
					permissions.setId(Long.valueOf(objects[0] + ""));
				if (objects[1] != null)
					permissions.setPermissionName(String.valueOf(objects[1] + ""));
				if (objects[2] != null)
					permissions.setFlag(Boolean.valueOf(objects[2] + ""));
				list.add(permissions);
			}
		} catch (Exception e) {

		}
		return list;
	}

}
