package com.ojas.rpo.security.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name = "candidatemapping")
@javax.persistence.Entity
public class CandidateMapping implements Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7890357955827364622L;

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private BdmReq bdmReq;

	@ManyToOne
	private Candidate candidate;

	@Column(name="candidateStatus")
	private Long candidateStatus;
	
	private Date lastUpdatedDate;
	
	@ManyToOne
	private User mappedUser;
	
	@Column(name="reg_id")
	private Long regId;
	
	@Column(name="stageOfInterview")
	private Long stageOfInterview;
	
	@Column(name="remarks")
	private String remarks;
	
	@Column(name="diff_assign_mapping_days")
	private int diffOfAssignedMapperDays;
	
	@Column(name="diff_mapping_review_days")
	private int diffOfMappedReviewDays;
	
	@Column(name="diff_review_submit_days")
	private int diffOfReviewSubmitDays;
	
	@Column(name="diff_submit_short_listed_days")
	private int diffOfSubmitShortListedDays;
	
	@Column(name="client_comments")
	private String clientComments;
	
	@Column(name="ctc_offered")
	private Long ctcOffered;
	
	@Column(name="offer_release_date")
	private Date offerReleaseDate;
	
	@Column(name="date_of_joining")
	private Date dateOfJoining;
	
	@Column(name="candidate_comments")
	private String candidateComments;
	
	@Column(name="reason")
	private String reason;
	
	
	

	public String getCandidateComments() {
		return candidateComments;
	}

	public void setCandidateComments(String candidateComments) {
		this.candidateComments = candidateComments;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getDiffOfMappedReviewDays() {
		return diffOfMappedReviewDays;
	}

	public void setDiffOfMappedReviewDays(int diffOfMappedReviewDays) {
		this.diffOfMappedReviewDays = diffOfMappedReviewDays;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

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

	

	

	public Long getCandidateStatus() {
		return candidateStatus;
	}

	public void setCandidateStatus(Long candidateStatus) {
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

	public int getDiffOfAssignedMapperDays() {
		return diffOfAssignedMapperDays;
	}

	public void setDiffOfAssignedMapperDays(int diffOfAssignedMapperDays) {
		this.diffOfAssignedMapperDays = diffOfAssignedMapperDays;
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

	public String getClientComments() {
		return clientComments;
	}

	public void setClientComments(String clientComments) {
		this.clientComments = clientComments;
	}

	public Long getCtcOffered() {
		return ctcOffered;
	}

	public void setCtcOffered(Long ctcOffered) {
		this.ctcOffered = ctcOffered;
	}

	public Date getOfferReleaseDate() {
		return offerReleaseDate;
	}

	public void setOfferReleaseDate(Date offerReleaseDate) {
		this.offerReleaseDate = offerReleaseDate;
	}

	public Date getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	

	
	
}
