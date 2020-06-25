package com.ojas.rpo.security.registration;

import java.util.List;

import com.ojas.rpo.security.dao.Dao;
import com.ojas.rpo.security.entity.CompanyReg;
import com.ojas.rpo.security.entity.Response;


public interface RegistrationDAO extends Dao<CompanyReg, Long>{
	List<CompanyReg> findByRegId(Long id);
	int findByEmailId(String emailId,String companyName);
	int findByName(String companyName);
	int findByPhoneNo(Long phoneNo);
	Long getRegistrationId(String emailId); 
	Long getUserId(Long regId); 
	Response updateStatusById(Long id);
	Response updateRegistrationIdById(Long id,String emailId);

}
