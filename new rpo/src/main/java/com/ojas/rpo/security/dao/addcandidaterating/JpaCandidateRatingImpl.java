package com.ojas.rpo.security.dao.addcandidaterating;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.CandidateRating;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.transfer.CandidateJobRatingSearchDTO;
import com.ojas.rpo.security.util.CandidateJobRatingSearch;

public class JpaCandidateRatingImpl extends JpaDao<CandidateRating, Long> implements CandidateRatingDaoInf {

	public JpaCandidateRatingImpl() {
		super(CandidateRating.class);
	}

	@Override
	public Response findByJobsWithIdrating(CandidateJobRatingSearch candidateJobRatingSearch) {
		Response response = null;

		StringBuilder stringBuilder = new StringBuilder(
				"select cr.id,job.nameOfRequirement,cr.rating,cr.comments,cr.candidateId,cr.lastUpdateDate,us.id as userId,job.id as bdmId "
						+ " from CandidateRating as cr inner join bdmreq as job on job.id=cr.bdmId"
						+ " inner join candidate as cd on cd.id=cr.candidateId inner join user as us on us.id=cr.userId where cr.registrationId=?");

		if (candidateJobRatingSearch.getCandidateId() != null) {
			stringBuilder.append(" and cd.id=" + candidateJobRatingSearch.getCandidateId());
		}

		if (candidateJobRatingSearch.getBdmId() != null) {
			stringBuilder.append(" and job.id=" + candidateJobRatingSearch.getBdmId());
		}
		if (candidateJobRatingSearch.getUserId() != null) {
			stringBuilder.append(" and us.id=" + candidateJobRatingSearch.getUserId());
		}
		try {
			Query createNativeQuery = getEntityManager().createNativeQuery(stringBuilder.toString());
			createNativeQuery.setParameter(1, candidateJobRatingSearch.getRegistrationId());

			@SuppressWarnings("unchecked")
			List<Object[]> resultList = createNativeQuery.getResultList();
			List<CandidateJobRatingSearchDTO> candidateJobRatingSearchDTOList = new ArrayList<>();

			if (resultList.isEmpty()) {

				response = new Response(ExceptionMessage.DataIsEmpty);
			} else {
				for (Object[] objects : resultList) {

					CandidateJobRatingSearchDTO candidateJobRatingSearchDTO = new CandidateJobRatingSearchDTO();
					if (objects[0] != null) {
						candidateJobRatingSearchDTO.setId(Long.valueOf(String.valueOf(objects[0])));

					}
					if (objects[1] != null) {
						candidateJobRatingSearchDTO.setJob(String.valueOf(objects[1]));

					}
					if (objects[2] != null) {
						candidateJobRatingSearchDTO.setRating(Long.valueOf(String.valueOf(objects[2])));

					}
					if (objects[3] != null) {
						candidateJobRatingSearchDTO.setComments(String.valueOf(objects[3]) + " ");

					}
					if (objects[4] != null) {
						candidateJobRatingSearchDTO.setCandidateId(Long.valueOf(String.valueOf(objects[4])));
					}
					if (objects[5] != null) {
						candidateJobRatingSearchDTO.setLastUpdateDate((Date) (objects[5]));

					}
					candidateJobRatingSearchDTOList.add(candidateJobRatingSearchDTO);

				}

				response = new Response(ExceptionMessage.OK, candidateJobRatingSearchDTOList);

			}
		} catch (PersistenceException pe) {

			response = new Response(ExceptionMessage.Exception, "500",
					"Unable to Retrieve Customers List. " + " " + pe.getLocalizedMessage());
		} catch (Exception e) {

			response = new Response(ExceptionMessage.Exception, "500",
					" Unable to Search Requirements " + e.fillInStackTrace());
		}
		return response;
	}

	@Override
	@Transactional
	public Integer setRating(CandidateRating candidateRating) {

		String sql = "update  candidate set rating=(SELECT CandidateRating.rating FROM testing.CandidateRating  where CandidateRating.registrationId=? "
				+ " and CandidateRating.userId=? and CandidateRating.candidateId=? order by lastUpdateDate desc limit 1) where id=?";
		return this.getEntityManager().createNativeQuery(sql).setParameter(1, candidateRating.getRegistrationId())
				.setParameter(2, candidateRating.getUserId()).setParameter(3, candidateRating.getCandidateId())
				.setParameter(4, candidateRating.getCandidateId()).executeUpdate();

	}

	public Long getId(CandidateRating candidateRating) {

		String query = "SELECT id FROM testing.candidaterating where candidateId=? and registrationId=? "
				+ "and rating=? and userId=? and comments='" + candidateRating.getComments()
				+ "' order by lastUpdateDate desc limit 1";
		String id = this.getEntityManager().createNativeQuery(query).setParameter(1, candidateRating.getCandidateId())
				.setParameter(2, candidateRating.getRegistrationId()).setParameter(3, candidateRating.getRating())
				.setParameter(4, candidateRating.getUserId()).getSingleResult().toString();

		return Long.valueOf(id);
	}

}
