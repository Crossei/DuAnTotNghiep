package com.example.demo.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import com.example.demo.dao.Booking;
import com.example.demo.dao.BookingDetail;
import com.example.demo.dao.Customer;
import com.example.demo.dao.Service;
import com.example.demo.dao.Staff;
import com.example.demo.dao.User;
import com.example.demo.dto.AddStaffDTO;
import com.example.demo.dto.DatLichDTO;
import com.example.demo.service.CustomerRepository;
import com.example.demo.service.ServiceRepository;
import com.example.demo.service.StaffRepository;
import com.example.demo.service.User2Repository;
import com.example.demo.service.UserRepository;
@Controller
public class InsertController {

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
	private BCryptPasswordEncoder passEnCoder;

	@RequestMapping("/dashboard/staff/addStaff")
	public String addStaff(Model model) {
		AddStaffDTO staff = new AddStaffDTO();
		model.addAttribute("staff",staff);
		return "dashboard/addStaff";
	}
	@RequestMapping("/dashboard/profile")
	public String Profile() {
		
		return "dashboard/profile";
	}
	
	
	@RequestMapping(value="/saveStaff",method =  RequestMethod.POST)
public String saveStaff(@ModelAttribute(name="staff") AddStaffDTO staffDto, Model model,
		
			RedirectAttributes ra,@RequestParam("fileImage") MultipartFile multipartFile) throws IOException, ParseException{
			
			String fileName= StringUtils.cleanPath(multipartFile.getOriginalFilename());
			if(fileName.length()!=0) {
			staffDto.setImage(fileName);
			}else {
				staffDto.setImage("fb.png");
			}
			SimpleDateFormat fomater = new SimpleDateFormat("YYYY-MM-dd");
			
			
			// save tai khoan 
			List<User> user1 = user2Repo.findByUsername(staffDto.getEmail());
			String role;
			Integer role_bacsi ;
			if(user1.isEmpty()) {
				if(staffDto.getRole() == 1) {
					role = "ROLE_BACSI";
					role_bacsi = 2;
				}else {
					role = "ROLE_LETAN";
					role_bacsi = 3;
				}
				User user = new User(staffDto.getName_staff(),staffDto.getEmail(),passEnCoder.encode("123"),role,1);
				user2Repo.save(user);
				
			}else {
				model.addAttribute("error", "Email này đã được đăng ký");
				return "dashboard/addStaff";
			}
			List<User> userAll = user2Repo.findAll();
			User newUser = userAll.get(userAll.size() - 1);
			Staff staffSae = new Staff(newUser.getId(), staffDto.getName_staff(), staffDto.getSex(), staffDto.getAddress(), staffDto.getImage(),
					staffDto.getEmail(), staffDto.getPhone(), staffDto.getDateWorking_Start(), 1,role_bacsi);
			
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
			ra.addFlashAttribute("message","Thêm nhân viên thành công!");
			
		return "redirect:/dashboard/staff";
	}
	
	@RequestMapping(value="/saveStaff1",method =  RequestMethod.POST)
	public String saveStaff1(@ModelAttribute(name="staff") Staff staff, Model model,
			@RequestParam("image") String image,
		
				AddStaffDTO staffDto,
				RedirectAttributes ra,@RequestParam("fileImage") MultipartFile multipartFile) throws IOException, ParseException{
				SimpleDateFormat fomater = new SimpleDateFormat("YYYY-MM-dd");
				
		
				staff.setDateWorking_Start(staffDto.getDateWorking_Start());
				staff.setStatus(1);
				String fileName= StringUtils.cleanPath(multipartFile.getOriginalFilename());
				if(fileName.length()!=0) {
				staff.setImage(fileName);
				
				
				
				// save tai khoan 
				
//				List<User> userAll = user2Repo.findAll();
//				User newUser = userAll.get(userAll.size() - 1);
//				Staff staffSae = new Staff(staffDto.getId_user(), staffDto.getName_staff(), staffDto.getSex(), staffDto.getAddress(), staffDto.getImage(),
//						staffDto.getEmail(), staffDto.getPhone(), date, 0);
//				SimpleDateFormat fomater = new SimpleDateFormat("YYYY-MM-dd");
//				Date date = fomater.parse(staffDto.getDateWorking_Start());
//				
//				staff.setDateWorking_Start(date);
				Staff saveStaff =staffRepo.save(staff);
				
				String uploadDir = "/assets/img/" +saveStaff.getId_staff();
				Path uploadPath =Paths.get(uploadDir);
				if(!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}
				
				
				try(InputStream inputStream =multipartFile.getInputStream()){
				Path filePath = uploadPath.resolve(fileName);
				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				}catch(IOException e) {
					throw new IOException("lỗi"+ fileName);
				}
				}else {
					staff.setImage(image);
					staffRepo.save(staff);
				}
				ra.addFlashAttribute("message","Cập nhật thông tin thành công!");
				
			return "redirect:/dashboard/staff";
		}
	
