package com.example.demo.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dao.User;



public interface UserRepository extends JpaRepository<User, Integer> {
	java.util.Optional<User> findByUsernameAndStatus(String username,int i);
	
	User findByUsernameIs(String username);
	User findById(int id);
	
	public User findByResetPasswordToken(String token);
}
