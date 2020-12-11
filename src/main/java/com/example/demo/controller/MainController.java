package com.example.demo.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.Booking;
import com.example.demo.dao.BookingDetail;
import com.example.demo.dao.Customer;
import com.example.demo.dao.Service;
import com.example.demo.dao.Staff;
import com.example.demo.dao.User;
import com.example.demo.dto.DatLichDTO;
import com.example.demo.dto.TiepNhanLichKhamDTO;
import com.example.demo.service.BookingDetailRepository;
import com.example.demo.service.BookingRepository;
import com.example.demo.service.CustomerRepository;
import com.example.demo.service.MyUserDetailService;
import com.example.demo.service.ServiceRepository;
import com.example.demo.service.StaffRepository;
import com.example.demo.service.User2Repository;
import com.example.demo.service.UserRepository;

@Controller
public class MainController {

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
	private UserRepository userRepo;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@InitBinder
	protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(dateFormat, true));
	}

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

		List<BookingDetail> detailList = new ArrayList<>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Customer cus = cusRepo.findByEmail(authentication.getName());

		List<Booking> bookList;
		if (checkLoggedIn()) {
			bookList = bokRepo.findByIdCus(cus.getId_cus());
		} else {
			return "home";
		}

		for (Booking bookItem : bookList) {
			List<BookingDetail> detailList1 = bokDetailRepo.findByIdBooking(bookItem.getId_booking());
			if (!detailList1.isEmpty()) {
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
		Customer cus = cusRepo.findByEmail(authentication.getName());
		cus.setPhone(datLich.getSdt());
		cusRepo.save(cus);
		DatLichDTO datLich1 = new DatLichDTO();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date = formatter.parse(datLich.getDate());

		Booking book = new Booking(cus.getId_cus());
		bokRepo.save(book);

		List<Booking> bookList = bokRepo.findAll();
		Booking bookItem = bookList.get(bookList.size() - 1);

		BookingDetail bookDetail = new BookingDetail(1, datLich.getId_ser(), bookItem.getId_booking(), date, 0, 1);
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
			List<User> userList = repo.findAll();
			User getUser = userList.get(userList.size() - 1);
			Customer cus = new Customer(getUser.getName(), getUser.getUsername(), getUser.getId(), 1);
			cus.setImage("fb.png");
			cusRepo.save(cus);
		} else {
			model.addAttribute("error", "Email này đã được đăng ký");
			return "register";
		}

		// service.save(user);
		return "login";
	}

	
	// tin tuc
	@RequestMapping("/tintuc")
	public String tinTuc(Model model) {
		return "tintuc";
	}

	@RequestMapping("/tintuc2")
	public String tinTuc2(Model model) {
		return "tintuc2";
	}
	// end tin tuc

	// dat lich - thong tin them
	@RequestMapping("/thongtinthem")
	public String thongTinThem(Model model) {
		return "thongtinthem";
	}

	// chi tiet cac dich vu
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
	// end

	
	
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
