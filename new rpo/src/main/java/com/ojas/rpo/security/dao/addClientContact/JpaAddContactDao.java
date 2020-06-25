package com.ojas.rpo.security.dao.addClientContact;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

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
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.AddContact;
import com.ojas.rpo.security.entity.AddContactList;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.transfer.ContactListTransfer;
import com.ojas.rpo.security.util.AddContactSearch;
import com.ojas.rpo.security.util.ReadingAppProps;

public class JpaAddContactDao extends JpaDao<AddContact, Long> implements AddContactDao {

	@Value("${excelFile}")
	private String excelFilePath;

	public JpaAddContactDao() {
		super(AddContact.class);
	}

	@Override
	@Transactional
	public AddContact save(AddContact entity) {
		return this.getEntityManager().merge(entity);
	}

	@Override
	@Transactional(readOnly = true)
	public List<AddContact> findContactByClientId(Long id, Long regId) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<AddContact> cq = cb.createQuery(AddContact.class);
		Root<AddContact> e = cq.from(AddContact.class);
		Join<AddContact, AddContact> r = e.join("client", JoinType.LEFT);
		Predicate p = cb.and(cb.equal(r.get("id"), id), cb.equal(e.get("registrationId"), regId));
		cq.where(p);
		TypedQuery<AddContact> tq = getEntityManager().createQuery(cq);
		return tq.getResultList();
	}

	@Override
	@Transactional
	public List<AddContact> getByContactId(Long id, Long regId) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<AddContact> cq = cb.createQuery(AddContact.class);
		Root<AddContact> contact = cq.from(AddContact.class);
		Predicate predicate = cb.and(cb.equal(contact.get("id"), id), cb.equal(contact.get("registrationId"), regId));
		cq.where(predicate);
		TypedQuery<AddContact> createQuery = getEntityManager().createQuery(cq);
		return createQuery.getResultList();

	}

	@Override
	@Transactional(readOnly = true)
	public List<AddContact> findActiveContactByClientId(Long id, String role, Long userId) {
		Predicate p = null;
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<AddContact> cq = cb.createQuery(AddContact.class);
		Root<AddContact> e = cq.from(AddContact.class);
		Join<AddContact, AddContact> r = e.join("client", JoinType.LEFT);
		if (role.equalsIgnoreCase("Lead")) {
			p = cb.and(cb.equal(r.get("id"), id), cb.equal(e.get("status"), "Active"),
					cb.equal(e.get("lead_id"), userId));
		} else if (role.equalsIgnoreCase("AM")) {
			p = cb.and(cb.equal(r.get("id"), id), cb.equal(e.get("status"), "Active"),
					cb.equal(e.get("accountManger_id"), userId));
		} else if (role.equalsIgnoreCase("BDM")) {
			p = cb.and(cb.equal(r.get("id"), id), cb.equal(e.get("status"), "Active"),
					cb.equal(e.get("primaryContact_id"), userId));
		}

		cq.where(p);
		TypedQuery<AddContact> tq = getEntityManager().createQuery(cq);
		return tq.getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public Response findContactByBdmId(Long id, String role, String sortField, String sortOrder, String searchField,
			String searchText, Integer pageNo, Integer pageSize) {
		Response response = null;
		Query query = null;
		Query query1 = null;
		int totalRecords = 0;
		String sql = "SELECT a.contact_Name,a.mobile,a.email,c.clientName,a.id , u1.firstName as bdm ,u2.firstName as secbdm, "
				+ " u3.firstName as amname ,u4.firstName as leadname ,a.lastUpdatedDate FROM `addcontact` a INNER JOIN `client` c "
				+ "on a.client_id=c.id " + "INNER JOIN `user` u1 ON  u1.id = a.primaryContact_id "
				+ "INNER JOIN `user` u2 ON u2.id = a.secondaryContact_id "
				+ "INNER JOIN `user` u3 ON u3.id = a.accountManger_id " + " INNER JOIN `user` u4 ON u4.id = a.lead_id ";
		StringBuilder sqlbuilder = new StringBuilder(sql);

		if (null != sortField && !sortField.isEmpty() && !sortField.equalsIgnoreCase("undefined")) {
			if (sortField.equalsIgnoreCase("contactName")) {
				sortField = "a.contact_Name";
			}
			if (sortField.equalsIgnoreCase("clientName")) {
				sortField = "c.clientName";
			}
			if (sortField.equalsIgnoreCase("email")) {
				sortField = "a.email";
			}
			if (sortField.equalsIgnoreCase("mobile")) {
				sortField = "a.mobile";
			}
			if (sortField.equalsIgnoreCase("id")) {
				sortField = "a.id";
			}
		} else {
			sortField = "a.lastUpdatedDate";
		}

		if (sortOrder.equalsIgnoreCase("ASC")) {
			sortOrder = "ASC";
		} else {
			sortOrder = "DESC";
		}

		if (null != searchField && !searchField.isEmpty() && !searchField.equalsIgnoreCase("undefined")) {
			if (searchField.equalsIgnoreCase("contactName")) {
				searchField = "a.contact_Name";
			}
			if (searchField.equalsIgnoreCase("clientName")) {
				searchField = "c.clientName";
			}
			if (searchField.equalsIgnoreCase("email")) {
				searchField = "a.email";
			}
			if (searchField.equalsIgnoreCase("mobile")) {
				searchField = "a.mobile";
			}
			if (searchField.equalsIgnoreCase("id")) {
				searchField = "a.id";
			}
		}

		int startingRow = 0;
		if (pageNo == 1) {
			startingRow = 0;
		} else {
			startingRow = ((pageNo - 1) * pageSize);
		}

		try {
			if (role.equalsIgnoreCase("BDM")) {
				sqlbuilder.append(" WHERE (a.primaryContact_id=? OR a.secondaryContact_id=?) ");

				if (null != searchField && !searchField.equals("undefined") && null != searchText
						&& !searchText.equals("undefined") && !searchText.isEmpty()) {
					sqlbuilder.append(" AND " + searchField + " LIKE '%" + searchText + "%' ORDER BY " + sortField + " "
							+ sortOrder + " LIMIT " + startingRow + "," + pageSize);
				} else {
					sqlbuilder.append(
							" ORDER BY " + sortField + " " + sortOrder + " LIMIT " + startingRow + "," + pageSize);
				}

				System.out.println("query = " + sqlbuilder.toString());

				String queryCount = "SELECT count(*) FROM addcontact a INNER JOIN client c on a.client_id=c.id  "
						+ " WHERE (a.primaryContact_id=? OR a.secondaryContact_id=?)";

				query1 = getEntityManager().createNativeQuery(queryCount.toString());
				query1.setParameter(1, id);
				query1.setParameter(2, id);
				totalRecords = Integer.parseInt(query1.getSingleResult().toString());
				query = getEntityManager().createNativeQuery(sqlbuilder.toString());
				query.setParameter(1, id);
				query.setParameter(2, id);
			} else if (role.equalsIgnoreCase("AM")) {

				sqlbuilder.append(" WHERE a.accountManger_id=? ");
				if (null != searchField && !searchField.equals("undefined") && null != searchText
						&& !searchText.equals("undefined") && !searchText.isEmpty()) {
					sqlbuilder.append(" AND " + searchField + " LIKE '%" + searchText + "%' ORDER BY " + sortField + " "
							+ sortOrder + " " + " LIMIT " + startingRow + "," + pageSize);
				} else {
					sqlbuilder.append(
							" ORDER BY " + sortField + " " + sortOrder + " LIMIT " + startingRow + "," + pageSize);
				}

				String countQuery = "SELECT count(*) FROM `addcontact` a INNER JOIN `client` c on a.client_id=c.id  WHERE a.accountManger_id=?";
				query1 = getEntityManager().createNativeQuery(countQuery.toString());
				query1 = getEntityManager().createNativeQuery(countQuery.toString());
				query1.setParameter(1, id);
				totalRecords = Integer.parseInt(query1.getSingleResult().toString());

				query = getEntityManager().createNativeQuery(sqlbuilder.toString());
				query.setParameter(1, id);
			} else if (role.equalsIgnoreCase("Lead")) {

				sqlbuilder.append(" WHERE a.lead_id=? ");
				if (null != searchField && !searchField.equals("undefined") && null != searchText
						&& !searchText.equals("undefined") && !searchText.isEmpty()) {
					sqlbuilder.append(" AND " + searchField + " LIKE '%" + searchText + "%' ORDER BY " + sortField + " "
							+ sortOrder + " " + " LIMIT " + startingRow + "," + pageSize);
				} else {
					sqlbuilder.append(
							" ORDER BY " + sortField + " " + sortOrder + " LIMIT " + startingRow + "," + pageSize);
				}

				String countQuery = "SELECT count(*) FROM `addcontact` a INNER JOIN `client` c on a.client_id=c.id  WHERE a.lead_id=?";
				query1 = getEntityManager().createNativeQuery(countQuery.toString());
				query1 = getEntityManager().createNativeQuery(countQuery.toString());
				query1.setParameter(1, id);
				totalRecords = Integer.parseInt(query1.getSingleResult().toString());

				query = getEntityManager().createNativeQuery(sqlbuilder.toString());
				query.setParameter(1, id);
			}

			List<Object[]> results = query.getResultList();
			List<ContactListTransfer> contactList = new ArrayList<ContactListTransfer>();
			if (!results.isEmpty()) {
				for (Object[] data : results) {
					ContactListTransfer contact = new ContactListTransfer();

					contact.setContactName(data[0] + "");
					contact.setMobile((String) data[1]);
					contact.setEmail(data[2] + "");
					contact.setClientName(data[3] + "");
					contact.setId(data[4] + "");
					contact.setBdmName(data[5] + "");
					contact.setAmName(data[7] + "");
					contact.setLeadName(data[8] + "");
					contact.setLastUpdatedDate((Date) data[9]);
					contactList.add(contact);
				}

				response = new Response(ExceptionMessage.OK, contactList);
			} else {
				response = new Response(ExceptionMessage.DataIsEmpty, "No Contacts Found");
			}
			response.setTotalRecords(totalRecords);

			if (totalRecords == 0) {
				response.setTotalRecords(totalRecords);
				response.setTotalPages(0);
			} else {
				Integer totalPages = Integer.valueOf(totalRecords) / pageSize;
				totalPages = (totalPages == 0) ? totalPages : totalPages + 1;
				response.setTotalPages(totalPages);
			}

		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "500",
					" Unable Retrieve Contact List Due to following Exception : " + e.getMessage());
		}

		return response;

	}

	@Override
	@Transactional
	public int updatingStatus(Long id, String status) {
		int i = 0;
		if (status != null) {
			Query q = getEntityManager().createNativeQuery("update addcontact set status=?  where id =?");
			q.setParameter(1, status);
			q.setParameter(2, id);
			i = q.executeUpdate();

		}

		return i;

	}

	@Transactional(readOnly = true)
	@Override
	public List<String> findActiveContactByEmail(Long clientId, String email, Long regId) {
		List<String> listData = new ArrayList<>();
		Query query = null;
		String sql = "SELECT email from addcontact where email='" + email + "' and client_id=" + clientId
				+ " and registrationId=" + regId;
		query = getEntityManager().createNativeQuery(sql);

		// @SuppressWarnings("unchecked")
		List<String> results = query.getResultList();

		return results;

	}

	@Transactional(readOnly = true)
	@Override
	public List<Object[]> findActiveContactByEmailList(Long clientId, String email) {
		Query query = null;
		String sql = "SELECT id,client_id from addcontact where email='" + email + "' and client_id=" + clientId;
		query = getEntityManager().createNativeQuery(sql);
		List<Object[]> results = query.getResultList();

		return results;

	}

	@SuppressWarnings("unchecked")
	@Override
	public Response searchAll(String result, Integer pageNo, Integer pageSize) {
		Response response = null;
		Integer totalRecords = 0;
		System.out.println("---------------" + result);

		String query = "from AddContact ac where ";
		query = query.concat(result);
		try {
			Query searchAllQuery = this.getEntityManager().createQuery(query);
			searchAllQuery.setFirstResult(pageNo);
			searchAllQuery.setMaxResults(pageSize);
			System.out.println(searchAllQuery.getResultList());
			String countSql = "SELECT COUNT(*) FROM addcontact as c ";
			Query countQuery = this.getEntityManager().createNativeQuery(countSql);

			Object countResult = countQuery.getSingleResult();
			totalRecords = Integer.valueOf(countResult.toString());

			List findAll = searchAllQuery.getResultList();
			System.out.println(findAll.isEmpty() + "   and  " + findAll);
			if (findAll.isEmpty()) {
				response = new Response(ExceptionMessage.DataIsEmpty, "No Content Found");
				response.setErrorCode("204");
				response.setErrorMessage("No Contact");
			} else {
				response = new Response(ExceptionMessage.OK, findAll);
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
	public boolean findEmialDuplicates(String email, Long id) {
		String sql = "select id from addcontact where email = " + "'+email+'";
		List resultList = getEntityManager().createNativeQuery(sql).getResultList();
		if (resultList.size() > 1)
			return false;
		else
			return true;
	}

	@Override

	public HashMap<List<AddContact>, Object> findAll(Integer pageNo, Integer pageSize, Long regId) {
		Response response = null;

		HashMap<List<AddContact>, Object> hashMap = new HashMap<List<AddContact>, Object>();
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<AddContact> criteriaQuery = builder.createQuery(AddContact.class);

		Root<AddContact> root = criteriaQuery.from(AddContact.class);

		Predicate p = builder.equal(root.get("registrationId"), regId);

		criteriaQuery.where(p);
		TypedQuery<AddContact> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		typedQuery.setFirstResult(pageNo);
		typedQuery.setMaxResults(pageSize);

		String countSql = "SELECT COUNT(*) FROM addcontact as c where registration_id=" + regId;
		Query countQuery = this.getEntityManager().createNativeQuery(countSql);

		Object countResult = countQuery.getSingleResult();

		List<AddContact> findAll = typedQuery.getResultList();
		hashMap.put(findAll, countResult);

		if (findAll.isEmpty())
			return null;
		else
			return hashMap;

	}

	@Override
	public Long getClientIdByRegId(Long regId) {
		List resultList = getEntityManager()
				.createNativeQuery("select client_id from addcontact where registration_id = " + regId).getResultList();

		return Long.valueOf(String.valueOf(resultList.get(0)));

	}

	@SuppressWarnings("unchecked")
	@Override
	public Response findDataBasedOnRegId(AddContactSearch addContactSearch) {
		Response response = null;

		if (addContactSearch.getRegistrationId() == null || addContactSearch.getRegistrationId() == 0
				|| addContactSearch.getPageNum() == null || addContactSearch.getPageSize() == null) {
			return new Response(ExceptionMessage.Bad_Request);
		}
		List<Object[]> addContactListDTO = null;
		Integer totalRecords = 0;
		StringBuilder sql = new StringBuilder("SELECT addcontact.id," + "    addcontact.email,"
				+ "    addcontact.mobile," + "    addcontact.phone," + "    addcontact.client_id,"
				+ "   u1.firstName as name," + "    addcontact.domain," + "    addcontact.firstName,"
				+ "    addcontact.isPrimaryContact," + "    addcontact.jobTitle," + "    addcontact.lastName,"
				+ "    addcontact.others," + "    addcontact.EmailOptOut," + "    addcontact.description,"
				+ "    addcontact.registrationId," + "    addcontact.skypeId," + "    addcontact.twitterId,"
				+ "    ct.clientName,concat(addcontact.lastName,' ',addcontact.firstName) as fullName"
				+ "    FROM testing.addcontact addcontact INNER JOIN `user` u1 ON u1.id = addcontact.contactOwner"
				+ "   INNER JOIN client ct ON addcontact.client_id =ct.id where addcontact.registrationId =?");

		StringBuilder countSql = new StringBuilder(
				"SELECT COUNT(*) FROM  testing.addcontact addcontact INNER JOIN client ct ON addcontact.client_id = ct.id where addcontact.registrationId =? ");
		if (addContactSearch.getClientId() != null) {
			sql.append(" and addcontact.client_id=" + addContactSearch.getClientId());
			countSql.append(" and ct.id=" + addContactSearch.getClientId());
		}
		if (addContactSearch.getContactid() != null) {
			sql.append(" and addcontact.id =" + addContactSearch.getContactid());
			countSql.append(" and addcontact.id =" + addContactSearch.getContactid());
		}
		if (addContactSearch.getClientName() != null) {
			sql.append(" and ct.clientName= '" + addContactSearch.getClientName() + "'");
			countSql.append(" and ct.clientName = '" + addContactSearch.getClientName() + "'");
		}
		if (addContactSearch.getContactName() != null && (!addContactSearch.getContactName().trim().isEmpty())) {
			sql.append(" and concat(addcontact.lastName,' ',addcontact.firstName) LIKE '"
					+ addContactSearch.getContactName() + "%'");
			countSql.append(" and concat(addcontact.lastName,' ',addcontact.firstName) LIKE '"
					+ addContactSearch.getContactName() + "%'");
		}
		if (addContactSearch.getEmail() != null && (!addContactSearch.getEmail().trim().isEmpty())) {
			sql.append(" and addcontact.email LIKE '" + addContactSearch.getEmail() + "%'");
			countSql.append(" and addcontact.email LIKE  '" + addContactSearch.getEmail() + "%'");
		}
		if (addContactSearch.getPhone() != null && (!addContactSearch.getPhone().trim().isEmpty())) {
			sql.append(" and addcontact.mobile LIKE '" + addContactSearch.getPhone() + "%'");
			countSql.append(" and addcontact.mobile LIKE '" + addContactSearch.getPhone() + "%'");
		}
		int startingRow = 0;
		if (addContactSearch.getPageNum() == 1) {
			startingRow = 0;
		} else {
			startingRow = ((addContactSearch.getPageNum() - 1) * addContactSearch.getPageSize());
		}
		if (addContactSearch.isFlag()) {
			sql = sql.append(
					"  order by addcontact.id DESC LIMIT " + startingRow + "," + addContactSearch.getPageSize());
			Query query = null;
			try {
				query = getEntityManager().createNativeQuery(sql.toString());
				query.setParameter(1, addContactSearch.getRegistrationId());
				addContactListDTO = query.getResultList();
				Query countQuery = this.getEntityManager().createNativeQuery(countSql.toString());
				countQuery.setParameter(1, addContactSearch.getRegistrationId());
				Object countResult = countQuery.getSingleResult();
				totalRecords = Integer.valueOf(countResult.toString());
				List<AddContactList> responseList = new ArrayList<AddContactList>();
				if (addContactListDTO.isEmpty()) {
					response = new Response(ExceptionMessage.DataIsEmpty, "No Content Found");
				} else {
					for (Object[] req : addContactListDTO) {
						AddContactList transferObj = new AddContactList();
						transferObj.setId(Long.valueOf(req[0] + ""));
						if ((req[1]) != null)
							transferObj.setEmail(req[1] + "");
						if ((req[2]) != null)
							transferObj.setMobile(req[2] + "");
						transferObj.setPhone(req[3] + "");
						if ((req[4]) != null)
							transferObj.setClient_id(Long.valueOf(req[4] + ""));
						if ((req[5]) != null)
							transferObj.setContactOwner(req[5] + "");
						transferObj.setDomain(req[6] + "");
						if ((req[7]) != null)
							transferObj.setFirstName((req[7] + ""));
						if ((req[8]) != null)
							transferObj.setIsPrimaryContact((Boolean) (req[8]));
						transferObj.setJobTitle(req[9] + "");
						if ((req[10]) != null)
							transferObj.setLastName(req[10] + "");
						transferObj.setOthers(req[11] + "");
						if ((req[12]) != null)
							transferObj.setEmailOptOut((Boolean) (req[12]));
						transferObj.setDescription(req[13] + "");
						transferObj.setRegistrationId(Long.valueOf(req[14] + ""));
						transferObj.setSkypeId(req[15] + "");
						transferObj.setTwitterId(req[16] + "");
						if ((req[17]) != null)
							transferObj.setClientName(req[17] + "");
						transferObj.setFullName(req[18] + "");
						responseList.add(transferObj);
					}
					response = new Response(ExceptionMessage.OK, responseList);
				}

				Integer totalPages = 1;
				if (totalRecords == 0) {
					response.setTotalPages(0);
				} else {
					totalPages = totalRecords / addContactSearch.getPageSize();
					totalPages = (totalRecords % addContactSearch.getPageSize() > 0) ? totalPages + 1 : totalPages;
				}
				response.setTotalPages(totalPages);
				response.setTotalRecords(totalRecords);
				response.setResult(responseList);
			} catch (PersistenceException pe) {
				response = new Response(ExceptionMessage.Exception, "500",
						"Unable to Retrieve Customers List. " + " " + pe.getLocalizedMessage());
			} catch (Exception e) {
				response = new Response(ExceptionMessage.Exception, "500",
						" Unable Retrieve Customers List " + e.getLocalizedMessage());
			}
			return response;
		} else if (!addContactSearch.isFlag()) {
			Query query = null;
			FileInputStream inputStream = null;
			FileOutputStream out = null;
			File file = null;
			XSSFWorkbook workbook = null;
			try {
				query = getEntityManager().createNativeQuery(sql.toString());
				query.setParameter(1, addContactSearch.getRegistrationId());
				addContactListDTO = query.getResultList();

				workbook = new XSSFWorkbook();
				XSSFSheet spreadsheet = workbook.createSheet("Contact Details ");

				int rowCount = 0;
				Row header = spreadsheet.createRow(rowCount);
				header.createCell(0).setCellValue("Id");
				header.createCell(1).setCellValue("Email");
				header.createCell(2).setCellValue("Mobile");
				header.createCell(3).setCellValue("Phone");
				header.createCell(4).setCellValue("Client Id");
				header.createCell(5).setCellValue("Contact Owner");
				header.createCell(6).setCellValue("Domain");
				header.createCell(7).setCellValue("First Name");
				header.createCell(8).setCellValue("Is Primary Contact");
				header.createCell(9).setCellValue("Job Title");
				header.createCell(10).setCellValue("Last Name");
				header.createCell(11).setCellValue("Email Option");
				header.createCell(12).setCellValue("Description");
				header.createCell(13).setCellValue("Registration Id");
				header.createCell(14).setCellValue("Skype Id");
				header.createCell(15).setCellValue("Twitter Id");
				header.createCell(16).setCellValue("Client Name");
				for (Object[] cell : addContactListDTO) {

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
				pro.append("ContactDetails" + strDate + "File");

				file = new File(pro + ".xlsx");

				out = new FileOutputStream(file);

				workbook.write(out);

				byte[] bytes = null;
				Path p = Paths.get(file.getAbsolutePath());
				bytes = Files.readAllBytes(p);

				response = new Response(ExceptionMessage.StatusSuccess, bytes);
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
		return response;

	}

}
