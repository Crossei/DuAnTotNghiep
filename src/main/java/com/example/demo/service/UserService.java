package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.User;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	public List<User> listAll(){
		return userRepo.findAll();
		}
	
	public void saveUser(User user) {
		userRepo.save(user);
	}
	
	public User get(Integer id) {
		return userRepo.findById(id).get();
	}
}
