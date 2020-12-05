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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Exception.UserNotFoundException;
import com.example.demo.configure.Utility;
import com.example.demo.dao.User;
import com.example.demo.service.MyUserDetailService;
import com.example.demo.service.User2Repository;

import net.bytebuddy.utility.RandomString;

@Controller
public class forgotPasswordController {
	
	@Autowired
	private MyUserDetailService service;
	
	@Autowired
    private JavaMailSender emailSender;
	
	@GetMapping("/forgot-password")
	public String showForgotPasswordForm(Model model) {
		model.addAttribute("pageTitle", "Forgot Password");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || authentication instanceof AnonymousAuthenticationToken)
		return "forgot_password";
		
		return "redirect:/";
	}
	
	@PostMapping("/forgot-password")
	public String processForgotPassword(HttpServletRequest rq,Model model) throws NonUniqueResultException {
		String email = rq.getParameter("username");
		String token =  RandomString.make(45);
		
		try {
			service.updateResetPassword(token, email);
			String resetPasswordLink = "http://localhost:8080/reset-password?token="+token;
			
			sendEmail(email,resetPasswordLink);
			
			model.addAttribute("msg", "Tin nhắn đổi mật khẩu đã được gửi đến email của bạn");
			
			 
		} catch (UserNotFoundException e) {
			model.addAttribute("error", e.getMessage());
			e.printStackTrace();
		}
		
		
		return "forgot_password";
	}

	private void sendEmail(String email, String resetPasswordLink) {
		// TODO Auto-generated method stub
		MimeMessage msg = emailSender.createMimeMessage();
		MimeMessageHelper  helper =  new MimeMessageHelper(msg);
		
		try {
			helper.setFrom("chiendt20@gmail.com" , "Hỗ Trợ:  ");
			helper.setTo(email);
			String s = "Đây là Link thay đổi mật khẩu";
			String content = "<p>Xin chào</p>"
							+"<p>Bạn đã yêu cầu đổi mật khẩu</p>"
							+"<p>Nhấn vào Link bên dưới để đổi mật khẩu</p>"
							+"<p><a href=\""+resetPasswordLink+"\">Nhấn vào đây</a></p>"
							+"<p>Bỏ qua email này nếu bạn vẫn nhớ mật khẩu của bạn</p>";
			helper.setSubject(s);
			helper.setText(content, true);
			emailSender.send(msg);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
	
	@GetMapping("/reset-password")
	public String showResetPasswordForm(@Param (value = "token")String token, Model model) {
		User user = service.get(token);
		if(user == null) {
			model.addAttribute("msg", "Token không hợp lệ");
			model.addAttribute("title", "Lấy lại mật khẩu: ");
			return "msg";
		}
		model.addAttribute("token", token);
		model.addAttribute("pageTitle", "Lấy lại mật khẩu");
		return "reset_password";
		
	}
	
	@PostMapping("/reset-password")
	public String processResetPassword(HttpServletRequest rq,Model model) {
		String token = rq.getParameter("token");
		String pss = rq.getParameter("password");
		
		User user = service.get(token);
		if(user == null) {
			model.addAttribute("title", "Lấy lại mật khẩu: ");		
		}else {
			service.updatePassword(user, pss);	
		}
		return "login";
	}
	
}
