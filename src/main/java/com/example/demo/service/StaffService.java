package com.example.demo.service;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;

import javax.persistence.Column;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.demo.service.StaffRepository;


import com.example.demo.dao.Staff;

@Service
public class StaffService {

	@Autowired
	private StaffRepository staffRepo;
	
	public void saveImage(MultipartFile file, String name_staff,int sex,int id_user,
	String address, String email,String phone) {
		Staff s = new Staff();
		String fileName = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
		if(fileName.contains("..")) {
		System.out.println("not");	
		}
		try{
			s.setAddress(Base64.getEncoder().encodeToString(file.getBytes()));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
		s.setEmail(email);
		s.setId_user(id_user);
		s.setName_staff(name_staff);
		s.setPhone(phone);
		s.setSex(sex);
		
		staffRepo.save(s);
	}
}
