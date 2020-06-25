package com.ojas.rpo.security.dao.menu;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.Menu;

public class MenuDaoImpl extends JpaDao<Menu, Long> implements MenuDao {

	public MenuDaoImpl() {
		super(Menu.class);
	}

	@Override
	public boolean chekduplicate(String menuName) {
		boolean result = false;
		try {
			Query query = getEntityManager().createNativeQuery("select menuName FROM menu WHERE menuName=?");
			query.setParameter(1, menuName);
			@SuppressWarnings("unchecked")
			List<String> results = query.getResultList();
			List<String> listData = new ArrayList<String>();

			for (String cName : results) {
				if (menuName.equalsIgnoreCase(cName)) {
					listData.add(menuName);
				}
			}
			if (listData.isEmpty()) {
				result = false;
			} else {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	
	@Override
	@Transactional
	public List<Menu> findAllMenu(Integer startingRow, Integer pagesize) {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Menu> criteriaQuery = builder.createQuery(Menu.class);
		Root<Menu> root = criteriaQuery.from(Menu.class);
		criteriaQuery.orderBy(builder.desc(root.get("id")));
		TypedQuery<Menu> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		System.out.println(typedQuery.setFirstResult(startingRow).setMaxResults(pagesize).getResultList().toString());
		return typedQuery.getResultList();
	}

}
