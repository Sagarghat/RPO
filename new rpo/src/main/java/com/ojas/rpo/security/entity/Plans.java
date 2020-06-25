package com.ojas.rpo.security.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Table(name = "plans")
@javax.persistence.Entity
public class Plans implements Entity {
	
	@Id
	@GeneratedValue
	@Column(name = "plan_id")
	private Long planId;

	@Column(name = "no_of_cvs")
	private Long noOfEmails;

	@Column(name = "no_of_users")
	private Long noOfUsers;

	@Column(name = "talent_flag")
	private int talentFlag;

	@Column(name = "plan_type")
	private String planType;

	@Column(name = "price")
	private double price;
	private Long registrationId;
	

	public Long getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(Long registrationId) {
		this.registrationId = registrationId;
	}

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

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

}
