package com.ojas.rpo.security.dao.addcandidaterating;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.CandidateRating;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.CandidateJobRatingSearch;

public interface CandidateRatingDaoInf extends Dao<CandidateRating, Long> {

	Response findByJobsWithIdrating(CandidateJobRatingSearch candidateJobRatingSearch);

	Integer setRating(CandidateRating candidateRating);

	Long getId(CandidateRating candidateRating);

}
