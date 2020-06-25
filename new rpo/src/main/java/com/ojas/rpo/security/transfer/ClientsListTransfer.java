package com.ojas.rpo.security.transfer;

import java.util.Date;

public class ClientsListTransfer {

	private Long id;
	private String clientName;
	private String customerType;
	private Date startDate;
	private String amName;
	private String leadName;
	private Date lastUpdatedDate;
	private Date date;
	private Long phone;
	private String email;
	private String fax;
	private String website;

	private String about;
	private String industry;

	private Long createdBy;

	private Long updatedBy;

	private String contractFile;

	private String others;

	private Date endDate;
	private String leavesAllowed;

	private Long leadId;
	private Long accountManagerId;

	private Long registrationId;
	private Long billingModel;

	private String tdspercentage;

	private Long paymentTerms;
	private String leadEmail;

	public String getLeadEmail() {
		return leadEmail;
	}

	public void setLeadEmail(String leadEmail) {
		this.leadEmail = leadEmail;
	}

	private Long source;

	public Long getSource() {
		return source;
	}

	public void setSource(Long source) {
		this.source = source;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getLeadName() {
		return leadName;
	}

	public void setLeadName(String leadName) {
		this.leadName = leadName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getAmName() {
		return amName;
	}

	public void setAmName(String amName) {
		this.amName = amName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getLeavesAllowed() {
		return leavesAllowed;
	}

	public void setLeavesAllowed(String leavesAllowed) {
		this.leavesAllowed = leavesAllowed;
	}

	public Long getLeadId() {
		return leadId;
	}

	public void setLeadId(Long leadId) {
		this.leadId = leadId;
	}

	public Long getAccountManagerId() {
		return accountManagerId;
	}

	public void setAccountManagerId(Long accountManagerId) {
		this.accountManagerId = accountManagerId;
	}

	public Long getBillingModel() {
		return billingModel;
	}

	public void setBillingModel(Long billingModel) {
		this.billingModel = billingModel;
	}

	public String getTdspercentage() {
		return tdspercentage;
	}

	public void setTdspercentage(String tdspercentage) {
		this.tdspercentage = tdspercentage;
	}

	public Long getPaymentTerms() {
		return paymentTerms;
	}

	public void setPaymentTerms(Long paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public Long getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(Long registrationId) {
		this.registrationId = registrationId;
	}

	public String getContractFile() {
		return contractFile;
	}

	public void setContractFile(String contractFile) {
		this.contractFile = contractFile;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

}
