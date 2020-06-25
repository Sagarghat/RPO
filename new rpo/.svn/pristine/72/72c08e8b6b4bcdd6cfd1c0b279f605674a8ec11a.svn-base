package com.ojas.rpo.security.entity;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.map.annotate.JsonView;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ojas.rpo.security.JsonViews;

@Table(name = "user")
@javax.persistence.Entity
public class User implements Entity, UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private Long id;

	@Column(unique = true)
	private String name;

	@Column(length = 80)
	private String password;

	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column
	private String contactNumber;
	@Column(unique = true)
	private String email;
	@Column
	private String role;

	@Column
	private String status;
	@Column
	private String domin;

	@Column
	private Date date;

	@Column(name = "reg_id")
	private Long registrationId;

	@ManyToOne

	private User reportingId;

	@Column

	private String extension;

	@Column
	private Date dob;
	@Column
	private Date doj;
	@Column
	private String newPassword;

	@Column
	private String employeeId;

	@ManyToMany

	@JoinTable(name = "userFeatures", joinColumns = @JoinColumn(name = "userId", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "featuresId", referencedColumnName = "ID"))
	private List<Feature> feature;

	public List<Feature> getFeature() {
		return feature;
	}

	public void setFeature(List<Feature> feature) {
		this.feature = feature;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Date getDoj() {
		return doj;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	private Set<Role> roles = new HashSet<Role>();

	public User() {
		this.date = new Date();
	}

	public User(String name, String passwordHash) {
		this.name = name;
		this.password = passwordHash;

	}

	@JsonView(JsonViews.User.class)
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@JsonView(JsonViews.Admin.class)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonView(JsonViews.User.class)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonView(JsonViews.User.class)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@JsonView(JsonViews.User.class)
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	@JsonView(JsonViews.User.class)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonView(JsonViews.User.class)
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@JsonView(JsonViews.User.class)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void addRole(Role role) {
		this.roles.add(role);
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.getRoles();
	}

	@Override
	public String getUsername() {
		return this.name;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public User(String name, String password, String firstName, String lastName, String contactNumber, String email,
			String role, String question, String answer, String status, String extension, String designation, Date dob,
			Date doj, String newPassword, User reportingId) {

		super();
		this.name = name;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.contactNumber = contactNumber;
		this.email = email;
		this.role = role;
		this.status = "Active";
		this.extension = extension;
		this.dob = dob;
		this.doj = doj;
		this.newPassword = newPassword;
		this.reportingId = reportingId;

	}

	public User getReportingId() {
		return reportingId;
	}

	public void setReportingId(User reportingId) {
		this.reportingId = reportingId;
	}

	public Long getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(Long registrationId) {
		this.registrationId = registrationId;
	}

	public String getDomin() {
		return domin;
	}

	public void setDomin(String domin) {
		this.domin = domin;
	}

}
