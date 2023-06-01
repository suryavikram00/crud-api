/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.api.generic.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author NMSLAP570
 */
@MappedSuperclass
public class BaseEntity<T> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private T id;		

	public BaseEntity() {
	}

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}


}
