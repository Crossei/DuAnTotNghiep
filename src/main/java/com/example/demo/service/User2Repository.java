package com.example.demo.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.dao.User;


public interface User2Repository extends JpaRepository<User, Integer> {

	public List<User> findByUsername(String username);
	
	public User findByResetPasswordToken(String token);
}
