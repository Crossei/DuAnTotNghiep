package com.example.demo.dto;

import java.util.Date;
import java.util.List;

import javax.annotation.ManagedBean;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;


public class DatLichDTO {
	private String name;
	private String sdt;
	private String email;
	private String date;
	private List<Integer> id_ser;
	@DateTimeFormat(pattern = "HH:mm")
	private String gioKham;
	
	
	
	public String getGioKham() {
		return gioKham;
	}
	public void setGioKham(String gioKham) {
		this.gioKham = gioKham;
	}
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
	public List<Integer> getId_ser() {
		return id_ser;
	}
	public void setId_ser(List<Integer> id_ser) {
		this.id_ser = id_ser;
	}
		
	
	
}
