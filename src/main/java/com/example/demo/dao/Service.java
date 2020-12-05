package com.example.demo.dao;

import java.io.Serializable;
import java.sql.Time;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="`service`")
public class Service  implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id_ser;
	private String name;
	private Time time_working;
	private String dsc ; 	
	private float price;
	private Boolean status;
	
	
	
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getId_ser() {
		return id_ser;
	}
	public void setId_ser(int id_ser) {
		this.id_ser = id_ser;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Time getTime_working() {
		return time_working;
	}
	public void setTime_working(Time time_working) {
		this.time_working = time_working;
	}
	public String getDsc() {
		return dsc;
	}
	public void setDsc(String dsc) {
		this.dsc = dsc;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	
}
