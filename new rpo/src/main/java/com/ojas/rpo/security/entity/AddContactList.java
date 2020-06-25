package com.ojas.rpo.security.entity;

public class AddContactList {

	private Long id;
	private String email;
	private String mobile;
	private String phone;
	private Long client_id;
	private String contactOwner;
	private String domain;
	private String firstName;
	private Boolean isPrimaryContact;
	private String jobTitle;
	private String lastName;
	private String others;
	private Boolean emailOptOut;
	private String description;
	private Long registrationId;
	private String skypeId;
	private String twitterId;
	private String clientName;
	private String fullName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getClient_id() {
		return client_id;
	}

	public void setClient_id(Long client_id) {
		this.client_id = client_id;
	}

	public String getContactOwner() {
		return contactOwner;
	}

	public void setContactOwner(String contactOwner) {
		this.contactOwner = contactOwner;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Boolean getIsPrimaryContact() {
		return isPrimaryContact;
	}

	public void setIsPrimaryContact(Boolean isPrimaryContact) {
		this.isPrimaryContact = isPrimaryContact;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public Boolean getEmailOptOut() {
		return emailOptOut;
	}

	public void setEmailOptOut(Boolean emailOptOut) {
		this.emailOptOut = emailOptOut;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(Long registrationId) {
		this.registrationId = registrationId;
	}

	public String getSkypeId() {
		return skypeId;
	}

	public void setSkypeId(String skypeId) {
		this.skypeId = skypeId;
	}

	public String getTwitterId() {
		return twitterId;
	}

	public void setTwitterId(String twitterId) {
		this.twitterId = twitterId;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String toString() {
		return "AddContactList [id=" + id + ", email=" + email + ", mobile=" + mobile + ", phone=" + phone
				+ ", client_id=" + client_id + ", contactOwner=" + contactOwner + ", domain=" + domain + ", firstName="
				+ firstName + ", isPrimaryContact=" + isPrimaryContact + ", jobTitle=" + jobTitle + ", lastName="
				+ lastName + ", others=" + others + ", emailOptOut=" + emailOptOut + ", description=" + description
				+ ", registrationId=" + registrationId + ", skypeId=" + skypeId + ", twitterId=" + twitterId
				+ ", clientName=" + clientName + "]";
	}

}
