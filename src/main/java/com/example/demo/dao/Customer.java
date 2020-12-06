package com.example.demo.dao;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id_cus;
	private String name_cus;
	private String phone;
	private String email;
	private int id_user	;
	private Integer status;
	
	
	
	
	public int getId_user() {
		return id_user;
	}
	public void setId_user(int id_user) {
		this.id_user = id_user;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public int getId_cus() {
		return id_cus;
	}
	public void setId_cus(int id_cus) {
		this.id_cus = id_cus;
	}
	public String getName_cus() {
		return name_cus;
	}
	public void setName_cus(String name_cus) {
		this.name_cus = name_cus;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
