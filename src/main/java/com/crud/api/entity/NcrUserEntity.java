package com.crud.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.crud.api.model.NcrUserModel;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "ncr_user")
public class NcrUserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "email_id")
	private String emailId;

	@Column(name = "user_name")
	private String username;

	@Column(name = "password")
	private String password;
	
	@Transient
	private String plainPassword;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "role")
	private String role;

	@Column(name = "fc")
	private String fc;

	@Column(name = "permission_details")
	private String permissionDetails;

	@Column(name = "active")
	private boolean active;

	public NcrUserEntity(Integer id) {
		this.id = id;
	}

	public NcrUserEntity(Integer id, String emailId, String userName, String password, String firstName, String lastName) {
		this.id = id;
		this.emailId = emailId;
		this.username = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getFc() {
		return fc;
	}

	public void setFc(String fc) {
		this.fc = fc;
	}

	public String getPermissionDetails() {
		return permissionDetails;
	}

	public void setPermissionDetails(String permissionDetails) {
		this.permissionDetails = permissionDetails;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public NcrUserEntity(NcrUserModel ncrUserModel) {
		this.emailId = ncrUserModel.getUserName();
		this.username= ncrUserModel.getUserName();
		this.firstName =ncrUserModel.getFirstname();
		this.lastName = ncrUserModel.getLastname()!=null?ncrUserModel.getLastname():"";
		
	}

}
