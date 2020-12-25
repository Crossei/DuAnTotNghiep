package com.example.demo.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class LichSuDatLichDTO {
	private int stt;
	private int id_detail;
	private String tendv;
	private String tenbs;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ngayDat;
	@DateTimeFormat(pattern = "HH:mm")
	private Date gioBatDau;
	private float giaTien;
	private int trangThai;
	
	
	
	
	public int getId_detail() {
		return id_detail;
	}
	public void setId_detail(int id_detail) {
		this.id_detail = id_detail;
	}
	public int getStt() {
		return stt;
	}
	public void setStt(int stt) {
		this.stt = stt;
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
	public Date getGioBatDau() {
		return gioBatDau;
	}
	public void setGioBatDau(Date gioBatDau) {
		this.gioBatDau = gioBatDau;
	}
	public float getGiaTien() {
		return giaTien;
	}
	public void setGiaTien(float giaTien) {
		this.giaTien = giaTien;
	}
	public int getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}
	
	
	
}
