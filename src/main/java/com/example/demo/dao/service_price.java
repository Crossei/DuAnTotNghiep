package com.example.demo.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="`service_price`")
public class service_price {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id_ser_price;
	private int id_ser;
	private float price;
	public int getId_ser_price() {
		return id_ser_price;
	}
	public void setId_ser_price(int id_ser_price) {
		this.id_ser_price = id_ser_price;
	}
	public int getId_ser() {
		return id_ser;
	}
	public void setId_ser(int id_ser) {
		this.id_ser = id_ser;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	
}
