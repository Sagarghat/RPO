package com.ojas.rpo.security.dao.candidateReqMapping;

import java.util.Date;
import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.BdmReq;
import com.ojas.rpo.security.entity.Candidate;
import com.ojas.rpo.security.entity.CandidateData;
import com.ojas.rpo.security.entity.CandidateMapping;
import com.ojas.rpo.security.entity.CandidateMappingRequest;
import com.ojas.rpo.security.entity.Response;

public interface CandidateReqMappingDao extends Dao<CandidateMapping, Long> {

	CandidateMapping findMappedCandidate(Long candidateId, Long reqId, Long userId,Long regId);

	Response changeCandidateMapping(CandidateMapping candidateMapping);

	List<CandidateData> getCandidatesMappingByStatus(String status);
	
	Response compareCandidateAndRequirment(Candidate candidate,BdmReq requirements,Long regId);
	
	Response submitToCustomer(CandidateMappingRequest candidateMapping);
	
	Response reviewProfile(CandidateMappingRequest candidateMapping);
	
	int checkDuplicateMapping(Long candidateId, Long reqId, Long userId,Long regId);
	
	Long getStageOfInterviewStatusId(String status);
	
	Long getCandidateStatusId(String status);
	
	int updateStatusByCandidateId(Long id, Long statusId);
	
	Response getMappingDetails(CandidateMappingRequest candidateMapping);
	Response getMappingByStatus(CandidateMappingRequest candidateMapping);
	
	Response updateOfferRelease( CandidateMappingRequest candidateMapping) ;
	Response updateOfferStatus(CandidateMappingRequest candidateMapping);
	Response updateOnBoardStatus(CandidateMappingRequest candidateMapping);
	
	Date getlastUpdatedDate(CandidateMappingRequest candidateMapping);
	
	Response getCountByReq(CandidateMappingRequest candidateMapping);
	
	
	 
	
	

}
