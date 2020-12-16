package com.example.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import org.thymeleaf.engine.AttributeName;

import com.example.demo.dao.Service;
import com.example.demo.dao.Staff;
import com.example.demo.dao.User;
import com.example.demo.dto.AddStaffDTO;
import com.example.demo.service.ServiceRepository;
@Controller
public class AddServiceController {
	
	
	@Autowired
	private ServiceRepository ser1Repo;
	
	
	@RequestMapping("/dashboard/services/addServices")
	public String addService(Model model) {
		Service service = new Service();
		model.addAttribute("service",service);
		return "dashboard/addServices";
	}

//	@RequestMapping(value="/saveStaff",method =  RequestMethod.POST)
//public String saveStaff(@ModelAttribute(name="staff") AddStaffDTO staffDto, Model model,
//			RedirectAttributes ra,@RequestParam("fileImage") MultipartFile multipartFile) throws IOException, ParseException{
//			
//			String fileName= StringUtils.cleanPath(multipartFile.getOriginalFilename());
//			staffDto.setImage(fileName);
//			
//			SimpleDateFormat fomater = new SimpleDateFormat("YYYY-MM-dd");
//			Date date = fomater.parse(staffDto.getDateWorking_Start());
//			
//			// save tai khoan 
//			List<User> user1 = user2Repo.findByUsername(staffDto.getEmail());
//			String role;
//			if(user1.isEmpty()) {
//				if(staffDto.getRole() == 1) {
//					role = "ROLE_BACSI";
//				}else {
//					role = "ROLE_LETAN";
//				}
//				User user = new User(staffDto.getName_staff(),staffDto.getEmail(),passEnCoder.encode("123"),role);
//				user2Repo.save(user);
//				
//			}else {
//				model.addAttribute("error", "Email này đã được đăng ký");
//				return "dashboard/addStaff";
//			}
//			List<User> userAll = user2Repo.findAll();
//			User newUser = userAll.get(userAll.size() - 1);
//			Staff staffSae = new Staff(newUser.getId(), staffDto.getName_staff(), staffDto.getSex(), staffDto.getAddress(), staffDto.getImage(),
//					staffDto.getEmail(), staffDto.getPhone(), date, 0);
//			
//			Staff saveStaff =staffRepo.save(staffSae);
//			
//			String uploadDir = "/assets/img/" +saveStaff.getId_staff();
//			Path uploadPath =Paths.get(uploadDir);
//			if(!Files.exists(uploadPath)) {
//				Files.createDirectories(uploadPath);
//			}
//			ra.addFlashAttribute("message","oki");
//			
//			try(InputStream inputStream =multipartFile.getInputStream()){
//			Path filePath = uploadPath.resolve(fileName);
//			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
//			}catch(IOException e) {
//				throw new IOException("lỗi"+ fileName);
//			}
//			
//			
//		return "redirect:/dashboard/staff";
//	}
//	
	@RequestMapping(value="/save1",method =  RequestMethod.POST)
	public String saveService1(@ModelAttribute(name="service") Service service, Model model,
			@RequestParam("price") float price,
			RedirectAttributes ra,@RequestParam("fileImage") MultipartFile multipartFile) throws IOException, ParseException{
		
		String fileName= StringUtils.cleanPath(multipartFile.getOriginalFilename());
		Locale localeVN = new Locale("vi", "VN");
	    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
	    String str1 = currencyVN.format(price);
	    System.out.println("Số " + price + " sau khi định dạng = " + str1);
	    service.setStatus(1);
	    if(str1.length()!=0) {
	    	service.setPrice(str1);
	    }else {
		   service.setPrice("");
	    }
			if(fileName.length()!=0) {
				service.setImage(fileName);

			Service saveService =ser1Repo.save(service);
			
			String uploadDir = "/assets/img/" +saveService.getId_ser();
			Path uploadPath =Paths.get(uploadDir);
			if(!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			
			
			try(InputStream inputStream =multipartFile.getInputStream()){
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			}catch(IOException e) {
				throw new IOException("lỗi"+ fileName);
			}}else {
				service.setImage("tay-trang-rang.jpg");
				ser1Repo.save(service);
			}
			ra.addFlashAttribute("message","Thêm dịch vụ thành công!");
		return "redirect:/dashboard/services";
	}
	@RequestMapping(value="/save",method =  RequestMethod.POST)
	public String saveService(@ModelAttribute(name="service") Service service, Model model,
		@RequestParam("image") String image, 
		@RequestParam("price") float price,
			RedirectAttributes ra,@RequestParam("fileImage") MultipartFile multipartFile) throws IOException, ParseException{
		
		String fileName= StringUtils.cleanPath(multipartFile.getOriginalFilename());
		Locale localeVN = new Locale("vi", "VN");
	    NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
	    String str1 = currencyVN.format(price);
	    System.out.println("Số " + price + " sau khi định dạng = " + str1);
	    service.setStatus(1);
	    if(str1.length()!=0) {
	    	service.setPrice(str1);
	    }else {
		   service.setPrice("");
	    }
			if(fileName.length()!=0) {
				service.setImage(fileName);

			Service saveService =ser1Repo.save(service);
			
			String uploadDir = "/assets/img/" +saveService.getId_ser();
			Path uploadPath =Paths.get(uploadDir);
			if(!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			
			
			try(InputStream inputStream =multipartFile.getInputStream()){
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
			}catch(IOException e) {
				throw new IOException("lỗi"+ fileName);
			}}else {
				service.setImage(image);
				ser1Repo.save(service);
			}
			ra.addFlashAttribute("message","Cập nhật dịch vụ thành công!");
		
		return "redirect:/dashboard/services";
	}
	
	@RequestMapping(value="/editService/{id_ser}")
	public ModelAndView  editService(@PathVariable(name="id_ser") Integer id_ser ) {
		ModelAndView mav= new ModelAndView("dashboard/editServices");
		Service service =ser1Repo.getOne(id_ser);
		mav.addObject("service",service);
		return mav;
	}
}
