package com.example.demo.dto;

import java.sql.Time;
import java.util.Date;

public class tiepNhanLichKhamDTO {
	private String name;
	private String sdt;
	private String tendv;
	private String tenbs;
	private Date ngayDat;
	private Time gioBatDau;
	private float giaTien;
	private int status;
	
	
	
	@Override
	public String toString() {
		return "tiepNhanLichKhamDTO [name=" + name + ", sdt=" + sdt + ", tendv=" + tendv + ", tenbs=" + tenbs
				+ ", ngayDat=" + ngayDat + ", gioBatDau=" + gioBatDau + ", giaTien=" + giaTien + ", status=" + status
				+ "]";
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
	public String getTendv() {
		return tendv;
	}
	public void setTendv(String tendv) {
		this.tendv = tendv;
	}
	public String getTenbs() {
		return tenbs;
	}
	public void setTenbs(String tenbs) {
		this.tenbs = tenbs;
	}
	public Date getNgayDat() {
		return ngayDat;
	}
	public void setNgayDat(Date ngayDat) {
		this.ngayDat = ngayDat;
	}
	public Time getGioBatDau() {
		return gioBatDau;
	}
	public void setGioBatDau(Time gioBatDau) {
		this.gioBatDau = gioBatDau;
	}
	public float getGiaTien() {
		return giaTien;
	}
	public void setGiaTien(float giaTien) {
		this.giaTien = giaTien;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
}
