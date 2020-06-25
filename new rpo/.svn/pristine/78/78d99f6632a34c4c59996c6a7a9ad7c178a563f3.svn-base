package com.ojas.rpo.security.dao.bdmreqdtls;

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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.BdmReq;
import com.ojas.rpo.security.entity.BdmReqList;
import com.ojas.rpo.security.entity.BdmReqSearch;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.BdmUpdate;

public class JpaBdmReqDao extends JpaDao<BdmReq, Long> implements BdmReqDao {

	@Value("${excelFile}")
	private String excelFilePath;

	public JpaBdmReqDao() {
		super(BdmReq.class);
	}
//

	@Transactional
	public Response updateReqDetails(BdmReq bdmReq) {

		return null;
	}

	@Override
	public BdmReq getRequirementById(BdmReqSearch bdmReqSearch) {
		Predicate p = null;
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<BdmReq> cq = cb.createQuery(BdmReq.class);

		Root<BdmReq> r = cq.from(BdmReq.class);

		p = cb.and(cb.equal(r.get("id"), bdmReqSearch.getBdmId()),
				cb.equal(r.get("registrationId"), bdmReqSearch.getRegistrationId()));

		cq.where(p);
		TypedQuery<BdmReq> tq = getEntityManager().createQuery(cq);
		if (tq != null && !tq.getResultList().isEmpty()) {

			return tq.getResultList().get(0);
		}

		return null;

	}

