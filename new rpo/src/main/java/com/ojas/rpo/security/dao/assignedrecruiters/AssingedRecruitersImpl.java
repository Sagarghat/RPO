package com.ojas.rpo.security.dao.assignedrecruiters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.dao.sendemail.MailSender;
import com.ojas.rpo.security.entity.AssignedRecruiters;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.util.AssignedRecruitersMapper;
import com.ojas.rpo.security.util.EmailEntity;
import com.ojas.rpo.security.util.RecruiterTransfer;

@Repository
public class AssingedRecruitersImpl extends JpaDao<AssignedRecruiters, Long> implements AssingnedRecruitersDao {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	public AssingedRecruitersImpl() {
		super(AssignedRecruiters.class);
	}

	@Value("${excelFile}")
	private String excelFilePath;

	@Autowired
	private MailSender javaMailSender;

	@Override
	@Transactional
	public List<AssignedRecruiters> create(AssignedRecruitersMapper mapper) {
		AssignedRecruiters recruiters = new AssignedRecruiters();
		AssignedRecruiters assignedRecruiters = new AssignedRecruiters();
		String cc = null;
		Long[] ids = mapper.getRecruiterIdsList();
		List<AssignedRecruiters> assignedRecruitersList = new ArrayList<>();
		try {
			for (Long id : ids) {
				recruiters.setAssignedDate(new Date());
				recruiters.setClientId(mapper.getClientId());
				recruiters.setRequirementId(mapper.getRequirementId());
				recruiters.setUserId(mapper.getUserId());
				recruiters.setRegistrationId(mapper.getRegistrationId());

				recruiters.setRecruiterId(id);
				StringBuilder builder = new StringBuilder(
						"select count(*) from assignedrecruiters where registrationId=? and (recruiterId=? and userId=? and clientid=? and requirementId=?) ");
				
				Query query = this.getEntityManager().createNativeQuery(builder.toString());
				query.setParameter(1, mapper.getRegistrationId());
				query.setParameter(2, id);
				query.setParameter(3, mapper.getUserId());
				query.setParameter(4, mapper.getClientId());
				query.setParameter(5, mapper.getRequirementId());
				Long singleResult = Long.valueOf(query.getSingleResult() + "");
				System.out.println(singleResult);

				if (singleResult == 0) {
					assignedRecruiters = this.getEntityManager().merge(recruiters);
					assignedRecruitersList.add(assignedRecruiters);
				}

			}
			String emailQuery = "select u.email as CCEmail,req.nameOfrequirement,c.clientName from user u inner join user u1  inner join  bdmreq req inner join client c where u.id=?  and req.id=? and c.id=?";
			Query query = this.getEntityManager().createNativeQuery(emailQuery);
			query.setParameter(1, mapper.getUserId());
			query.setParameter(2, mapper.getRequirementId());
			query.setParameter(3, mapper.getClientId());
			List<Object[]> resultArray = query.getResultList();
			StringBuilder toEmails = new StringBuilder();
			List<Object> toEmailList = new ArrayList<>();
			int i = 0;
			for (Long id : ids) {
				Query toEmailQuery = this.getEntityManager().createNativeQuery("select email from user where id=? ");
				toEmailQuery.setParameter(1, id);

				toEmailList = toEmailQuery.getResultList();

				for (Object toemail : toEmailList) {
					toEmails.append(toemail);
					if (toEmailList.size() != ids.length)
						toEmails.append(",");
					i++;

				}
			}
			EmailEntity entity = new EmailEntity();
			for (Object[] obj : resultArray) {
				if (obj[0] != null)
					cc = obj[0] + "";

				entity.setCc(cc);
				entity.setTo(toEmails.toString());
				String subject = "Assigning recruiter";
				String mailMessage = " The Requirement of  <h1>" + obj[1] + " </h1> with requiremnet id:"
						+ mapper.getRequirementId() + " and for the client <h1 style=color:blue>" + obj[2];

				entity.setMessageBody(mailMessage);
				entity.setMessagesubject(subject);

			}
			if (entity != null && javaMailSender != null)
				javaMailSender.sendMail(entity);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return assignedRecruitersList;

	}

	@Override
	@Transactional
	public Response deleteRecruitersById(AssignedRecruitersMapper mapper) {
		StringBuilder builder = new StringBuilder("delete from assignedrecruiters where ");
		int i = 0;
		try {
			if (mapper.getRequirementId() != null)
				builder.append(" requirementId=" + mapper.getRequirementId());
			else
				return new Response(ExceptionMessage.DataIsEmpty, "requirement id must required");

			if (mapper.getClientId() != null)
				builder.append(" and clientId=" + mapper.getClientId());
			else
				return new Response(ExceptionMessage.DataIsEmpty, "Client Id must required");
			if (mapper.getRegistrationId() != null)
				builder.append(" and registrationId=" + mapper.getRegistrationId());
			else
				return new Response(ExceptionMessage.DataIsEmpty, "RegistrationId must not be null");
			StringBuilder deletedBuilder = null;
			if (mapper.getRecruiterIdsList() == null)
				return new Response(ExceptionMessage.DataIsEmpty, "recruiter ids must not be null");
			else {
				for (Long id : mapper.getRecruiterIdsList()) {
					deletedBuilder = new StringBuilder(builder.toString());
					deletedBuilder.append(" and recruiterId=" + id);
					Query query = this.getEntityManager().createNativeQuery(deletedBuilder.toString());
					if (query.executeUpdate() != 0)
						i++;
					System.out.println(i);

				}
			}

			return new Response(ExceptionMessage.StatusSuccess, i + " recruiters are deleted");
		} catch (Exception e) {
			logger.info("Error came in deleted");
			return new Response(ExceptionMessage.Bad_Request);
		}
	}

	@Override
	public Response getByRecId(AssignedRecruitersMapper mapper) {
		logger.info("starting of getById");
		try {

			String getQuery = "select c.clientName,r.requirementType,u.name as userName,u1.name  as recruiterName from  testing.client c  "
					+ " inner join testing.assignedrecruiters res inner join testing.user u inner join testing.user u1 inner join  testing.bdmreq r "
					+ "on c.id=?   and    u.id=? and u1.id in (select res.recruiterId from testing.assignedrecruiters where res.requirementId=?) and r.id=? and res.registrationId=?";
			Query query = this.getEntityManager().createNativeQuery(getQuery);
			List<RecruiterTransfer> transferList = new ArrayList<>();
			query.setParameter(1, mapper.getClientId());
			query.setParameter(2, mapper.getUserId());
			query.setParameter(3, mapper.getRequirementId());
			query.setParameter(4, mapper.getRequirementId());
			query.setParameter(5, mapper.getRegistrationId());

			List<Object[]> results = query.getResultList();

			for (Object[] objs : results) {
				RecruiterTransfer transferObj = new RecruiterTransfer();
				transferObj.setClientName(objs[0] + "");
				transferObj.setRequirementName(objs[1] + "");
				transferObj.setUserName(objs[2] + "");
				transferObj.setRecruitersName(objs[3] + "");
				transferList.add(transferObj);
			}
			System.out.println(transferList.size());
			if (!transferList.isEmpty())
				return new Response(ExceptionMessage.StatusSuccess, transferList, "recruiters names retireved");

		} catch (Exception e) {
			e.printStackTrace();

			return new Response(ExceptionMessage.Bad_Request);
		}
		return null;
	}

	@Override
	@Transactional
	public Response getAllRecruiters(AssignedRecruitersMapper mapper) {
		logger.info("starting of getAll");
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		List<RecruiterTransfer> tranferObjList = new ArrayList<>();
		Integer totalRecords = 0;
		Integer totalPages = 1;
		Response response = new Response();

		if (mapper.getRegistrationId() == null || mapper.getUserId() == null || mapper.getPageNum() == null
				|| mapper.getPageSize() == null) {
			return new Response(ExceptionMessage.DataIsEmpty, "given data is not sufficient");
		} else {

			StringBuilder countBuilder = new StringBuilder(
					"select  count(*) from assignedrecruiters res inner join bdmreq req on req.id=res.requirementId inner join client c on c.id=res.clientId "
							+ "							inner join user u on u.id=res.userId inner join user u1 on u1.id=res.recruiterId where res.registrationId=? and res.userId=?  ");

			StringBuilder resultBuilder = new StringBuilder(

					"    select  req.nameOfRequirement,req.id as reqid,c.id as clientid,c.clientName,res.assignedDate,u.name,u1.email as email ,res.recruiterId "
							+ ",u1.name as recruiterName,u.name as assignedBY from assignedrecruiters res inner join bdmreq req on req.id=res.requirementId  inner join client c on c.id=res.clientId  "
							+ " inner join user u on u.id=res.userId inner join user u1 on u1.id=res.recruiterId where res.registrationId=? and res.userId=?");
			if (mapper.getRequirementId() != null) {
				resultBuilder.append(" and res.requirementId=" + mapper.getRequirementId());
				countBuilder.append(" and res.requirementId=" + mapper.getRequirementId());
			}
			/*
			 * if (mapper.getClientId() != null) { resultBuilder.append(" and res.clientId="
			 * + mapper.getClientId()); countBuilder.append(" and res.clientId=" +
			 * mapper.getClientId()); }
			 */
			if (mapper.getAssignedDate() != null) {
				resultBuilder
						.append(" and res.assignedDate like '" + dateformat.format(mapper.getAssignedDate()) + "%'");
				countBuilder
						.append(" and res.assignedDate  like '" + dateformat.format(mapper.getAssignedDate()) + "%'");
			}
			if (mapper.getRequirementName() != null && !mapper.getRequirementName().isEmpty()) {
				resultBuilder.append(" and  req.nameOfRequirement like '" + mapper.getRequirementName() + "%'");
				countBuilder.append(" and  req.nameOfRequirement like '" + mapper.getRequirementName() + "%'");
			}
			if (mapper.getRecruiterName() != null && !mapper.getRecruiterName().isEmpty()) {
				resultBuilder.append(" and u1.name like '" + mapper.getRecruiterName() + "%'");
				countBuilder.append(" and u1.name like '" + mapper.getRecruiterName() + "%'");
			}

			if (mapper.getClientId() != null) {
				resultBuilder.append(" and c.id=" + mapper.getClientId());
				countBuilder.append(" and c.id=" + mapper.getClientId());
			}

			if (mapper.getClientName() != null && !mapper.getClientName().isEmpty()) {
				resultBuilder.append(" and c.clientName like '" + mapper.getClientName() + "%'");
				countBuilder.append(" and c.clientName like '" + mapper.getClientName() + "%'");
			}

			if (mapper.isFlag()) {

				int startingRow = 0;
				if (mapper.getPageNum() == 1) {
					startingRow = 0;
				} else {
					startingRow = ((mapper.getPageNum() - 1) * mapper.getPageSize());

				}

				resultBuilder.append(" order by c.id DESC LIMIT " + startingRow + "," + mapper.getPageSize());

				// getting all the records from the table.
				Query resultQuery = this.getEntityManager().createNativeQuery(resultBuilder.toString());
				resultQuery.setParameter(1, mapper.getRegistrationId());
				resultQuery.setParameter(2, mapper.getUserId());

				List<Object[]> resultsList = resultQuery.getResultList();

				System.out.println("Starting row" + startingRow);

				// counting the no of records.
				Query count = this.getEntityManager().createNativeQuery(countBuilder.toString());
				// assingning values to resultBuilder

				count.setParameter(1, mapper.getRegistrationId());
				count.setParameter(2, mapper.getUserId());

				Object result = count.getSingleResult();

				for (Object[] value : resultsList) {
					RecruiterTransfer transferObj = new RecruiterTransfer();

					transferObj.setRequirementName(value[0] + "");
					logger.info(transferObj.getRecruitersName());
					transferObj.setRequirementId(Long.valueOf(value[1] + ""));
					transferObj.setClientId(Long.valueOf(value[2] + ""));
					transferObj.setClientName(value[3] + "");
					transferObj.setAssignedDate((Date) value[4]);
					transferObj.setUserName(value[5] + "");
					transferObj.setEmail(value[6] + "");
					transferObj.setRecruiterId(Long.valueOf(value[7] + ""));
					transferObj.setRecruitersName(value[8] + "");

					transferObj.setAssignedBy(value[9] + "");

					tranferObjList.add(transferObj);
				}
				if (tranferObjList.isEmpty())
					return new Response(ExceptionMessage.DataIsEmpty, "Data is Empty");
				else {

					totalRecords = Integer.valueOf(result.toString());

					if (totalRecords == 0) {
						response.setTotalPages(0);
					} else if (totalRecords == 1 || totalRecords < mapper.getPageSize()) {
						response.setTotalPages(1);
						response.setTotalRecords(totalRecords);
					} else {
						totalPages = totalRecords / mapper.getPageSize();

						totalPages = (totalRecords % mapper.getPageSize() > 0) ? totalPages + 1 : totalPages;

					}
				}

				response.setTotalPages(totalPages);
				response.setTotalRecords(totalRecords);
				response.setResult(tranferObjList);
				return response;
			} else {
				FileInputStream inputStream = null;
				FileOutputStream out = null;
				File file = null;
				XSSFWorkbook workbook = null;
				try {
					Query resultQuery = this.getEntityManager().createNativeQuery(resultBuilder.toString());
					resultQuery.setParameter(1, mapper.getRegistrationId());
					resultQuery.setParameter(2, mapper.getUserId());

					List<Object[]> resultsList = resultQuery.getResultList();
					workbook = new XSSFWorkbook();
					XSSFSheet spreadsheet = workbook.createSheet("Assigned Recruiters Details ");

					int rowCount = 0;
					Row header = spreadsheet.createRow(rowCount);
					header.createCell(0).setCellValue("Recruiter Name");
					header.createCell(1).setCellValue("Requirement Name");
					header.createCell(2).setCellValue("Client Name");
					header.createCell(3).setCellValue("User Name");
					header.createCell(4).setCellValue("Recruiter Id");
					header.createCell(5).setCellValue("Requirement Id");
					header.createCell(6).setCellValue("Client Id");
					header.createCell(7).setCellValue("Email");
					header.createCell(8).setCellValue("Assigned Date");
					header.createCell(9).setCellValue("Assigned By");

					for (Object[] cell : resultsList) {

						Row row1 = spreadsheet.createRow(++rowCount);
						int colCount = -1, count = 0;

						for (Object object : cell) {
							if (count != 11) {
								Cell cell1 = row1.createCell(++colCount);
								if (object instanceof Integer)
									cell1.setCellValue(Long.valueOf(object + ""));
								else if (object instanceof String)
									cell1.setCellValue(object + "");
								else if (object instanceof Long)
									cell1.setCellValue(Long.valueOf(object + ""));
								else
									cell1.setCellValue(object + "");
							}
							count++;
						}
					}
					StringBuilder pro = new StringBuilder(excelFilePath);

					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					String strDate = dateFormat.format(new Date());
					pro.append("AssignedRecruitersDetails" + strDate + "File");

					file = new File(pro + ".xlsx");

					out = new FileOutputStream(file);

					workbook.write(out);

					byte[] b = new byte[1024];
					inputStream = new FileInputStream(file);

					byte[] encodedBytes = null;
					while ((inputStream.read(b)) != -1) {
						String name = (new String(b));
						encodedBytes = Base64.encodeBase64(name.getBytes());
					}
					response = new Response(ExceptionMessage.StatusSuccess, encodedBytes);
				} catch (Exception e) {
					response = new Response(ExceptionMessage.Exception, e.getLocalizedMessage());
				} finally {
					try {
						out.close();
						inputStream.close();

						if (file.exists()) {
							file.delete();
						}

						workbook.close();
					} catch (Exception e) {
						response = new Response(ExceptionMessage.Bad_Request, e.getLocalizedMessage());
					}
				}
			}
		}

		return response;

	}

	@Override
	public Boolean checkduplicates(AssignedRecruitersMapper mapper) {
		StringBuilder builder = new StringBuilder(
				"select count(*) from assignedrecruiters where registrationId=? and (recruiterId=? and userId=? and clientid=? and requirementId=? ");

		Query query = this.getEntityManager().createNativeQuery(builder.toString());
		query.setParameter(1, mapper.getRegistrationId());

		return false;
	}

	@Override
	public Date getAssignedDate(AssignedRecruitersMapper mapper) {
		Date assignedDate = null;
		StringBuilder builder = new StringBuilder(
				"select assignedDate from assignedrecruiters where registrationId=? and   userId=? and   requirementId=? ");

		Query query = this.getEntityManager().createNativeQuery(builder.toString());
		query.setParameter(1, mapper.getRegistrationId());
		query.setParameter(2, mapper.getUserId());
		query.setParameter(3, mapper.getRequirementId());
		try {
			Object object = query.getSingleResult();

			if (object instanceof Date) {
				assignedDate = (Date) object;
			}
		} catch (Exception e) {

		}
		return assignedDate;
	}

}
