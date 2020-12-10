package com.example.demo.dao;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class WorkingCalendar {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id_working;
	private int id_staff;
	private int shift1;
	private int shift2;
	private int shift3;
	private Date dateWorking;
	
	public int getId_working() {
		return id_working;
	}
	public void setId_working(int id_working) {
		this.id_working = id_working;
	}
	public int getId_staff() {
		return id_staff;
	}
	public void setId_staff(int id_staff) {
		this.id_staff = id_staff;
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
