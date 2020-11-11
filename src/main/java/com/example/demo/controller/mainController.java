package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.User;
import com.example.demo.service.MyUserDetailService;


@Controller
public class mainController {
	
	@Autowired
	private MyUserDetailService service;
	
	@RequestMapping("/")
	public String homePage() {
		return "home";
	}
	
	@RequestMapping("/login")
	public String loginPage() {
		return "login";
	}
	@RequestMapping("/register")
	public String registerPage() {
		return "register";
	}
	@RequestMapping("/dashboard")
	public String dashboard() {
		return "dashboard/admin";
	}
	
	
	
	@RequestMapping("/logout-success")
	public String logoutPage() {
		return "logout.jsp";
	}
	
	@PostMapping("/register" )
	public User registerUserAccount(User user) {
		service.save(user);
		return user;
	}
}
