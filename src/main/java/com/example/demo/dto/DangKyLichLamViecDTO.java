package com.example.demo.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

public class DangKyLichLamViecDTO {
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dangKyNgay;
	private List<Integer> thu2;
	private List<Integer> thu3;
	private List<Integer> thu4;
	private List<Integer> thu5;
	private List<Integer> thu6;
	private List<Integer> thu7;
	public Date getDangKyNgay() {
		return dangKyNgay;
	}
	public List<Integer> getThu2() {
		return thu2;
	}
	public void setThu2(List<Integer> thu2) {
		this.thu2 = thu2;
	}
	public List<Integer> getThu3() {
		return thu3;
	}
	public void setThu3(List<Integer> thu3) {
		this.thu3 = thu3;
	}
	public List<Integer> getThu4() {
		return thu4;
	}
	public void setThu4(List<Integer> thu4) {
		this.thu4 = thu4;
	}
	public List<Integer> getThu5() {
		return thu5;
	}
	public void setThu5(List<Integer> thu5) {
		this.thu5 = thu5;
	}
	public List<Integer> getThu6() {
		return thu6;
	}
	public void setThu6(List<Integer> thu6) {
		this.thu6 = thu6;
	}
	public List<Integer> getThu7() {
		return thu7;
	}
	public void setThu7(List<Integer> thu7) {
		this.thu7 = thu7;
	}
	public void setDangKyNgay(Date dangKyNgay) {
		this.dangKyNgay = dangKyNgay;
	}

	
	
	
	
}
