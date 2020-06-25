package com.ojas.rpo.security.dao.candidate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.ojas.rpo.security.certificatenames.CertificateTypeDao;
import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.dao.Qualification.QualificationDao;
import com.ojas.rpo.security.dao.candidateReqMapping.CandidateReqMappingDao;
import com.ojas.rpo.security.dao.location.SkillDao;
import com.ojas.rpo.security.entity.BdmReq;
import com.ojas.rpo.security.entity.Candidate;
import com.ojas.rpo.security.entity.CandidateSearchList;
import com.ojas.rpo.security.entity.CandidateStatusDTO;
import com.ojas.rpo.security.entity.Employee;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.SummaryReport;
import com.ojas.rpo.security.transfer.CandidateListTransfer;
import com.ojas.rpo.security.util.CandidateSearch;
import com.ojas.rpo.security.util.SearchTrackerList;

/**
 * 
 * @author Jyothi.Gurijala
 *
 */
public class JpaCandidateDao extends JpaDao<Candidate, Long> implements CandidatelistDao {
	public JpaCandidateDao() {
		super(Candidate.class);
	}

	@Autowired
	private QualificationDao qualificationDao;

	@Autowired
	private SkillDao skillDao;

	@Autowired
	private CertificateTypeDao certficateDao;

	@Value("${fileUploadPath}")
	private String excelTrackerPath;

	@Value("${excelFile}")
	private String excelFilePath;
	@Autowired
	private CandidateReqMappingDao canReqDao;

	private Workbook book;

	@Override
	@Transactional
	public boolean updateCandiate(CandidateStatusDTO statusDto, String status) {
		boolean result = false;
		Query q = getEntityManager().createNativeQuery(
				"update candidatemapping set candidateStatus=?, lastUpdatedDate=now() where candidate_id =? and bdmReq_id=? and mappedUser_id=?");
		q.setParameter(1, status);
		q.setParameter(2, statusDto.getCandidateId());
		q.setParameter(3, statusDto.getRequirementId());
		q.setParameter(4, statusDto.getLoginId());
		int i = q.executeUpdate();
		if (i > 0) {
			return true;
		}
		return result;
	}

	@Override
	@Transactional
	public boolean chekduplicate(String mobile, String email, String pancardNumber) {

		Query query = getEntityManager()
				.createQuery("SELECT mobile,email,pancardNumber FROM Candidate  WHERE mobile=? OR email=?");

		query.setParameter(1, mobile);
		query.setParameter(2, email);

		@SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();

		if (results.isEmpty()) {
			return false;
		} else {
			return true;

		}
	}

	@Override
	@Transactional
	public List<Object[]> getMobileDetails(String mobile, String email, String pancardNumber) {
		// boolean result = false;
		RestTemplate restTemplate = new RestTemplate();

		Query query = getEntityManager()
				.createQuery("SELECT id,mobile,email,pancardNumber FROM Candidate  WHERE mobile=? OR email=?");

		query.setParameter(1, mobile);
		query.setParameter(2, email);

		@SuppressWarnings("unchecked")
		List<Object[]> results = query.getResultList();

		return results;

	}

	public Candidate getCandidateByMobileNumber(String mobile, Long id) {

		Candidate cand = null;
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Candidate> criteriaQuery = builder.createQuery(this.entityClass);

		Root<Candidate> root = criteriaQuery.from(this.entityClass);
		Path<String> namePath = root.get("mobile");
		criteriaQuery.where(builder.equal(namePath, mobile));

		TypedQuery<Candidate> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		List<Candidate> users = typedQuery.getResultList();

		if (users.size() > 0) {
			cand = (Candidate) users.get(0);

		}
		return cand;
	}

