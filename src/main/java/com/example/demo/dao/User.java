package com.example.demo.dao;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="`user`")
public class User {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	private String name;
	private String username;
	
		private String password;
	private boolean active;
	private String roles;
	
	@Column(name =  "reset_password_token")
	private String resetPasswordToken;
	
	public User() {
		
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", username=" + username + ", password=" + password + ", active="
				+ active + ", roles=" + roles + "]";
	}
	


	public User(String name,String username, String password,String roles) {
		this.name = name;
		this.username = username;
		this.password = password;
		this.roles =  roles;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}


	public String getName() {
		return name;
	}
	
	


	public String getReset_password_token() {
		return resetPasswordToken;
	}

	public void setReset_password_token(String reset_password_token) {
		this.resetPasswordToken = reset_password_token;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}
	
	
	
}

