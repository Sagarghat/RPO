package com.ojas.rpo.security.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * JPA Annotated Pojo that represents a blog post.
 *
 */
@Table(name = "bdmreq")
@javax.persistence.Entity
public class BdmReq implements Entity {

	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "req_id", strategy = "com.ojas.rpo.security.util.RequirementIdGenerator")
	@GeneratedValue(generator = "req_id")
	private Long id;

	@ManyToOne
	private Client client;

	@ManyToOne
	private AddContact addContact;

	@Column
	private String nameOfRequirement;

	@Column
	private Integer noOfPositions;

	@Column
	private Date requirementStartdate;

	@Column
	private Date requirementEndDate;

	@Column
	private String totalExperience;

	@Column
	private String city;

	@Column
	private String country;

	@ManyToMany
	@JoinTable(name = "reqskillMapping", joinColumns = @JoinColumn(name = "REQ_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "SKILL_ID", referencedColumnName = "ID"))
	private List<Skill> skills = new ArrayList<Skill>();

	@Column
	private String otherSkills;

	@ManyToMany
	@JoinTable(name = "reqqualificationMapping", joinColumns = @JoinColumn(name = "REQ_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "QULIFICATION_ID", referencedColumnName = "ID"))
	private List<AddQualification> qualifications = new ArrayList<AddQualification>();

	@Column
	private String otherQualification;

	@Column
	private String jobType;

	private Long registrationId;

	@ManyToOne
	private User createdBy;

	@Column
	private String state;

	@Column
	private Long isHot;

	@Column
	private String comment;

	@Column
	private String fileAttachements;

	public Long getIsHot() {
		return isHot;
	}

	public String getFileAttachements() {
		return fileAttachements;
	}

	public void setFileAttachements(String fileAttachements) {
		this.fileAttachements = fileAttachements;
	}

	public void setIsHot(Long isHot) {
		this.isHot = isHot;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Industry getIndustry() {
		return industry;
	}

	public void setIndustry(Industry industry) {
		this.industry = industry;
	}

	@ManyToOne
	private User modifiedBy;

	@Column
	private Date lastUpdatedDate;

	@Column
	private String requirementStatus;

	@Column
	private String noticePeriod;

	private String budgetTypeForPermanent;

	private String budgetAmountForPermanent;

	private String minBudget;

	private String maxBudget;

	private Long numberOfPositions;

	@Column
	private String requirementType;

	private Integer durationOfContract;

	private String otherLocation;
	private String otherEducations;
	@Column
	private Double revenuePerPosition;

	private String requirementDescription;
	private Long relavantExperience;

	@Column
	private Double expectedRevenue;

	@ManyToOne
	private Industry industry;
	@Column
	private Double actualRevenue;

	@Column
	private Double missedRevenue;

	@Column
	private String jobDesc;

	private Long zipCode;

	public Long getNumberOfPositions() {
		return numberOfPositions;
	}

	public void setNumberOfPositions(Long numberOfPositions) {
		this.numberOfPositions = numberOfPositions;
	}

	public String getOtherLocation() {
		return otherLocation;
	}

	public void setOtherLocation(String otherLocation) {
		this.otherLocation = otherLocation;
	}

	public String getOtherEducations() {
		return otherEducations;
	}

	public void setOtherEducations(String otherEducations) {
		this.otherEducations = otherEducations;
	}

	public String getRequirementDescription() {
		return requirementDescription;
	}

	public void setRequirementDescription(String requirementDescription) {
		this.requirementDescription = requirementDescription;
	}

	public Long getRelavantExperience() {
		return relavantExperience;
	}

	public void setRelavantExperience(Long relavantExperience) {
		this.relavantExperience = relavantExperience;
	}

	public Long getZipCode() {
		return zipCode;
	}

	public void setZipCode(Long zipCode) {
		this.zipCode = zipCode;
	}

	@ManyToMany
	@JoinTable(name = "reqLocationsMapping", joinColumns = @JoinColumn(name = "REQ_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "LOCATION_ID", referencedColumnName = "ID"))
	private List<Location> locations = new ArrayList<Location>();

	@ManyToMany
	@JoinTable(name = "reqdesignationMapping", joinColumns = @JoinColumn(name = "REQ_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "DESIGNATION_ID", referencedColumnName = "ID"))
	private List<Designation> designations = new ArrayList<Designation>();

	@ManyToMany
	@JoinTable(name = "reqcertificatesMapping", joinColumns = @JoinColumn(name = "REQ_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "CERTIFICATE_ID", referencedColumnName = "ID"))
	private List<Certificate> certificates = new ArrayList<Certificate>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public AddContact getAddContact() {
		return addContact;
	}

	public void setAddContact(AddContact addContact) {
		this.addContact = addContact;
	}

	public String getNameOfRequirement() {
		return nameOfRequirement;
	}

	public void setNameOfRequirement(String nameOfRequirement) {
		this.nameOfRequirement = nameOfRequirement;
	}

	public Integer getNoOfPositions() {
		return noOfPositions;
	}

	public void setNoOfPositions(Integer noOfPositions) {
		this.noOfPositions = noOfPositions;
	}

	public Date getRequirementStartdate() {
		return requirementStartdate;
	}

	public void setRequirementStartdate(Date requirementStartdate) {
		this.requirementStartdate = requirementStartdate;
	}

	public Date getRequirementEndDate() {
		return requirementEndDate;
	}

	public void setRequirementEndDate(Date requirementEndDate) {
		this.requirementEndDate = requirementEndDate;
	}

	public String getTotalExperience() {
		return totalExperience;
	}

	public void setTotalExperience(String totalExperience) {
		this.totalExperience = totalExperience;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<Skill> getSkills() {
		return skills;
	}

	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}

	public String getOtherSkills() {
		return otherSkills;
	}

	public void setOtherSkills(String otherSkills) {
		this.otherSkills = otherSkills;
	}

	public List<AddQualification> getQualifications() {
		return qualifications;
	}

	public void setQualifications(List<AddQualification> qualifications) {
		this.qualifications = qualifications;
	}

	public String getOtherQualification() {
		return otherQualification;
	}

	public void setOtherQualification(String otherQualification) {
		this.otherQualification = otherQualification;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public Long getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(Long registrationId) {
		this.registrationId = registrationId;
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(User modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getRequirementStatus() {
		return requirementStatus;
	}

	public void setRequirementStatus(String requirementStatus) {
		this.requirementStatus = requirementStatus;
	}

	public String getNoticePeriod() {
		return noticePeriod;
	}

	public void setNoticePeriod(String noticePeriod) {
		this.noticePeriod = noticePeriod;
	}

	public String getBudgetTypeForPermanent() {
		return budgetTypeForPermanent;
	}

	public void setBudgetTypeForPermanent(String budgetTypeForPermanent) {
		this.budgetTypeForPermanent = budgetTypeForPermanent;
	}

	public String getBudgetAmountForPermanent() {
		return budgetAmountForPermanent;
	}

	public void setBudgetAmountForPermanent(String budgetAmountForPermanent) {
		this.budgetAmountForPermanent = budgetAmountForPermanent;
	}

	public String getMinBudget() {
		return minBudget;
	}

	public void setMinBudget(String minBudget) {
		this.minBudget = minBudget;
	}

	public String getMaxBudget() {
		return maxBudget;
	}

	public void setMaxBudget(String maxBudget) {
		this.maxBudget = maxBudget;
	}

	public String getRequirementType() {
		return requirementType;
	}

	public void setRequirementType(String requirementType) {
		this.requirementType = requirementType;
	}

	public Integer getDurationOfContract() {
		return durationOfContract;
	}

	public void setDurationOfContract(Integer durationOfContract) {
		this.durationOfContract = durationOfContract;
	}

	public Double getRevenuePerPosition() {
		return revenuePerPosition;
	}

	public void setRevenuePerPosition(Double revenuePerPosition) {
		this.revenuePerPosition = revenuePerPosition;
	}

	public Double getExpectedRevenue() {
		return expectedRevenue;
	}

	public void setExpectedRevenue(Double expectedRevenue) {
		this.expectedRevenue = expectedRevenue;
	}

	public Double getActualRevenue() {
		return actualRevenue;
	}

	public void setActualRevenue(Double actualRevenue) {
		this.actualRevenue = actualRevenue;
	}

	public Double getMissedRevenue() {
		return missedRevenue;
	}

	public void setMissedRevenue(Double missedRevenue) {
		this.missedRevenue = missedRevenue;
	}

	public String getJobDesc() {
		return jobDesc;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	public List<Location> getLocations() {
		return locations;
	}

	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}

	public List<Designation> getDesignations() {
		return designations;
	}

	public void setDesignations(List<Designation> designations) {
		this.designations = designations;
	}

	public List<Certificate> getCertificates() {
		return certificates;
	}

	public void setCertificates(List<Certificate> certificates) {
		this.certificates = certificates;
	}

}
