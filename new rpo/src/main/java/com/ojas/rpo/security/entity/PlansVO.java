package com.ojas.rpo.security.entity;



public class PlansVO {

	private Long planId;

	private Long noOfEmails;

	
	private Long noOfUsers;

	
	private int talentFlag;

	
	private String planType;

	
	private double price;


	public Long getPlanId() {
		return planId;
	}


	public void setPlanId(Long planId) {
		this.planId = planId;
	}


	public Long getNoOfEmails() {
		return noOfEmails;
	}


	public void setNoOfEmails(Long noOfEmails) {
		this.noOfEmails = noOfEmails;
	}


	public Long getNoOfUsers() {
		return noOfUsers;
	}


	public void setNoOfUsers(Long noOfUsers) {
		this.noOfUsers = noOfUsers;
	}


	public int getTalentFlag() {
		return talentFlag;
	}


	public void setTalentFlag(int talentFlag) {
		this.talentFlag = talentFlag;
	}


	public String getPlanType() {
		return planType;
	}


	public void setPlanType(String planType) {
		this.planType = planType;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
