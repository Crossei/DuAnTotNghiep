package com.example.demo.dao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.istack.Nullable;

@Entity
public class WorkingCalendar {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id_working;
	@Column(name="id_staff")
	private int idstaff;
	@Nullable
	private Integer shift1;
	@Nullable
	private Integer shift2;
	@Nullable
	private Integer shift3;
	private Date dateWorking;
	private Integer status;
	
	
	public WorkingCalendar(int id_staff, Integer shift1, Integer shift2, Integer shift3, Date dateWorking,
			Integer status) {
		super();
		this.idstaff = id_staff;
		this.shift1 = shift1;
		this.shift2 = shift2;
		this.shift3 = shift3;
		this.dateWorking = dateWorking;
		this.status = status;
	}

	

	@Override
	public String toString() {
		return "WorkingCalendar [id_working=" + id_working + ", idstaff=" + idstaff + ", shift1=" + shift1 + ", shift2="
				+ shift2 + ", shift3=" + shift3 + ", dateWorking=" + dateWorking + ", status=" + status + "]";
	}



	public WorkingCalendar() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
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
	public Integer getShift1() {
		return shift1;
	}
	public void setShift1(Integer shift1) {
		this.shift1 = shift1;
	}
	public Integer getShift2() {
		return shift2;
	}
	public void setShift2(Integer shift2) {
		this.shift2 = shift2;
	}
	public Integer getShift3() {
		return shift3;
	}
	public void setShift3(Integer shift3) {
		this.shift3 = shift3;
	}
	public Date getDateWorking() {
		return dateWorking;
	}
	public void setDateWorking(Date dateWorking) {
		this.dateWorking = dateWorking;
	}
	
	
}
