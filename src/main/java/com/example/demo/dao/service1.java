package com.example.demo.dao;

import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="`service1`")
public class service1 {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id_ser;
	private String name;
	private Time time_working;
	private String dsc ; 	
	private Boolean status;
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
