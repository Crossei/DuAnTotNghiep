package com.example.demo.dao;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="`service_price`")
public class Service_price implements Serializable{
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id_ser_price;
	
	@Column(name = "id_ser")
	private int idser;
	private float price;
	
	
	public int getId_ser_price() {
		return id_ser_price;
	}
	public void setId_ser_price(int id_ser_price) {
		this.id_ser_price = id_ser_price;
	}
	public int getId_ser() {
		return idser;
	}
	public void setId_ser(int id_ser) {
		this.idser = id_ser;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
	
}
