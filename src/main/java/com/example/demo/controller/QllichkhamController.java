package com.example.demo.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.Booking;
import com.example.demo.dao.BookingDetail;
import com.example.demo.dao.Customer;
import com.example.demo.dao.Service;
import com.example.demo.dao.Staff;
import com.example.demo.dao.User;
import com.example.demo.dao.WorkingCalendar;
import com.example.demo.dto.TiepNhanLichKhamDTO;
import com.example.demo.service.BookingDetailRepository;
import com.example.demo.service.BookingRepository;
import com.example.demo.service.CustomerRepository;
import com.example.demo.service.MyUserDetailService;
import com.example.demo.service.ServiceRepository;
import com.example.demo.service.StaffRepository;
import com.example.demo.service.User2Repository;
import com.example.demo.service.UserRepository;
import com.example.demo.service.WorkingCalendarRepository;


@Controller
public class QllichkhamController {

	private List<Staff> staffList;

	@Autowired
	private UserRepository repo;
	@Autowired
	private User2Repository repo2;
	@Autowired
	private StaffRepository staffRepo;
	@Autowired
	private WorkingCalendarRepository workRepo;
	@Autowired
	private BookingDetailRepository bookDettailRepo;
	@Autowired
	private ServiceRepository serRepo;
	@Autowired
	private BookingRepository bokRepo;
	@Autowired
	private CustomerRepository cusRepo;
	
	
	
	@RequestMapping("/dashboard/ql_lichkham/{id_staff}")
	public String quanLyLichKhamCalendar1(@PathVariable(name="id_staff") Integer id_staff, Model model) throws ParseException {
		
		List<TiepNhanLichKhamDTO> tiepNhanList = new ArrayList<>();
		List<BookingDetail> bookingItems = bookDettailRepo.findAll();
		List<Booking> bookingCus = bokRepo.findAll();
		List<Customer> cusList = cusRepo.findAll();
		for(BookingDetail bookList : bookingItems) {
			for(Booking bookCusList : bookingCus) {
				if(bookList.getId_booking() == bookCusList.getId_booking() &&bookList.getStatus()
						!= 0 && bookList.getActive() == 1 && bookList.getId_staff() == id_staff) {
					TiepNhanLichKhamDTO tiepNhapLich = new TiepNhanLichKhamDTO();
					Customer cus = cusRepo.findById(bookCusList.getId_cus());
					tiepNhapLich.setId_detail(bookList.getId_detail());
					tiepNhapLich.setName(cus.getName_cus());
					tiepNhapLich.setSdt(cus.getPhone());
					Service ser = serRepo.findById(bookList.getId_service());
					tiepNhapLich.setTendv(ser.getName());
					tiepNhapLich.setGiaTien(ser.getPrice());
					tiepNhapLich.setStatus(bookList.getStatus());
					tiepNhapLich.setGioBatDau(bookList.getTime_start());
					tiepNhapLich.setNgayDat(bookList.getDateWorking_Start());
					tiepNhapLich.setActive(bookList.getActive());
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
		
		List<WorkingCalendar> workCheck1 = workRepo.findByIdstaffAndStatus(id_staff, 1);
		List<WorkingCalendar> workLay0 = workRepo.findByIdstaffAndStatus(id_staff, 0);
		Calendar dateNow = Calendar.getInstance();
		dateNow.setTime(new Date());
		int getToday = dateNow.get(Calendar.DAY_OF_WEEK);
		
		
		if (getToday == 6 || getToday == 7) {			
			model.addAttribute("dangKyLich", true);		
		} else {
			model.addAttribute("dangKyLich", false);
		}
		
		if(!workCheck1.isEmpty() && getToday == 6 && !workLay0.isEmpty()) { //check neu = 1 -> da dang ky
			model.addAttribute("dangKyLich", false);
		}
		
		if (getToday == 1 && !workLay0.isEmpty()) {
			for (WorkingCalendar workingCalendar : workLay0) {
				workRepo.delete(workingCalendar);
			}
		}
		List<WorkingCalendar> workWeek = workRepo.findByIdstaff(id_staff);
		List<WorkingCalendar> workWeek1 = workRepo.findByIdstaffAndStatus(id_staff,1);
		List<WorkingCalendar> workWeek0 = workRepo.findByIdstaffAndStatus(id_staff,0);
		if(workWeek.isEmpty()) {
			model.addAttribute("thongBao", "Bạn chưa đăng ký lịch khám");
			model.addAttribute("hien", false);
			return "dashboard/ql_lichkham";
		}
		model.addAttribute("workWeek", workWeek1);
		if(!workWeek0.isEmpty()) {
			model.addAttribute("hien0", true);
			model.addAttribute("workWeek0", workWeek0);
		}else {
			model.addAttribute("hien0", false);
		}
		
		model.addAttribute("hien", true);
		return "dashboard/ql_lichkham";
	}
	
//	public Integer layIDStaff1(@PathVariable(name = "id_staff") int id) {
//		Staff staff = staffRepo.findById(id);
//		return staff.getId_staff();
//	}
//	
//	
//	
//	public Integer layIDStaff() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		User user = repo.findByUsernameIs(authentication.getName());
//	    Staff staff = staffRepo.findByIduser(user.getId()); // lay staff id
//		return staff.getId_staff();
//	}
	
}
