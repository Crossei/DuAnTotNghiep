package com.example.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.example.demo.dao.Staff;

import com.example.demo.dto.AddStaffDTO;
import com.example.demo.service.StaffRepository;
@Controller
public class insertController2 {

	@Autowired
	private StaffRepository staffRepo;

	@RequestMapping("/dashboard/staff/addStaff")
	public String addStaff(Model model) {
		Staff staff = new Staff();
		model.addAttribute("staff",staff);
		return "dashboard/addStaff";
	}
	
	@RequestMapping(value="/saveStaff",method =  RequestMethod.POST)
	public String saveStaff(@ModelAttribute(name="staff") AddStaffDTO staffDto, 
			RedirectAttributes ra,
		
			@RequestParam("fileImage") MultipartFile multipartFile) throws IOException, ParseException{
					
			String fileName= StringUtils.cleanPath(multipartFile.getOriginalFilename());
			staffDto.setImage(fileName);
			
			SimpleDateFormat fomater = new SimpleDateFormat("YYYY-MM-dd");
			Date date = fomater.parse(staffDto.getDateWorking_Start());
			
			Staff staffSae = new Staff(staffDto.getId_user(), staffDto.getName_staff(), staffDto.getSex(), staffDto.getAddress(), staffDto.getImage(),
					staffDto.getEmail(), staffDto.getPhone(), date, 0);
			
			Staff saveStaff =staffRepo.save(staffSae);
			
			String uploadDir = "/assets/img/" +saveStaff.getId_staff();
			Path uploadPath =Paths.get(uploadDir);
			if(!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			ra.addFlashAttribute("message","oki");
			
			try(InputStream inputStream =multipartFile.getInputStream()){
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			}catch(IOException e) {
				throw new IOException("lỗi"+ fileName);
			}
			
			
		return "redirect:/dashboard/staff";
	}
	
	@RequestMapping(value="/editStaff/{id_staff}")
	public ModelAndView  editStaff(@PathVariable(name="id_staff") Integer id_staff ) {
		ModelAndView mav= new ModelAndView("dashboard/editStaff");
		Staff staff =staffRepo.getOne(id_staff);
		mav.addObject("staff",staff);
		return mav;
	}
	
	


}
