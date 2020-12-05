package com.example.demo.dao;

import java.sql.Time;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "`booking_detais`")
public class BookingDetail {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id_detail;
	private int id_staff;
	private int id_service;
	@Column(name = "id_booking")
	private int idBooking;
	private Date dateWorking_Start;
	private Time time_start;
	private Time time_end;
	private Integer status;
	private Integer active;
	
	
	
	
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}
	@Override
	public String toString() {
		return "BookingDetail [id_detail=" + id_detail + ", id_staff=" + id_staff + ", id_service=" + id_service
				+ ", idBooking=" + idBooking + ", dateWorking_Start=" + dateWorking_Start + ", time_start=" + time_start
				+ ", time_end=" + time_end + ", status=" + status + "]";
	}
	public BookingDetail() {
		
		// TODO Auto-generated constructor stub
	}
	public BookingDetail(int id_staff, int id_service, int id_booking, Date dateWorking_Start, Integer status) {
		super();
		this.id_staff = id_staff;
		this.id_service = id_service;
		this.idBooking = id_booking;
		this.dateWorking_Start = dateWorking_Start;
		this.status = status;
	}
	public int getId_detail() {
		return id_detail;
	}
	public void setId_detail(int id_detail) {
		this.id_detail = id_detail;
	}
	public int getId_staff() {
		return id_staff;
	}
	public void setId_staff(int id_staff) {
		this.id_staff = id_staff;
	}
	public int getId_service() {
		return id_service;
	}
	public void setId_service(int id_service) {
		this.id_service = id_service;
	}
	public int getId_booking() {
		return idBooking;
	}
	public void setId_booking(int id_booking) {
		this.idBooking = id_booking;
	}
	public Date getDateWorking_Start() {
		return dateWorking_Start;
	}
	public void setDateWorking_Start(Date dateWorking_Start) {
		this.dateWorking_Start = dateWorking_Start;
	}
	public Time getTime_start() {
		return time_start;
	}
	public void setTime_start(Time time_start) {
		this.time_start = time_start;
	}
	public Time getTime_end() {
		return time_end;
	}
	public void setTime_end(Time time_end) {
		this.time_end = time_end;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
}
