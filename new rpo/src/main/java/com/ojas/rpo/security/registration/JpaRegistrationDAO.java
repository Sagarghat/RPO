package com.ojas.rpo.security.registration;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import org.springframework.transaction.annotation.Transactional;

import com.ojas.rpo.security.dao.JpaDao;
import com.ojas.rpo.security.entity.CompanyReg;
import com.ojas.rpo.security.entity.ExceptionMessage;
import com.ojas.rpo.security.entity.Response;
import com.ojas.rpo.security.entity.User;

public class JpaRegistrationDAO extends JpaDao<CompanyReg, Long> implements RegistrationDAO {

	public JpaRegistrationDAO() {
		super(CompanyReg.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	@Transactional
	public CompanyReg save(CompanyReg entity) {
		// TODO Auto-generated method stub
		return this.getEntityManager().merge(entity);
	}

	@Override
	public List<CompanyReg> findByRegId(Long id) {
		Query query = null;
		query = getEntityManager().createQuery(" from CompanyReg reg where reg.registrationId.id=" + id);

		List<CompanyReg> results = query.getResultList();

		return results;
	}

	@Override
	@Transactional(readOnly = true)
	public int findByEmailId(String emailId, String companyName) {
		Query query = null;
		query = getEntityManager()
				.createQuery(" select count(reg.emailId) from CompanyReg reg where reg.emailId.emailId='" + emailId
						+ "' or companyName= '" + companyName + "'");

		Number results = (Number) query.getSingleResult();

		return results.intValue();
	}

	@Override
	@Transactional(readOnly = true)
	public int findByName(String companyName) {
		Query query = null;
		query = getEntityManager()
				.createQuery(" select count(reg.companyName) from CompanyReg reg where reg.companyName.companyName='"
						+ companyName + "'");

		Number results = (Number) query.getSingleResult();

		return results.intValue();
	}

	@Override
	@Transactional(readOnly = true)
	public int findByPhoneNo(Long phoneNo) {
		Query query = null;
		query = getEntityManager()
				.createQuery(" select count(reg.phoneNo) from CompanyReg reg where reg.phoneNo.phoneNo=" + phoneNo);

		Number results = (Number) query.getSingleResult();

		return results.intValue();
	}

	@Override
	@Transactional(readOnly = true)
	public Long getRegistrationId(String emailId) {
		Query query = null;
		query = getEntityManager().createQuery(
				" select reg.registrationId from CompanyReg reg where reg.emailId.emailId='" + emailId + "'");

		Number results = (Number) query.getSingleResult();

		return results.longValue();
	}

	@Override
	@Transactional(readOnly = true)
	public Long getUserId(Long regId) {
		Query query = null;
		query = getEntityManager()
				.createQuery(" select user.id from User user where user.registrationId.registrationId=" + regId);

		Number results = (Number) query.getSingleResult();

		return results.longValue();
	}

	@Transactional
	public Response updateStatusById(Long regId) {
		try {
			String sql = "UPDATE `user` SET `status`='Active' where id =" + regId;
			if (null != regId) {
				int updateResult = this.getEntityManager().createNativeQuery(sql).executeUpdate();
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

	@Transactional
	public Response updateRegistrationIdById(Long regId, String emailId) {
		try {
			String sql = "UPDATE `user` SET  reg_id=" + regId + ", role ='SuperAdmin' where email ='" + emailId + "'";
			int updateResult = this.getEntityManager().createNativeQuery(sql).executeUpdate();
			System.out.println("Update Result is " + updateResult);
			if (updateResult > 0) {
				Response res = new Response(ExceptionMessage.OK);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Response(ExceptionMessage.OK);
	}

	@Transactional
	public Response updateUserById(Long id, User userUpdate) {
//		try {
//			User user = find(id);
//			// String reportingQuery="select name from user where reportingId=
//			// "+user.getReportingId();
//			String sql = "UPDATE `user` SET ";
//
//			StringBuilder sqlBuilder = new StringBuilder(sql);
//			if (null != user) {
//				if (userUpdate.getFirstName() != null) {
//					sqlBuilder.append(" firstName ='" + userUpdate.getFirstName() + "',");
//				}
//				if (userUpdate.getLastName() != null) {
//					sqlBuilder.append(" lastname='" + userUpdate.getLastName() + "',");
//				}
//				if (userUpdate.getEmail() != null) {
//					sqlBuilder.append(" email='" + userUpdate.getEmail() + "',");
//				}
//				if (userUpdate.getContactNumber() != null) {
//					sqlBuilder.append(" contactNumber=" + userUpdate.getContactNumber() + ",");
//				}
//				if (userUpdate.getExtension() != null) {
//					sqlBuilder.append(" extension=" + userUpdate.getExtension() + ",");
//				}
//				if (userUpdate.getRole() != null) {
//					sqlBuilder.append(" role='" + userUpdate.getRole() + "',");
//				}
//				if (userUpdate.getDoj() != null) {
//
//					sqlBuilder.append(" doj='" + new java.sql.Date(userUpdate.getDoj().getTime()) + "',");
//				}
//				if (userUpdate.getStatus() != null) {
//					sqlBuilder.append(" status='" + userUpdate.getStatus() + "',");
//				}
//				if (userUpdate.getReportingId() != null) {
//					sqlBuilder.append(" reporting_id=" + userUpdate.getReportingId().getId() + ",");
//				}
//				if (userUpdate.getSalary() != null) {
//					sqlBuilder.append(" salary=" + userUpdate.getSalary() + ",");
//				}
//				if (userUpdate.getVariablepay() != null) {
//					sqlBuilder.append(" variablepay=" + userUpdate.getVariablepay() + ",");
//				}
//				if (userUpdate.getCtc() != null) {
//					sqlBuilder.append(" ctc=" + userUpdate.getCtc() + ",");
//				}
//				if (userUpdate.getMintarget() != null) {
//					sqlBuilder.append(" mintarget=" + userUpdate.getMintarget() + ",");
//				}
//				if (userUpdate.getMaxtarget() != null) {
//					sqlBuilder.append(" maxtarget=" + userUpdate.getMaxtarget() + ",");
//				}
//
//				if (userUpdate.getTargetduration() != null) {
//					sqlBuilder.append(" targetduration='" + userUpdate.getTargetduration() + "',");
//				}
//				sqlBuilder.setCharAt(sqlBuilder.length() - 1, ' ');
//				sqlBuilder.append(" WHERE id  = ?");
//				int updateResult = this.getEntityManager().createNativeQuery(sqlBuilder.toString()).setParameter(1, id)
//						.executeUpdate();
//				System.out.println("Update Result is " + updateResult);
//				if (updateResult > 0) {
//					Response res = new Response(ExceptionMessage.OK);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return new Response(ExceptionMessage.OK);
	}

}