	@Transactional
	public Response getAllCandidatesByAddedPerson(Long id, Integer pageNo, Integer pageSize, String sortingOrder,
			String sortingField, String searchText, String searchField) {

		List<CandidateListTransfer> result = new ArrayList<CandidateListTransfer>();
		Response response = null;
		Integer totalRecords = 0;
		Query query = null;
		Query query1 = null;
		StringBuilder sql = new StringBuilder(
				"select concat(can.firstName,' ',can.lastName) AS candidateName,can.email,can.mobile,can.skypeId,can.state,can.totalExperience,can.id,"
						+ "can.submissionDate,can.statusLastUpdatedDate from candidate can  "
						+ "  where  can.user_id = ? and  id not in(select candidate_id from candidatemapping where onBoardedStatus='on Boarded')");

		StringBuilder countQuery = new StringBuilder("select count(*) from candidate can "
				+ "  where  can.user_id = ? and id not in(select candidate_id from candidatemapping where onBoardedStatus='on Boarded')");

		if (null != sortingOrder && sortingOrder.equalsIgnoreCase("DESC")) {
			sortingOrder = "ASC";
		} else {
			sortingOrder = "DESC";
		}

		if (null != sortingField && !sortingField.equalsIgnoreCase("undefined") && !sortingField.isEmpty()) {
			if (sortingField.equalsIgnoreCase("candidateName") || "candidateName".contains(sortingField)) {
				sortingField = "concat(can.firstName,' ',can.lastName)";
			} else if (sortingField.equalsIgnoreCase("email") || "email".contains(sortingField)) {
				sortingField = "can.email";
			} else if ((sortingField.equalsIgnoreCase("submissionDate")
					|| sortingField.equalsIgnoreCase("submissionDate"))) {
				sortingField = "can.submissionDate";
			} else if (sortingField.equalsIgnoreCase("totalExperience") || "totalExperience".contains(sortingField)) {
				sortingField = "can.totalExperience";
			} else if (sortingField.equalsIgnoreCase("id")) {
				sortingField = "can.id";
			}

		} else {
			sortingField = "can.statusLastUpdatedDate";
		}

		if (null != searchField && !searchField.equalsIgnoreCase("undefined") && !searchField.isEmpty()) {
			if (searchField.equalsIgnoreCase("candidateName") || "candidateName".contains(searchField)) {
				searchField = "concat(can.firstName,' ',can.lastName)";
			} else if (searchField.equalsIgnoreCase("email") || "email".contains(searchField)) {
				searchField = "can.email";
			} else if (searchField.equalsIgnoreCase("totalExperience") || "totalExperience".contains(searchField)) {
				searchField = "can.totalExperience";
			}

		}

		int startingRow = 0;
		if (pageNo == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNo - 1) * pageSize);
		}

		if (null != searchField && null != searchText && !searchField.equals("undefined")
				&& !searchText.equals("undefined") && !searchText.isEmpty()) {
			sql.append(" AND " + searchField + " LIKE '%" + searchText + "%' ORDER BY " + sortingField + " "
					+ sortingOrder + " LIMIT " + startingRow + "," + pageSize);
		} else {
			sql.append(" ORDER BY " + sortingField + " " + sortingOrder + " LIMIT " + startingRow + "," + pageSize);
		}

		try {
			query = this.getEntityManager().createNativeQuery(sql.toString());
			query.setParameter(1, id);
			List<Object[]> results = query.getResultList();
			query1 = this.getEntityManager().createNativeQuery(countQuery.toString());
			query1.setParameter(1, id);
			totalRecords = Integer.parseInt(query1.getSingleResult().toString());
			if (results.isEmpty()) {
				response = new Response(ExceptionMessage.DataIsEmpty, "No Candidates Found");
			} else {
				for (Object[] can : results) {
					CandidateListTransfer candidate = new CandidateListTransfer();
					candidate.setCandidateName((String) can[0]);
					candidate.setEmail((String) can[1]);
					candidate.setMobile((String) can[2]);
					candidate.setSkypeId((String) can[3]);
					candidate.setState((String) can[4]);
					candidate.setTotalExperience((can[5].toString()));
					candidate.setCandidateId(Long.valueOf(can[6].toString()));
					candidate.setSubmissionDate((Date) (can[7]));
					candidate.setStatusLastUpdatedDate((Date) (can[8]));
					result.add(candidate);
				}

				response = new Response(result, ExceptionMessage.OK);
				// totalRecords = results.size();
				response.setTotalRecords(totalRecords);

				if (totalRecords == 0) {
					response.setTotalRecords(totalRecords);
					response.setTotalPages(0);
				}

				if ((totalRecords > 0) && (Integer.valueOf(totalRecords) <= pageSize)) {
					response.setTotalPages(1);
				} else {
					Integer totalPages = Integer.valueOf(totalRecords) / pageSize;
					totalPages = (totalPages == 0) ? totalPages : totalPages + 1;
					response.setTotalPages(totalPages);
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "500",
					"Unable to Retrieve Requirements due to following Error : " + e.getMessage());
		}
		return response;
	}

	@Override
	public List<Candidate> getCandiateByRecurtierIdByStatus(Long recutierId, String status) {
		// TODO Auto-generated method stub

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Candidate> cq = cb.createQuery(Candidate.class);
		Root<Candidate> e = cq.from(Candidate.class);
		Join<Candidate, Candidate> r = e.join("user", JoinType.LEFT);
		Predicate predicates = cb.and(cb.equal(r.get("id"), recutierId), cb.equal(e.get("status"), status));

		cq.where(predicates);
		TypedQuery<Candidate> tq = getEntityManager().createQuery(cq);
		List<Candidate> resultList = tq.getResultList();
		return resultList;

	}

	@Override
	@Transactional(readOnly = true)
	public List<Candidate> findAll(String status) {
		final CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Candidate> criteriaQuery = builder.createQuery(Candidate.class);

		Root<Candidate> root = criteriaQuery.from(Candidate.class);
		Predicate p = builder.notEqual(root.get("candidateStatus"), "Created");
		criteriaQuery.where(p);
		criteriaQuery.orderBy(builder.desc(root.get("date")));

		TypedQuery<Candidate> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	public List<Candidate> getCandiateByRequirementId(Long requiremnetId) {
		Query query = null;
		List<Candidate> requirementsList = null;

		query = getEntityManager().createQuery(
				"select req.candidate from CandidateMapping req where req.bdmReq.id = ? order by req.id desc");
		query.setParameter(1, requiremnetId);
		requirementsList = query.getResultList();

		return requirementsList;

	}

	public Response getAllInterviewCandidateByPostingTittle(Long reg_Id, Integer pageNo, Integer pageSize) {
		Response response = null;
		Integer totalRecords = 0;
		System.out.println(reg_Id);
		String sql = "SELECT c.id, CONCAT(c.firstName,c.lastName) AS fullname, c.city, cm.candidateStatus,  cm.submissionDate,c.candidateSource, c.email "
				+ "FROM candidatemapping AS cm INNER JOIN candidate AS c ON cm.candidate_id=c.id INNER JOIN USER AS u "
				+ "ON cm.bdmReq_id=u.id WHERE u.reg_id=" + reg_Id;
		try {
			Query createNativeQuery = this.getEntityManager().createNativeQuery(sql);

			int startingRow = 0;
			if (pageNo == 1) {
				startingRow = 0;
			} else {
				startingRow = ((pageNo - 1) * pageSize);
			}
			totalRecords = createNativeQuery.getResultList().size();
			createNativeQuery.setFirstResult(startingRow);
			createNativeQuery.setMaxResults(pageSize);

			List<Object[]> resultList = createNativeQuery.getResultList();
			List<CandidateListTransfer> candidateListTransfersList = new ArrayList<>();
			if (resultList.isEmpty()) {
				response = new Response(ExceptionMessage.DataIsEmpty, "No Content Found");

			} else {
				for (Object[] object : resultList) {
					CandidateListTransfer candidateListTransfer = new CandidateListTransfer();
					candidateListTransfer.setCandidateId(Long.valueOf(String.valueOf(object[0])));
					candidateListTransfer.setCandidateName(String.valueOf(object[1]));
					// candidateListTransfer.setCity(String.valueOf(object[2]));
					candidateListTransfer.setCandidateStatus(String.valueOf(object[3]));
					// Date date = new
					// SimpleDateFormat("yyyy-mm-dd").parse(String.valueOf(object[4]));
					// candidateListTransfer.setSubmissionDate((date));
					// candidateListTransfer.setSource(String.valueOf(object[5]));
					candidateListTransfer.setEmail(String.valueOf(object[6]));
					candidateListTransfersList.add(candidateListTransfer);
				}
				response = new Response(ExceptionMessage.OK, candidateListTransfersList);
				response.setTotalRecords(totalRecords);
				response.setStatus2("200");
			}

			if (totalRecords == 0) {
				response.setTotalPages(0);
			} else {
				Integer totalPages = Integer.valueOf(totalRecords) / pageSize;
				totalPages = (totalRecords % pageSize > 0) ? totalPages + 1 : totalPages;
				response.setTotalPages(totalPages);
			}
		} catch (PersistenceException pe) {
			response = new Response(ExceptionMessage.Exception, "500",
					"Unable to Retrieve contact List. " + " " + pe.getLocalizedMessage());
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "500",
					" Unable Retrieve contact List " + e.getLocalizedMessage());
		}
		return response;
	}

	@Override
	public Response getCandiateByRequirementId(Long requiremnetId, Integer pageNo, Integer pageSize,
			String sortingOrder, String sortingField, String searchText, String searchField) {
		Response response = null;
		Integer totalRecords = 0;
		/*
		 * query = getEntityManager().createQuery(
		 * "select req.candidate from CandidateMapping req where req.bdmReq.id = ? order by req.id desc"
		 * ); query.setParameter(1, requiremnetId); requirementsList =
		 * query.getResultList();
		 */

		StringBuilder sql = new StringBuilder(
				"SELECT c.id,concat(c.firstName,' ',c.lastName), c.submittionDate, cm.candidateStatus, c.email, c.mobile FROM `candidatemapping` cm INNER JOIN `candidate` c ON cm.candidate_id=c.id WHERE cm.bdmReq_id = ? ");
		StringBuilder countSql = new StringBuilder(
				"SELECT count(*) FROM `candidatemapping` cm INNER JOIN `candidate` c ON cm.candidate_id=c.id WHERE cm.bdmReq_id = ? ");

		if (null != sortingOrder && sortingOrder.equalsIgnoreCase("ASC")) {
			sortingOrder = "ASC";
		} else {
			sortingOrder = "DESC";
		}

		if (null != sortingField && !sortingField.equalsIgnoreCase("undefined") && !sortingField.isEmpty()) {
			if (sortingField.equalsIgnoreCase("candidateName")) {
				sortingField = "concat(c.firstName,' ',c.lastName)";
			}
			if (sortingField.equalsIgnoreCase("candidateStatus")) {
				sortingField = "cm.candidateStatus";
			}
			if (sortingField.equalsIgnoreCase("c.submissionDate")) {
				sortingField = "c.submittionDate";
			}
			if (sortingField.equalsIgnoreCase("c.email")) {
				sortingField = "email";
			}
		} else {
			sortingField = "c.id";
		}

		if (null != searchField && !searchField.equalsIgnoreCase("undefined") && !searchField.isEmpty()) {
			if (searchField.equalsIgnoreCase("candidateName")) {
				searchField = "concat(c.firstName,' ',c.lastName)";
			}
			if (searchField.equalsIgnoreCase("candidateStatus")) {
				searchField = "c.candidateStatus";
			}
			if (searchField.equalsIgnoreCase("c.email")) {
				searchField = "email";
			}
		}

		int startingRow = 0;
		if (pageNo == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNo - 1) * pageSize);
		}

		if (null != searchField && null != searchText && !searchField.equals("undefined")
				&& !searchText.equals("undefined") && !searchText.isEmpty()) {
			sql.append(" AND " + searchField + " LIKE '%" + searchText + "%' ORDER BY " + sortingField + " "
					+ sortingOrder + " LIMIT " + startingRow + "," + pageSize);
		} else {
			sql.append(" ORDER BY " + sortingField + " " + sortingOrder + " LIMIT " + startingRow + "," + pageSize);
		}

		try {
			Query query = this.getEntityManager().createNativeQuery(sql.toString());
			query.setParameter(1, requiremnetId);
			Query countQuery = this.getEntityManager().createNativeQuery(countSql.toString());
			countQuery.setParameter(1, requiremnetId);

			List<Object[]> results = query.getResultList();
			List<CandidateListTransfer> result = new ArrayList<CandidateListTransfer>();
			if (results.isEmpty()) {
				response = new Response(ExceptionMessage.DataIsEmpty, "No Requirements Found");
			} else {
				for (Object[] data : results) {
					CandidateListTransfer candidate = new CandidateListTransfer();

					if (null != data[0]) {
						candidate.setCandidateId(Long.valueOf(data[0].toString()));
					}
					if (null != data[1]) {
						candidate.setCandidateName(data[1].toString());
					}
					if (null != data[2]) {
						candidate.setSubmissionDate((Date) data[2]);
					}
					if (null != data[3]) {
						candidate.setCandidateStatus(data[3].toString());
					}
					if (null != data[4]) {
						candidate.setEmail(data[4].toString());
					}
					if (null != data[5]) {
						candidate.setMobile(data[5].toString());
					}

					result.add(candidate);
				}
				response = new Response(ExceptionMessage.OK, result);
			}
			totalRecords = Integer.valueOf(countQuery.getSingleResult().toString());
			response.setTotalRecords(totalRecords);

			if (totalRecords == 0) {
				response.setTotalPages(0);
			} else {
				Integer totalPages = Integer.valueOf(totalRecords) / pageSize;
				totalPages = (totalPages == 0) ? totalPages : totalPages + 1;
				response.setTotalPages(totalPages);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "500",
					"Unable Retrieve Candidates due to : " + e.getMessage());
		}

		return response;

	}

	@Override
	public List<BdmReq> getRequiremenByCandiateId(Long candidateId) {
		// TODO Auto-generated method stub
		Query query = null;
		List<BdmReq> requirementsList = null;

		query = getEntityManager().createQuery(
				"select req.bdmReq from CandidateMapping req where req.candidate.id = ? order by req.id desc");
		query.setParameter(1, candidateId);
		requirementsList = query.getResultList();

		return requirementsList;

	}

	@Override
	public List<Candidate> getCandiateByRecurtierId(Long recutierId) {
		// TODO Auto-generated method stub

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Candidate> cq = cb.createQuery(Candidate.class);
		Root<Candidate> e = cq.from(Candidate.class);
		Join<Candidate, Candidate> r = e.join("user", JoinType.LEFT);
		Predicate p = cb.equal(r.get("id"), recutierId);
		cq.where(p);
		TypedQuery<Candidate> tq = getEntityManager().createQuery(cq);
		return tq.getResultList();

	}

	@Override
	public List<Candidate> getCandiateBySkillName(String skillName) {
		// TODO Auto-generated method stub

		Query query = getEntityManager()
				.createNativeQuery("select *  from " + " candidate c , skill s , skillcandidate sc where s.skillName="
						+ " ? and c.id = sc.candidate_ID " + " and c.id = sc.SKILL_ID ");
		query.setParameter(1, skillName);
		List<Object[]> results = query.getResultList();
		return null;

	}

	@Override
	public Map<String, Integer> getCandidateStatuCount(String status) {

		Query q = getEntityManager().createNativeQuery("select candidateStatus ,count(*) from Candidate where status= '"
				+ status + "' group by candidateStatus");

		Map<String, Integer> map = new HashMap<String, Integer>();
		List<Object[]> results = q.getResultList();

		for (Object obj[] : results) {

			map.put(obj[0].toString(), new Integer(obj[1].toString()));

		}

		return map;
	}

	@Override
	public Map<String, Integer> getCandidateStatusCountByRecruiter(String status) {

		Query q = getEntityManager().createNativeQuery("SELECT " + "    candidateStatus, COUNT(*), us.name " + "FROM "
				+ "    Candidate can , User us where can.user_id=us.id "
				+ "GROUP BY candidateStatus ,us.name and can.status='" + status + "'");

		Map<String, Integer> map = new HashMap<String, Integer>();
		List<Object[]> results = q.getResultList();

		for (Object obj[] : results) {

			map.put(obj[0].toString(), new Integer(obj[1].toString()));

		}

		return map;
	}

	@Override
	public Map<String, Integer> getCandidateStatusCountByRecruiterId(Long id, String status) {

		Query q = getEntityManager().createNativeQuery("SELECT " + "    candidateStatus, COUNT(*), us.name " + "FROM "
				+ "    Candidate can , User us where can.user_id=" + id
				+ " GROUP BY candidateStatus ,us.name and can.status='" + status + "'");

		Map<String, Integer> map = new HashMap<String, Integer>();
		List<Object[]> results = q.getResultList();

		for (Object obj[] : results) {

			map.put(obj[0].toString(), new Integer(obj[1].toString()));

		}

		return map;
	}

	@Override
	@Transactional
	public int updatingStatus(Long id, String status, String offerStatus, Long reqid, Long userId) {
		int i = 0;
		if (status != null) {
			Query q = getEntityManager().createNativeQuery(
					"update candidatemapping set candidateStatus=? , offereStatus =?, lastUpdatedDate=now()  where candidate_id =?  and mappedUser_id=? and bdmReq_id=?");
			q.setParameter(1, status);
			q.setParameter(2, offerStatus);
			q.setParameter(3, id);

			q.setParameter(4, userId);
			q.setParameter(5, reqid);
			i = q.executeUpdate();
		} else {
			Query q = getEntityManager().createNativeQuery(
					"update candidatemapping set  offereStatus =?, lastUpdatedDate=now() where candidate_id =? and mappedUser_id=? and bdmReq_id=?");
			q.setParameter(1, offerStatus);
			q.setParameter(2, id);

			q.setParameter(3, userId);
			q.setParameter(4, reqid);
			i = q.executeUpdate();
		}

		return i;
	}

	@Override
	@Transactional
	public int updatingOnBoardStatus(Long id, String status, String onBoardStatus, Date onboardeddate, String ctc,
			String reqId, Long userId) {
		int i = 0;
		if (status != null) {
			Query q = getEntityManager().createNativeQuery(
					"update candidatemapping set candidateStatus=? , lastUpdatedDate=now(), onBoardedDate =? ,onBoardedStatus=? , offeredCtc=? where candidate_id =? and bdmReq_id=? and mappedUser_id =?");
			q.setParameter(1, status);
			q.setParameter(2, onboardeddate);
			q.setParameter(3, onBoardStatus);
			q.setParameter(4, ctc);
			q.setParameter(5, id);
			q.setParameter(6, reqId);
			q.setParameter(7, userId);
			i = q.executeUpdate();
		} else {
			Query q = getEntityManager().createNativeQuery(
					"update candidatemapping set  onBoardedStatus =?, lastUpdatedDate=now() where candidate_id =? and bdmReq_id=? and mappedUser_id =?");
			q.setParameter(1, onBoardStatus);
			q.setParameter(2, id);
			q.setParameter(3, reqId);
			q.setParameter(4, userId);
			i = q.executeUpdate();
		}
		return i;
	}

	@Override
	@Transactional
	public int confirmBoardStatus(Long id, String onBoardStatus, Date abscondeddate, String reqId, Long userId) {
		int i = 0;

		Query q = getEntityManager().createNativeQuery(
				"update candidatemapping set candidateStatus=?, lastUpdatedDate=now() , absconded_date =? ,onBoardedStatus=?  where candidate_id =? and mappedUser_id=? and bdmReq_id=?");
		q.setParameter(1, onBoardStatus);
		q.setParameter(2, abscondeddate);
		q.setParameter(3, onBoardStatus);
		q.setParameter(4, id);

		q.setParameter(5, userId);
		q.setParameter(6, reqId);
		i = q.executeUpdate();

		return i;
	}

	@Override
	public double getInsurance(Employee employee, Double age) {
		double insuranceCoverage = 0.0;
		double insuranceCover = 0.0;
		Query q = null;

		if (employee.getEmployeeType().equalsIgnoreCase("Spl")) {
			insuranceCoverage = 100000.00;
		} else if (employee.getTotalCtc() < 500000.00) {
			insuranceCoverage = 300000.00;

		} else if (employee.getTotalCtc() >= 500000.00 && employee.getTotalCtc() < 1000000.00) {
			insuranceCoverage = 500000.00;

		} else if (employee.getTotalCtc() >= 1000000.00) {
			insuranceCoverage = 700000.00;
		}
		if (age < 35.00) {
			String sql = "select 35Yrs from insurance where sum_insured='" + insuranceCoverage + "'";
			q = getEntityManager().createNativeQuery(sql);
		} else if (age > 35.00 && age < 45.00) {
			String sql = "select 45Yrs from insurance where sum_insured='" + insuranceCoverage + "'";
			q = getEntityManager().createNativeQuery(sql);
		} else if (age > 45.00 && age < 55.00) {
			String sql = "select 55Yrs from insurance where sum_insured='" + insuranceCoverage + "'";
			q = getEntityManager().createNativeQuery(sql);
		} else if (age > 55.00 && age < 65.00) {
			String sql = "select 65Yrs from insurance where sum_insured='" + insuranceCoverage + "'";
			q = getEntityManager().createNativeQuery(sql);
		} else if (age > 65.00 && age < 70.00) {
			String sql = "select 70Yrs from insurance where sum_insured='" + insuranceCoverage + "'";
			q = getEntityManager().createNativeQuery(sql);
		} else if (age > 70.00 && age < 75.00) {
			String sql = "select 75Yrs from insurance where sum_insured='" + insuranceCoverage + "'";
			q = getEntityManager().createNativeQuery(sql);
		} else if (age > 75.00 && age < 80.00) {
			String sql = "select 80Yrs from insurance where sum_insured='" + insuranceCoverage + "'";
			q = getEntityManager().createNativeQuery(sql);
		}
		List<String> results = q.getResultList();
		if (results != null && !results.isEmpty()) {
			insuranceCover = Double.parseDouble(results.get(0));
		}

		return insuranceCover;
	}

	@Transactional
	public Response updateCandidateInfo(Long id, Candidate candidate) {
		Response response = null;
		return response;

		/*
		 * try { Candidate candidateCheck = find(id); if (null == candidateCheck) {
		 * response = new Response(ExceptionMessage.Not_Found,
		 * "Candidate Details not found with"); } else { StringBuilder updateQuery = new
		 * StringBuilder("UPDATE candidate SET "); if (null != candidate.getFirstName())
		 * { updateQuery.append(" firstName = ?,  "); } if (null !=
		 * candidate.getLastName()) { updateQuery.append(" lastName = ?, "); } if (null
		 * != candidate.getEmail()) { updateQuery.append(" email = ?, "); } if (null !=
		 * candidate.getAltenateEmail()) { updateQuery.append(" altenateEmail = ?, "); }
		 * if (null != candidate.getMobile()) { updateQuery.append(" mobile = ?, "); }
		 * if (null != candidate.getAlternateMobile()) {
		 * updateQuery.append(" alternateMobile = ?, "); } if (null != candidate.getC())
		 * { updateQuery.append(" candidateSource = ?, "); } if (null !=
		 * candidate.getTotalExperience()) {
		 * updateQuery.append(" totalExperience = ?, "); } if (null !=
		 * candidate.getRelevantExperience()) {
		 * updateQuery.append(" relevantExperience = ?, "); } if (null !=
		 * candidate.getCurrentCTC()) { updateQuery.append(" currentCTC = ?, "); } if
		 * (null != candidate.getExpectedCTC()) {
		 * updateQuery.append(" expectedCTC = ?, "); } if (null !=
		 * candidate.getNoticePeriod()) { updateQuery.append(" noticePeriod = ?, "); }
		 * if (null != candidate.getSalaryNegotiable()) {
		 * updateQuery.append(" salaryNegotiable = ?, "); } if (null !=
		 * candidate.getCurrentCompanyName()) {
		 * updateQuery.append(" currentCompanyName = ?, "); } if (null !=
		 * candidate.getPayRollCompanyName()) {
		 * updateQuery.append(" payRollCompanyName = ?, "); } if (null !=
		 * candidate.getCurrentJobType()) { updateQuery.append(" currentJobType = ?, ");
		 * } if (null != candidate.getWillingtoRelocate()) {
		 * updateQuery.append(" willingtoRelocate = ?, "); } if (null !=
		 * candidate.getSkypeID()) { updateQuery.append(" skypeID = ?, "); } if (null !=
		 * candidate.getPancardNumber()) { updateQuery.append(" pancardNumber = ?, "); }
		 * if (null != candidate.getCity()) { updateQuery.append(" city = ?, "); } if
		 * (null != candidate.getState()) { updateQuery.append(" state = ?, "); } if
		 * (null != candidate.getStreet1()) { updateQuery.append(" street1 = ?, "); } if
		 * (null != candidate.getStreet2()) { updateQuery.append(" street2 = ?, "); } if
		 * (null != candidate.getPincode()) { updateQuery.append(" pincode = ? "); } if
		 * (null != candidate.getAppliedPossitionFor()) {
		 * updateQuery.append(" PositionAppliedFor = ? "); } if (null !=
		 * candidate.getOtherSkils()) { updateQuery.append(" other_skils = ? "); } if
		 * (null != candidate.getOtherLocation()) {
		 * updateQuery.append(" other_locations = ? "); } if (null !=
		 * candidate.getOtherEducations()) {
		 * updateQuery.append(" other_educations = ? "); }
		 * updateQuery.append(" WHERE id = ?");
		 * 
		 * Query sqlUpdateQuery =
		 * this.getEntityManager().createNativeQuery(updateQuery.toString());
		 * sqlUpdateQuery.setParameter(1, candidate.getFirstName());
		 * sqlUpdateQuery.setParameter(2, candidate.getLastName());
		 * sqlUpdateQuery.setParameter(3, candidate.getEmail());
		 * sqlUpdateQuery.setParameter(4, candidate.getAltenateEmail());
		 * sqlUpdateQuery.setParameter(5, candidate.getMobile());
		 * sqlUpdateQuery.setParameter(6, candidate.getAlternateMobile());
		 * sqlUpdateQuery.setParameter(7, candidate.getCandidateSource());
		 * sqlUpdateQuery.setParameter(8, candidate.getTotalExperience());
		 * sqlUpdateQuery.setParameter(9, candidate.getRelevantExperience());
		 * sqlUpdateQuery.setParameter(10, candidate.getCurrentCTC());
		 * sqlUpdateQuery.setParameter(11, candidate.getExpectedCTC());
		 * sqlUpdateQuery.setParameter(12, candidate.getNoticePeriod());
		 * sqlUpdateQuery.setParameter(13, candidate.getSalaryNegotiable());
		 * sqlUpdateQuery.setParameter(14, candidate.getCurrentCompanyName());
		 * sqlUpdateQuery.setParameter(15, candidate.getPayRollCompanyName());
		 * sqlUpdateQuery.setParameter(16, candidate.getCurrentJobType());
		 * sqlUpdateQuery.setParameter(17, candidate.getWillingtoRelocate());
		 * sqlUpdateQuery.setParameter(18, candidate.getSkypeID());
		 * sqlUpdateQuery.setParameter(19, candidate.getPancardNumber());
		 * sqlUpdateQuery.setParameter(20, candidate.getCity());
		 * sqlUpdateQuery.setParameter(21, candidate.getState());
		 * sqlUpdateQuery.setParameter(22, candidate.getStreet1());
		 * sqlUpdateQuery.setParameter(23, candidate.getStreet2());
		 * sqlUpdateQuery.setParameter(24, candidate.getPincode());
		 * sqlUpdateQuery.setParameter(25, candidate.getAppliedPossitionFor());
		 * sqlUpdateQuery.setParameter(26, candidate.getOtherSkils());
		 * sqlUpdateQuery.setParameter(27, candidate.getOtherLocation());
		 * sqlUpdateQuery.setParameter(28, candidate.getOtherEducations());
		 * sqlUpdateQuery.setParameter(29, id);
		 * 
		 * this.qualificationDao.updateQualificationsByCandidateId(id,
		 * candidate.getEducation()); this.skillDao.updateSkillsByCandidateId(id,
		 * candidate.getSkills());
		 * this.certficateDao.updateCandidateCertificateDetailsbyId(id,
		 * candidate.getCertificates());
		 * 
		 * int updateValue = sqlUpdateQuery.executeUpdate(); if (updateValue > 0) {
		 * response = new Response(ExceptionMessage.OK,
		 * "Candidate Details Updated Succesfully."); } else { response = new
		 * Response(ExceptionMessage.OK, "No Update Done"); } } } catch (Exception e) {
		 * e.printStackTrace(); response = new Response(ExceptionMessage.Exception,
		 * "Unable to Update candidate Details due to : " + e.getMessage()); }
		 * 
		 * return response;
		 */
	}

	/*
	 * @Override public Candidate getCandidateId(String pancardNumber) { //
	 * Auto-generated method stub return null; }
	 */

	@Override
	@Transactional
	public boolean updateCandiate(CandidateStatusDTO statusDto) {
		boolean result = false;
		Query q = getEntityManager().createNativeQuery(
				"update candidatemapping set reason=?,lastUpdatedDate=now() , candidateStatus =? where mappedUser_id=? and candidate_id = ? and bdmReq_id = ?");

		q.setParameter(1, statusDto.getReason());
		q.setParameter(2, statusDto.getCandidateStatus());
		q.setParameter(3, statusDto.getLoginId());
		q.setParameter(4, statusDto.getCandidateId());
		q.setParameter(5, statusDto.getRequirementId());
		int i = q.executeUpdate();
		if (i > 0) {
			return true;
		}
		return result;
	}

	@Override
	public Response getCandidatelistinExcel(SearchTrackerList searchTrackerList, Long clienId, Long reqid,
			Properties properties) {
		Response response = null;
		Workbook workbook = new XSSFWorkbook();

		List<String> fieldsList = searchTrackerList.getList();
		FileOutputStream fileOut = null;
		try {

			Sheet sheet = workbook.createSheet("candidate");
			Font headerFont = workbook.createFont();

			headerFont.setBold(true);
			headerFont.setFontHeightInPoints((short) 14);

			CellStyle backgroundStyle = workbook.createCellStyle();
			backgroundStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			backgroundStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			headerFont.setColor(IndexedColors.BLACK.getIndex());
			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			Row headerRow = sheet.createRow(0);

			for (int i = 0; i < fieldsList.size(); i++) {
				Cell cell = headerRow.createCell(i);
				if (!fieldsList.get(i).equalsIgnoreCase("Vendor Name")) {
					if (!fieldsList.get(i).equalsIgnoreCase("Education")) {
						cell.setCellValue(properties.getProperty(fieldsList.get(i)));
					}
				}

				if (fieldsList.get(i).equalsIgnoreCase("Vendor Name")) {
					cell.setCellValue("Vendor Name");
				}

				if (fieldsList.get(i).equalsIgnoreCase("Education")) {
					cell.setCellValue("Education");
				}

				cell.setCellStyle(headerCellStyle);

			}

			int count = 0;
			StringBuilder columns = null;
			for (String col : fieldsList) {

				if (count == 0) {
					if (!col.equalsIgnoreCase("Education")) {
						if (!col.equalsIgnoreCase("Vendor Name")) {
							columns = new StringBuilder().append(col);
						}
					}
				} else {
					if (!col.equalsIgnoreCase("Education")) {
						if (!col.equalsIgnoreCase("Vendor Name")) {
							columns.append(", " + col);
						}
					}

				}
				if (!col.equalsIgnoreCase("Education")) {
					if (!col.equalsIgnoreCase("Vendor Name")) {
						count++;
					}
				}
			}

			String query = "select " + columns.toString() + ", cm.candidate_id as iddds from candidatemapping cm "
					+ "inner join candidate c on cm.candidate_id = c.id "
					+ "inner join bdmreq br on cm.bdmReq_id = br.id inner join `client` cl on br.client_id = cl.id "
					+ "where br.client_id =" + clienId + " and  cm.bdmReq_id = " + reqid;

			Query qn = this.getEntityManager().createNativeQuery(query);

			@SuppressWarnings("unchecked")
			List<Object[]> results = qn.getResultList();
			String fileName = clienId + "-" + reqid + new Date().getTime();
			String education = "";
			int rowNum = 1;
			for (Object obj[] : results) {
				Row row = sheet.createRow(rowNum++);

				for (int i = 0; i < fieldsList.size(); i++) {
					if (!fieldsList.get(i).equalsIgnoreCase("Vendor Name")) {
						if (!fieldsList.get(i).equalsIgnoreCase("Education")) {
							row.createCell(i).setCellValue(obj[i] + "");
						}
					}
					if (fieldsList.get(i).equalsIgnoreCase("Vendor Name")) {
						if (!fieldsList.get(i).equalsIgnoreCase("Education")) {
							row.createCell(i).setCellValue("Ojas Innovative Technologies");
						}
					}
					education = "";
					if (fieldsList.get(i).equalsIgnoreCase("Education")) {
						String quee = "select education_ID,qualificationName from addqualification e ,candidateeducationmap map where id =map.education_ID=e.id and candidate_ID = "
								+ obj[columns.toString().split(",").length] + "";
						Query qn1 = this.getEntityManager().createNativeQuery(quee);
						@SuppressWarnings("unchecked")
						List<Object[]> results2 = qn1.getResultList();
						for (Object obj2[] : results2) {
							education = education + obj2[1] + ",";

						}
						row.createCell(i).setCellValue(education);
					}

				}
			}

			fileOut = new FileOutputStream(excelTrackerPath + fileName + ".xlsx");

			for (int i = 0; i < fieldsList.size(); i++) {
				sheet.autoSizeColumn(i);

			}
			workbook.write(fileOut);

			return response = new Response(ExceptionMessage.StatusSuccess, fileName);

		} catch (

		Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.ExcepcetdDataNotAvilable);
		} finally {
			try {
				fileOut.close();
				workbook.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return response;
	}

	@Override
	public Response getSummaryReport(Long bdmReq_id) {
		System.out.println("entered into the dao layer");

		String queryStmt = "SELECT count(*),bdmReq_id,c.candidateStatus,req.nameOfRequirement  FROM testing.candidatemapping c,bdmreq req,client cli ,candidate can where c.bdmReq_id=req.id and cli.id=req.client_id and c.candidate_id=can.id";

		if (bdmReq_id == null) {
			queryStmt = queryStmt + " group by candidateStatus,bdmReq_id";
		} else {
			String id = bdmReq_id.toString();
			queryStmt = queryStmt + " and bdmReq_id=" + id + " group by candidateStatus,bdmReq_id";
		}
		Query createNativeQuery = this.getEntityManager().createNativeQuery(queryStmt);

		@SuppressWarnings("unchecked")
		List<Object[]> resultList = createNativeQuery.getResultList();

		List<SummaryReport> list = new ArrayList<SummaryReport>();
		if (!resultList.isEmpty() && resultList.size() > 0) {
			for (Object[] result : resultList) {
				SummaryReport sr = new SummaryReport();
				sr.setStatusCount(Integer.parseInt(result[0] + ""));
				sr.setRequirementId(Long.parseLong(result[1] + ""));
				sr.setCandidateStatus((result[2] + ""));
				sr.setNameOfTheReq(result[3] + "");
				list.add(sr);
			}
		} else {
			return new Response(ExceptionMessage.Not_Found);
		}
		return new Response(ExceptionMessage.StatusSuccess, list);

	}

	@Override
	@Transactional
	public List<String> chekduplicate(String mobile, String email, Long regId) {

		Query query = getEntityManager().createQuery("SELECT email FROM Candidate  WHERE email=? and registrationId=?");
		query.setParameter(1, email);
		query.setParameter(2, regId);
		@SuppressWarnings("unchecked")
		List<String> results = query.getResultList();
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Response findAllCandidateBasedOnRegId(CandidateSearch candidateSearch) {
		Response response = null;
		List<Object[]> candidatesearchList = null;
		Integer totalRecords = 0;

		StringBuilder countSql = new StringBuilder("SELECT count(*)  FROM candidate inner join source as su "
				+ " on su.id= source_id  inner join user as us on  us.id=user_id inner join CandidateStatus as cdst on cdst.id=candidateStatusMaster_id inner join CandidateJobTitle as cjt on cjt.id=candidateJobTitle_id "
				+ " inner join addqualification as quali on quali.id=addQualificationHeld_id where registrationId=?");

		StringBuilder sql = new StringBuilder(
				"SELECT candidate.id,concat(candidate.firstName,' ', candidate.lastName) AS candidateName,candidate.altenateEmail,candidate.alternateMobile,"
						+ " candidate.city,candidate.country,candidate.currentCTC,candidate.currentCompanyName,candidate.email,candidate.expectedCTC,candidate.mobile,"
						+ " candidate.skypeID,candidate.totalExperience,candidate.twitterId,candidate.website,candidate.noticePeriod,su.sourceName, "
						+ " concat(us.firstName,' ',us.lastName) AS ownerName,cdst.status,cjt.currentJobTittle,quali.qualificationName,candidate.lastUpdatedDate,"
						+ " candidate.rating FROM candidate inner join source as su "
						+ " on su.id= source_id  inner join user as us on  us.id=user_id inner join CandidateStatus as cdst on cdst.id=candidateStatusMaster_id inner join CandidateJobTitle as cjt on cjt.id=candidateJobTitle_id "
						+ " inner join addqualification as quali on quali.id=addQualificationHeld_id where registrationId=?");
		if (candidateSearch.getCandidateId() != null) {
			sql.append(" and candidate.id=" + candidateSearch.getCandidateId());
			countSql.append(" and candidate.id=" + candidateSearch.getCandidateId());
		}
		if (candidateSearch.getCandidateName() != null) {
			sql.append(" and concat(candidate.firstName,' ',candidate.lastName) like'"
					+ candidateSearch.getCandidateName() + "%'");
			countSql.append(" and concat(candidate.firstName,' ',candidate.lastName) like '"
					+ candidateSearch.getCandidateName() + "%'");
		}

		if (candidateSearch.getCountry() != null) {

			sql.append(" and candidate.country = '" + candidateSearch.getCountry().trim() + "'");
			countSql.append(" and candidate.country ='" + candidateSearch.getCountry().trim() + "'");

		}
		if (candidateSearch.getMobile() != null && (!candidateSearch.getMobile().trim().isEmpty())) {

			sql.append(" and candidate.mobile ='" + candidateSearch.getMobile().trim() + "'");
			countSql.append(" and candidate.mobile ='" + candidateSearch.getMobile().trim() + "'");

		}
		if (candidateSearch.getEmail() != null && (!candidateSearch.getEmail().trim().isEmpty())) {
			sql.append(" and candidate.email='" + candidateSearch.getEmail().trim() + "'");
			countSql.append(" and candidate.email='" + candidateSearch.getEmail().trim() + "'");

		}
		if (candidateSearch.getExceptedsalary() != null && (!candidateSearch.getExceptedsalary().trim().isEmpty())) {
			sql.append(" and candidate.expectedCTC='" + candidateSearch.getExceptedsalary().trim() + "'");
			countSql.append(" and candidate.expectedCTC='" + candidateSearch.getExceptedsalary().trim() + "'");
		}
		if (candidateSearch.getHighestQualificationHeld() != null
				&& (!candidateSearch.getHighestQualificationHeld().trim().isEmpty())) {

			sql.append(" and quali.qualificationName='" + candidateSearch.getHighestQualificationHeld().trim() + "'");
			countSql.append(
					" and quali.qualificationName='" + candidateSearch.getHighestQualificationHeld().trim() + "'");

		}
		if (candidateSearch.getCandidateStatus() != null && candidateSearch.getBdmReqId() != null) {
			sql.append(
					" and cm.stageOfInterview= (select id from assesmentname am where  am.assesmentName='submission') and "
							+ " cm.candidateStatus =(select id from candidatestatus "
							+ " where status='shortlisted') and cm.bdmReq_id=" + candidateSearch.getBdmReqId());
			countSql.append(
					" and cm.stageOfInterview= (select id from assesmentname am where  am.assesmentName='submission') and "
							+ " cm.candidateStatus =(select id from candidatestatus "
							+ " where status='shortlisted') and cm.bdmReq_id=" + candidateSearch.getBdmReqId());
		}
		int startingRow = 0;
		if (candidateSearch.getPageNum() == 1) {
			startingRow = 0;
		} else {
			startingRow = ((candidateSearch.getPageNum() - 1) * candidateSearch.getPageSize());
		}

		if (candidateSearch.isFlag()) {
			sql = sql.append(" order by candidate.id DESC LIMIT " + startingRow + "," + candidateSearch.getPageSize());

			Query query = null;
			try {
				query = getEntityManager().createNativeQuery(sql.toString());
				query.setParameter(1, candidateSearch.getRegistrationId());
				candidatesearchList = query.getResultList();
				Query countQuery = this.getEntityManager().createNativeQuery(countSql.toString());
				countQuery.setParameter(1, candidateSearch.getRegistrationId());
				Object countResult = countQuery.getSingleResult();
				totalRecords = Integer.valueOf(countResult.toString());
				System.out.println(totalRecords + "are");
				List<CandidateSearchList> responseList = new ArrayList<CandidateSearchList>();
				if (candidatesearchList.isEmpty()) {
					response = new Response(ExceptionMessage.DataIsEmpty, "No Content Found");
				} else {
					for (Object[] objects : candidatesearchList) {
						CandidateSearchList transferObj = new CandidateSearchList();
						if (objects[0] != null)
							transferObj.setId(Long.valueOf(objects[0] + ""));
						if (objects[1] != null)
							transferObj.setFullName(String.valueOf(objects[1] + ""));
						if (objects[2] != null)
							transferObj.setAltenateEmail(String.valueOf(objects[2] + ""));
						if (objects[3] != null)
							transferObj.setAlternateMobile(String.valueOf(objects[3] + ""));
						if (objects[4] != null)
							transferObj.setCity(String.valueOf(objects[4] + ""));
						if (objects[5] != null)
							transferObj.setCountry(String.valueOf(objects[5] + ""));
						if (objects[6] != null)
							transferObj.setCurrentCTC(String.valueOf(objects[6] + ""));
						if (objects[7] != null)
							transferObj.setCurrentCompanyName(String.valueOf(objects[7] + ""));
						if (objects[8] != null)
							transferObj.setEmail(String.valueOf(objects[8] + ""));
						if (objects[9] != null)
							transferObj.setExpectedCTC(String.valueOf(objects[9] + ""));
						if (objects[10] != null)
							transferObj.setMobile(String.valueOf(objects[10] + ""));
						if (objects[11] != null)
							transferObj.setSkypeID(String.valueOf(objects[11] + ""));
						if (objects[12] != null)
							transferObj.setTotalExperience(String.valueOf(objects[12] + ""));
						if (objects[13] != null)
							transferObj.setTwitterId(String.valueOf(objects[13] + ""));
						if (objects[14] != null)
							transferObj.setWebsite(String.valueOf(objects[14] + ""));
						if (objects[15] != null)
							transferObj.setNoticePeriod(String.valueOf(objects[15] + ""));
						if (objects[16] != null)
							transferObj.setSource(String.valueOf(objects[16] + ""));
						if (objects[17] != null)
							transferObj.setOwner(String.valueOf(objects[17] + ""));
						if (objects[18] != null)
							transferObj.setCandidateStatusMaster(String.valueOf(objects[18] + ""));
						if (objects[19] != null)
							transferObj.setCandidateJobTitle(String.valueOf(objects[19] + ""));
						if (objects[20] != null)
							transferObj.setAddQualificationHeld(String.valueOf(objects[20] + ""));
						if ((objects[21]) != null)
							transferObj.setLastUpdatedDate((Date) objects[21]);

						if ((objects[22]) != null) {
							transferObj.setCandidateRating(Long.valueOf(String.valueOf(objects[22])));
						}
						responseList.add(transferObj);
						response = new Response(ExceptionMessage.Accepted, responseList);
						Integer totalPages = 1;
						if (totalRecords == 0) {
							response.setTotalPages(0);
						} else {
							totalPages = totalRecords / candidateSearch.getPageSize();
							totalPages = (totalRecords % candidateSearch.getPageSize() > 0) ? totalPages + 1
									: totalPages;

						}
						response.setTotalPages(totalPages);
						response.setTotalRecords(totalRecords);
						response.setResult(responseList);
					}
				}
			} catch (PersistenceException pe) {
				response = new Response(ExceptionMessage.Exception, "500",
						"Unable to Retrieve Customers List. " + " " + pe.getLocalizedMessage());
			} catch (Exception e) {
				e.printStackTrace();
				response = new Response(ExceptionMessage.Exception, "500",
						" Unable to Search Requirements " + e.fillInStackTrace());
			}
			return response;
		} else {
			XSSFWorkbook workbook = null;
			FileOutputStream out = null;
			File file = null;
			Query query = null;
			try {

				query = getEntityManager().createNativeQuery(sql.toString());
				query.setParameter(1, candidateSearch.getRegistrationId());
				candidatesearchList = query.getResultList();
				workbook = new XSSFWorkbook();
				XSSFSheet spreadsheet = workbook.createSheet("Candidate Details");

				int rowCount = 0;
				Row header = spreadsheet.createRow(rowCount);
				header.createCell(0).setCellValue("Id");
				header.createCell(1).setCellValue("Full Name");
				header.createCell(2).setCellValue("Alternate Email");
				header.createCell(3).setCellValue("Alternate Mobile");
				header.createCell(4).setCellValue("City");
				header.createCell(5).setCellValue("Country");
				header.createCell(6).setCellValue("Current CTC");
				header.createCell(7).setCellValue("Current Company Name");
				header.createCell(8).setCellValue("Email");
				header.createCell(9).setCellValue("Expected CTC");
				header.createCell(10).setCellValue("Mobile");
				header.createCell(11).setCellValue("Skype ID");
				header.createCell(12).setCellValue("Total Experience");
				header.createCell(13).setCellValue("Twitter Id");
				header.createCell(14).setCellValue("Website");
				header.createCell(15).setCellValue("Notice Period");
				header.createCell(16).setCellValue("Source");
				header.createCell(17).setCellValue("Owner");
				header.createCell(18).setCellValue("Candidate Status Master");
				header.createCell(19).setCellValue("Candidate Job Title");
				header.createCell(20).setCellValue("Add Qualification Held");
				header.createCell(21).setCellValue("Last Updated Date");
				header.createCell(22).setCellValue("Rating");

				for (Object[] cell : candidatesearchList) {
					Row row1 = spreadsheet.createRow(++rowCount);
					int colCount = -1;

					for (Object object : cell) {

						Cell cell1 = row1.createCell(++colCount);
						if (object instanceof Integer)
							cell1.setCellValue(Long.valueOf(object + ""));
						else if (object instanceof String)
							cell1.setCellValue(object + "");
						else if (object instanceof Long)
							cell1.setCellValue(Long.valueOf(object + ""));
						else if (object instanceof Boolean)
							cell1.setCellValue(Boolean.valueOf(object + ""));
						else
							cell1.setCellValue(object + "");

					}
				}

				StringBuilder pro = new StringBuilder(excelFilePath);

				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
				String strDate = dateFormat.format(new Date());
				pro.append("CandidateDetails" + strDate + "File");
				file = new File(pro + ".xlsx");

				out = new FileOutputStream(file);
				workbook.write(out);
				byte[] bytes = null;
				java.nio.file.Path p = Paths.get(file.getAbsolutePath());
				bytes = Files.readAllBytes(p);
				response = new Response(ExceptionMessage.Accepted, bytes);

			} catch (Exception e) {

				response = new Response(ExceptionMessage.Exception, e.getLocalizedMessage());

			} finally {

				try {
					out.close();

					if (file.exists()) {
						Files.deleteIfExists(file.getAbsoluteFile().toPath());
					}

					workbook.close();

				} catch (IOException e) {
					response = new Response(ExceptionMessage.Bad_Request, e.getLocalizedMessage());

				}
			}
		}
		return response;

	}

}
