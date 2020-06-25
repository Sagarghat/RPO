package com.ojas.rpo.security.dao.InterviewDetails;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.InterviewDetails;
import com.ojas.rpo.security.entity.InterviewFIndallList;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.DateParsing;
import com.ojas.rpo.security.util.InterviewSearch;

public class JpaInterviewDetailsDao extends JpaDao<InterviewDetails, Long> implements InterviewDetailsDao {

	@Value("${excelFile}")
	private String excelFilePath;

	public JpaInterviewDetailsDao() {
		super(InterviewDetails.class);
	}

	@Override
	public Response findDataBasedOnRegId(InterviewSearch search) {
		Response response = new Response();

		if (search.getRegId() != null && search.getPageNo() != null && search.getPageSize() != null) {
			StringBuilder sql = new StringBuilder(
					"SELECT  inter.id as interviewId, cnd.id as candidateId, bdm.id as reqId,"

							+ " intType.modeofInterview as  interviewName,"

							+ " inter.interviewFrom, inter.interviewEnd, cnd.firstName as candidateName,"

							+ " cli.clientName as clientName, bdm.nameOfRequirement,"

							+ " inter.status,us.firstName as ownername , round.level , cli.id as clientId , addcon.id as contactId "

							+ " FROM interviewdetails as inter "

							+ " inner join interviewtype as intType on intType.id=inter.interviewName_id"

							+ " inner join candidate as cnd on cnd.id=inter.candidate_id"

							+ " inner join client as cli on inter.client_id = cli.id"

							+ " inner join bdmreq as bdm on bdm.id=inter.bdmReq_id "
							+ " inner join interviewround as round on round.id = inter.round_id "

							+ " inner join user as us on us.id=inter.interviewOwner_id "
							+ " inner join addcontact as addcon on inter.interviewer_id = addcon.id"

							+ " where  inter.reg_id= " + search.getRegId());

			if (search.getBdmreq() != null) {
				sql.append(" and bdm.nameOfRequirement LIKE '" + search.getBdmreq() + "%'");
			}
			if (search.getCandidateName() != null) {
				sql.append(" and cnd.firstName LIKE '" + search.getCandidateName() + "%'");
			}
			if (search.getFrom() != null) {

				sql.append(" and inter.interviewFrom >='" + search.getFrom() + "'");
			}
			if (search.getTo() != null) {
				sql.append(" and interviewEnd <= '" + search.getTo() + "'");
			}
			if (search.getInterviewName() != null) {
				sql.append(" and intType.modeofInterview LIKE '" + search.getInterviewName() + "%'");
			}
			if (search.getInterviewOwner() != null) {
				sql.append(" and us.firstName LIKE '" + search.getInterviewOwner() + "%'");
			}
			if (search.getClientName() != null) {
				sql.append(" and cli.clientName LIKE '" + search.getClientName() + "%'");
			}
			if (search.getRound() != null) {
				sql.append(" and round.level like '" + search.getRound() + "%'");
			}
			if (search.getClientId() != null) {
				sql.append(" and cli.id like '" + search.getClientId() + "%'");
			}
			if (search.getContactId() != null) {
				sql.append(" and addcon.id like '" + search.getContactId() + "%'");
			}
			Integer totalRecords = 0;

			List<Object[]> resultList = getEntityManager().createNativeQuery(sql.toString()).getResultList();
			if (resultList != null) {
				totalRecords = resultList.size();
			}

			int startingRow = 0;
			if (search.getPageNo() == 1) {
				startingRow = 0;
			} else {
				startingRow = ((search.getPageNo() - 1) * search.getPageSize());

			}

			Integer totalPages = 1;
			if (totalRecords == 0) {
				response.setTotalPages(0);
			} else {
				totalPages = totalRecords / search.getPageSize();

				totalPages = (totalRecords % search.getPageSize() > 0) ? totalPages + 1 : totalPages;

			}

			if (search.isFlag() == true) {

				sql = sql.append(" order by inter.id ASC LIMIT " + startingRow + "," + search.getPageSize());
				Query query = null;
				try {
					List<Object[]> resultList2 = getEntityManager().createNativeQuery(sql.toString()).getResultList();
					System.out.println(sql.toString());
					List<InterviewFIndallList> list = new ArrayList<>();
					for (Object[] ob : resultList2) {
						sql = sql.append(" order by inter.id DESC LIMIT " + startingRow + "," + search.getPageSize());
						InterviewFIndallList tranferObject = new InterviewFIndallList();
						tranferObject.setInterviewId(Long.valueOf(ob[0] + ""));
						tranferObject.setCandidateid(Long.valueOf(ob[1] + ""));
						tranferObject.setRequirementId(Long.valueOf(ob[2] + ""));
						tranferObject.setInterviewName(ob[3] + "");
						tranferObject.setFrom(ob[4] + "");
						tranferObject.setTo(ob[5] + "");
						tranferObject.setCandidateName(ob[6] + "");
						tranferObject.setClientName(ob[7] + "");
						tranferObject.setPostingTitle(ob[8] + "");
						tranferObject.setInterviewStatus(ob[9] + "");
						tranferObject.setInterviewOwner(ob[10] + "");
						tranferObject.setInterviewRound(ob[11] + "");
						tranferObject.setClientId(Long.valueOf(ob[12] + ""));
						list.add(tranferObject);
					}

					response.setResult(list);
					response.setStatus(ExceptionMessage.StatusSuccess);
					response.setTotalPages(totalPages);
					response.setTotalRecords(totalRecords);

				} catch (PersistenceException pe) {
					response = new Response(ExceptionMessage.Exception, "500",
							"Unable to Retrieve Customers List. " + " " + pe.getLocalizedMessage());
				} catch (Exception e) {
					response = new Response(ExceptionMessage.Exception, "500",
							" Unable Retrieve Customers List " + e.getLocalizedMessage());
				}
				return response;

			} else {
				FileInputStream inputStream = null;
				FileOutputStream out = null;
				File file = null;
				Query query = null;
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet("interview Details");

				List<Object[]> resultList2 = getEntityManager().createNativeQuery(sql.toString()).getResultList();

				Row header = sheet.createRow(0);
				header.createCell(0).setCellValue("id");
				header.createCell(1).setCellValue("interviewName");
				header.createCell(2).setCellValue("from");
				header.createCell(3).setCellValue("to");
				header.createCell(4).setCellValue("candidateName");
				header.createCell(6).setCellValue("postingTitle");
				header.createCell(7).setCellValue("interviewStatus");
				header.createCell(8).setCellValue("interviewOwner");

				int rowCount = 0;
				for (Object[] obj : resultList2) {
					Row row = sheet.createRow(++rowCount);
					int columnCount = -1;
					int i = 0;
					for (Object object : obj) {
						if (i != 2 || i != 3) {
							Cell cell = row.createCell(++columnCount);
							if (object instanceof Integer)
								cell.setCellValue(Long.valueOf(object + ""));
							else if (object instanceof String)
								cell.setCellValue(object + "");
							else if (object instanceof Long)
								cell.setCellValue(Long.valueOf(object + ""));
							else if (object instanceof Boolean)
								cell.setCellValue(Boolean.valueOf(object + ""));
							else {

								cell.setCellValue(object + "");
							}
						}
						i++;
					}

				}

				try {
					StringBuilder pro = new StringBuilder(excelFilePath);
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					String strDate = dateFormat.format(new Date());
					pro.append("interviewDetails" + strDate + "File");
					file = new File(pro + ".xlsx");
					out = new FileOutputStream(file);
					workbook.write(out);
					byte[] bytes = null;
					Path p = Paths.get(file.getAbsolutePath());
					bytes = Files.readAllBytes(p);
					response = new Response(ExceptionMessage.StatusSuccess, bytes);
				}

				catch (Exception e) {
					e.printStackTrace();
					response = new Response(ExceptionMessage.Exception);
				} finally {
					if (file.exists()) {
						file.delete();
					}
					try {
						out.close();
						workbook.close();
					} catch (Exception e) {
						response = new Response(ExceptionMessage.Bad_Request, e.getLocalizedMessage());
					}
				}
				return response;
			}

		}
		return response;
	}

