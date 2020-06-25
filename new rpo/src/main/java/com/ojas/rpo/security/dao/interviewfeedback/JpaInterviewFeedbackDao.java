package com.ojas.rpo.security.dao.interviewfeedback;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.bcel.generic.IINC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.dao.CandidateStatusMaster.CandidateStatusMasterDaoInf;
import com.ojas.rpo.security.dao.InterviewDetails.InterviewDetailsDao;
import com.ojas.rpo.security.dao.candidate.CandidatelistDao;
import com.ojas.rpo.security.entity.Candidate;
import com.ojas.rpo.security.entity.CandidateStatusMaster;
import com.ojas.rpo.security.entity.InterviewDetails;
import com.ojas.rpo.security.entity.InterviewFeedback;

public class JpaInterviewFeedbackDao extends JpaDao<InterviewFeedback, Long> implements InterviewFeedbackDao {
	public JpaInterviewFeedbackDao() {
		super(InterviewFeedback.class);
	}
	
	@Autowired
	private CandidateStatusMasterDaoInf dao;
	
	@Autowired
	private InterviewDetailsDao intdao;
	@Autowired
	private CandidatelistDao cdao;

	

	@Override
	public InterviewFeedback getById(Long intId) {
		
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<InterviewFeedback> cq  = cb.createQuery(InterviewFeedback.class);
		Root<InterviewFeedback> myTable  = cq.from(InterviewFeedback.class);
		Predicate predicate = cb.equal(myTable.get("interviewId"), intId);
		cq.where(predicate);
		InterviewFeedback interviewFeedback = getEntityManager().createQuery(cq).getSingleResult();
		return interviewFeedback;
		
	}
	
	
	
	public Long getFeedbackIdByIntId(Long id) {
		 List resultList = getEntityManager()
		.createNativeQuery("select id from interviewfeedback where interviewId = "+id).getResultList();
		 if(!resultList.isEmpty())
		return Long.valueOf(resultList.get(0)+"");
		 else return null;
		
	}

	@Override
	@Transactional
	public void updateCandidateMapping( InterviewFeedback interviewFeedback) {
		
		String sql = "update candidateMapping set candidateStatus  = "+interviewFeedback.getcStatus().getId()+" where candidate_id = "
				+interviewFeedback.getCandidateid() +" and bdmReq_id = "+interviewFeedback.getRequirementId();
		
		getEntityManager().createNativeQuery(sql).executeUpdate();
		
	}
	
	
	
	
	
	
	
	
	


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.rpo.security.dao.JpaDao#findAll()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<InterviewFeedback> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<InterviewFeedback> criteriaQuery = builder.createQuery(InterviewFeedback.class);

		Root<InterviewFeedback> root = criteriaQuery.from(InterviewFeedback.class);
		criteriaQuery.orderBy(builder.desc(root.get("id")));

		TypedQuery<InterviewFeedback> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	@Transactional
	public void updateCandiate(Long candiateId, String status, Long reqId, Long userId) {
		// boolean result=false;
		Query q = getEntityManager().createNativeQuery(
				"update candidatemapping set candidateStatus=?, lastUpdatedDate=now() where candidate_id =? and bdmReq_id=? and mappedUser_id=? ");
		q.setParameter(1, status);
		q.setParameter(2, candiateId);
		q.setParameter(3, reqId);
		q.setParameter(4, userId);
		int i = q.executeUpdate();
		/*
		 * if(i>0){ return true; } return result;
		 */
	}

	@Override
	@Transactional
	public void updateInterviewDetails(Long candiateId, String status, String nameOftheRound, Long reqId, Long useid) {
		// boolean result=false;
		Query q = getEntityManager()
				.createNativeQuery("update interviewdetails set status='In Progress' , nameOfRound='" + nameOftheRound
						+ "' where candidate_id =" + candiateId + " and requirement_id=" + reqId + " and userId="
						+ useid);
		int i = q.executeUpdate();
		/*
		 * if(i>0){ return true; } return result;
		 */
	}

	@Override
	@Transactional(readOnly = true)
	public InterviewFeedback findById(Long candidateId) {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<InterviewFeedback> criteriaQuery = builder.createQuery(this.entityClass);

		Root<InterviewFeedback> root = criteriaQuery.from(this.entityClass);
		Path<Long> namePath = root.get("candidateid");
		criteriaQuery.where(builder.equal(namePath, candidateId));
		criteriaQuery.orderBy(builder.desc(root.get("date")));

		TypedQuery<InterviewFeedback> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		List<InterviewFeedback> users = typedQuery.getResultList();

		if (users.isEmpty()) {
			return null;
		}

		return users.iterator().next();
	
	}

	@Override
	public void removeInterviewFeedbackByCandidateId(Long id) {
		String sql = " DELETE FROM interviewfeedback  WHERE candidateid =" + id;
		Query query = getEntityManager().createNativeQuery(sql);
		query.executeUpdate();
	}



	



	





	

	

}