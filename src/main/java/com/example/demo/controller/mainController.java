package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.User;
import com.example.demo.service.MyUserDetailService;
import com.example.demo.service.User2Repository;
import com.example.demo.service.UserRepository;

@Controller
public class mainController {

	@Autowired
	private MyUserDetailService service;

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private User2Repository repo2;

	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@RequestMapping("/")
	public String homePage() {
		return "home";
	}
	@RequestMapping("/datlich")
	public String datLich() {
		return "datlich";
	}
	@RequestMapping("/about")
	public String about() {
		return "about";
	}
	
	@RequestMapping("/doctor-list")
	public String doctorList() {
		return "doctor-list";
	}
	
	@RequestMapping("/thongtinthem")
	public String moreInfor() {
		return "thongtinthem";
	}

	@RequestMapping("/login")
	public String loginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";}
		
		return "redirect:/";
	}

	@RequestMapping("/register")
	public String registerPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || authentication instanceof AnonymousAuthenticationToken)
			return "register";
		return "redirect:/";
	}

	@RequestMapping("/dashboard")
	public String dashboard() {
		return "dashboard/admin";
	}

	@RequestMapping("/changePass")
	public String changePass() {
		return "doiMatKhau";
	}

	@RequestMapping("/logout-success")
	public String logoutPage() {
		return "logout";
	}

	@PostMapping("/register")
	public User registerUserAccount(User user,Model model) {
		
		
		
		List<User> user1 = repo2.findByUsername(user.getUsername());
		if(user1.isEmpty()) {
			service.save(user);
		}else {
			model.addAttribute("error", "Email này đã được đăng ký");
		}
	
		System.out.println("ok email nay` ngon");
		
	// service.save(user);
		return user;
	}
	
	public boolean checkLoggedIn() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || authentication instanceof AnonymousAuthenticationToken)
			return false;
		return true;
	}


	/*
	 * @PostMapping("/changePass") public User changePass(User user) { List<User>
	 * userName = repo.findAll(); for (User user1 : userName) {
	 * System.out.println(user1.getPassword());
	 * 
	 * if (user1.getUsername().equals(user.getUsername())) {
	 * 
	 * } else { System.out.println("ok email ko ngon"); } } return user; }
	 */
	
	

}
