package com.example.demo.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.Booking;
import com.example.demo.dao.BookingDetail;
import com.example.demo.dao.Customer;
import com.example.demo.dao.Service;
import com.example.demo.dao.Staff;
import com.example.demo.dao.User;
import com.example.demo.dto.DatLichDTO;
import com.example.demo.service.BookingDetailRepository;
import com.example.demo.service.BookingRepository;
import com.example.demo.service.CustomerRepository;
import com.example.demo.service.MyUserDetailService;
import com.example.demo.service.ServiceRepository;
import com.example.demo.service.StaffRepository;
import com.example.demo.service.User2Repository;
import com.example.demo.service.UserRepository;

@Controller
public class mainController {

	private List<Staff> staffList;
	 
	@Autowired
	private MyUserDetailService service;
	@Autowired
	private UserRepository repo;
	@Autowired
	private User2Repository repo2;
	@Autowired
	private StaffRepository staffRepo;
	@Autowired
	private ServiceRepository ser1Repo;
	@Autowired
	private CustomerRepository cusRepo;
	@Autowired
	private BookingDetailRepository bokDetailRepo;
	@Autowired
	private BookingRepository bokRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@RequestMapping("/doctor-list")
	public String docList() {
		return "doctor-list";
	}

	@RequestMapping("/service-list")
	public String serviceList() {
		return "service-list";
	}

	@RequestMapping("/")
	public String homePage() {
		return "home";
	}
	
	@RequestMapping("/lichsudat")
	public String lichSuDat(Model model) {
	
		List<BookingDetail> detailList =  new ArrayList<>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = repo.findByUsernameIs(authentication.getName());
		
		List<Booking> bookList;
		if(checkLoggedIn()) {
			bookList = bokRepo.findByIdCus(user.getId());
		} else {
			return "home";
		}
		
		for(Booking bookItem: bookList) {	
			List<BookingDetail>  detailList1 = bokDetailRepo.findByIdBooking(bookItem.getId_booking());			
			if(!detailList1.isEmpty()) {
				BookingDetail bot = detailList1.get(0);
				detailList.add(bot);
			}
		}
		List<Service> service = ser1Repo.findAll();
		model.addAttribute("serLList", service);
		model.addAttribute("detailList", detailList);
		return "lichSuDatLich";
	}
	

	@RequestMapping("/datlich")
	public String datLich(Model model) {
		DatLichDTO datLichDTO = new DatLichDTO();

		List<Service> serList = ser1Repo.findAll();
		model.addAttribute("serList", serList);
		model.addAttribute("datLichDTO", datLichDTO);
		return "datlich";
	}

	@PostMapping(value = "/datlich")
	public String datLichSave(@ModelAttribute("datLichDTO") DatLichDTO datLich, Model model) throws ParseException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (checkLoggedIn() == false) {
			return "login";
		}

		System.out.println(authentication.getName());
		User user = repo.findByUsernameIs(authentication.getName());
		System.out.println("id user :" + user.getId());
		DatLichDTO datLich1 = new DatLichDTO();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = formatter.parse(datLich.getDate());
		
		Booking book = new Booking(user.getId());
		bokRepo.save(book);
		
		List<Booking> bookList = bokRepo.findAll();
		Booking bookItem = bookList.get(bookList.size()-1);
		
		BookingDetail bookDetail = new BookingDetail(1,datLich.getId_ser(),bookItem.getId_booking(),date,1);
		bokDetailRepo.save(bookDetail);

		return "home";
	}

	@RequestMapping("/about")
	public String about() {
		return "about";
	}

	@RequestMapping("/login")
	public String loginPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "login";
		}

		return "redirect:/";
	}

	@RequestMapping("/register")
	public String registerPage() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken)
			return "register";
		return "redirect:/";
	}

	@RequestMapping("/dashboard")
	public String dashboardAdmin() {

		return "dashboard/admin";
	}

	@RequestMapping("/dashboard/staff")
	public String dashboardStaff(Model model) {
		staffList = staffRepo.findAll();
		model.addAttribute("staffLists", staffList);
		return "dashboard/staff";
	}

	@RequestMapping("/dashboard/services")
	public String dashboardServices(Model model) {
		List<Service> ser1 = ser1Repo.findAll();
		model.addAttribute("ser1", ser1);
		return "dashboard/services";
	}

	@RequestMapping("/dashboard/customer")
	public String dashboardCustomer(Model model) {
		List<Customer> cus = cusRepo.findAll();
		model.addAttribute("cusLists", cus);
		return "dashboard/customer";
	}

	@RequestMapping("/dashboard/account")
	public String dashboardAccount() {
		return "dashboard/account";
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
	public String registerUserAccount(User user, Model model) {

		List<User> user1 = repo2.findByUsername(user.getUsername());
		if (user1.isEmpty()) {
			service.save(user);
		} else {
			model.addAttribute("error", "Email này đã được đăng ký");
			return "register";
		}

		// service.save(user);
		return "login";
	}

	@RequestMapping("/dashboard/quanlylichkham")
	public String quanLyLichKham(Model model) {
		return "dashboard/ql_lichkham";
	}

	@RequestMapping("/dashboard/quanlylichkham/register-work")
	public String lichKhamRegister(Model model) {

		final DateFormat dateFormat1 = new SimpleDateFormat("dd/MM/YYYY");
		Calendar dateNow = Calendar.getInstance();
		dateNow.setTime(new Date()); // Now use today date.
		Calendar dateFuture = Calendar.getInstance();
		dateFuture.setTime(new Date()); // Now use today date.
		dateFuture.add(Calendar.DATE, 7); // Adding 5 days
		System.out.println(dateFormat1.format(dateNow.getTime()));
		System.out.println(dateFormat1.format(dateFuture.getTime()));
		model.addAttribute("dateNow", dateFormat1.format(dateNow.getTime()));
		model.addAttribute("dateFuture", dateFormat1.format(dateFuture.getTime()));

		return "dashboard/register_work";
	}

	@RequestMapping("/dashboard/quanlylichkham/calendar")
	public String quanLyLichKhamCalendar(Model model) {
		return "dashboard/calender_kham";
	}

	public boolean checkLoggedIn() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken)
			return false;
		return true;
	}

	public List<Staff> getStaffList() {
		return staffList;
	}

	public void setStaffList(List<Staff> staffList) {
		this.staffList = staffList;
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
