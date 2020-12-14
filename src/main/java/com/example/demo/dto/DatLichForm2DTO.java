package com.example.demo.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class DatLichForm2DTO {
	private int idDichvu;
	private int idStaff;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date  ngay;
	@DateTimeFormat(pattern = "HH:mm")
	private Date gio;
	private String hovaTen;
	private String sdt;
	private String ghiChu;
	
	
	
	
	@Override
	public String toString() {
		return "DatLichForm2DTO [idDichvu=" + idDichvu + ", idStaff=" + idStaff + ", ngay=" + ngay + ", gio=" + gio
				+ ", hovaTen=" + hovaTen + ", sdt=" + sdt + ", ghiChu=" + ghiChu + "]";
	}


	public DatLichForm2DTO() {
		super();
		// TODO Auto-generated constructor stub
	}


	public DatLichForm2DTO(int idDichvu, int idStaff, Date ngay, Date gio, String hovaTen, String sdt, String ghiChu) {
		super();
		this.idDichvu = idDichvu;
		this.idStaff = idStaff;
		this.ngay = ngay;
		this.gio = gio;
		this.hovaTen = hovaTen;
		this.sdt = sdt;
		this.ghiChu = ghiChu;
	}


	public int getIdDichvu() {
		return idDichvu;
	}
	
	
	public int getIdStaff() {
		return idStaff;
	}


	public void setIdStaff(int idStaff) {
		this.idStaff = idStaff;
	}


	public void setIdDichvu(int idDichvu) {
		this.idDichvu = idDichvu;
	}


	public Date getNgay() {
		return ngay;
	}
	public void setNgay(Date ngay) {
		this.ngay = ngay;
	}
	public Date getGio() {
		return gio;
	}
	public void setGio(Date gio) {
		this.gio = gio;
	}
	public String getHovaTen() {
		return hovaTen;
	}
	public void setHovaTen(String hovaTen) {
		this.hovaTen = hovaTen;
	}
	public String getSdt() {
		return sdt;
	}
	public void setSdt(String sdt) {
		this.sdt = sdt;
	}
	public String getGhiChu() {
		return ghiChu;
	}
	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
	
	
	
}
