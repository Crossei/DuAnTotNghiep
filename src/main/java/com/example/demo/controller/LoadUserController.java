package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.Staff;
import com.example.demo.dao.User;
import com.example.demo.service.UserRepository;;


@Controller
public class LoadUserController {
	
	@Autowired
	private UserRepository userRepo;
	
	private List<User> userList;
	
	@RequestMapping("/dashboard/account")
	public String dashboardUser(Model model) {
		userList = userRepo.findAll();
		model.addAttribute("userLists", userList);
		return "dashboard/account";
	}
	
	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
}
