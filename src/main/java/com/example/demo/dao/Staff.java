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
	private String image;
	private String email;
	private String phone;
	@Column(name = "dateWorking_Start")
	private Date dateWorking_Start;
	private Integer status;
	
	
	
	
	public Staff() {
	
	}
	public Staff(int id_user, String name_staff, int sex, String address, String image, String email, String phone,
			Date dateWorking_Start, Integer status) {
		super();
		this.id_user = id_user;
		this.name_staff = name_staff;
		this.sex = sex;
		this.address = address;
		this.image = image;
		this.email = email;
		this.phone = phone;
		this.dateWorking_Start = dateWorking_Start;
		this.status = status;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
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
	public void setDateWorking_Start(Date date) {
		this.dateWorking_Start = date;
	}
	
	
	
}
