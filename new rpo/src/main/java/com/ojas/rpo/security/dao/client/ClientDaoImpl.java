package com.ojas.rpo.security.dao.client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.Client;
import com.ojas.rpo.security.entity.ClientSearch;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.transfer.ClientsListTransfer;
import com.ojas.rpo.security.util.ClientChange;

public class ClientDaoImpl extends JpaDao<Client, Long> implements ClientDao {

	@Value("${excelFile}")
	private String excelFilePath;

	public ClientDaoImpl() {
		super(Client.class);
	}

	@Transactional(readOnly = true)
	public List<Client> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Client> criteriaQuery = builder.createQuery(Client.class);
		Root<Client> root = criteriaQuery.from(Client.class);
		criteriaQuery.orderBy(builder.desc(root.get("id")));
		TypedQuery<Client> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	/*
	 * Working for checking duplicates for the clientName,gmail and phone along with
	 * registrationId
	 */

	@Override
	@Transactional
	public List<String> chekduplicate(String clientName, String email, String phone, Long regId) {
		List<String> listData = new ArrayList<>();

		try {
			Query query = getEntityManager().createNativeQuery(
					"select clientName FROM client WHERE ( email=? OR phone=? OR clientName=? ) and registrationId =?");

			query.setParameter(1, email);
			query.setParameter(2, phone);
			query.setParameter(3, clientName);
			query.setParameter(4, regId);

			@SuppressWarnings("unchecked")
			List<String> results = query.getResultList();

			for (String cName : results) {
				if (clientName.equalsIgnoreCase(cName)) {
					listData.add(clientName);
				}
			}

		} catch (Exception e) {
			return null;
		}

		return listData;

	}

	/*
	 * working for the saving of the client
	 */

	@Transactional
	public Client save(Client client) {
		System.out.println("saving data ......");
		return this.getEntityManager().merge(client);
	}

	/*
	 * working for the getting client by using clientId and registrationId
	 */

	@Override
	public Client findByIdAndRegId(Long id, Long regId) {
		Predicate p = null;
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Client> cq = cb.createQuery(Client.class);

		Root<Client> r = cq.from(Client.class);

		p = cb.and(cb.equal(r.get("id"), id), cb.equal(r.get("registrationId"), regId));

		cq.where(p);
		TypedQuery<Client> tq = getEntityManager().createQuery(cq);
		if (tq != null && !tq.getResultList().isEmpty()) {

			return tq.getResultList().get(0);
		}

		return null;

	}

