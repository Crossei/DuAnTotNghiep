package com.example.demo.dto;

import java.util.Date;

public class ShowLichDTO {
	private int getDate;
	private String tendv;
	private Date thoiGianDat;
	
	
	
	public int getGetDate() {
		return getDate;
	}
	public void setGetDate(int getDate) {
		this.getDate = getDate;
	}
	public String getTendv() {
		return tendv;
	}
	public void setTendv(String tendv) {
		this.tendv = tendv;
	}
	public Date getThoiGianDat() {
		return thoiGianDat;
	}
	public void setThoiGianDat(Date thoiGianDat) {
		this.thoiGianDat = thoiGianDat;
	}
	@Override
	public String toString() {
		return "ShowLichDTO [getDate=" + getDate + ", tendv=" + tendv + ", thoiGianDat=" + thoiGianDat + "]";
	}
	
	
	
	
	
	
}
