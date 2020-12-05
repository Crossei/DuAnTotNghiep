package com.example.demo.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Booking {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id_booking;
	@Column(name = "id_cus")
	private int idCus;
	
	
	
	public Booking(int id_cus) {
		super();
		this.idCus = id_cus;
	}
	public Booking() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId_booking() {
		return id_booking;
	}
	public void setId_booking(int id_booking) {
		this.id_booking = id_booking;
	}
	public int getId_cus() {
		return idCus;
	}
	public void setId_cus(int id_cus) {
		this.idCus = id_cus;
	}
	
	
}