	@Override
	public Response getAllRequirementsByRegId(BdmReqSearch bdmReqSearch) {
		Response response = null;
		List<Object[]> addRequimentDTO = null;
		Integer totalRecords = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

		// bdmReqSearch=new BdmReqSearch();
		StringBuilder countSql = new StringBuilder(
				"SELECT COUNT(*) FROM  testing.bdmreq INNER JOIN client ct ON bdmreq.client_id =ct.id "
						+ "INNER JOIN addContact addContact ON bdmreq.addContact_id =addContact.id  inner join user u on  u.id=bdmreq.createdby_id  where  bdmreq.registrationId =? ");
		StringBuilder sql = new StringBuilder("SELECT bdmreq.id," + " bdmreq.lastUpdatedDate," + "    bdmreq.maxBudget,"

				+ "    bdmreq.minBudget," + "    bdmreq.nameOfRequirement," + "    bdmreq.noticePeriod,"

				+ "    bdmreq.noOfPositions," + "    bdmreq.otherEducations," + "    bdmreq.otherLocation,"

				+ "    bdmreq.otherSkills," + "    bdmreq.relavantExperience," + "    bdmreq.requirementDescription,"

				+ "    bdmreq.requirementEndDate," + "    bdmreq.requirementStartdate,"

				+ "    bdmreq.requirementStatus," + "    bdmreq.requirementType," + "    bdmreq.totalExperience,"

				+ "    bdmreq.addContact_id," + "    bdmreq.client_id," + "    bdmreq.createdBy_id,"

				+ "    bdmreq.modifiedBy_id," + "    bdmreq.actualRevenue," + "    bdmreq.city," + "    bdmreq.country,"

				+ "    bdmreq.expectedRevenue," + "    bdmreq.industry_id," + "    bdmreq.jobDesc,"

				+ "    bdmreq.jobType," + "    bdmreq.missedRevenue,"

				+ "    bdmreq.otherSkills as skillsSet ," + "    bdmreq.revenuePerPosition," + "    bdmreq.state,"

				+ "    bdmreq.zipCode," + "    ct.clientName," + "	addContact.firstName,"

				+ "	addContact.email," + "    addContact.mobile," + " bdmreq.fileAttachements, " + " bdmreq.isHot  "

				+ "FROM testing.bdmreq INNER JOIN client ct ON ct.id=bdmreq.client_id  "

				+ "                    INNER JOIN addContact addContact ON  addContact.id=bdmreq.addContact_id inner join user u on  u.id=bdmreq.createdby_id where  bdmreq.registrationId =?");

		/*
		 * select req.id as reqId, date(now())-date(req.requirementEndDate) as
		 * datediff,date(req.requirementEndDate) from bdmreq req where
		 * date(now())-date(req.requirementenddate)<day(last_day(date(now())));
		 */

		if (bdmReqSearch.getClientId() != null) {
			sql.append(" and bdmreq.client_id LIKE '%" + bdmReqSearch.getClientId() + "%'");
			countSql.append(" and bdmreq.client_id LIKE '%" + bdmReqSearch.getClientId() + "%'");
		}
		if (bdmReqSearch.getBdmId() != null) {
			sql.append(" and bdmreq.id =" + bdmReqSearch.getBdmId());
			countSql.append(" and bdmreq.id=" + bdmReqSearch.getBdmId());
		}
		if (bdmReqSearch.getClientName() != null) {
			sql.append(" and ct.clientName LIKE '" + bdmReqSearch.getClientName() + "%'");
			countSql.append(" and ct.clientName LIKE '" + bdmReqSearch.getClientName() + "%'");
		}
		if (bdmReqSearch.getJobOpeningTitle() != null) {
			sql.append(" and bdmreq.nameOfRequirement like '" + bdmReqSearch.getJobOpeningTitle() + "%'");
			countSql.append(" and bdmreq.nameOfRequirement like '" + bdmReqSearch.getJobOpeningTitle() + "%'");

		}

		if (bdmReqSearch.getStatus() != null && !bdmReqSearch.getStatus().isEmpty()) {
			sql.append(" and bdmreq.requirementStatus ='" + bdmReqSearch.getStatus() + "'");
			countSql.append(" and bdmreq.requirementStatus = '" + bdmReqSearch.getStatus() + "'");

		}

		if (bdmReqSearch.getIsHot() != null ) {
			sql.append(" and bdmreq.isHot ='" + bdmReqSearch.getIsHot() + "'");
			countSql.append(" and bdmreq.isHot = '" + bdmReqSearch.getIsHot() + "'");
		}

		if (bdmReqSearch.getReqstartDate() == null || bdmReqSearch.getReqEndDate() == null) {
			if (bdmReqSearch.getReqstartDate() != null) {
				sql.append(" and bdmreq.requirementStartdate like '"
						+ dateformat.format(bdmReqSearch.getReqstartDate()) + "%' ");
				countSql.append(" and bdmreq.requirementStartdate like '"
						+ dateformat.format(bdmReqSearch.getReqstartDate()) + "%' ");
			}
			if (bdmReqSearch.getReqEndDate() != null) {

				sql.append(" and bdmreq.requirementEnddate  like '" + dateformat.format(bdmReqSearch.getReqEndDate())
						+ "%'");
				countSql.append(" and bdmreq.requirementEnddate like '"
						+ dateformat.format(bdmReqSearch.getReqEndDate()) + "%'");
			}
		} else {
			sql.append(" and bdmreq.requirementStartdate between '" + dateformat.format(bdmReqSearch.getReqstartDate())
					+ "'  and '" + dateformat.format(bdmReqSearch.getReqEndDate()) + "' ");
			countSql.append(" and bdmreq.requirementStartdate between '" + dateformat.format(bdmReqSearch.getReqstartDate())
					+ "' and '" + dateformat.format(bdmReqSearch.getReqEndDate()) + "' ");
		}

		if (bdmReqSearch.getAmName() != null) {
			sql.append(" and u.name like '" + bdmReqSearch.getAmName() + "%'");
			countSql.append(" and u.name like '" + bdmReqSearch.getAmName() + "%'");
		}

		int startingRow = 0;
		if (bdmReqSearch.getPageNum() == 1) {
			startingRow = 0;
		} else {
			startingRow = ((bdmReqSearch.getPageNum() - 1) * bdmReqSearch.getPageSize());

		}

		if (bdmReqSearch.isFlag() == true) {

			sql = sql.append(" order by ct.id DESC LIMIT " + startingRow + "," + bdmReqSearch.getPageSize());
			Query query = null;
			try {
				query = getEntityManager().createNativeQuery(sql.toString());
				query.setParameter(1, bdmReqSearch.getRegistrationId());
				addRequimentDTO = query.getResultList();

				Query countQuery = this.getEntityManager().createNativeQuery(countSql.toString());
				countQuery.setParameter(1, bdmReqSearch.getRegistrationId());

				Object countResult = countQuery.getSingleResult();
				totalRecords = Integer.valueOf(countResult.toString());
				System.out.println("totalRecords" + totalRecords);
				List<BdmReqList> responseList = new ArrayList<BdmReqList>();
				if (addRequimentDTO.isEmpty()) {
					response = new Response(ExceptionMessage.DataIsEmpty, "No Content Found");
				} else {
					for (Object[] req : addRequimentDTO) {
						BdmReqList transferObj = new BdmReqList();
						if (req[0] != null)
							transferObj.setId(Long.valueOf(req[0] + ""));
						if (req[1] != null)
							transferObj.setLastUpdatedDate((Date) req[1]);
						if (req[2] != null)
							transferObj.setMaxBudget(req[2] + "");
						if (req[3] != null)
							transferObj.setMinBudget(req[3] + "");
						if (req[4] != null)
							transferObj.setNameOfRequirement(req[4] + "");
						if (req[5] != null)
							transferObj.setNoticePeriod(req[5] + "");
						if (req[6] != null)
							transferObj.setNoOfPositions(req[6] + "");
						if (req[7] != null)
							transferObj.setOtherEducations(req[7] + "");
						if (req[8] != null)
							transferObj.setOtherLocation(req[8] + "");
						if (req[9] != null)
							transferObj.setOtherSkils(req[9] + "");
						if (req[10] != null)
							transferObj.setRelavantExperience(req[10] + "");
						if (req[11] != null)
							transferObj.setRequirementDescription(req[11] + "");
						if (req[12] != null)
							transferObj.setRequirementEndDate((Date) req[12]);
						if (req[13] != null)
							transferObj.setRequirementStartdate((Date) req[13]);
						if (req[14] != null)
							transferObj.setRequirementStatus(req[14] + "");
						if (req[15] != null)
							transferObj.setRequirementType(req[15] + "");
						if (req[16] != null)
							transferObj.setTotalExperience(req[16] + "");
						if (req[17] != null)
							transferObj.setAddContactId(req[17] + "");
						if (req[18] != null)
							transferObj.setClientId(req[18] + "");
						if (req[19] != null)
							transferObj.setCreatedById(req[19] + "");
						if (req[20] != null)
							transferObj.setModifiedById(req[20] + "");
						if (req[21] != null)
							transferObj.setActualRevenue(req[21] + "");
						if (req[22] != null)
							transferObj.setCity(req[22] + "");
						if (req[23] != null)
							transferObj.setCountry(req[23] + "");
						if (req[24] != null)
							transferObj.setExpectedRevenue(req[24] + "");
						if (req[25] != null)
							transferObj.setIndustry(req[25] + "");
						if (req[26] != null)
							transferObj.setJobDesc(req[26] + "");
						if (req[27] != null)
							transferObj.setJobType(req[27] + "");
						if (req[28] != null)
							transferObj.setMissedRevenue(req[28] + "");
						if (req[29] != null)
							transferObj.setOtherSkills(req[29] + "");
						if (req[30] != null)
							transferObj.setRevenuePerPosition(req[30] + "");
						if (req[31] != null)
							transferObj.setState(req[31] + "");
						if (req[32] != null)
							transferObj.setZipCode(req[32] + "");
						if (req[33] != null)
							transferObj.setClientName(req[33] + "");
						if (req[34] != null)
							transferObj.setContactFirstName(req[34] + "");
						if (req[35] != null)
							transferObj.setContactEmail(req[35] + "");
						if (req[36] != null)
							transferObj.setContactMobile(req[36] + "");

						if (req[37] != null)
							transferObj.setFileAttachements(req[37] + "");

						if (req[38] != null)
							transferObj.setIsHot(Long.valueOf(req[38] + ""));

						responseList.add(transferObj);

					}
					response = new Response(ExceptionMessage.OK, responseList);
				}
				Integer totalPages = 1;
				if (totalRecords == 0) {
					response.setTotalPages(0);
				} else if (totalRecords == 1 || bdmReqSearch.getPageSize() >= totalRecords) {
					response.setTotalPages(totalPages);

				} else {
					totalPages = totalRecords / bdmReqSearch.getPageSize();
					System.out.println(totalPages);
					totalPages = (totalRecords % bdmReqSearch.getPageSize() > 0) ? totalPages + 1 : totalPages;
				}
				System.out.println("totalPages" + totalPages);
				response.setTotalPages(totalPages);
				response.setTotalRecords(totalRecords);
			} catch (

			PersistenceException pe) {
				pe.printStackTrace();
				response = new Response(ExceptionMessage.Exception, "500",
						"Unable to Retrieve Customers List. " + " " + pe.getLocalizedMessage());
			} catch (Exception e) {
				e.printStackTrace();
				response = new Response(ExceptionMessage.Exception, "500",
						" Unable Retrieve Customers List " + e.getLocalizedMessage());
			}

			///////////////////////////////////////////////////////////

			return response;
		} else {
			FileOutputStream out = null;
			File file = null;
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("BDM Req Details");
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setFontHeightInPoints((short) 14);
			CellStyle backgroundStyle = workbook.createCellStyle();
			backgroundStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			backgroundStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerFont.setColor(IndexedColors.BLACK.getIndex());
			CellStyle headerCellStyle = workbook.createCellStyle();
			Query query = null;
			query = getEntityManager().createNativeQuery(sql.toString());
			query.setParameter(1, bdmReqSearch.getRegistrationId());
			addRequimentDTO = query.getResultList();
			Row header = sheet.createRow(0);
			header.createCell(0).setCellValue("Id");
			header.createCell(1).setCellValue("Last Updated Date");
			header.createCell(2).setCellValue("Max Budget");
			header.createCell(3).setCellValue("Min Budget");
			header.createCell(4).setCellValue("Name Of Requirement");
			header.createCell(5).setCellValue("Notice Period");
			header.createCell(6).setCellValue("No Of Positions");
			header.createCell(7).setCellValue("Other Educations");
			header.createCell(8).setCellValue("Other Location");
			header.createCell(9).setCellValue("Other Skills");
			header.createCell(10).setCellValue("Relevant Experience");
			header.createCell(11).setCellValue("Requirement Description");
			header.createCell(12).setCellValue("Requirement EndDate");
			header.createCell(13).setCellValue("Requirement Startdate");
			header.createCell(14).setCellValue("Requirement Status");
			header.createCell(15).setCellValue("Requirement Type");
			header.createCell(16).setCellValue("Total Experience");
			header.createCell(17).setCellValue("AddContact Id");
			header.createCell(18).setCellValue("Client Id");
			header.createCell(19).setCellValue("Created By Id");
			header.createCell(20).setCellValue("Modified By Id");
			header.createCell(21).setCellValue("Actual Revenue");
			header.createCell(22).setCellValue("City");
			header.createCell(23).setCellValue("Country");
			header.createCell(24).setCellValue("Expected Revenue");
			header.createCell(25).setCellValue("Industry");
			header.createCell(26).setCellValue("Job Desc");
			header.createCell(27).setCellValue("Job Type");
			header.createCell(28).setCellValue("Missed  Revenue");
			header.createCell(29).setCellValue("Skills Set");
			header.createCell(30).setCellValue("Revenue Per Position");
			header.createCell(31).setCellValue("State");
			header.createCell(32).setCellValue("Zip Code");
			header.createCell(33).setCellValue("Client Name");
			header.createCell(34).setCellValue("Contact First Name");
			header.createCell(35).setCellValue("Contact Email");
			header.createCell(36).setCellValue("Email");
			header.createCell(37).setCellValue("Contact Mobile");
			header.createCell(38).setCellValue("File Attachement");

			int rowCount = 0;
			for (Object[] obj : addRequimentDTO) {
				Row row = sheet.createRow(++rowCount);
				int columnCount = -1, col = 0;

				for (Object object : obj) {
					if (col != 37) {
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
					col++;
				}

			}

			try {
				StringBuilder pro = new StringBuilder(excelFilePath);

				String strDate = dateFormat.format(new Date());
				pro.append("BdmRequirementsDetails" + strDate + "File");

				file = new File(pro + ".xlsx");
				out = new FileOutputStream(file);
				workbook.write(out);

				byte[] encodedBytes = null;
				Path p = Paths.get(file.getAbsolutePath());
				encodedBytes = Files.readAllBytes(p);
				response = new Response(ExceptionMessage.StatusSuccess, encodedBytes);
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

		}

		/*
		 * try (FileOutputStream outputStream = new
		 * FileOutputStream("C:\\Users\\sajay\\Desktop\\final proj\\user.xlsx")) {
		 * workbook.write(outputStream); } catch (FileNotFoundException e) {
		 * e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
		 * }System.out.println(addRequimentDTO);response=new
		 * Response(ExceptionMessage.StatusSuccess,addRequimentDTO);
		 * 
		 */

		return response;

	}

	@Override
	@Transactional
	public Response updateReqDetails(BdmUpdate bdmUpdate) {

		StringBuilder builder = new StringBuilder("update bdmreq set");

		if (bdmUpdate.getIsHot() != 0 && bdmUpdate.getComment() != null) {
			builder.append(" isHot=" + bdmUpdate.getIsHot() + " , comment='" + bdmUpdate.getComment() + "'");

			if (bdmUpdate.getRegistrationId() == null && bdmUpdate.getClientId() == null
					&& bdmUpdate.getBdmId() == null) {
				return new Response(ExceptionMessage.DataIsEmpty, "please enter regId,clientId and bdmId");
			} else {
				builder.append(" where id=" + bdmUpdate.getBdmId() + " and client_id=" + bdmUpdate.getClientId()
						+ " and registrationId=" + bdmUpdate.getRegistrationId());
			}

			Query query = this.getEntityManager().createNativeQuery(builder.toString());

			int result = query.executeUpdate();
			if (result != 0)
				return new Response(ExceptionMessage.StatusSuccess, "details addedd to bdm");
		}

		System.out.println(builder.toString());

		return new Response(ExceptionMessage.Bad_Request);

	}
}
