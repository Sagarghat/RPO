package com.ojas.rpo.security.dao.candidate;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.BdmReq;
import com.ojas.rpo.security.entity.Candidate;
import com.ojas.rpo.security.entity.CandidateSearchList;
import com.ojas.rpo.security.entity.CandidateStatusDTO;
import com.ojas.rpo.security.entity.Employee;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.CandidateSearch;
import com.ojas.rpo.security.util.SearchTrackerList;

public interface CandidatelistDao extends Dao<Candidate, Long> {

	
	public Response findAllCandidateBasedOnRegId(CandidateSearch candidateSearch);

	List<String> chekduplicate(String mobile, String email, Long regId);

	Response getSummaryReport(Long bdmReq_id);

	boolean updateCandiate(CandidateStatusDTO statusDto, String status);

	boolean chekduplicate(String mobile, String email, String pancardNumber);

	List<Object[]> getMobileDetails(String mobile, String email, String pancardNumber);

	List<Candidate> getCandiateByRecurtierIdByStatus(Long recutierId, String status);

	List<Candidate> findAll(String status);

	List<Candidate> getCandiateByRequirementId(Long requiremnetId);

	Response getCandiateByRequirementId(Long requiremnetId, Integer pageNo, Integer pageSize, String sortingOrder,
			String sortingField, String searchText, String searchField);

	List<BdmReq> getRequiremenByCandiateId(Long candidateId);

	Map<String, Integer> getCandidateStatuCount(String status);

	Map<String, Integer> getCandidateStatusCountByRecruiter(String status);

	List<Candidate> getCandiateByRecurtierId(Long recutierId);

	Map<String, Integer> getCandidateStatusCountByRecruiterId(Long id, String status);

	int updatingStatus(Long id, String status, String offerStatus, Long reqid, Long userId);

	List<Candidate> getCandiateBySkillName(String skillName);

	int updatingOnBoardStatus(Long id, String status, String onBoardStatus, Date onboardeddate, String ctc,
			String reqId, Long userId);

	int confirmBoardStatus(Long id, String onBoardStatus, Date abscondeddate, String reqId, Long userId);

	Response getCandidatelistinExcel(SearchTrackerList searchTrackerList, Long clienId, Long reqid,
			Properties properties);

	double getInsurance(Employee employee, Double age);

	boolean updateCandiate(CandidateStatusDTO statusDto);

	public Candidate getCandidateByMobileNumber(String mobile, Long loginid);
}
