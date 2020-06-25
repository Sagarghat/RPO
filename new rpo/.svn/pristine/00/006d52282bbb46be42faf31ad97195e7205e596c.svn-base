package com.ojas.rpo.security.dao.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.User;
import com.ojas.rpo.security.entity.UserSearch;
import com.ojas.rpo.security.transfer.UserListTransfer;
import com.ojas.rpo.security.transfer.UsersList;
import com.ojas.rpo.security.util.ReadingAppProps;

public class JpaUserDao extends JpaDao<User, Long> implements UserDao {

	@Value("${excelFile}")
	private String excelFilePath;

	public JpaUserDao() {
		super(User.class);
	}

	@Override
	@Transactional(readOnly = true)
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		Predicate p;
		Predicate p1;
		Predicate p2;
		final CriteriaQuery<User> criteriaQuery = builder.createQuery(this.entityClass);

		Root<User> root = criteriaQuery.from(this.entityClass);
		Path<String> namePath = root.get("name");
		Path<String> statuspath = root.get("status");
		p = builder.equal(namePath, username);
		p1 = builder.equal(statuspath, "Active");
		p2 = builder.and(p, p1);
		criteriaQuery.where(p2);
		TypedQuery<User> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		List<User> users = typedQuery.getResultList();

		if (users.isEmpty()) {
			throw new UsernameNotFoundException("The user with name " + username + " was not found");
		}