	@Override
	@Transactional
	public void updateCandidateMapping(InterviewDetails details) {
		String sql = "update candidateMapping set lastUpdatedDate = ? , stageOfInterview = ?,  mappedUser_id =  ? where candidate_id  = ? and bdmReq_id = ? ";

		Query query = getEntityManager().createNativeQuery(sql);

		query.setParameter(1, new Date());
		query.setParameter(2, details.getAssesmentName().getId());
		query.setParameter(3, details.getInterviewOwner().getId());
		query.setParameter(4, details.getCandidate().getId());
		query.setParameter(5, details.getBdmReq().getId());
		query.executeUpdate();

	}

	@Override
	public void removeInterviewDetailsByCandidateId(Long candidateId, Long requimentId, Long userId) {
		String sql = " DELETE FROM interviewdetails  WHERE candidate_id =" + candidateId + " and requiment_id="
				+ requimentId + " and userId=" + userId;
		Query query = getEntityManager().createNativeQuery(sql);
		query.executeUpdate();

	}

	@Override
	public List<InterviewDetails> getInterviewDetailsByCandidateIdAndStatus(Long candidateId, String status,
			Long requimentId, Long userId) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<InterviewDetails> cq = getCriteriaQuery();
		Root<InterviewDetails> e = cq.from(InterviewDetails.class);
		Join<InterviewDetails, InterviewDetails> r = e.join("candidate", JoinType.LEFT);
		Join<InterviewDetails, InterviewDetails> r1 = e.join("requirement", JoinType.LEFT);
		Predicate predicates = cb.and(cb.equal(r.get("id"), candidateId), cb.equal(e.get("status"), status),
				cb.equal(r1.get("id"), requimentId), cb.equal(e.get("userId"), userId));

