package com.ojas.rpo.security.entity;

import java.util.Date;

public class BdmReqSearch {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String jobOpeningTitle;

	private String status;


	private Long bdmId;

	private Long registrationId;

	private Long clientId;

	private String clientName;

	private Integer pageNum;

	private Integer pageSize;

	private Long isHot;

	private boolean flag;

	private Date reqstartDate;
	private Date reqEndDate;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getJobOpeningTitle() {
		return jobOpeningTitle;
	}

	public void setJobOpeningTitle(String jobOpeningTitle) {
		this.jobOpeningTitle = jobOpeningTitle;
	}

	public Date getReqstartDate() {
		return reqstartDate;
	}

	public void setReqstartDate(Date reqstartDate) {
		this.reqstartDate = reqstartDate;
	}

	public Date getReqEndDate() {
		return reqEndDate;
	}

	public void setReqEndDate(Date reqEndDate) {
		this.reqEndDate = reqEndDate;
	}

	private String amName;

	public String getAmName() {
		return amName;
	}

	public void setAmName(String amName) {
		this.amName = amName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Long getBdmId() {
		return bdmId;
	}

	public void setBdmId(Long bdmId) {
		this.bdmId = bdmId;
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

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
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

	public Long getIsHot() {
		return isHot;
	}

	public void setIsHot(Long isHot) {
		this.isHot = isHot;
	}

}
