package com.ojas.rpo.security.dao.location;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.Skill;
import com.ojas.rpo.security.entity.Source;

/**
 * 
 * @author Jyothi.Gurijala
 *
 */
public class JpaSkillDao extends JpaDao<Skill, Long> implements SkillDao {
	public JpaSkillDao() {
		super(Skill.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.rpo.security.dao.JpaDao#findAll()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<Skill> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Skill> criteriaQuery = builder.createQuery(Skill.class);

		Root<Skill> root = criteriaQuery.from(Skill.class);
		criteriaQuery.orderBy(builder.desc(root.get("date")));

		TypedQuery<Skill> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	@Transactional
	public void updateSkillsByCandidateId(Long id, List<Skill> skills) {

		if (null != skills) {
			Query skillDeleteQuery = this.getEntityManager()
					.createNativeQuery(" DELETE FROM skillcandidate WHERE candidate_ID = ? ");
			skillDeleteQuery.setParameter(1, id);
			skillDeleteQuery.executeUpdate();

			for (Skill skillData : skills) {
				Query addSkillQuery = this.getEntityManager()
						.createNativeQuery("INSERT INTO skillcandidate(candidate_ID,SKILL_ID) values(?,?)");
				addSkillQuery.setParameter(1, id);
				addSkillQuery.setParameter(2, skillData.getId());
				addSkillQuery.executeUpdate();
			}
		}

	}

	@Override
	public Response findAllSkills(Integer pageNum, Integer pagesize, Long regId) {
		Response response = null;
		Integer totalRecords = 0;
		Integer startingRow = 0;
		if (pageNum == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNum - 1) * pagesize);
		}

		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Skill> criteriaQuery = builder.createQuery(Skill.class);
		Root<Skill> root = criteriaQuery.from(Skill.class);
		Predicate predicate = builder.equal(root.get("registrationId"),regId );
		criteriaQuery.where(predicate);
		TypedQuery<Skill> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		List<Skill> list = typedQuery.getResultList();
		List<Skill> resultList = typedQuery.setFirstResult(startingRow).setMaxResults(pagesize).getResultList();

		if (list.isEmpty()) {
			return new Response(ExceptionMessage.DataIsEmpty);
		} else {
			totalRecords = list.size();
			Integer totalPages = 1;
			if (totalRecords == 0) {
				response = new Response();
				response.setTotalPages(0);
			} else {
				totalPages = totalRecords / pagesize;

				totalPages = (totalRecords % pagesize > 0) ? totalPages + 1 : totalPages;

				response = new Response(ExceptionMessage.StatusSuccess);
			}

			response.setTotalPages(totalPages);
			response.setTotalRecords(totalRecords);
			response.setResult(resultList);
			return response;
		}
	}

	@Override
	public List<Skill> find(Long id, Long regId) {
		CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Skill> query = builder.createQuery(Skill.class);
		Root<Skill> root = query.from(Skill.class);
		Predicate predicate = builder.and(builder.equal(root.get("id"), id),builder.equal(root.get("registrationId"),regId));
		query.where(predicate);
		 TypedQuery<Skill> typedQuery = this.getEntityManager().createQuery(query);
		return typedQuery.getResultList();
	}

}
