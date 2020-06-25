package com.ojas.rpo.security.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "company_reg")
@javax.persistence.Entity
public class CompanyReg implements Entity {
	@Id
	@GeneratedValue
	@Column(name = "reg_id")
	private Long registrationId;
	
	@Column(name = "company_name")
	private String companyName;
	
	@Column(name = "email")
	private String emailId;
	
	@Column(name = "phone_no")
	private Long phoneNo;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "zip_code")
	private Long zipCode;
	
	@Column(name = "plan_id")
	private Long planId;
	
	@Column(name = "status")
	private int compStatus;
	
	@Column(name = "email_count")
	private int emailCount;
	
	@Column(name = "user_count")
	private int userCount;
	
	private String domain;
	
	
	
	

	public String getDomain() {
		return domain;
	}



	public void setDomain(String domain) {
		this.domain = domain;
	}



	public Long getZipCode() {
		return zipCode;
	}



	public void setZipCode(Long zipCode) {
		this.zipCode = zipCode;
	}



	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.getRegistrationId();
	}

	

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Long getRegistrationId() {
		return registrationId;
	}



	public void setRegistrationId(Long registrationId) {
		this.registrationId = registrationId;
	}



	public Long getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(Long phoneNo) {
		this.phoneNo = phoneNo;
	}

	

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Long getPlanId() {
		return planId;
	}

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	

	public int getCompStatus() {
		return compStatus;
	}



	public void setCompStatus(int compStatus) {
		this.compStatus = compStatus;
	}



	public int getEmailCount() {
		return emailCount;
	}



	public void setEmailCount(int emailCount) {
		this.emailCount = emailCount;
	}



	public int getUserCount() {
		return userCount;
	}



	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}



	


	

}
