package com.example.demo.controller;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.NonUniqueResultException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Exception.UserNotFoundException;
import com.example.demo.configure.Utility;
import com.example.demo.dao.Customer;
import com.example.demo.dao.User;
import com.example.demo.service.MyUserDetailService;
import com.example.demo.service.User2Repository;
import com.example.demo.service.UserRepository;

import net.bytebuddy.utility.RandomString;

@Controller
public class UpdatePassController {
	
	@Autowired
	private MyUserDetailService service;
	@Autowired
	private UserRepository repo;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	User2Repository repo2;

	
	
	
	@GetMapping("/doipass")
	public String showUpdatePasswordForm( Model model) {
		Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
		User user =repo.findByUsernameIs(authentication.getName());
		if(user == null) {
			
			model.addAttribute("title", "Lấy lại mật khẩu: ");
			return "msg";
		}
		model.addAttribute("token", user);
		model.addAttribute("pageTitle", "Lấy lại mật khẩu");
		return "doipass";
		
	}
	
//	@PostMapping("/reset-password")
//	public String processResetPassword(HttpServletRequest rq,Model model) {
//		String token = rq.getParameter("token");
//		String pss = rq.getParameter("password");
//		
//		User user = service.get(token);
//		if(user == null) {
//			model.addAttribute("title", "Lấy lại mật khẩu: ");		
//		}else {
//			service.updatePassword(user, pss);	
//		}
//		return "login";
//	}
	
	@PostMapping("/doipass")
	public String processUpdatePassword(HttpServletRequest rq,Model model) {
	
		Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
		User user =repo.findByUsernameIs(authentication.getName());
		
		String pss = rq.getParameter("password");
		
		String encodedPass = passwordEncoder.encode(pss);

		user.setPassword(encodedPass);
		user.setReset_password_token(null);

		repo2.save(user);
		return "login";
	}
	
}
