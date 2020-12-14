package com.example.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.Booking;
import com.example.demo.dao.BookingDetail;
import com.example.demo.dao.Customer;
import com.example.demo.dao.Service;
import com.example.demo.dao.Staff;
import com.example.demo.dao.User;
import com.example.demo.dto.DatLichForm2DTO;
import com.example.demo.service.BookingDetailRepository;
import com.example.demo.service.BookingRepository;
import com.example.demo.service.CustomerRepository;
import com.example.demo.service.ServiceRepository;
import com.example.demo.service.StaffRepository;
import com.example.demo.service.User2Repository;
import com.example.demo.service.UserRepository;

@Controller
public class DatLich1NguoiController {
	@Autowired
	private StaffRepository staffRepo;
	@Autowired
	private UserRepository repo;
	@Autowired
	private User2Repository user2Repo;
	@Autowired
	private ServiceRepository ser1Repo;
	@Autowired
	private CustomerRepository cusRepo;
	@Autowired
	private BookingRepository bokRepo;
	@Autowired
	private BookingDetailRepository bokDetailRepo;
	
	
	@PostMapping("/datlich2")
	public String datLich2(HttpServletRequest rq) throws ParseException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Customer cus = cusRepo.findByEmail(authentication.getName());
		Booking book = new Booking(cus.getId_cus());
		bokRepo.save(book);
		
		SimpleDateFormat fomat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat fomatGio = new SimpleDateFormat("HH:mm");
		Date date = fomat.parse(rq.getParameter("btn"));
		Date gio = fomatGio.parse(rq.getParameter("btn1"));

		
		List<Booking> bookList = bokRepo.findAll();
		Booking bookItem = bookList.get(bookList.size() - 1);
		
		BookingDetail bookDetail = new BookingDetail(Integer.parseInt(rq.getParameter("btn2")), Integer.parseInt(rq.getParameter("btn3")), bookItem.getId_booking(), date,gio, 0, 1);
		bokDetailRepo.save(bookDetail);
		return "redirect:/datlich2";
	}
	
	@RequestMapping("/datlich2")
	public String docList(Model model) {
		//lay ds dich vu
		List<Service> ser = ser1Repo.findAll();
		model.addAttribute("ser", ser);
		
		//lay ngay
		List<Date> ngayDatList = new ArrayList<>();
		Calendar c = Calendar.getInstance(); 
		c.setTime(new Date());
		Date bookDat = c.getTime();
		for(int i =0 ; i<7 ;i++) {
			int getToday = c.get(Calendar.DAY_OF_WEEK);
			ngayDatList.add(bookDat);
			if(getToday == 7) {
			c.add(Calendar.DATE, 2);
			}else {
			c.add(Calendar.DATE, 1);
			}
			bookDat = c.getTime();		
		}
		model.addAttribute("ngayDatList", ngayDatList);
		
		//lay ds bacsi'
		List<Staff> staffList =  staffRepo.findByRole(2);
		model.addAttribute("staffList", staffList);
		
		return "datlich2";
	}
}
