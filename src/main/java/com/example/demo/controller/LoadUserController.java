package com.example.demo.controller;

import java.util.ArrayList;
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
	
	
	
	@RequestMapping("/dashboard/account")
	public String dashboardUser(Model model) {
		List<User> userList = userRepo.findAll();
		List<User> userList1 = new ArrayList<>();
		for (User user : userList) {
			userList1.add(user);
			if(user.getRoles().equalsIgnoreCase("ROLE_QUANLY")) {
				userList1.remove(user);
			}
		}
		model.addAttribute("userLists", userList1);
		return "dashboard/account";
	}
	
}