	@RequestMapping(value="/saveStaff2",method =  RequestMethod.POST)
	public String saveStaff2(@ModelAttribute(name="staff") Staff staff, Model model,
			@RequestParam("image") String image,
		
				AddStaffDTO staffDto,
				RedirectAttributes ra,@RequestParam("fileImage") MultipartFile multipartFile) throws IOException, ParseException{
				SimpleDateFormat fomater = new SimpleDateFormat("YYYY-MM-dd");
				
		
				staff.setDateWorking_Start(staffDto.getDateWorking_Start());
				staff.setStatus(1);
				String fileName= StringUtils.cleanPath(multipartFile.getOriginalFilename());
				if(fileName.length()!=0) {
				staff.setImage(fileName);
				
				
				
				// save tai khoan 
				
//				List<User> userAll = user2Repo.findAll();
//				User newUser = userAll.get(userAll.size() - 1);
//				Staff staffSae = new Staff(staffDto.getId_user(), staffDto.getName_staff(), staffDto.getSex(), staffDto.getAddress(), staffDto.getImage(),
//						staffDto.getEmail(), staffDto.getPhone(), date, 0);
//				SimpleDateFormat fomater = new SimpleDateFormat("YYYY-MM-dd");
//				Date date = fomater.parse(staffDto.getDateWorking_Start());
//				
//				staff.setDateWorking_Start(date);
				Staff saveStaff =staffRepo.save(staff);
				
				String uploadDir = "/assets/img/" +saveStaff.getId_staff();
				Path uploadPath =Paths.get(uploadDir);
				if(!Files.exists(uploadPath)) {
					Files.createDirectories(uploadPath);
				}
				
				
				try(InputStream inputStream =multipartFile.getInputStream()){
				Path filePath = uploadPath.resolve(fileName);
				Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
				}catch(IOException e) {
					throw new IOException("lỗi"+ fileName);
				}
				}else {
					staff.setImage(image);
					staffRepo.save(staff);
				}
				ra.addFlashAttribute("message","Cập nhật thông tin thành công!");
				
			return "redirect:/dashboard";
		}
	
	@RequestMapping(value="/editStaff/{id_staff}")
	public ModelAndView  editStaff(@PathVariable(name="id_staff") Integer id_staff ) {
		ModelAndView mav= new ModelAndView("dashboard/editStaff");
		Staff staff =staffRepo.getOne(id_staff);
		mav.addObject("staff",staff);
		return mav;
	}
	@RequestMapping(value="/profile")
	public ModelAndView  profileStaff(Model model) {
		Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
		User user =repo.findByUsernameIs(authentication.getName());
		Staff staff = staffRepo.findByIduser(user.getId());
		ModelAndView mav= new ModelAndView("dashboard/profile");
		mav.addObject("staff",staff);
		return mav;
	}
	
	@RequestMapping("/profile1")
	public String datLich1(Model model) {
		DatLichDTO datLichDTO = new DatLichDTO();

		List<Service> serList = ser1Repo.findAll();
		model.addAttribute("serList", serList);
		model.addAttribute("datLichDTO", datLichDTO);
		return "profileCus";
	}

	
	@RequestMapping("/profile2")
	public ModelAndView  profileCus(Model model) {
		Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
		User user =repo.findByUsernameIs(authentication.getName());
		Customer customer = cusRepo.findByIduser(user.getId());
		ModelAndView mav= new ModelAndView("profileCus");
		mav.addObject("customer",customer);
		return mav;
	}
	
	@RequestMapping(value="/saveCus",method =  RequestMethod.POST)
	public String saveCus(@ModelAttribute(name="customer") Customer cus, Model model,
			@RequestParam("image") String image,
			RedirectAttributes ra,@RequestParam("fileImage") MultipartFile multipartFile) throws IOException, ParseException{
		
			String fileName= StringUtils.cleanPath(multipartFile.getOriginalFilename());
			if(fileName.length()!=0) {
			cus.setImage(fileName);
			cus.setStatus(1);
			Customer savecus =cusRepo.save(cus);
			
			String uploadDir = "/assets/img/" +savecus.getId_cus();
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
				cus.setImage(image);
				cusRepo.save(cus);
			}
			
			ra.addFlashAttribute("message","Cập nhật thông tin thành công!");
		return "redirect:/";
	}
	
	public Integer layIDStaff() {
		Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
		User user =repo.findByUsernameIs(authentication.getName());
		Staff staff = staffRepo.findByIduser(user.getId());
		return staff.getId_staff();

	}
	
	
	
	



}