		return users.get(0);
	}

	@Override
	@Transactional(readOnly = true)
	public User findByName(String name) {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<User> criteriaQuery = builder.createQuery(this.entityClass);

		Root<User> root = criteriaQuery.from(this.entityClass);
		Path<String> namePath = root.get("name");
		criteriaQuery.where(builder.equal(namePath, name));

		TypedQuery<User> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		List<User> users = typedQuery.getResultList();

		if (users.isEmpty()) {
			return null;
		}

		return users.iterator().next();
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findByRole(String role) {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<User> criteriaQuery = builder.createQuery(this.entityClass);

		Root<User> root = criteriaQuery.from(this.entityClass);
		Path<String> namePath = root.get("role");
		criteriaQuery.where(builder.equal(namePath, role));

		TypedQuery<User> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		List<User> users = typedQuery.getResultList();
		return users;
	}

	@Transactional(readOnly = true)
	public List<User> findByReportingId(Long id) {
		Query query = null;
		List<User> requirementsList = null;
		query = getEntityManager().createQuery("from User req where req.reportingId.id = " + id + "order by id desc");

		requirementsList = query.getResultList();
		return requirementsList;

	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findByOnlyBdmleadRole(Long regId) {
		Query query = null;
		List<User> requirementsList = null;
		query = getEntityManager().createQuery(
				"from User req where req.role in('BDM')  and req.registrationId=" + regId + "  order by id desc");

		requirementsList = query.getResultList();
		return requirementsList;
	}

	@Override
	@Transactional(readOnly = true)
	public List<User> findByOnlyAMRole(Long regId) {
		Query query = null;
		List<User> requirementsList = null;
		query = getEntityManager().createQuery(
				"from User req where req.role in('AM')  and req.registrationId=" + regId + " order by id desc");
		System.out.println("query::" + query);
		requirementsList = query.getResultList();
		System.out.println("requirementsList::" + requirementsList.size());
		return requirementsList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.rpo.security.dao.JpaDao#findAll()
	 */
	@Override
	@Transactional(readOnly = true)
	public List<User> findAll() {
		final CriteriaBuilder builder = this.getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);

		Root<User> root = criteriaQuery.from(User.class);
		criteriaQuery.orderBy(builder.desc(root.get("id")));

		TypedQuery<User> typedQuery = this.getEntityManager().createQuery(criteriaQuery);
		System.out.println(typedQuery.getResultList().toString());
		return typedQuery.getResultList();
	}

	@Override
	public List<User> findByRoleAndId(Long id, String role) {
		Query q = null;
		if (role.equalsIgnoreCase("BDM")) {
			q = getEntityManager()
					.createQuery("from User user where user.reportingId.id=" + id + " and role='" + role + "'");
		} else {
			q = getEntityManager().createQuery("from User user where role='" + role + "'");
		}
		List<User> results = q.getResultList();

		return results;
	}

	@Transactional
	public Response updateUserById(Long id, User userUpdate) {
		try {
			User user = find(id);
			// String reportingQuery="select name from user where reportingId=
			// "+user.getReportingId();
			String sql = "UPDATE `user` SET ";

			StringBuilder sqlBuilder = new StringBuilder(sql);
			if (null != user) {
				if (userUpdate.getFirstName() != null) {
					sqlBuilder.append(" firstName ='" + userUpdate.getFirstName() + "',");
				}
				if (userUpdate.getLastName() != null) {
					sqlBuilder.append(" lastname='" + userUpdate.getLastName() + "',");
				}
				if (userUpdate.getEmail() != null) {
					sqlBuilder.append(" email='" + userUpdate.getEmail() + "',");
				}
				if (userUpdate.getContactNumber() != null) {
					sqlBuilder.append(" contactNumber=" + userUpdate.getContactNumber() + ",");
				}
				if (userUpdate.getExtension() != null) {
					sqlBuilder.append(" extension=" + userUpdate.getExtension() + ",");
				}
				if (userUpdate.getRole() != null) {
					sqlBuilder.append(" role='" + userUpdate.getRole() + "',");
				}
				if (userUpdate.getDoj() != null) {

					sqlBuilder.append(" doj='" + new java.sql.Date(userUpdate.getDoj().getTime()) + "',");
				}
				if (userUpdate.getStatus() != null) {
					sqlBuilder.append(" status='" + userUpdate.getStatus() + "',");
				}
				if (userUpdate.getReportingId() != null) {
					sqlBuilder.append(" reporting_id=" + userUpdate.getReportingId().getId() + ",");
				}

				sqlBuilder.setCharAt(sqlBuilder.length() - 1, ' ');
				sqlBuilder.append(" WHERE id  = ?");
				int updateResult = this.getEntityManager().createNativeQuery(sqlBuilder.toString()).setParameter(1, id)
						.executeUpdate();
				System.out.println("Update Result is " + updateResult);
				if (updateResult > 0) {
					Response res = new Response(ExceptionMessage.OK);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(ExceptionMessage.OK);
	}

	///////////////////////////////////////

	@Override
	public Response findAllUsers(Integer pageNo, Integer pageSize, Long regId, String sortOrder, String sortField,
			String searchingField, String searchingInput, Boolean flag) {
		System.out.println("-----------" + flag);
		UsersList listss = new UsersList();
		Response response = null;
		// StringBuilder sql=new StringBuilder();
		String sql = " SELECT u1.id, u1.name, CONCAT(u1.firstName,' ',u1.lastName) AS fullName, u1.contactNumber, u1.extension, u1.email, u1.role, u1.status, CONCAT(u2.firstName,' ',u2.lastName) AS reportsTo, u2.name AS reportingMail FROM `user` u1 "
				+ " INNER JOIN `user` u2 ON u2.id = u1.reportingId_id  WHERE u1.reg_id=" + regId;

		if (null != searchingInput && null != searchingField) {
			String searchField = searchingField;
			if (searchField.equalsIgnoreCase("name") || "name".contains(searchingField)) {
				searchField = "u1.name";
			} else if (searchField.equalsIgnoreCase("fullName") || "fullName".contains(searchingField)) {
				searchField = "CONCAT(u1.firstName,' ',u1.lastName)";
			} else if (searchField.equalsIgnoreCase("contactNumber") || "contactNumber".contains(searchingField)) {
				searchField = "u1.contactNumber";
			} else if (searchField.equalsIgnoreCase("extension") || "extension".contains(searchingField)) {
				searchField = "u1.extension";
			} else if (searchField.equalsIgnoreCase("email") || "email".contains(searchingField)) {
				searchField = "u1.email";
			} else if (searchField.equalsIgnoreCase("role") || "role".contains(searchingField)) {
				searchField = "u1.role";
			} else if (searchField.equalsIgnoreCase("status") || "status".contains(searchingField)) {
				searchField = "u1.status";
			} else if (searchField.equalsIgnoreCase("reportsTo") || "reportsTo".contains(searchingField)) {
				searchField = "CONCAT(u2.firstName,' ',u2.lastName)";
			} else if (searchField.equalsIgnoreCase("reportingMail") || "reportingMail".contains(searchingField)) {
				searchField = "u2.name";
			} else {
				searchField = "u1.id";
			}
			// sql.append(" and " + searchField + " LIKE '%" + searchingInput + "%' ");
			sql = sql + " and " + searchField + " LIKE '%" + searchingInput + "%' ";
		}
		if (flag == true) {
			System.out.println("======" + flag);
			if (null != sortField) {
				String sortingField = sortField;
				if (sortingField.equalsIgnoreCase("name") || "name".contains(sortingField)) {
					sortingField = "u1.name";
				} else if (sortingField.equalsIgnoreCase("fullName") || "fullName".contains(sortingField)) {
					sortingField = "CONCAT(u1.firstName,' ',u1.lastName)";
				} else if (sortingField.equalsIgnoreCase("contactNumber") || "contactNumber".contains(sortingField)) {
					sortingField = "u1.contactNumber";
				} else if (sortingField.equalsIgnoreCase("extension") || "extension".contains(sortingField)) {
					sortingField = "u1.extension";
				} else if (sortingField.equalsIgnoreCase("email") || "email".contains(sortingField)) {
					sortingField = "u1.email";
				} else if (sortingField.equalsIgnoreCase("role") || "role".contains(sortingField)) {
					sortingField = "u1.role";
				} else if (sortingField.equalsIgnoreCase("status") || "status".contains(sortingField)) {
					sortingField = "u1.status";
				} else if (sortingField.equalsIgnoreCase("reportsTo") || "reportsTo".contains(sortingField)) {
					sortingField = "CONCAT(u2.firstName,' ',u2.lastName)";
				} else if (sortingField.equalsIgnoreCase("reportingMail") || "reportingMail".contains(sortingField)) {
					sortingField = "u2.name";
				} else {
					sortingField = "u1.id";
				}
				// sql.append(" ORDER BY u1.id , " + sortingField );
				// sql.append(" ORDER BY u1.id " );
				sql = sql + " ORDER BY " + sortingField + " ";

				sql = sql + " ORDER BY u1.id ";
			}
			if (null != sortOrder && (sortOrder.equalsIgnoreCase("ASC"))) {
				// sql.append(" ASC ");
				sql = sql + " ASC ";
			} else if (null != sortOrder && (sortOrder.equalsIgnoreCase("DESC"))) {
				// sql.append(" DESC ");
				sql = sql + " DESC ";
			}
			int startingRow = 0;
			if ((null != pageNo) && (null != pageSize)) {

				if (pageNo == 1) {
					startingRow = 0;
				} else {
					startingRow = ((pageNo - 1) * pageSize);
				}
				// sql.append( " LIMIT " + startingRow + "," + pageSize);
				sql = sql + " LIMIT " + startingRow + "," + pageSize;
				List<UserListTransfer> userList = new ArrayList<UserListTransfer>();

				Query selectQuery = this.getEntityManager().createNativeQuery(sql.toString());
				List<Object[]> results = selectQuery.getResultList();
				for (Object[] obj : results) {
					UserListTransfer user = new UserListTransfer();
					user.setId(Long.valueOf(obj[0].toString()));
					user.setName((String) obj[1]);
					user.setContactNumber((String) (obj[3]));
					user.setExtension(((String) (obj[4])));
					user.setEmail((String) obj[5]);
					user.setRole((String) obj[6]);
					user.setStatus((String) obj[7]);
					user.setFullName((String) obj[2]);
					user.setReportsTo((String) obj[8]);
					user.setReportingMail((String) obj[9]);
					userList.add(user);
				}
				Integer totalPages = 1;
				int totalRecords = findAll().size();
				listss.setTotalRecords(totalRecords);
				if (totalRecords == 0) {
					listss.setTotalPages(0);
				}

				if ((totalRecords > 0) && (Integer.valueOf(totalRecords) <= pageSize)) {
					listss.setTotalPages(1);
				} else {
					totalPages = Integer.valueOf(totalRecords) / pageSize;
					totalPages = (totalPages == 0) ? totalPages : totalPages + 1;
					listss.setTotalPages(totalPages);
				}
				listss.setList(userList);
				System.out.println(listss);
				response = new Response(ExceptionMessage.StatusSuccess, userList);
				response.setResult(listss);
				response.setTotalPages(totalPages);
				response.setTotalRecords(totalRecords);
			}
			return response;
		} else {
			FileInputStream inputStream = null;
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

			Query selectQuery = this.getEntityManager().createNativeQuery(sql.toString());
			List<Object[]> results = selectQuery.getResultList();

			Row header = sheet.createRow(0);
			header.createCell(0).setCellValue("id");
			header.createCell(1).setCellValue("name");
			header.createCell(2).setCellValue("contactNumber");
			header.createCell(3).setCellValue("extension");
			header.createCell(4).setCellValue("email");
			header.createCell(5).setCellValue("role");
			header.createCell(6).setCellValue("status");
			header.createCell(7).setCellValue("fullName");
			header.createCell(8).setCellValue("reportsTo");
			header.createCell(9).setCellValue("reportingMail");
			int rowCount = 0;
			for (Object[] obj : results) {
				Row row = sheet.createRow(++rowCount);
				int columnCount = -1;
				for (Object object : obj) {
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

			}

			try {
				StringBuilder pro = new StringBuilder(excelFilePath);
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
				String strDate = dateFormat.format(new Date());
				pro.append("UserDetails" + strDate + "File");

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
			}

			catch (Exception e) {
				e.printStackTrace();
				response = new Response(ExceptionMessage.Exception);
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

////////////////////////////////////////////
	/*
	 * @Override public Response findAllUsers(Integer pageNo, Integer pageSize, Long
	 * regId, String sortOrder, String sortField, String searchingField, String
	 * searchingInput , boolean flag ) {
	 * 
	 * UsersList listss = null; Response response = null; // ; String sql =
	 * " SELECT u1.id, u1.name, CONCAT(u1.firstName,' ',u1.lastName) AS fullName, u1.contactNumber, u1.extension, u1.email, u1.role, u1.status, CONCAT(u2.firstName,' ',u2.lastName) AS reportsTo, u2.name AS reportingMail FROM `user` u1 "
	 * + " INNER JOIN `user` u2 ON u2.id = u1.reportingId_id  WHERE u1.reg_id=" +
	 * regId;
	 * 
	 * if (null != searchingInput && null != searchingField) {
	 * 
	 * String searchField = searchingField;
	 * 
	 * if (searchField.equalsIgnoreCase("name") || "name".contains(searchingField))
	 * { searchField = "u1.name"; } else if
	 * (searchField.equalsIgnoreCase("fullName") ||
	 * "fullName".contains(searchingField)) { searchField =
	 * "CONCAT(u1.firstName,' ',u1.lastName)"; } else if
	 * (searchField.equalsIgnoreCase("contactNumber") ||
	 * "contactNumber".contains(searchingField)) { searchField = "u1.contactNumber";
	 * } else if (searchField.equalsIgnoreCase("extension") ||
	 * "extension".contains(searchingField)) { searchField = "u1.extension"; } else
	 * if (searchField.equalsIgnoreCase("email") ||
	 * "email".contains(searchingField)) { searchField = "u1.email"; } else if
	 * (searchField.equalsIgnoreCase("role") || "role".contains(searchingField)) {
	 * searchField = "u1.role"; } else if (searchField.equalsIgnoreCase("status") ||
	 * "status".contains(searchingField)) { searchField = "u1.status"; } else if
	 * (searchField.equalsIgnoreCase("reportsTo") ||
	 * "reportsTo".contains(searchingField)) { searchField =
	 * "CONCAT(u2.firstName,' ',u2.lastName)"; } else if
	 * (searchField.equalsIgnoreCase("reportingMail") ||
	 * "reportingMail".contains(searchingField)) { searchField = "u2.name"; } else {
	 * searchField = "u1.id"; }
	 * 
	 * sql = sql + " and " + searchField + " LIKE '%" + searchingInput + "%' "; }
	 * 
	 * if (null != sortField) { String sortingField = sortField;
	 * 
	 * if (sortingField.equalsIgnoreCase("name") || "name".contains(sortingField)) {
	 * sortingField = "u1.name"; } else if
	 * (sortingField.equalsIgnoreCase("fullName") ||
	 * "fullName".contains(sortingField)) { sortingField =
	 * "CONCAT(u1.firstName,' ',u1.lastName)"; } else if
	 * (sortingField.equalsIgnoreCase("contactNumber") ||
	 * "contactNumber".contains(sortingField)) { sortingField = "u1.contactNumber";
	 * } else if (sortingField.equalsIgnoreCase("extension") ||
	 * "extension".contains(sortingField)) { sortingField = "u1.extension"; } else
	 * if (sortingField.equalsIgnoreCase("email") || "email".contains(sortingField))
	 * { sortingField = "u1.email"; } else if (sortingField.equalsIgnoreCase("role")
	 * || "role".contains(sortingField)) { sortingField = "u1.role"; } else if
	 * (sortingField.equalsIgnoreCase("status") || "status".contains(sortingField))
	 * { sortingField = "u1.status"; } else if
	 * (sortingField.equalsIgnoreCase("reportsTo") ||
	 * "reportsTo".contains(sortingField)) { sortingField =
	 * "CONCAT(u2.firstName,' ',u2.lastName)"; } else if
	 * (sortingField.equalsIgnoreCase("reportingMail") ||
	 * "reportingMail".contains(sortingField)) { sortingField = "u2.name"; } else {
	 * sortingField = "u1.id"; }
	 * 
	 * sql = sql + " ORDER BY " + sortingField + " ";
	 * 
	 * sql = sql + " ORDER BY u1.id "; }
	 * 
	 * if (null != sortOrder && (sortOrder.equalsIgnoreCase("ASC"))) { sql = sql +
	 * " ASC "; } else if (null != sortOrder &&
	 * (sortOrder.equalsIgnoreCase("DESC"))) { sql = sql + "   DESC "; }
	 * 
	 * int startingRow = 0; if ((null != pageNo) && (null != pageSize)) {
	 * 
	 * if (pageNo == 1) { startingRow = 0; } else { startingRow = ((pageNo - 1) *
	 * pageSize); }
	 * 
	 * sql = sql + " LIMIT " + startingRow + "," + pageSize; }
	 * 
	 * List<UserListTransfer> userList;
	 * 
	 * Query selectQuery = this.getEntityManager().createNativeQuery(sql);
	 * List<Object[]> results = selectQuery.getResultList();
	 * 
	 * 
	 * int startingRow = 0;
	 * 
	 * if ((null != pageNo) && (null != pageSize)) {
	 * 
	 * if (pageNo == 1) { startingRow = 0; } else { startingRow = ((pageNo - 1) *
	 * pageSize); } // if (flag == true) { sql = sql + " LIMIT " + startingRow + ","
	 * + pageSize;
	 * 
	 * List<UserListTransfer> userList;
	 * 
	 * Query selectQuery = this.getEntityManager().createNativeQuery(sql);
	 * List<Object[]> results = selectQuery.getResultList();
	 * 
	 * 
	 * userList = new ArrayList<UserListTransfer>(); for (Object[] obj : results) {
	 * UserListTransfer user = new UserListTransfer();
	 * user.setId(Long.valueOf(obj[0].toString())); user.setName((String) obj[1]);
	 * user.setContactNumber((String) (obj[3])); user.setExtension(((String)
	 * (obj[4]))); user.setEmail((String) obj[5]); user.setRole((String) obj[6]);
	 * user.setStatus((String) obj[7]); user.setFullName((String) obj[2]);
	 * user.setReportsTo((String) obj[8]); user.setReportingMail((String) obj[9]);
	 * userList.add(user); } // response = new Response(ExceptionMessage.OK,
	 * userList); // return response; // } int totalRecords = 0; response = new
	 * Response(ExceptionMessage.StatusSuccess, userList);
	 * response.setTotalRecords(totalRecords); totalRecords = findAll().size();
	 * 
	 * if (totalRecords == 0) { response.setTotalPages(0); } if (pageSize != null &&
	 * pageNo != null) { if ((totalRecords > 0) && (Integer.valueOf(totalRecords) <=
	 * pageSize)) { response.setTotalPages(1); } else {
	 * 
	 * Integer totalPages = Integer.valueOf(totalRecords) / pageSize; totalPages =
	 * (totalPages == 0) ? totalPages : totalPages + 1;
	 * response.setTotalPages(totalPages); } }
	 * 
	 * 
	 * listss = new UsersList(); int totalRecords = findAll().size(); if
	 * (totalRecords == 0) { response.setTotalPages(0); } else { Integer totalPages
	 * = totalRecords / pageSize; totalPages = (totalPages == 0) ? totalPages :
	 * totalPages + 1; response.setTotalPages(totalPages); }
	 * response.setTotalRecords(totalRecords); response.setResult(listss); response
	 * = new Response(ExceptionMessage.StatusSuccess);
	 * 
	 * 
	 * return response; } else { XSSFWorkbook workbook = new XSSFWorkbook();
	 * XSSFSheet sheet = workbook.createSheet("BDM Req Details"); Row header =
	 * sheet.createRow(0); header.createCell(0).setCellValue("id");
	 * header.createCell(1).setCellValue("name");
	 * header.createCell(2).setCellValue("contactNumber");
	 * header.createCell(3).setCellValue("extension");
	 * header.createCell(4).setCellValue("email");
	 * header.createCell(5).setCellValue("role");
	 * header.createCell(6).setCellValue("status");
	 * header.createCell(7).setCellValue("fullName");
	 * header.createCell(8).setCellValue("reportsTo");
	 * header.createCell(9).setCellValue("reportingMail");
	 * 
	 * List<UserListTransfer> userList; Query selectQuery =
	 * this.getEntityManager().createNativeQuery(sql); List<Object[]> results =
	 * selectQuery.getResultList(); int rowCount = 0; for (Object[] obj : results) {
	 * Row row = sheet.createRow(++rowCount); int columnCount = -1; for (Object
	 * object : obj) { Cell cell = row.createCell(++columnCount); if (object
	 * instanceof Integer) cell.setCellValue(Long.valueOf(object + "")); else if
	 * (object instanceof String) cell.setCellValue(object + ""); else if (object
	 * instanceof Long) cell.setCellValue(Long.valueOf(object + "")); else if
	 * (object instanceof Boolean) cell.setCellValue(Boolean.valueOf(object + ""));
	 * else { cell.setCellValue(object + ""); } } try { File file = new
	 * File("C:\\Users\\sajay\\Desktop\\final proj\\user.xlsx"); String path =
	 * file.getAbsolutePath(); FileOutputStream out = new FileOutputStream(file);
	 * workbook.write(out); out.close(); byte[] b = new byte[1024]; FileInputStream
	 * inputStream = new FileInputStream(file); int nRead = 0; String reference =
	 * null; byte[] encodedBytes = null; while ((nRead = inputStream.read(b)) != -1)
	 * { String name = (new String(b)); encodedBytes =
	 * Base64.encodeBase64(name.getBytes()); System.out.println(new
	 * String(encodedBytes)); } inputStream.close(); response = new
	 * Response(ExceptionMessage.StatusSuccess, encodedBytes); } catch (Exception e)
	 * { response = new Response(ExceptionMessage.Exception); } } }
	 * 
	 * 
	 * return response;
	 * 
	 * }
	 */

	public Response getAllReportingManagers() {

		List<String> reportingManagerList = null;
		Query query = null;
		Response response = null;
		try {

			query = getEntityManager().createNativeQuery(
					"SELECT concat(firstname,' ',lastname) As reportsTo FROM testing.user where user.role!='recruiter' And user.role!='Admin'");

			reportingManagerList = query.getResultList();

			if (reportingManagerList.isEmpty()) {
				response = new Response(ExceptionMessage.DataIsEmpty);
			} else {
				response = new Response(ExceptionMessage.OK, reportingManagerList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "500", "Unable to Retrive Managers List");
		}
		return response;

	}

	@Override
	public Response getUserNamesByRole(String role, Long regId) {
		Response response = null;
		Query userQuery = getEntityManager().createNativeQuery(
				"SELECT u.id, concat(u.firstName,' ',u.lastName) from user `u` where u.role=? and reg_id = ?");
		userQuery.setParameter(1, role);
		userQuery.setParameter(2, regId);
		try {
			List<Object[]> results = userQuery.getResultList();
			List<UserNameTransfer> result = new ArrayList<UserNameTransfer>();
			for (Object[] data : results) {
				UserNameTransfer unt = new UserNameTransfer();
				unt.setId(Long.valueOf(data[0].toString()));
				unt.setFullNameOfUser(data[1].toString());
				result.add(unt);
			}
			if (results.isEmpty()) {
				response = new Response(ExceptionMessage.DataIsEmpty, "No Users found By Role = " + role);
			} else {
				response = new Response(ExceptionMessage.OK, result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "500",
					"Unable Retrieve User List due to : " + e.getMessage());
		}
		return response;
	}

	@Override
	public Response getRecruitersByReportingId(Long id) {
		Response response = null;
		Query userQuery = getEntityManager()
				.createNativeQuery("SELECT id, concat(firstName,' ',lastName) AS recruiterName "
						+ "FROM user WHERE role in('recruiter' ,'Lead')");
		try {
			List<Object[]> results = userQuery.getResultList();
			List<UserNameTransfer> result = new ArrayList<UserNameTransfer>();
			for (Object[] data : results) {
				UserNameTransfer unt = new UserNameTransfer();
				unt.setId(Long.valueOf(data[0].toString()));
				unt.setFullNameOfUser(data[1].toString());
				result.add(unt);
			}
			if (results.isEmpty()) {
				response = new Response(ExceptionMessage.DataIsEmpty, "No Users found By Reporting Id = " + id);
			} else {
				response = new Response(ExceptionMessage.OK, result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response = new Response(ExceptionMessage.Exception, "500",
					"Unable Retrieve User List due to : " + e.getMessage());
		}
		return response;
	}

	@Override
	public Response updateUserByRegId(Long id, Long regId, User userUpdate) {
		Response response = null;
		try {
			User user = find(id);

			if (user.getId() == id) {

				String sql = "UPDATE `user` SET ";
				StringBuilder sqlBuilder = new StringBuilder(sql);
				if (null != user) {
					if (userUpdate.getFirstName() != null) {
						sqlBuilder.append(" firstName ='" + userUpdate.getFirstName() + "',");
					}
					if (userUpdate.getLastName() != null) {
						sqlBuilder.append(" lastname='" + userUpdate.getLastName() + "',");
					}
					if (userUpdate.getEmail() != null) {
						sqlBuilder.append(" email='" + userUpdate.getEmail() + "',");
					}
					if (userUpdate.getContactNumber() != null) {
						sqlBuilder.append(" contactNumber=" + userUpdate.getContactNumber() + ",");
					}
					if (userUpdate.getExtension() != null) {
						sqlBuilder.append(" extension=" + userUpdate.getExtension() + ",");
					}
					if (userUpdate.getRole() != null) {
						sqlBuilder.append(" role='" + userUpdate.getRole() + "',");
					}
					if (userUpdate.getReportingId() != null) {
						sqlBuilder.append(" reportingId_id=" + userUpdate.getReportingId().getId() + ",");
					}
					sqlBuilder.setCharAt(sqlBuilder.length() - 1, ' ');
					sqlBuilder.append(" WHERE id  = ? and reg_id=?");
					int updateResult = this.getEntityManager().createNativeQuery(sqlBuilder.toString())
							.setParameter(1, id).setParameter(2, regId).executeUpdate();

					if (updateResult > 0) {
						response = new Response(ExceptionMessage.OK, updateResult);
					}
				}
			} else {
				response = new Response(ExceptionMessage.Not_Found);
			}
		} catch (Exception e) {

			response = new Response(ExceptionMessage.Bad_Request, e.getLocalizedMessage());
		}
		return response;

	}

	/*
	 * @Override public Response findAllUsersByRegId(Integer pageNo, Integer
	 * pageSize, Long regId, boolean flag) { Response response = null; Integer
	 * totalPages = 1; String sql =
	 * " SELECT u1.id, u1.name, CONCAT(u1.firstName,' ',u1.lastName) AS fullName, u1.contactNumber, u1.extension, u1.email, u1.role, u1.status FROM `user` u1 where reg_id="
	 * + regId + " order by u1.id desc ";
	 * 
	 * int totalRecords = 0; Query selectQuery =
	 * this.getEntityManager().createNativeQuery(sql); List<Object[]> results =
	 * selectQuery.getResultList(); if (results != null) { totalRecords =
	 * results.size(); } int startingRow = 0;
	 * 
	 * if (flag == true) {
	 * 
	 * if ((null != pageNo) && (null != pageSize)) {
	 * 
	 * if (pageNo == 1) { startingRow = 0; } else { startingRow = ((pageNo - 1) *
	 * pageSize); }
	 * 
	 * sql = sql + " LIMIT " + startingRow + "," + pageSize;
	 * 
	 * List<UserListTransfer> userList;
	 * 
	 * selectQuery = this.getEntityManager().createNativeQuery(sql);
	 * 
	 * results = selectQuery.getResultList();
	 * 
	 * userList = new ArrayList<UserListTransfer>(); for (Object[] obj : results) {
	 * UserListTransfer user = new UserListTransfer();
	 * user.setId(Long.valueOf(obj[0].toString())); user.setName((String) obj[1]);
	 * user.setFullName((String) obj[2]); user.setContactNumber((String) (obj[3]));
	 * user.setExtension((String) (obj[4])); user.setEmail((String) obj[5]);
	 * user.setRole((String) obj[6]); user.setStatus((String) obj[7]);
	 * 
	 * 
	 * 
	 * if (totalRecords == 0) { response.setTotalPages(0);
	 * 
	 * userList.add(user); }
	 * 
	 * response = new Response(ExceptionMessage.StatusSuccess, userList);
	 * 
	 * 
	 * if ((totalRecords > 0) && (Integer.valueOf(totalRecords) <= pageSize)) {
	 * response.setTotalPages(1); } else { totalPages =
	 * Integer.valueOf(totalRecords) / pageSize; totalPages = (totalPages == 0) ?
	 * totalPages : totalPages + 1; response.setTotalPages(totalPages);
	 * 
	 * } } } else { System.out.println("ocati..."); XSSFWorkbook workbook = new
	 * XSSFWorkbook(); XSSFSheet sheet = workbook.createSheet("UserDetails"); Row
	 * header = sheet.createRow(0); header.createCell(0).setCellValue("id");
	 * header.createCell(1).setCellValue("name");
	 * header.createCell(2).setCellValue("fullName");
	 * header.createCell(3).setCellValue("contactNumber");
	 * header.createCell(4).setCellValue("extension");
	 * header.createCell(5).setCellValue("email");
	 * header.createCell(6).setCellValue("role");
	 * header.createCell(7).setCellValue("status");
	 * 
	 * header.createCell(7).setCellValue("question");
	 * header.createCell(8).setCellValue("answer");
	 * 
	 * header.createCell(10).setCellValue("date");
	 * header.createCell(11).setCellValue("dob1");
	 * header.createCell(12).setCellValue("doj1");
	 * header.createCell(13).setCellValue("reportsTo");
	 * header.createCell(14).setCellValue("reportingMail");
	 * 
	 * header.createCell(16).setCellValue("dob");
	 * header.createCell(17).setCellValue("doj");
	 * header.createCell(18).setCellValue("salary");
	 * header.createCell(19).setCellValue("ctc");
	 * header.createCell(20).setCellValue("mintarget");
	 * header.createCell(21).setCellValue("maxtarget");
	 * header.createCell(22).setCellValue("targetduration");
	 * 
	 * 
	 * 
	 * int rowCount = 0; for (Object[] obj : results) { Row row =
	 * sheet.createRow(++rowCount); int columnCount = -1; for (Object object : obj)
	 * { Cell cell = row.createCell(++columnCount); if (object instanceof Integer)
	 * cell.setCellValue(Long.valueOf(object + "")); else if (object instanceof
	 * String) cell.setCellValue(object + ""); else if (object instanceof Long)
	 * cell.setCellValue(Long.valueOf(object + "")); else if (object instanceof
	 * Boolean) cell.setCellValue(Boolean.valueOf(object + "")); else {
	 * cell.setCellValue(object + ""); } } } try { ReadingAppProps f=new
	 * ReadingAppProps(); String propertyRead = f.get(); File file = new
	 * File(propertyRead+"/"+"user.xlsx"); String path = file.getAbsolutePath();
	 * FileOutputStream out = new FileOutputStream(file); workbook.write(out);
	 * out.close(); byte[] b = new byte[1024]; FileInputStream inputStream = new
	 * FileInputStream(file); int nRead = 0; //String reference = null; byte[]
	 * encodedBytes = null; while ((nRead = inputStream.read(b)) != -1) { String
	 * name = (new String(b)); encodedBytes = Base64.encodeBase64(name.getBytes());
	 * System.out.println(new String(encodedBytes)); } inputStream.close();
	 * 
	 * if (file.exists()) { file.delete(); }
	 * 
	 * response = new Response(ExceptionMessage.StatusSuccess, encodedBytes); }
	 * catch (Exception e) { response = new Response(ExceptionMessage.Exception); }
	 * 
	 * response.setTotalPages(totalPages); response.setTotalRecords(totalRecords);
	 * 
	 * } } return response;
	 * 
	 * 
	 * }
	 * 
	 */

	@SuppressWarnings("unchecked")
	@Override
	public Response findAllUsersByRegId(Integer pageNo, Integer pageSize, Long regId, Boolean flag) {
		Response response = null;
		System.out.println("--------------------" + flag);
		String sql = " SELECT u1.id, u1.name, CONCAT(u1.firstName,' ',u1.lastName) AS fullName, u1.contactNumber, u1.extension, u1.email, u1.role, u1.status FROM `user` u1 where reg_id="
				+ regId + " order by u1.id desc ";

		int totalRecords = 0;
		Query selectQuery = this.getEntityManager().createNativeQuery(sql);
		List<Object[]> results = selectQuery.getResultList();
		if (results != null) {
			totalRecords = results.size();
		}
		int startingRow = 0;

		if ((null != pageNo) && (null != pageSize)) {

			if (pageNo == 1) {
				startingRow = 0;
			} else {
				startingRow = ((pageNo - 1) * pageSize);
			}
			if (flag == true) {
				sql = sql + " LIMIT " + startingRow + "," + pageSize;

				List<UserListTransfer> userList;

				selectQuery = this.getEntityManager().createNativeQuery(sql);

				results = selectQuery.getResultList();

				userList = new ArrayList<UserListTransfer>();
				for (Object[] obj : results) {
					UserListTransfer user = new UserListTransfer();
					user.setId(Long.valueOf(obj[0].toString()));
					user.setName((String) obj[1]);
					user.setFullName((String) obj[2]);
					user.setContactNumber((String) (obj[3]));
					user.setExtension((String) (obj[4]));
					user.setEmail((String) obj[5]);
					user.setRole((String) obj[6]);
					user.setStatus((String) obj[7]);

					userList.add(user);
				}

				response = new Response(ExceptionMessage.StatusSuccess, userList);

				if (totalRecords == 0) {
					response.setTotalPages(0);
				}

				if ((totalRecords > 0) && (Integer.valueOf(totalRecords) <= pageSize)) {
					response.setTotalPages(1);
				} else {
					Integer totalPages = Integer.valueOf(totalRecords) / pageSize;
					totalPages = (totalPages == 0) ? totalPages : totalPages + 1;
					response.setTotalPages(totalPages);
					response.setTotalRecords(totalRecords);
				}
			}
			System.out.println("non else");
			return response;
		} else {
			System.out.println("else");
			XSSFWorkbook workbook = null;
			FileOutputStream out = null;
			FileInputStream inputStream = null;
			File file = null;
			System.out.println("ocati...");
			try {
				workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet("UserDetails");
				Row header = sheet.createRow(0);
				header.createCell(0).setCellValue("id");
				header.createCell(1).setCellValue("name");
				header.createCell(2).setCellValue("fullName");
				header.createCell(3).setCellValue("contactNumber");
				header.createCell(4).setCellValue("extension");
				header.createCell(5).setCellValue("email");
				header.createCell(6).setCellValue("role");
				header.createCell(7).setCellValue("status");
				/*
				 * header.createCell(7).setCellValue("question");
				 * header.createCell(8).setCellValue("answer");
				 * 
				 * header.createCell(10).setCellValue("date");
				 * header.createCell(11).setCellValue("dob1");
				 * header.createCell(12).setCellValue("doj1");
				 * header.createCell(13).setCellValue("reportsTo");
				 * header.createCell(14).setCellValue("reportingMail");
				 * 
				 * header.createCell(16).setCellValue("dob");
				 * header.createCell(17).setCellValue("doj");
				 * header.createCell(18).setCellValue("salary");
				 * header.createCell(19).setCellValue("ctc");
				 * header.createCell(20).setCellValue("mintarget");
				 * header.createCell(21).setCellValue("maxtarget");
				 * header.createCell(22).setCellValue("targetduration");
				 */

				int rowCount = 0;
				for (Object[] obj : results) {
					Row row = sheet.createRow(++rowCount);
					int columnCount = -1;
					for (Object object : obj) {
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

					StringBuilder pro = new StringBuilder(excelFilePath);

					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
					String strDate = dateFormat.format(new Date());
					pro.append("UserDetails" + strDate + "File");
					file = new File(pro + ".xlsx");
					out = new FileOutputStream(file);
					workbook.write(out);
					out.close();
					byte[] bufferSize = new byte[1024];
					inputStream = new FileInputStream(file);
					int nRead = 0;

					byte[] encodedBytes = null;
					while ((nRead = inputStream.read(bufferSize)) != -1) {
						String name = (new String(bufferSize));
						encodedBytes = Base64.encodeBase64(name.getBytes());

					}

					response = new Response(ExceptionMessage.Accepted, encodedBytes);

				}
			} catch (Exception e) {

				response = new Response(ExceptionMessage.Exception, e.getLocalizedMessage());

			} finally {
				try {

					if (file.exists()) {
						file.delete();
					}
					inputStream.close();
					out.close();
					workbook.close();

				} catch (IOException e) {
					response = new Response(ExceptionMessage.Bad_Request, e.getLocalizedMessage());

				}
			}
			return response;
		}

	}

//	@Override
//	public boolean chekduplicate(String email) {
//		boolean result = false;
//		try {
//			Query query = getEntityManager().createNativeQuery("select email FROM user WHERE email=?");
//			query.setParameter(1, email);
//			@SuppressWarnings("unchecked")
//			List<String> results = query.getResultList();
//
//			List<String> listData = new ArrayList<String>();
//
//			for (String cName : results) {
//				if (email.equalsIgnoreCase(cName)) {
//					listData.add(email);
//				}
//			}
//			if (listData.size() >= 1) {
//				result = true;
//			} else {
//				result = false;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return result;
//	}

	@SuppressWarnings("unchecked")
	@Override
	public Response usersListByRegIdRole(Integer pageNo, Integer pageSize, Long regId, String role, boolean flag) {
		Response response = null;
		String sql = " SELECT u1.id, u1.name, CONCAT(u1.firstName,' ',u1.lastName) AS fullName, u1.contactNumber, u1.extension, u1.email, u1.role, u1.status FROM `user` u1 where reg_id="
				+ regId + " and role = '" + role + "' order by u1.id desc ";

		int totalRecords = 0;
		Query selectQuery = this.getEntityManager().createNativeQuery(sql);
		List<Object[]> results = selectQuery.getResultList();
		if (results != null) {
			totalRecords = results.size();
		}
		int startingRow = 0;
		if (flag == true) {
			if ((null != pageNo) && (null != pageSize)) {

				if (pageNo == 1) {
					startingRow = 0;
				} else {
					startingRow = ((pageNo - 1) * pageSize);
				}

				sql = sql + " LIMIT " + startingRow + "," + pageSize;
			}

			List<UserListTransfer> userList;

			selectQuery = this.getEntityManager().createNativeQuery(sql);

			results = selectQuery.getResultList();

			userList = new ArrayList<UserListTransfer>();
			for (Object[] obj : results) {
				UserListTransfer user = new UserListTransfer();
				user.setId(Long.valueOf(obj[0].toString()));
				user.setName((String) obj[1]);
				user.setFullName((String) obj[2]);
				user.setContactNumber((obj[3]) + "");
				user.setExtension((obj[4]) + "");
				user.setEmail((String) obj[5]);
				user.setRole((String) obj[6]);
				user.setStatus((String) obj[7]);

				userList.add(user);
			}

			response = new Response(ExceptionMessage.StatusSuccess, userList);
			response.setTotalRecords(totalRecords);
			if (totalRecords == 0) {
				response.setTotalPages(0);
			}
			if (pageSize != null && pageNo != null) {
				if ((totalRecords > 0) && (Integer.valueOf(totalRecords) <= pageSize)) {
					response.setTotalPages(1);
				} else {

					Integer totalPages = Integer.valueOf(totalRecords) / pageSize;
					totalPages = (totalPages == 0) ? totalPages : totalPages + 1;
					response.setTotalPages(totalPages);
					response.setTotalRecords(totalRecords);
				}
			}

		} else {
			System.out.println("else");
			XSSFWorkbook workbook = null;
			FileOutputStream out = null;
			FileInputStream inputStream = null;
			File file = null;
			System.out.println("ocati...");
			try {
				workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet("UserDetails");
				Row header = sheet.createRow(0);

				header.createCell(0).setCellValue("id");
				header.createCell(1).setCellValue("name");
				header.createCell(2).setCellValue("fullName");
				header.createCell(3).setCellValue("contactNumber");
				header.createCell(4).setCellValue("extension");
				header.createCell(5).setCellValue("email");
				header.createCell(6).setCellValue("role");
				header.createCell(7).setCellValue("status");

				int rowCount = 0;
				for (Object[] obj : results) {
					Row row = sheet.createRow(++rowCount);
					int columnCount = -1;
					for (Object object : obj) {
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

					StringBuilder pro = new StringBuilder(excelFilePath);

					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
					String strDate = dateFormat.format(new Date());
					pro.append("UserDetails" + strDate + "File");
					file = new File(pro + ".xlsx");
					out = new FileOutputStream(file);
					workbook.write(out);
					out.close();
					byte[] bufferSize = new byte[1024];
					inputStream = new FileInputStream(file);
					int nRead = 0;

					byte[] encodedBytes = null;
					while ((nRead = inputStream.read(bufferSize)) != -1) {
						String name = (new String(bufferSize));
						encodedBytes = Base64.encodeBase64(name.getBytes());

					}

					response = new Response(ExceptionMessage.Accepted, encodedBytes);

				}
			} catch (Exception e) {

				response = new Response(ExceptionMessage.Exception, e.getLocalizedMessage());

			} finally {
				try {

					if (file.exists()) {
						file.delete();
					}
					inputStream.close();
					out.close();
					workbook.close();

				} catch (IOException e) {
					response = new Response(ExceptionMessage.Bad_Request, e.getLocalizedMessage());

				}
			}

		}
		return response;

	}

	public int userCount(Long regId) {
		String queryString = "SELECT p.no_of_users+1-count(*) as user_count FROM plans p join company_reg c on p.plan_id= c.plan_id  join "
				+ " user u on c.reg_id = u.reg_id where u.reg_id=" + regId;
//		String queryString="SELECT no_of_users-count(*) as user_count FROM testing.plans p join testing.company_reg c on p.plan_id= c.plan_id  join "
//				+ " testing.user u on c.reg_id = u.reg_id where u.reg_id="+regId+" and role!='superadmin'";	
		System.out.println("queryString:::" + queryString);
		Query query = this.getEntityManager().createNativeQuery(queryString);

		Number results = (Number) query.getSingleResult();
		System.out.println("results:::" + results.intValue());
		return results.intValue();

	}

	//////////////////////////////// new Search
	@Override
	public Response findAllUsers(UserSearch userSearch) {
		Integer totalRecords = 0;
		Response response = null;
		UsersList listss = new UsersList();
		StringBuilder sql = new StringBuilder();
		StringBuilder count = new StringBuilder();
		if (userSearch.getRole().equalsIgnoreCase("SUPERADMIN") || userSearch.getRole().equalsIgnoreCase("Admin")) {
			sql.append(" SELECT u1.id, u1.name, CONCAT(u1.firstName,' ',u1.lastName) AS fullName, u1.contactNumber,"
					+ " u1.extension, u1.email, u1.role, u1.status,"
					+ " CONCAT(u1.firstName,' ',u1.lastName) AS reportsTo, u1.name AS reportingMail FROM `user` u1 "
					// + " INNER JOIN `user` u1 ON u1.id = u1.reportingId_id "
					+ " WHERE u1.reg_id=?");
			count.append("select count(*) from user u1 WHERE u1.reg_id=" + userSearch.getRegistrationId());

		} else {
			sql.append(" SELECT u1.id, u1.name, CONCAT(u1.firstName,' ',u1.lastName) AS fullName, u1.contactNumber,"
					+ " u1.extension, u1.email, u1.role, u1.status,"
					+ " CONCAT(u2.firstName,' ',u2.lastName) AS reportsTo, u2.name AS reportingMail FROM `user` u1 "
					+ " INNER JOIN `user` u2 ON u2.id = u1.reportingId_id  WHERE u1.reg_id=? ");
			count.append(
					"select count(*) from user u1 INNER JOIN `user` u2 ON u2.id = u1.reportingId_id  WHERE u1.reg_id= "
							+ userSearch.getRegistrationId());

		}

		if (userSearch.getContactNo() != null && (!userSearch.getContactNo().trim().isEmpty())) {
			sql.append(" and u1.contactNumber Like '" + userSearch.getContactNo() + "%'");
			count.append(" and u1.contactNumber Like '" + userSearch.getContactNo() + "%'");
		} 
		if (userSearch.getEmail() != null && (!userSearch.getEmail().trim().isEmpty())) {
			sql.append(" and u1.email Like '" + userSearch.getEmail() + "%'");
			count.append(" and u1.email Like '" + userSearch.getEmail() + "%'");

		} 
		if (userSearch.getFullName() != null && (!userSearch.getFullName().trim().isEmpty())) {
			sql.append(" and CONCAT(u1.firstName,' ',u1.lastName) Like '" + userSearch.getFullName() + "%'");
			count.append(" and CONCAT(u1.firstName,' ',u1.lastName) Like '" + userSearch.getFullName() + "%'");
		} 
		if(!userSearch.getRole().equalsIgnoreCase("SUPERADMIN")) {
			if(!userSearch.getRole().equalsIgnoreCase("Admin")) {
				if (userSearch.getRole() != null && (!userSearch.getRole().trim().isEmpty())) {
					sql.append(" and u1.role Like '" + userSearch.getRole() + "%'");
					count.append(" and u1.role Like '" + userSearch.getRole() + "%'");

				}
			}
		}
		
		int startingRow = 0;
		if (userSearch.getPageNo() == 1) {
			startingRow = 0;
		} else {
			startingRow = ((userSearch.getPageNo() - 1) * userSearch.getPageSize());
		}

		if (userSearch.isFlag()) {
			sql.append(" order by u1.id asc LIMIT " + startingRow + "," + userSearch.getPageSize());
			Query selectQuery = this.getEntityManager().createNativeQuery(sql.toString());
			selectQuery.setParameter(1, userSearch.getRegistrationId());
			List<Object[]> results = selectQuery.getResultList();

			Query query = this.getEntityManager().createNativeQuery(count.toString());
			Object countResult = query.getSingleResult();

			totalRecords = Integer.valueOf(countResult.toString());

			List<UserListTransfer> userList;
			userList = new ArrayList<>();
			for (Object[] obj : results) {
				UserListTransfer user = new UserListTransfer();
				user.setId(Long.valueOf(obj[0].toString()));
				user.setName((String) obj[1]);
				user.setFullName((String) obj[2]);
				user.setContactNumber((String) (obj[3]));
				user.setExtension((String) (obj[4]));
				user.setEmail((String) obj[5]);
				user.setRole((String) obj[6]);
				user.setStatus((String) obj[7]);
				userList.add(user);
			}
			listss.setList(userList);
			response = new Response(ExceptionMessage.Accepted, listss);
			Integer totalPages = 1;
			if (totalRecords == 0) {
				response.setTotalPages(1);
			} else {
				totalPages = totalRecords / userSearch.getPageSize();
				totalPages = (totalRecords % userSearch.getPageSize()) > 0 ? totalPages + 1 : totalPages;
				response.setTotalPages(totalPages);
				response.setTotalRecords(totalRecords);

			}

			return response;

		} else {
			FileOutputStream out = null;
			File file = null;
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("User Details");
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setFontHeightInPoints((short) 14);
			CellStyle backgroundStyle = workbook.createCellStyle();
			backgroundStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
			backgroundStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerFont.setColor(IndexedColors.BLACK.getIndex());
			CellStyle headerCellStyle = workbook.createCellStyle();
			Query query = null;
			Row header = sheet.createRow(0);
			header.createCell(0).setCellValue("Id");
			header.createCell(1).setCellValue("Name");
			header.createCell(2).setCellValue("Contact Number");
			header.createCell(3).setCellValue("Extension");
			header.createCell(4).setCellValue("Email");
			header.createCell(5).setCellValue("Role");
			header.createCell(6).setCellValue("Status");
			header.createCell(7).setCellValue("Full Name");
			header.createCell(8).setCellValue("Reports To");
			header.createCell(9).setCellValue("Reporting Mail");
			int rowCount = 0;

			Query selectQuery = this.getEntityManager().createNativeQuery(sql.toString());
			selectQuery.setParameter(1, userSearch.getRegistrationId());
			List<Object[]> results = selectQuery.getResultList();
			for (Object[] obj : results) {
				Row row = sheet.createRow(++rowCount);
				int columnCount = -1;
				for (Object object : obj) {
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

			}

			try {
				StringBuilder pro = new StringBuilder(excelFilePath);
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
				String strDate = dateFormat.format(new Date());
				pro.append("UserDetails" + strDate + "File");

				file = new File(pro + ".xlsx");
				out = new FileOutputStream(file);
				workbook.write(out);
				byte[] bytes = null;
				java.nio.file.Path p = Paths.get(file.getAbsolutePath());
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

	////////////////////

	class UserNameTransfer {
		Long id;
		String fullNameOfUser;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getFullNameOfUser() {
			return fullNameOfUser;
		}

		public void setFullNameOfUser(String fullNameOfUser) {
			this.fullNameOfUser = fullNameOfUser;
		}

	}

	@Override
	public List<String> findActiveContactByEmail(Long id, String email, Long registrationId) {
		List<String> listData = new ArrayList<>();
		Query query = null;
		String sql = "SELECT email from user where email='" + email + "' and id=" + id + " and reg_Id="
				+ registrationId;
		query = getEntityManager().createNativeQuery(sql);

		// @SuppressWarnings("unchecked")
		List<String> results = query.getResultList();
		System.out.println(results);
		return results;
	}

}

/*
 * @Override public Response findAllUsers(UserSearch userSearch) {
 * 
 * Response response = null; UsersList listss = new UsersList(); StringBuilder
 * sql = new StringBuilder(); if
 * (userSearch.getRole().equalsIgnoreCase("SUPERADMIN") ||
 * userSearch.getRole().equalsIgnoreCase("Admin")) { sql.
 * append(" SELECT u1.id, u1.name, CONCAT(u1.firstName,' ',u1.lastName) AS fullName, u1.contactNumber,"
 * + " u1.extension, u1.email, u1.role, u1.status," +
 * " CONCAT(u1.firstName,' ',u1.lastName) AS reportsTo, u1.name AS reportingMail FROM `user` u1 "
 * // + " INNER JOIN `user` u1 ON u1.id = u1.reportingId_id " +
 * " WHERE u1.reg_id=?"); } else { sql.
 * append(" SELECT u1.id, u1.name, CONCAT(u1.firstName,' ',u1.lastName) AS fullName, u1.contactNumber,"
 * + " u1.extension, u1.email, u1.role, u1.status," +
 * " CONCAT(u2.firstName,' ',u2.lastName) AS reportsTo, u2.name AS reportingMail FROM `user` u1 "
 * + " INNER JOIN `user` u2 ON u2.id = u1.reportingId_id  WHERE u1.reg_id=?"); }
 * 
 * if (userSearch.getContactNo() != null &&
 * (!userSearch.getContactNo().trim().isEmpty())) {
 * sql.append(" and u1.contactNumber Like '" + userSearch.getContactNo() +
 * "%'");
 * 
 * } else if (userSearch.getEmail() != null &&
 * (!userSearch.getEmail().trim().isEmpty())) {
 * sql.append(" and u1.email Like '" + userSearch.getEmail() + "%'");
 * 
 * } else if (userSearch.getFullName() != null &&
 * (!userSearch.getFullName().trim().isEmpty())) {
 * sql.append(" and CONCAT(u1.firstName,' ',u1.lastName) Like '" +
 * userSearch.getFullName() + "%'"); } else if (userSearch.getRoleSearchParam()
 * != null && (!userSearch.getRoleSearchParam().trim().isEmpty())) {
 * sql.append(" and u1.role Like '" + userSearch.getRoleSearchParam() + "%'");
 * 
 * }else if (userSearch.getRole() != null &&
 * (!userSearch.getRole().trim().isEmpty())) { sql.append(" and u1.role Like '"
 * + userSearch.getRole() + "%'"); }
 * 
 * 
 * Integer pageNo = userSearch.getPageNo(); Integer pageSize =
 * userSearch.getPageSize(); int totalRecords = 0; Query selectQuery =
 * this.getEntityManager().createNativeQuery(sql.toString());
 * selectQuery.setParameter(1, userSearch.getRegistrationId()); List<Object[]>
 * results = selectQuery.getResultList();
 * 
 * 
 * System.out.println("///////////////////////" + results.size()); if
 * (userSearch.isFlag()) {
 * 
 * if (results != null) { totalRecords = results.size(); } int startingRow = 0;
 * 
 * if (userSearch.getPageNo() == 1) { startingRow = 0; } else { startingRow =
 * ((userSearch.getPageNo() - 1) * userSearch.getPageSize()); }
 * sql.append(" LIMIT " + startingRow + "," + userSearch.getPageSize());
 * 
 * List<UserListTransfer> userList;
 * 
 * // selectQuery = this.getEntityManager().createNativeQuery(sql.toString());
 * // results = selectQuery.getResultList();
 * 
 * userList = new ArrayList<>(); for (Object[] obj : results) { UserListTransfer
 * user = new UserListTransfer(); user.setId(Long.valueOf(obj[0].toString()));
 * user.setName((String) obj[1]); user.setFullName((String) obj[2]);
 * user.setContactNumber((String) (obj[3])); user.setExtension((String)
 * (obj[4])); user.setEmail((String) obj[5]); user.setRole((String) obj[6]);
 * user.setStatus((String) obj[7]); userList.add(user);
 * System.out.println(userList); }
 * 
 * Integer totalPages = 1; totalRecords = results.size();
 * listss.setTotalRecords(totalRecords); if (totalRecords == 0) {
 * listss.setTotalPages(0); }
 * 
 * if ((totalRecords > 0) && (Integer.valueOf(totalRecords) <= pageSize)) {
 * listss.setTotalPages(1); } else { totalPages = Integer.valueOf(totalRecords)
 * / pageSize; totalPages = (totalPages == 0) ? totalPages : totalPages + 1;
 * listss.setTotalPages(totalPages); } listss.setList(userList);
 * 
 * response = new Response(ExceptionMessage.StatusSuccess, userList);
 * response.setResult(listss); response.setTotalPages(totalPages);
 * response.setTotalRecords(totalRecords);
 * 
 * } else { FileInputStream inputStream = null; FileOutputStream out = null;
 * File file = null; XSSFWorkbook workbook = new XSSFWorkbook(); XSSFSheet sheet
 * = workbook.createSheet("BDM Req Details"); Font headerFont =
 * workbook.createFont(); headerFont.setBold(true);
 * headerFont.setFontHeightInPoints((short) 14); CellStyle backgroundStyle =
 * workbook.createCellStyle();
 * backgroundStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
 * backgroundStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
 * headerFont.setColor(IndexedColors.BLACK.getIndex()); CellStyle
 * headerCellStyle = workbook.createCellStyle(); Query query = null; Row header
 * = sheet.createRow(0); header.createCell(0).setCellValue("Id");
 * header.createCell(1).setCellValue("Name");
 * header.createCell(2).setCellValue("Contact Number");
 * header.createCell(3).setCellValue("Extension");
 * header.createCell(4).setCellValue("Email");
 * header.createCell(5).setCellValue("Role");
 * header.createCell(6).setCellValue("Status");
 * header.createCell(7).setCellValue("Full Name");
 * header.createCell(8).setCellValue("Reporting To");
 * header.createCell(9).setCellValue("Reporting Mail"); int rowCount = 0; for
 * (Object[] obj : results) { Row row = sheet.createRow(++rowCount); int
 * columnCount = -1; for (Object object : obj) { Cell cell =
 * row.createCell(++columnCount); if (object instanceof Integer)
 * cell.setCellValue(Long.valueOf(object + "")); else if (object instanceof
 * String) cell.setCellValue(object + ""); else if (object instanceof Long)
 * cell.setCellValue(Long.valueOf(object + "")); else if (object instanceof
 * Boolean) cell.setCellValue(Boolean.valueOf(object + "")); else {
 * 
 * cell.setCellValue(object + ""); }
 * 
 * }
 * 
 * }
 * 
 * try { StringBuilder pro = new StringBuilder(excelFilePath); SimpleDateFormat
 * dateFormat = new SimpleDateFormat("yyyy-mm-dd"); String strDate =
 * dateFormat.format(new Date()); pro.append("UserDetails" + strDate + "File");
 * 
 * file = new File(pro + ".xlsx"); out = new FileOutputStream(file);
 * workbook.write(out);
 * 
 * byte[] encodedBytes = null; java.nio.file.Path p =
 * Paths.get(file.getAbsolutePath()); encodedBytes = Files.readAllBytes(p);
 * response = new Response(ExceptionMessage.StatusSuccess, encodedBytes); }
 * 
 * catch (Exception e) { e.printStackTrace(); response = new
 * Response(ExceptionMessage.Exception); } finally { try { out.close(); if
 * (file.exists()) { file.delete(); } workbook.close(); } catch (IOException e)
 * { response = new Response(ExceptionMessage.Exception,
 * e.getLocalizedMessage()); } } return response; }
 * 
 * return response; }
 */
