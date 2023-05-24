package com.crud.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.crud.api.entity.NcrUserEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NcrUserModel {

	@JsonProperty("id")
	private Integer id;

	@JsonProperty("username")
	private String userName;

	@JsonProperty("password")
	private String password;
	
	@JsonProperty("firstname")
	private String firstname;
	
	@JsonProperty("lastname")
	private String lastname;
	
	@JsonProperty("permissiondetails")
	private String permissionDetails;
	
	public NcrUserModel(NcrUserEntity entity) {
		this.id = entity.getId();
		this.userName = entity.getUserName();
		this.password = entity.getPassword();
		this.firstname=entity.getFirstName();
		this.permissionDetails=entity.getPermissionDetails();
	}

}
