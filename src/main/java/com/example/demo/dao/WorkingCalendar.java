package com.example.demo.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class WorkingCalendar { 
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id_working;
	@Column(name = "id_staff")
	private int idstaff;
	private int shift1;
	private int shift2;
	private int shift3;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dateWorking;
	private int status;
	
	
	
	@Override
	public String toString() {
		return "WorkingCalendar [id_working=" + id_working + ", idstaff=" + idstaff + ", shift1=" + shift1 + ", shift2="
				+ shift2 + ", shift3=" + shift3 + ", dateWorking=" + dateWorking + ", status=" + status + "]";
	}
	public WorkingCalendar() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WorkingCalendar(int id_staff, int shift1, int shift2, int shift3, Date dateWorking, int status) {
		super();
		this.idstaff = id_staff;
		this.shift1 = shift1;
		this.shift2 = shift2;
		this.shift3 = shift3;
		this.dateWorking = dateWorking;
		this.status = status;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getId_working() {
		return id_working;
	}
	public void setId_working(int id_working) {
		this.id_working = id_working;
	}
	public int getId_staff() {
		return idstaff;
	}
	public void setId_staff(int id_staff) {
		this.idstaff = id_staff;
	}
	public int getShift1() {
		return shift1;
	}
	public void setShift1(int shift1) {
		this.shift1 = shift1;
	}
	public int getShift2() {
		return shift2;
	}
	public void setShift2(int shift2) {
		this.shift2 = shift2;
	}
	public int getShift3() {
		return shift3;
	}
	public void setShift3(int shift3) {
		this.shift3 = shift3;
	}
	public Date getDateWorking() {
		return dateWorking;
	}
	public void setDateWorking(Date dateWorking) {
		this.dateWorking = dateWorking;
	}
	
	
}
