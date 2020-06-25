package com.ojas.rpo.security.util;

import java.util.Map;

public class MappingStatuses {
	private int statusCount;
	private String stages;
	private String requirementName;
	private String clientName;
	
	private int screeningCount;
	private int submissionCount;
	private int interviewCount;
	private int offerCount;
	private int hiredCount;
	private int otherCount;
	private Long bdmReqId;
	
	public int getStatusCount() {
		return statusCount;
	}
	public void setStatusCount(int statusCount) {
		this.statusCount = statusCount;
	}
	public String getStages() {
		return stages;
	}
	public void setStages(String stages) {
		this.stages = stages;
	}
	public String getRequirementName() {
		return requirementName;
	}
	public void setRequirementName(String requirementName) {
		this.requirementName = requirementName;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}	
	public int getScreeningCount() {
		return screeningCount;
	}
	public void setScreeningCount(int screeningCount) {
		this.screeningCount = screeningCount;
	}
	public int getSubmissionCount() {
		return submissionCount;
	}
	public void setSubmissionCount(int submissionCount) {
		this.submissionCount = submissionCount;
	}
	public int getInterviewCount() {
		return interviewCount;
	}
	public void setInterviewCount(int interviewCount) {
		this.interviewCount = interviewCount;
	}
	public int getOfferCount() {
		return offerCount;
	}
	public void setOfferCount(int offerCount) {
		this.offerCount = offerCount;
	}
	public int getHiredCount() {
		return hiredCount;
	}
	public void setHiredCount(int hiredCount) {
		this.hiredCount = hiredCount;
	}
	public int getOtherCount() {
		return otherCount;
	}
	public void setOtherCount(int otherCount) {
		this.otherCount = otherCount;
	}
	public Long getBdmReqId() {
		return bdmReqId;
	}
	public void setBdmReqId(Long bdmReqId) {
		this.bdmReqId = bdmReqId;
	}
	
	
	
}
