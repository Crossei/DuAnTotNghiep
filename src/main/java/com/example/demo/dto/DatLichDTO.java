package com.example.demo.dto;

import java.util.Date;

import javax.annotation.ManagedBean;

import org.springframework.stereotype.Component;


public class DatLichDTO {
	private String name;
	private String sdt;
	private String email;
	private String date;
	private int id_ser;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSdt() {
		return sdt;
	}
	public void setSdt(String sdt) {
		this.sdt = sdt;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getId_ser() {
		return id_ser;
	}
	public void setId_ser(int id_ser) {
		this.id_ser = id_ser;
	}
		
	
	
}
