package com.example.demo.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="`staff`")
public class Staff {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id_staff;
	private int id_user;
	private String name_staff;
	private int sex;
	private String address;
	private String email;
	private String phone;
	@Column(name = "dateWorking_Start")
	private Date dateWorking_Start;
	private Integer status;

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public int getId_user() {
		return id_user;
	}
	public void setId_user(int id_user) {
		this.id_user = id_user;
	}
	public int getId_staff() {
		return id_staff;
	}
	public void setId_staff(int id_staff) {
		this.id_staff = id_staff;
	}
	public String getName_staff() {
		return name_staff;
	}
	public void setName_staff(String name_staff) {
		this.name_staff = name_staff;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getDateWorking_Start() {
		return dateWorking_Start;
	}
	public void setDateWorking_Start(Date dateWorking_Start) {
		this.dateWorking_Start = dateWorking_Start;
	}
	
	
	
}
