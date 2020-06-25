package com.ojas.rpo.security.util;

public class AddContactSearch {

	private Long clientId;

	private Long contactid;

	private String clientName;

	private String contactName;

	private String email;

	private String phone;

	private Long registrationId;

	private Integer pageNum;

	private Integer pageSize;

	private boolean flag;

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Long getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(Long registrationId) {
		this.registrationId = registrationId;
	}

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

	public Long getContactid() {
		return contactid;
	}

	public void setContactid(Long contactid) {
		this.contactid = contactid;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "AddContactSearch [clientId=" + clientId + ", contactid=" + contactid + ", clientName=" + clientName
				+ ", contactName=" + contactName + ", email=" + email + ", phone=" + phone + "]";
	}

}
