package com.example.demo.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.Service;
import com.example.demo.dao.Staff;
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
		List<Staff> staffList =  staffRepo.findAll();
		model.addAttribute("staffList", staffList);
		
		return "datlich2";
	}
}
