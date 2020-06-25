package com.ojas.rpo.security.entity;

import java.util.Date;




public class CandidateMappingRequest {
	
	private Long id;
	private BdmReq bdmReq;	
	private Candidate candidate;
	private String candidateStatus;	
	private Date lastUpdatedDate;	
	private User mappedUser;	
	private Long regId;	
	private Long stageOfInterview;	
	private String remarks;	
	private Long candidateStatusId;	
	private boolean statusFlag;	
	private int diffOfMappedReviewDays;	
	private int diffOfReviewSubmitDays;	
	private int diffOfSubmitShortListedDays;	
	private Integer pageNum;
	private Integer pageSize;	
	private Date fromDate;	
	private Date toDate;	
	private String clientName;	
	private String requirementName;
	private Long ctcOffered;
	private Date dateOfJoining;
	private Date offerReleaseDate;
	private boolean flag;
	private String stage;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BdmReq getBdmReq() {
		return bdmReq;
	}

	public void setBdmReq(BdmReq bdmReq) {
		this.bdmReq = bdmReq;
	}

	public Candidate getCandidate() {
		return candidate;
	}

	public void setCandidate(Candidate candidate) {
		this.candidate = candidate;
	}

	public String getCandidateStatus() {
		return candidateStatus;
	}

	public void setCandidateStatus(String candidateStatus) {
		this.candidateStatus = candidateStatus;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public User getMappedUser() {
		return mappedUser;
	}

	public void setMappedUser(User mappedUser) {
		this.mappedUser = mappedUser;
	}

	public Long getRegId() {
		return regId;
	}

	public void setRegId(Long regId) {
		this.regId = regId;
	}

	public Long getStageOfInterview() {
		return stageOfInterview;
	}

	public void setStageOfInterview(Long stageOfInterview) {
		this.stageOfInterview = stageOfInterview;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getCandidateStatusId() {
		return candidateStatusId;
	}

	public void setCandidateStatusId(Long candidateStatusId) {
		this.candidateStatusId = candidateStatusId;
	}

	public boolean isStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(boolean statusFlag) {
		this.statusFlag = statusFlag;
	}

	public int getDiffOfMappedReviewDays() {
		return diffOfMappedReviewDays;
	}

	public void setDiffOfMappedReviewDays(int diffOfMappedReviewDays) {
		this.diffOfMappedReviewDays = diffOfMappedReviewDays;
	}

	public int getDiffOfReviewSubmitDays() {
		return diffOfReviewSubmitDays;
	}

	public void setDiffOfReviewSubmitDays(int diffOfReviewSubmitDays) {
		this.diffOfReviewSubmitDays = diffOfReviewSubmitDays;
	}

	public int getDiffOfSubmitShortListedDays() {
		return diffOfSubmitShortListedDays;
	}

	public void setDiffOfSubmitShortListedDays(int diffOfSubmitShortListedDays) {
		this.diffOfSubmitShortListedDays = diffOfSubmitShortListedDays;
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

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getRequirementName() {
		return requirementName;
	}

	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public Long getCtcOffered() {
		return ctcOffered;
	}

	public void setCtcOffered(Long ctcOffered) {
		this.ctcOffered = ctcOffered;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public Date getOfferReleaseDate() {
		return offerReleaseDate;
	}

	public void setOfferReleaseDate(Date offerReleaseDate) {
		offerReleaseDate = offerReleaseDate;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	
}