	/*
	 * Working for the finding and searching of the clients with pagination.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Response searchAllClientsByRegId(ClientSearch clientSearch) {
		Integer totalRecords = 0;
		SimpleDateFormat dateformat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
		Response response = null;
		List<Object[]> clientsList;
		List<ClientsListTransfer> responseList = new ArrayList<>();
		StringBuilder builder = new StringBuilder(
				"SELECT client.id," + "    client.clientName," + "    client.email," + "    client.endDate,"
						+ "    client.lastUpdatedDate," + "    client.leavesAllowed," + "    client.phone,"
						+ "    client.startDate," + "    client.tdspercentage," + "    client.billingModel_id,"
						+ "   ct.customerType," + "    client.paymentTerms_id," + "    client.about,"
						+ "    client.fax," + "    client.industry_id," + "    client.website," + "    client.others,"
						+ "    client.createdBy," + "    client.updatedBy," + "    client.accountManagerId,"
						+ "    client.contractFile," + "    client.leadId," + "    client.registrationId,"
						+ "    client.date,    " + "    concat(u2.firstname,u2.lastname) as leadName,"
						+ "        u1.name as amName" + " ,   client.source_id   ," + " u2.email as leadMail "
						+ " FROM testing.client client INNER JOIN customertype ct ON client.customerType_id = ct.id "
						+ "				INNER JOIN `user` u1 ON u1.id = client.accountManagerId "
						+ "				 INNER JOIN `user` u2 ON u2.id = client.leadId where registrationId=?");

		StringBuilder countSql = new StringBuilder(
				"SELECT COUNT(*) FROM testing.client client   INNER JOIN customertype ct ON client.customerType_id = ct.id INNER JOIN `user` u1 ON u1.id = client.accountManagerId "
						+ "							 INNER JOIN `user` u2 ON u2.id = client.leadId  where registrationId=? ");
		if (clientSearch.getClientId() != null) {
			builder.append(" and client.id=" + clientSearch.getClientId());
			countSql.append(" and client.id=" + clientSearch.getClientId());
		}
		if (clientSearch.getClientName() != null) {
			builder.append(" and client.clientName like '" + clientSearch.getClientName() + "'");
			countSql.append(" and client.clientName like '" + clientSearch.getClientName() + "'");
		}

		if (clientSearch.getAmName() != null) {
			builder.append(" and u1.name like '" + clientSearch.getAmName() + "%'");
			countSql.append(" and u1.name like '" + clientSearch.getAmName() + "%'");
		}
		if (clientSearch.getStartDate() != null) {
			builder.append(" and client.startDate='" + dateformat.format(clientSearch.getStartDate()) + "'");
			countSql.append(" and client.startDate='" + dateformat.format(clientSearch.getStartDate()) + "'");
		}
		if (clientSearch.getEndDate() != null) {
			builder.append(" and client.endDate='" + dateformat.format(clientSearch.getEndDate()) + "'");
			countSql.append(" and client.endDate='" + dateformat.format(clientSearch.getEndDate()) + "'");
		}

		int startingRow = 0;
		if (clientSearch.getPageNum() == 1) {
			startingRow = 0;
		} else {
			startingRow = ((clientSearch.getPageNum() - 1) * clientSearch.getPageSize());

		}
		if (clientSearch.isFlag()) {

			builder.append(" order by client.id DESC LIMIT " + startingRow + "," + clientSearch.getPageSize());
			Query query = null;
			try {
				// if (clientSearch.getFlag().equals(true)) {
				query = getEntityManager().createNativeQuery(builder.toString());
				query.setParameter(1, clientSearch.getRegistrationId());
				clientsList = query.getResultList();

				Query countQuery = this.getEntityManager().createNativeQuery(countSql.toString());

				countQuery.setParameter(1, clientSearch.getRegistrationId());
				Object countResult = countQuery.getSingleResult();
				totalRecords = Integer.valueOf(countResult.toString());

				if (clientsList.isEmpty()) {
					response = new Response(ExceptionMessage.DataIsEmpty, "No Content Found");
				} else {
					for (Object[] req : clientsList) {
						ClientsListTransfer transferObj = new ClientsListTransfer();
						transferObj.setId(Long.valueOf(req[0] + ""));
						transferObj.setClientName(req[1] + "");
						transferObj.setEmail(req[2] + "");
						transferObj.setEndDate((Date) req[3]);
						transferObj.setLastUpdatedDate((Date) req[4]);
						transferObj.setLeavesAllowed(req[5] + "");
						transferObj.setPhone(Long.valueOf(req[6] + ""));
						transferObj.setStartDate((Date) req[7]);
						transferObj.setTdspercentage(req[8] + "");
						transferObj.setBillingModel(Long.valueOf(req[9] + ""));
						transferObj.setCustomerType(req[10] + "");
						transferObj.setPaymentTerms(Long.valueOf(req[11] + ""));
						transferObj.setAbout(req[12] + " ");
						transferObj.setFax(req[13] + "");
						transferObj.setIndustry(req[14] + "");
						transferObj.setWebsite(req[15] + "");
						transferObj.setOthers(req[16] + "");
						if (req[17] != null)
							transferObj.setCreatedBy(Long.valueOf(req[17] + ""));
						if (req[18] != null)
							transferObj.setUpdatedBy(Long.valueOf(req[18] + ""));
						if (req[19] != null)
							transferObj.setAccountManagerId(Long.valueOf(req[19] + ""));
						if (req[20] != null)
							transferObj.setContractFile(req[20] + "");
						if (req[21] != null)
							transferObj.setLeadId(Long.valueOf(req[21] + ""));
						if (req[22] != null)
							transferObj.setRegistrationId(Long.valueOf(req[22] + ""));
						if (req[23] != null)
							transferObj.setAmName(req[25] + "");
						if (req[24] != null)
							transferObj.setLeadName(req[24] + "");
						if (req[25] != null)
							transferObj.setDate((Date) req[23]);
						if (req[26] != null)
							transferObj.setSource(Long.valueOf(req[26] + ""));
						if (req[27] != null)
							transferObj.setLeadEmail(req[27] + "");

						responseList.add(transferObj);

					}
					response = new Response(ExceptionMessage.OK, responseList);
				}

				Integer totalPages = 1;
				if (totalRecords == 0) {
					response.setTotalPages(0);
				} else {
					totalPages = totalRecords / clientSearch.getPageSize();

					totalPages = (totalRecords % clientSearch.getPageSize() > 0) ? totalPages + 1 : totalPages;
				}
				response.setTotalPages(totalPages);
				response.setTotalRecords(totalRecords);

				return response;
			} catch (PersistenceException pe) {
				pe.printStackTrace();
				return new Response(ExceptionMessage.Exception, "500",
						"Unable to Retrieve Customers List. " + " " + pe.getLocalizedMessage());
			} catch (Exception e) {
				e.printStackTrace();
				return new Response(ExceptionMessage.Exception, "500",
						" Unable Retrieve Customers List " + e.getLocalizedMessage());
			}

		} else {

			FileOutputStream out = null;
			File file = null;
			XSSFWorkbook workbook = null;
			Query query = null;
			try {
				query = getEntityManager().createNativeQuery(builder.toString());
				query.setParameter(1, clientSearch.getRegistrationId());
				clientsList = query.getResultList();

				workbook = new XSSFWorkbook();
				XSSFSheet spreadsheet = workbook.createSheet("Client Details ");

				int rowCount = 1;

				Row header = spreadsheet.createRow(rowCount);
				header.createCell(0).setCellValue("Id");
				header.createCell(1).setCellValue("Client Name");
				header.createCell(2).setCellValue("Email");
				header.createCell(3).setCellValue("End Date");
				header.createCell(4).setCellValue("Last Updated Date");
				header.createCell(5).setCellValue("Leaves Allowed");
				header.createCell(6).setCellValue("Phone");
				header.createCell(7).setCellValue("Start Date");
				header.createCell(8).setCellValue("TDS Percentage");
				header.createCell(9).setCellValue("Billing Model");
				header.createCell(10).setCellValue("Customer Type");
				header.createCell(11).setCellValue("Payment Terms");
				header.createCell(12).setCellValue("About");
				header.createCell(13).setCellValue("Fax");
				header.createCell(14).setCellValue("Industry");
				header.createCell(15).setCellValue("Website");
				header.createCell(16).setCellValue("Created By");
				header.createCell(17).setCellValue("Updated By");
				header.createCell(18).setCellValue("Account Manager Id");
				header.createCell(19).setCellValue("Lead Id");
				header.createCell(20).setCellValue("Registration Id");
				header.createCell(21).setCellValue("Date");
				header.createCell(22).setCellValue("AM Mail");
				header.createCell(23).setCellValue("Lead Mail");
				header.createCell(24).setCellValue("Source Id");

				for (Object[] cell : clientsList) {
					rowCount++;
					Row row1 = spreadsheet.createRow(++rowCount);
					int colCount = -1, col = 0;
					for (Object object : cell) {
						if (col != 16 && col != 20) {
							Cell cell1 = row1.createCell(++colCount);
							if (object instanceof Integer)
								cell1.setCellValue(Long.valueOf(object + ""));
							else if (object instanceof String)
								cell1.setCellValue(object + "");
							else if (object instanceof Long)
								cell1.setCellValue(Long.valueOf(object + ""));
							else if (object instanceof Boolean)
								cell1.setCellValue(Boolean.valueOf(object + ""));
							else if (object instanceof Date)
								cell1.setCellValue((Date) object + " ");
							else
								cell1.setCellValue(object + "");
						}
						col++;
					}
				}

				StringBuilder pro = new StringBuilder(excelFilePath);

				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
				String strDate = dateFormat.format(new Date());
				pro.append("ClientDetails" + strDate + "File");

				file = new File(pro + ".xlsx");
				out = new FileOutputStream(file);

				workbook.write(out);

				byte[] encodedBytes = null;
				Path p = Paths.get(file.getAbsolutePath());
				encodedBytes = Files.readAllBytes(p);
				response = new Response(ExceptionMessage.StatusSuccess, encodedBytes);

			} catch (Exception e) {
				response = new Response(ExceptionMessage.Exception, e.getLocalizedMessage());
			} finally {
				try {

					out.close();

					if (file.exists()) {
						file.delete();
					}

					workbook.close();
				} catch (IOException e) {
					response = new Response(ExceptionMessage.Exception, e.getLocalizedMessage());
				}
			}
			return response;
		}
	}

	@Override
	@Transactional
	public Response changeClientOwner(ClientChange clientChange) {
		StringBuilder builder = new StringBuilder("update client set ");
		if (clientChange.getLeadId() == null && clientChange.getAccountManagerId() == null) {
			return new Response(ExceptionMessage.DataIsEmpty, "lead and AM ids not to be null");
		}
		if (clientChange.getLeadId() != null) {
			builder.append(" leadId=" + clientChange.getLeadId());
		}

		if (clientChange.getLeadId() != null && clientChange.getAccountManagerId() != null) {
			builder.append(",");
		}
		if (clientChange.getAccountManagerId() != null)
			builder.append(" accountManagerId=" + clientChange.getAccountManagerId());

		if (clientChange.getRegistrationId() != null && clientChange.getClientId() != null) {
			builder.append(" where registrationId=" + clientChange.getRegistrationId() + " and id="
					+ clientChange.getClientId());

		} else {
			return new Response(ExceptionMessage.Bad_Request, "RegistrationId and clientId must require");
		}
		Query query = this.getEntityManager().createNativeQuery(builder.toString());

		System.out.println(query.toString());

		int result = query.executeUpdate();

		if (result != 0)
			return new Response(ExceptionMessage.StatusSuccess, "Client Updated");
		else {
			return new Response(ExceptionMessage.DataIsNotSaved, "client Not updated");
		}

	}
}
