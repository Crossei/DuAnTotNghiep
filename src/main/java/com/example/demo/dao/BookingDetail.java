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
	@Column(name = "id_staff")
	private int idstaff;
	private int id_service;
	@Column(name = "id_booking")
	private int idBooking;
	@Column(name = "dateWorking_Start")
	private Date dateworkingstart;
	@Column(name= "time_start")
	private Date timestart;
	private Date time_end;
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
		return "BookingDetail [id_detail=" + id_detail + ", idstaff=" + idstaff + ", id_service=" + id_service
				+ ", idBooking=" + idBooking + ", dateworkingstart=" + dateworkingstart + ", timestart=" + timestart
				+ ", time_end=" + time_end + ", status=" + status + ", active=" + active + "]";
	}
	public BookingDetail() {
		
		// TODO Auto-generated constructor stub
	}
	
	
	
	public BookingDetail(int idstaff, int id_service, int idBooking, Date dateWorking_Start, Date time_start,
			Integer status, Integer active) {
		super();
		this.idstaff = idstaff;
		this.id_service = id_service;
		this.idBooking = idBooking;
		this.dateworkingstart = dateWorking_Start;
		this.timestart = time_start;
		this.status = status;
		this.active = active;
	}
	public BookingDetail(int id_staff, Date dateWorking_Start, Time time_start) {
		super();
		this.idstaff = id_staff;
		this.dateworkingstart = dateWorking_Start;
		this.timestart = time_start;
	}
	public BookingDetail(int id_staff, int id_service, int id_booking, Date dateWorking_Start, Integer status,Integer active) {
		super();
		this.idstaff = id_staff;
		this.id_service = id_service;
		this.idBooking = id_booking;
		this.dateworkingstart = dateWorking_Start;
		this.status = status;
		this.active = active;
	}
	public int getId_detail() {
		return id_detail;
	}
	public void setId_detail(int id_detail) {
		this.id_detail = id_detail;
	}
	public int getId_staff() {
		return idstaff;
	}
	public void setId_staff(int id_staff) {
		this.idstaff = id_staff;
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
		return dateworkingstart;
	}
	public void setDateWorking_Start(Date dateWorking_Start) {
		this.dateworkingstart = dateWorking_Start;
	}
	public Date getTime_start() {
		return timestart;
	}
	public void setTime_start(Date time_start) {
		this.timestart = time_start;
	}
	public Date getTime_end() {
		return time_end;
	}
	public void setTime_end(Date time_end) {
		this.time_end = time_end;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
}
