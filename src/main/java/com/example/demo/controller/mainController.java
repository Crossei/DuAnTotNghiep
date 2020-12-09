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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.Booking;
import com.example.demo.dao.BookingDetail;
import com.example.demo.dao.Customer;
import com.example.demo.dao.Service;
import com.example.demo.dao.Staff;
import com.example.demo.dao.User;
import com.example.demo.dto.DatLichDTO;
import com.example.demo.dto.tiepNhanLichKhamDTO;
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
	
	@GetMapping("lichsudat/delete/{id_detail}")
	public String deleteLichDat(@PathVariable(name = "id_detail") int id) {
		BookingDetail bookItem = new BookingDetail();
		bookItem = bokDetailRepo.findById(id);
		bookItem.setActive(0);
		bokDetailRepo.save(bookItem);
		
		return "redirect:/lichsudat";
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
	@RequestMapping("/dashboard/lichkham")
	public String nhanLichKham(Model model) {
		List<tiepNhanLichKhamDTO> tiepNhanList = new ArrayList<>();
		List<BookingDetail> bookingItems = bokDetailRepo.findAll();
		List<Booking> bookingCus = bokRepo.findAll();
		List<Customer> cusList = cusRepo.findAll();
		for(BookingDetail bookList : bookingItems) {
			for(Booking bookCusList : bookingCus) {
				if(bookList.getId_booking() == bookCusList.getId_booking()) {
					tiepNhanLichKhamDTO tiepNhapLich = new tiepNhanLichKhamDTO();
					Customer cus = cusRepo.findById(bookCusList.getId_cus());
					tiepNhapLich.setName(cus.getName_cus());
					tiepNhapLich.setSdt(cus.getPhone());
					
					Service ser = ser1Repo.findById(bookList.getId_service());
					tiepNhapLich.setTendv(ser.getName());
					tiepNhapLich.setGiaTien(ser.getPrice());
				
					tiepNhapLich.setStatus(bookList.getStatus());
					tiepNhapLich.setGioBatDau(tiepNhapLich.getGioBatDau());
					tiepNhapLich.setNgayDat(bookList.getDateWorking_Start());
					//ten bacsi
					Staff staf = staffRepo.findById(bookList.getId_staff());
					if(bookList.getId_staff() == 1) {
						tiepNhapLich.setTenbs("N/A");
					}else {
						tiepNhapLich.setTenbs(staf.getName_staff());
					}
					tiepNhanList.add(tiepNhapLich);
				}
			}
		}
		model.addAttribute("tiepNhanList", tiepNhanList);
		return "dashboard/tiepNhanLichKham";
	}

	@RequestMapping("/dashboard/account")
	public String dashboardAccount() {
		return "dashboard/account";
	}

	@RequestMapping("/changePass")
	public String changePass() {
		return "doiMatKhau";
	}
	
	@RequestMapping("/acc-info")
	public String thongTinTaiKhoan() {
		return "acc-info";
	}

	@RequestMapping("/logout-success")
	public String logoutPage() {
		return "logout";
	}

	
	//dang ky
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

	
	//tin tuc
	@RequestMapping("/tintuc")
	public String tinTuc(Model model) {
		return "tintuc";
	}
	
	@RequestMapping("/tintuc2")
	public String tinTuc2(Model model) {
		return "tintuc2";
	}
	//end tin tuc
	
	
	
	//dat lich - thong tin them
	@RequestMapping("/thongtinthem")
	public String thongTinThem(Model model) {
		return "thongtinthem";
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

	//chi tiet cac dich vu
	@RequestMapping("/nhakhoatongquat")
	public String dv1() {
		return "chitietdichvu/NhaKhoaTongQuat";
	}
	
	@RequestMapping("/rangtreem")
	public String dv2() {
		return "chitietdichvu/RangTreEm";
	}
	
	@RequestMapping("/nanchinhrang")
	public String dv3() {
		return "chitietdichvu/NanChinhRang";
	}
	
	@RequestMapping("/chinhnhacochucnang")
	public String dv4() {
		return "chitietdichvu/ChinhNhaCoChucNang";
	}
	
	@RequestMapping("/nhakhoathammy")
	public String dv5() {
		return "chitietdichvu/NhaKhoaThamMy";
	}
	
	@RequestMapping("/nhakhoaphuchinh")
	public String dv6() {
		return "chitietdichvu/NhaKhoaPhucHinh";
	}
	
	@RequestMapping("/dieutrinoinha")
	public String dv7() {
		return "chitietdichvu/DieuTriNoiNha";
	}
	
	@RequestMapping("/cayghep")
	public String dv8() {
		return "chitietdichvu/CayGhepImplant";
	}
	
	@RequestMapping("/phauthuatrm")
	public String dv9() {
		return "chitietdichvu/PhauThuatRangMieng";
	}
	//end
	
	
	
	
	
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
