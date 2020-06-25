package com.ojas.rpo.security.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.map.annotate.JsonView;
import org.hibernate.annotations.GenericGenerator;

import com.ojas.rpo.security.JsonViews;

@Table(name = "client")
@javax.persistence.Entity
public class Client implements Entity {

	private static final long serialVersionUID = 3773681429440197799L;
	@Id
	@GenericGenerator(name = "client_id", strategy = "com.ojas.rpo.security.util.ClientIdGenerator")
	@GeneratedValue(generator = "client_id")
	private Long id;
	@Column(unique = true, nullable = false)
	private String clientName;
	@Column
	private Date date;
	@Column
	private String phone;
	@Column
	private String email;
	@Column
	private String fax;
	@Column
	private String website;

	@Column
	private String about;
	@ManyToOne
	private Industry industry;

	@Column
	private Long createdBy;

	@Column
	private Long updatedBy;

	@Column
	private String contractFile;

	@Column
	private String others;

	@Column
	private Date startDate;
	@Column
	private Date endDate;
	@Column
	private String leavesAllowed;

	@Column
	private Long leadId;
	@Column
	private Long accountManagerId;

	private String timeDiff;

	public String getTimeDiff() {
		return timeDiff;
	}

	public void setTimeDiff(String timeDiff) {
		this.timeDiff = timeDiff;
	}

	@ManyToOne
	private BillingModel billingModel;

	@Column
	private String tdspercentage;

	private Date lastUpdatedDate;

	@ManyToOne
	private PaymentTerms paymentTerms;

	@ManyToMany
	@JoinTable(name = "servicesMap", joinColumns = @JoinColumn(name = "client_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "service_ID", referencedColumnName = "ID"))
	private List<Services> services;

	@ManyToOne
	private CustomerType customerType;

	private Long registrationId;

	@ManyToOne
	private Source source;

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

	public Industry getIndustry() {
		return industry;
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
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

	public Long getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(Long registrationId) {
		this.registrationId = registrationId;
	}

	public String getLeavesAllowed() {
		return leavesAllowed;
	}

	public void setLeavesAllowed(String leavesAllowed) {
		this.leavesAllowed = leavesAllowed;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	@JsonView(JsonViews.User.class)

	public void setDate(Date date) {
		this.date = date;
	}

	public Client() {
		this.date = new Date();
	}

	@JsonView(JsonViews.User.class)
	public Date getDate() {
		return this.date;
	}

	@JsonView(JsonViews.User.class)
	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BillingModel getBillingModel() {
		return billingModel;
	}

	public void setBillingModel(BillingModel billingModel) {
		this.billingModel = billingModel;
	}

	public PaymentTerms getPaymentTerms() {
		return paymentTerms;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public void setPaymentTerms(PaymentTerms paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	public String getTdspercentage() {
		return tdspercentage;
	}

	public void setTdspercentage(String tdspercentage) {
		this.tdspercentage = tdspercentage;
	}

	public List<Services> getServices() {
		return services;
	}

	public void setServices(List<Services> services) {
		this.services = services;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", clientName=" + clientName + ", date=" + date + ", phone=" + phone + ", email="
				+ email + ", fax=" + fax + ", website=" + website + ", about=" + about + ", industry=" + industry
				+ ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", contractFile=" + contractFile
				+ ", others=" + others + ", startDate=" + startDate + ", endDate=" + endDate + ", leavesAllowed="
				+ leavesAllowed + ", leadId=" + leadId + ", accountManagerId=" + accountManagerId + ", billingModel="
				+ billingModel + ", tdspercentage=" + tdspercentage + ", lastUpdatedDate=" + lastUpdatedDate
				+ ", paymentTerms=" + paymentTerms + ", services=" + services + ", customerType=" + customerType
				+ ", registrationId=" + registrationId + "]";
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

}