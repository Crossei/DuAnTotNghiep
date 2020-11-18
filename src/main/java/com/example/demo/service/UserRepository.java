package com.example.demo.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dao.User;



public interface UserRepository extends JpaRepository<User, Integer> {
	java.util.Optional<User> findByUsername(String username);
	
	User findByUsernameIs(String username);
	
	public User findByResetPasswordToken(String token);
}
