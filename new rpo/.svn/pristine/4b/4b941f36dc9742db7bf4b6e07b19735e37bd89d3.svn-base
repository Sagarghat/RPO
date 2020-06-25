package com.ojas.rpo.security.dao.roles;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;
import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Roles;
import com.ojas.rpo.security.entity.Source;

public class RolesDaoImpl extends JpaDao<Roles, Long> implements RolesDao {

	public RolesDaoImpl() {
		super(Roles.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<Roles> findAll(Integer startingRow, Integer pageSize) {

		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Roles> criteriaQuery = builder.createQuery(Roles.class);
		Root<Roles> root = criteriaQuery.from(Roles.class);
		TypedQuery<Roles> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		typedQuery.setFirstResult(startingRow).setMaxResults(pageSize).getResultList().toString();
		return typedQuery.getResultList();
	}

}