		cq.where(predicates);
		TypedQuery<InterviewDetails> tq = getEntityManager().createQuery(cq);
		List<InterviewDetails> resultList = tq.getResultList();
		return resultList;

	}

	public InterviewDetails getInterviewDetailsByCandidateId(Long candidateId, Long requimentId, Long userId) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<InterviewDetails> cq = getCriteriaQuery();
		Root<InterviewDetails> e = cq.from(InterviewDetails.class);
		Join<InterviewDetails, InterviewDetails> r = e.join("candidate", JoinType.LEFT);
		Join<InterviewDetails, InterviewDetails> r1 = e.join("requirement", JoinType.LEFT);
		Predicate predicates = cb.and(cb.equal(r.get("id"), candidateId), cb.equal(r1.get("id"), requimentId),
				cb.equal(e.get("userId"), userId));

		cq.where(predicates);
		TypedQuery<InterviewDetails> tq = getEntityManager().createQuery(cq);
		List<InterviewDetails> resultList = tq.getResultList();
		if (resultList.isEmpty()) {
			return null;
		}

		return resultList.get(0);
	}

	private CriteriaBuilder getCriteriaBuilder() {
		CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
		return criteriaBuilder;
	}

	private CriteriaQuery<InterviewDetails> getCriteriaQuery() {
		CriteriaQuery<InterviewDetails> criteriaQuery = getCriteriaBuilder().createQuery(InterviewDetails.class);
		return criteriaQuery;
	}

	@Transactional
	public InterviewDetails updateInterviewStatus(Long candidateId, Long requimentId, Long userId,
			String activestatus) {
		InterviewDetails data = getInterviewDetailsByCandidateId(candidateId, requimentId, userId);

		// data.setStatus("Waiting for Interview Feedback");
		// data.setStatus(activestatus);
		// data.setLastUpdatedDate(new Date());

		InterviewDetails updateData = save(data);
		return updateData;
	}

}
