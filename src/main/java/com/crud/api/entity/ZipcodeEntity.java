package com.crud.api.entity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.crud.api.generic.entity.BaseEntity;

@Entity
@Table(name = "ncr_zipcode")
public class ZipcodeEntity extends BaseEntity<Integer> implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@NotNull
	@Column(name = "id")
	private Integer id;
	@Basic(optional = false)
	@NotNull
	@Column(name = "zipcode")
	private int zipcode;
	@Size(max = 45)
	@Column(name = "available_status")
	private String availableStatus;
	@Size(max = 250)
	@Column(name = "dpo")
	private String dpo;
	@Column(name = "rush_ship")
	private Character rushShip;
	@Column(name = "cod_ship")
	private Character codShip;
	@Column(name = "inj_ship")
	private Character injShip;
	@Column(name = "fc_branch_code")
	private Integer fcBranchCode;
	@Column(name = "api_status")
	private Character apiStatus;
	@Column(name = "zipcode_status")
	private Character zipcodeStatus;
	@Column(name = "rest_pincode")
	private Character restPincode;
	@Size(max = 50)
	@Column(name = "zc_state")
	private String zcState;
	@Size(max = 10)
	@Column(name = "zc_state_code")
	private String zcStateCode;
	@Size(max = 50)
	@Column(name = "zc_district")
	private String zcDistrict;

	@Column(name = "nspc")
	private String nspc;

	@Column(name = "sspc")
	private String sspc;

	@Column(name = "jit_zc_status")
	private String jitZcStatus;

	public ZipcodeEntity() {
	}

	public ZipcodeEntity(Integer id) {
		this.id = id;
	}

	public ZipcodeEntity(Integer id, int zipcode) {
		this.id = id;
		this.zipcode = zipcode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getZipcode() {
		return zipcode;
	}

	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}

	public String getAvailableStatus() {
		return availableStatus;
	}

	public void setAvailableStatus(String availableStatus) {
		this.availableStatus = availableStatus;
	}

	public String getDpo() {
		return dpo;
	}

	public void setDpo(String dpo) {
		this.dpo = dpo;
	}

	public Character getRushShip() {
		return rushShip;
	}

	public void setRushShip(Character rushShip) {
		this.rushShip = rushShip;
	}

	public Character getCodShip() {
		return codShip;
	}

	public void setCodShip(Character codShip) {
		this.codShip = codShip;
	}

	public Character getInjShip() {
		return injShip;
	}

	public void setInjShip(Character injShip) {
		this.injShip = injShip;
	}

	public Integer getFcBranchCode() {
		return fcBranchCode;
	}

	public void setFcBranchCode(Integer fcBranchCode) {
		this.fcBranchCode = fcBranchCode;
	}

	public Character getApiStatus() {
		return apiStatus;
	}

	public void setApiStatus(Character apiStatus) {
		this.apiStatus = apiStatus;
	}

	public Character getZipcodeStatus() {
		return zipcodeStatus;
	}

	public void setZipcodeStatus(Character zipcodeStatus) {
		this.zipcodeStatus = zipcodeStatus;
	}

	public Character getRestPincode() {
		return restPincode;
	}

	public void setRestPincode(Character restPincode) {
		this.restPincode = restPincode;
	}

	public String getZcState() {
		return zcState;
	}

	public void setZcState(String zcState) {
		this.zcState = zcState;
	}

	public String getZcStateCode() {
		return zcStateCode;
	}

	public void setZcStateCode(String zcStateCode) {
		this.zcStateCode = zcStateCode;
	}

	public String getZcDistrict() {
		return zcDistrict;
	}

	public void setZcDistrict(String zcDistrict) {
		this.zcDistrict = zcDistrict;
	}

	public String getNspc() {
		return nspc;
	}

	public void setNspc(String nspc) {
		this.nspc = nspc;
	}

	public String getSspc() {
		return sspc;
	}

	public void setSspc(String sspc) {
		this.sspc = sspc;
	}

	public String getJitZcStatus() {
		return jitZcStatus;
	}

	public void setJitZcStatus(String jitZcStatus) {
		this.jitZcStatus = jitZcStatus;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof ZipcodeEntity)) {
			return false;
		}
		ZipcodeEntity other = (ZipcodeEntity) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.crud.api.entity.ZipcodeEntity[ id=" + id + " ]";
	}

}
