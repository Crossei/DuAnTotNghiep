package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.StaffRepository;

@Controller
public class userController {
	
	@Autowired
	private StaffRepository staffRepo;
	
	int id_staff;
	
	@RequestMapping("dashboard/staff/delete/{id_staff}")
	public String delete(@PathVariable(name = "id_staff") int id) {
		staffRepo.deleteById(id);
		return "redirect:/dashboard/staff";
	}

	
}
