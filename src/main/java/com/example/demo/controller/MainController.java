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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.dao.Booking;
import com.example.demo.dao.BookingDetail;
import com.example.demo.dao.Customer;
import com.example.demo.dao.Service;
import com.example.demo.dao.Staff;
import com.example.demo.dao.User;
import com.example.demo.dto.DatLichDTO;
import com.example.demo.dto.LichSuDatLichDTO;
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

	


	@RequestMapping("/")
	public String homePage() {
		return "home";
	}

	@RequestMapping("/lichsudat")
	public String lichSuDat(Model model) {
		List<LichSuDatLichDTO> lichSuDatList = new ArrayList<>();
		List<BookingDetail> detailList = new ArrayList<>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Customer cus = cusRepo.findByEmail(authentication.getName());

		List<Booking> bookList;
		if (checkLoggedIn()) {
			bookList = bokRepo.findByIdCus(cus.getId_cus());
		} else {
			return "home";
		}
		
		List<BookingDetail> bookingItems = bokDetailRepo.findAll();
		List<Customer> cusList = cusRepo.findAll();
		for(BookingDetail bookList1 : bookingItems) {
			for(Booking bookCusList : bookList) {
				if(bookList1.getId_booking() == bookCusList.getId_booking() && bookList1.getActive() == 1) {
					LichSuDatLichDTO lichSuDat1 = new LichSuDatLichDTO();
					Customer cus1 = cusRepo.findById(bookCusList.getId_cus());				
					lichSuDat1.setId_detail(bookList1.getId_detail());
					Service ser = ser1Repo.findById(bookList1.getId_service());
					lichSuDat1.setTendv(ser.getName());
					lichSuDat1.setGiaTien(ser.getPrice());
				
					lichSuDat1.setTrangThai(bookList1.getStatus());
					lichSuDat1.setGioBatDau(bookList1.getTime_start());
					lichSuDat1.setNgayDat(bookList1.getDateWorking_Start());
					//ten bacsi
					Staff staf = staffRepo.findById(bookList1.getId_staff());
					if(bookList1.getId_staff() == 1) {
						lichSuDat1.setTenbs("N/A");
					}else {
						lichSuDat1.setTenbs(staf.getName_staff());
					}
					lichSuDatList.add(lichSuDat1);
				}
			}
		}
		model.addAttribute("lichSuDatList", lichSuDatList);
		
		return "lichSuDatLich";
	}

	@GetMapping("lichsudat/delete/{id_detail}")
	public String deleteLichDat(@PathVariable(name = "id_detail") int id, RedirectAttributes ra) {
		BookingDetail bookItem = new BookingDetail();
		bookItem = bokDetailRepo.findById(id);
		bookItem.setActive(0);
		bokDetailRepo.save(bookItem);
		ra.addFlashAttribute("message","Hủy lịch thành công!");
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
	public String datLichSave(@ModelAttribute("datLichDTO") DatLichDTO datLich, Model model
			, RedirectAttributes ra) throws ParseException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (checkLoggedIn() == false) {
			return "login";
		}
		Customer cus = cusRepo.findByEmail(authentication.getName());
		cus.setPhone(datLich.getSdt());
		cusRepo.save(cus);
		DatLichDTO datLich1 = new DatLichDTO();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatterGIO = new SimpleDateFormat("HH:mm");
		Date date = formatter.parse(datLich.getDate());
		Date gio = formatterGIO.parse(datLich.getGioKham());

		Booking book = new Booking(cus.getId_cus());
		bokRepo.save(book);

		List<Booking> bookList = bokRepo.findAll();
		Booking bookItem = bookList.get(bookList.size() - 1);
		List<Integer> datLichSer = datLich.getId_ser();
		for (Integer integer : datLichSer) {
			BookingDetail bookDetail = new BookingDetail(1, integer, bookItem.getId_booking(), date,gio, 0, 1);
			bokDetailRepo.save(bookDetail);
		}
		ra.addFlashAttribute("message","Đặt lịch thành công!");
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
	public String dashboardServices(Model model,RedirectAttributes redirect) {
		List<Service> ser1 = ser1Repo.findAll();
		model.addAttribute("ser1", ser1);
		redirect.addFlashAttribute("success", "");
		return "dashboard/services";
	}

	@RequestMapping("/dashboard/customer")
	public String dashboardCustomer(Model model) {
		List<Customer> cus = cusRepo.findAll();
		model.addAttribute("cusLists", cus);
		return "dashboard/customer";
	}

	

	@RequestMapping("/logout-success")
	public String logoutPage() {
		return "logout";
	}

	@PostMapping("/register")
	public String registerUserAccount(User user, Model model, RedirectAttributes ra) {

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
		ra.addFlashAttribute("message","Đăng ký tài khoản thành công!");
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
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			
			return false;
			}
		return true;
	}

	public List<Staff> getStaffList() {
		return staffList;
	}

	public void setStaffList(List<Staff> staffList) {
		this.staffList = staffList;
	}

	

}
