package com.ojas.rpo.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "addcontact")
@javax.persistence.Entity
public class AddContact implements Entity {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private String domain;

	@ManyToOne
	private Client client;

	@Column
	private String email;

	@Column
	private String jobTitle;

	@Column
	private String phone;

	@Column
	private String mobile;

	@Column
	private String twitterId;

	@Column
	private String skypeId;

	@Column
	private Long registrationId;

	@ManyToOne
	private Source source;

	@Column
	private Long contactOwner;

	@Column
	private Boolean isPrimaryContact;

	@Column
	private Boolean emailOptOut;
	@Column
	private String others;

	private String description;

	private Date lastModifiedTime;

	@Transient
	private String diffOfDays;

	private Long createdBy;

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public String getDiffOfDays() {
		return diffOfDays;
	}

	public void setDiffOfDays(String diffOfDays) {
		this.diffOfDays = diffOfDays;
	}

	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTwitterId() {
		return twitterId;
	}

	public void setTwitterId(String twitterId) {
		this.twitterId = twitterId;
	}

	public String getSkypeId() {
		return skypeId;
	}

	public void setSkypeId(String skypeId) {
		this.skypeId = skypeId;
	}

	public Long getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(Long registrationId) {
		this.registrationId = registrationId;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public Long getContactOwner() {
		return contactOwner;
	}

	public void setContactOwner(Long contactOwner) {
		this.contactOwner = contactOwner;
	}

	public Boolean getIsPrimaryContact() {
		return isPrimaryContact;
	}

	public void setIsPrimaryContact(Boolean isPrimaryContact) {
		this.isPrimaryContact = isPrimaryContact;
	}

	public Boolean getEmailOptOut() {
		return emailOptOut;
	}

	public void setEmailOptOut(Boolean emailOptOut) {
		this.emailOptOut = emailOptOut;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "AddContact [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", domain=" + domain
				+ ", client=" + client + ", email=" + email + ", jobTitle=" + jobTitle + ", phone=" + phone
				+ ", mobile=" + mobile + ", twitterId=" + twitterId + ", skypeId=" + skypeId + ", registrationId="
				+ registrationId + ", source=" + source + ", contactOwner=" + contactOwner + ", isPrimaryContact="
				+ isPrimaryContact + ", emailOptOut=" + emailOptOut + ", others=" + others + ", description="
				+ description + "]";
	}

}
