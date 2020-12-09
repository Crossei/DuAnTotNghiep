package com.example.demo.dao;

import java.util.Date;

import javax.persistence.Column;
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
	private String image;
	private String email;
	@Column(name= "id_user")
	private int iduser	;
	private Integer status;
	
	
	
	
	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public Customer(String name_cus, String image, String email, int iduser, Integer status) {
		super();
		this.name_cus = name_cus;
		this.image = image;
		this.email = email;
		this.iduser = iduser;
		this.status = status;
	}


	public Customer(String name_cus, String email, int id_user, Integer status) {
		super();
		this.name_cus = name_cus;
		this.email = email;
		this.iduser = id_user;
		this.status = status;
	}


	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public int getId_user() {
		return iduser;
	}
	public void setId_user(int id_user) {
		this.iduser = id_user;
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
