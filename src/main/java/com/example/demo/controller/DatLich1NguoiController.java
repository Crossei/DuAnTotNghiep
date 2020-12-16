package com.example.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.dao.Booking;
import com.example.demo.dao.BookingDetail;
import com.example.demo.dao.Customer;
import com.example.demo.dao.Service;
import com.example.demo.dao.Staff;
import com.example.demo.dao.User;
import com.example.demo.dao.WorkingCalendar;
import com.example.demo.dto.DatLichForm2DTO;
import com.example.demo.service.BookingDetailRepository;
import com.example.demo.service.BookingRepository;
import com.example.demo.service.CustomerRepository;
import com.example.demo.service.ServiceRepository;
import com.example.demo.service.StaffRepository;
import com.example.demo.service.User2Repository;
import com.example.demo.service.UserRepository;
import com.example.demo.service.WorkingCalendarRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class DatLich1NguoiController {
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
	private BookingRepository bokRepo;
	@Autowired
	private BookingDetailRepository bokDetailRepo;
	@Autowired
	private WorkingCalendarRepository workRepo;
	
	
	@GetMapping("/getListBacSi")
	@ResponseBody
	public String layDSBacSi(HttpServletRequest rq)  throws ParseException {
		Integer ca1 = null,ca2 = null,ca3 = null;
		SimpleDateFormat fomater = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat fomaterGio = new SimpleDateFormat("HH:mm");
		Date date = fomater.parse(rq.getParameter("getNgayDat"));
		Date gio = fomaterGio.parse(rq.getParameter("getThoiGian"));
		
		if(soSanhTime(gio,"8:59","11:31")) {
			ca1 = 1;
		} else if(soSanhTime(gio,"12:59","17:31")) {
			ca2 = 1;
		} else if(soSanhTime(gio,"17:59","19:31"))  {
			ca3 = 1;
		}
	
		List<Staff> staffList1 = new ArrayList<>();
		List<WorkingCalendar> workList = workRepo.findByDateWorking(date);
		if(!workList.isEmpty()) {
		List<Staff> staffList = staffRepo.findByRole(2);
			for (Staff staff : staffList) {
				staffList1.add(staff);												   // TH : staff ko có workingCalendar => van add vao staffList1
				for (WorkingCalendar workingCalendar : workList) { // TH : workingCalendar của staff = 1 thì add vào staffList1
					if(workingCalendar.getId_staff() == staff.getId_staff()) {
					if( ca1 != null && workingCalendar.getShift1() != ca1 ) {
						staffList1.remove(staff);
					}else if(ca2 != null && workingCalendar.getShift2() != ca2 ) {
						staffList1.remove(staff);
					}else if(ca3 != null && workingCalendar.getShift3() != ca3) {
						staffList1.remove(staff);
					}
					}
				}			
			}
		
			
		}else {			
		staffList1 =  staffRepo.findByRole(2);
		};
	
		List<BookingDetail> bookD = bokDetailRepo.findByDateworkingstart(date);
		
		if(!bookD.isEmpty()) {
			for (BookingDetail bookingDetail : bookD) {				
				Staff staffD = staffRepo.findById(bookingDetail.getId_staff());					
				String gio1 = fomaterGio.format(bookingDetail.getTime_start());		
				if(gio1.equalsIgnoreCase(rq.getParameter("getThoiGian"))) {
				staffList1.remove(staffD);
				}
			}
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String ajaxResponse = "";
		try {
			ajaxResponse = mapper.writeValueAsString(staffList1);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
       return ajaxResponse;
	}
	
	@PostMapping("/datlich2")
	public String datLich2(HttpServletRequest rq) throws ParseException {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Customer cus = cusRepo.findByEmail(authentication.getName());
		Booking book = new Booking(cus.getId_cus());
		bokRepo.save(book);
		
		SimpleDateFormat fomat = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat fomatGio = new SimpleDateFormat("HH:mm");
		Date date = fomat.parse(rq.getParameter("btn"));
		Date gio = fomatGio.parse(rq.getParameter("btn1"));

		
		List<Booking> bookList = bokRepo.findAll();
		Booking bookItem = bookList.get(bookList.size() - 1);
		
		BookingDetail bookDetail = new BookingDetail(Integer.parseInt(rq.getParameter("btn2")), Integer.parseInt(rq.getParameter("btn3")), bookItem.getId_booking(), date,gio, 0, 1);
		bokDetailRepo.save(bookDetail);
		return "redirect:/datlich2";
	}
	
	@RequestMapping("/datlich2")
	public String docList(Model model) {
		//lay ds dich vu
		List<Service> ser = ser1Repo.findAll();
		model.addAttribute("ser", ser);
		
		//lay ngay
		List<Date> ngayDatList = new ArrayList<>();
		Calendar c = Calendar.getInstance(); 
		c.setTime(new Date());
		Date bookDat = c.getTime();
		for(int i =0 ; i<7 ;i++) {
			int getToday = c.get(Calendar.DAY_OF_WEEK);
			ngayDatList.add(bookDat);
			if(getToday == 7) {
			c.add(Calendar.DATE, 2);
			}else {
			c.add(Calendar.DATE, 1);
			}
			bookDat = c.getTime();		
		}
		model.addAttribute("ngayDatList", ngayDatList);
		
		//lay ds bacsi'
		List<Staff> staffList =  staffRepo.findByRole(2);
		model.addAttribute("staffList", staffList);
		
		return "datlich2";
	}
	
	public boolean soSanhTime(Date dateDv, String caBatDau, String caKetthuc) throws ParseException {
		Date time1 = new SimpleDateFormat("HH:mm").parse(caBatDau);
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(time1);

		Date time2 = new SimpleDateFormat("HH:mm").parse(caKetthuc);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(time2);

		if (dateDv.after(calendar1.getTime()) && dateDv.before(calendar2.getTime())) {
			return true;
		}
		return false;
	}
}
