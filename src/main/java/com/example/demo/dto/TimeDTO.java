package com.example.demo.dto;


public class TimeDTO {
	private int id_ser;
	private String name;
	private String image;
	private String time_working;
	private String dsc ; 	
	private float price;
	private Boolean status;
	public int getId_ser() {
		return id_ser;
	}
	public void setId_ser(int id_ser) {
		this.id_ser = id_ser;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getTime_working() {
		return time_working;
	}
	public void setTime_working(String time_working) {
		this.time_working = time_working;
	}
	public String getDsc() {
		return dsc;
	}
	public void setDsc(String dsc) {
		this.dsc = dsc;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	
}
