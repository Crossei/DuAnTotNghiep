package com.example.demo.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.DateUtils;

import com.example.demo.dao.Booking;
import com.example.demo.dao.BookingDetail;
import com.example.demo.dao.Customer;
import com.example.demo.dao.Service;
import com.example.demo.dao.Staff;
import com.example.demo.dao.User;
import com.example.demo.dao.WorkingCalendar;
import com.example.demo.dto.DangKyLichLamViecDTO;
import com.example.demo.dto.ShowLichDTO;
import com.example.demo.dto.TiepNhanLichKhamDTO;
import com.example.demo.service.BookingDetailRepository;
import com.example.demo.service.BookingRepository;
import com.example.demo.service.CustomerRepository;
import com.example.demo.service.ServiceRepository;
import com.example.demo.service.StaffRepository;
import com.example.demo.service.User2Repository;
import com.example.demo.service.UserRepository;
import com.example.demo.service.WorkingCalendarRepository;

@Controller
public class LichLamViecController {
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
	
	@PostMapping("/dashboard/lichtaikham/luu")
	public String thayDoiLichKham(@ModelAttribute("bookItem") TiepNhanLichKhamDTO bookItem){
		BookingDetail getBookDetail = bookDettailRepo.findById(bookItem.getId_detail());
		getBookDetail.setDateWorking_Start(bookItem.getNgayDat());
		 java.sql.Time sqlTime = new java.sql.Time(bookItem.getGioBatDau().getTime());
		getBookDetail.setTime_start(sqlTime);
		getBookDetail.setStatus(2);
		bookDettailRepo.save(getBookDetail);
		return "redirect:/dashboard/quanlylichkham";
	}
	
	
	@RequestMapping("/dashboard/quanlylichkham/taikham/{id_bookingDetail}")
	public ModelAndView thayDoiLich(@PathVariable(name="id_bookingDetail") Integer id_bookingDetail,Model model) {
		ModelAndView mav= new ModelAndView("dashboard/editLichTaiKham.html");
		TiepNhanLichKhamDTO tiepNhanItem = new TiepNhanLichKhamDTO();
		BookingDetail bookItem =bookDettailRepo.getOne(id_bookingDetail);
		List<Booking> bookingCus = bokRepo.findAll();
		
		for(Booking bookCusList : bookingCus) {
			if(bookItem.getId_booking() == bookCusList.getId_booking() && bookItem.getActive() == 1) {
				tiepNhanItem.setId_detail(id_bookingDetail);
				Customer cus = cusRepo.findById(bookCusList.getId_cus());
				tiepNhanItem.setName(cus.getName_cus());
				tiepNhanItem.setSdt(cus.getPhone());		
				Service ser = serRepo.findById(bookItem.getId_service());
				tiepNhanItem.setTendv(ser.getName());
				tiepNhanItem.setGiaTien(ser.getPrice());		

				tiepNhanItem.setGioBatDau(tiepNhanItem.getGioBatDau());
				tiepNhanItem.setNgayDat(bookItem.getDateWorking_Start());
	
				break;
			}
		}
		
		
		mav.addObject("bookItem",tiepNhanItem);
		return mav;
	}
	
	@RequestMapping("/dashboard/quanlylichkham/xacnhan/{id_detail}")
	public String xacNhanDatLich(@PathVariable(name = "id_detail") int id,Model model) {
		BookingDetail bookItem = new BookingDetail();
		bookItem = bookDettailRepo.findById(id);
		bookItem.setStatus(3);
		bookDettailRepo.save(bookItem);
		
		return "redirect:/dashboard/lichkham";
	}

	@RequestMapping("/dashboard/quanlylichkham")
	public String quanLyLichKhamCalendar(Model model) throws ParseException {
		
		List<TiepNhanLichKhamDTO> tiepNhanList = new ArrayList<>();
		List<BookingDetail> bookingItems = bookDettailRepo.findAll();
		List<Booking> bookingCus = bokRepo.findAll();
		List<Customer> cusList = cusRepo.findAll();
		for(BookingDetail bookList : bookingItems) {
			for(Booking bookCusList : bookingCus) {
				if(bookList.getId_booking() == bookCusList.getId_booking() &&bookList.getStatus() != 0 && bookList.getActive() == 1 && bookList.getId_staff() == layIDStaff()) {
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
		
		List<WorkingCalendar> workCheck1 = workRepo.findByIdstaffAndStatus(layIDStaff(), 1);
		List<WorkingCalendar> workLay0 = workRepo.findByIdstaffAndStatus(layIDStaff(), 0);
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
		List<WorkingCalendar> workWeek = workRepo.findByIdstaff(layIDStaff());
		List<WorkingCalendar> workWeek1 = workRepo.findByIdstaffAndStatus(layIDStaff(),1);
		List<WorkingCalendar> workWeek0 = workRepo.findByIdstaffAndStatus(layIDStaff(),0);
		if(workWeek.isEmpty()) {
			model.addAttribute("thongBao", "Bạn chưa đăng ký lịch khám");
			model.addAttribute("hien", false);
			return "dashboard/calender_kham";
		}
		model.addAttribute("workWeek", workWeek1);
		if(!workWeek0.isEmpty()) {
			model.addAttribute("hien0", true);
			model.addAttribute("workWeek0", workWeek0);
		}else {
			model.addAttribute("hien0", false);
		}
		
		model.addAttribute("hien", true);
		return "dashboard/calender_kham";
	}

	@RequestMapping("/dashboard/quanlylichkham/dangky")
	public String dangKyLich(Model model) {

		final DateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");

		DangKyLichLamViecDTO dangKy = new DangKyLichLamViecDTO();
		model.addAttribute("dangKy", dangKy);
		Calendar dateMonday = Calendar.getInstance();
		dateMonday.setTime(new Date()); // Now use today date.
		
		int getToday = dateMonday.get(Calendar.DAY_OF_WEEK);
		if (getToday == 6) { 
			dateMonday.add(Calendar.DATE, 3);
		} else if (getToday == 7) {
			dateMonday.add(Calendar.DATE, 2);
		}
		
		Date date = dateMonday.getTime();
		Calendar dateFuture = Calendar.getInstance();
		dateFuture.setTime(date); // Now use today date.
		dateFuture.add(Calendar.DATE, 5); // Adding 5 days

		dangKy.setDangKyNgay(dateMonday.getTime());
		model.addAttribute("dateNow", dateFormat1.format(dateMonday.getTime()));
		
		model.addAttribute("dateFuture", dateFormat1.format(dateFuture.getTime()));

		return "dashboard/register_work";
	}

	@PostMapping("/dashboard/quanlylichkham/dangky/xacnhan")
	public String dangKyLichAct(@ModelAttribute(name = "dangKy") DangKyLichLamViecDTO dangKy) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = repo.findByUsernameIs(authentication.getName());
		Staff staff = staffRepo.findByIduser(user.getId()); // lay staff id
		List<Integer> thu2 = dangKy.getThu2();
		List<Integer> thu3 = dangKy.getThu3();
		List<Integer> thu4 = dangKy.getThu4();
		List<Integer> thu5 = dangKy.getThu5();
		List<Integer> thu6 = dangKy.getThu6();
		List<Integer> thu7 = dangKy.getThu7();

		Date dt = dangKy.getDangKyNgay();
		Calendar dateMonday = Calendar.getInstance();
		dateMonday.setTime(dangKy.getDangKyNgay()); // Now use today date.
		dt = dateMonday.getTime();
		// set = 0 
		List<WorkingCalendar> workCheck = workRepo.findByIdstaffAndStatus(layIDStaff(), 1);
		for (WorkingCalendar workingCalendar : workCheck) {
			workingCalendar.setStatus(0);
			workRepo.save(workingCalendar);
		}
		
		WorkingCalendar work2 = new WorkingCalendar(staff.getId_staff(), thu2.contains(1) == true ? 1 : 0,
				thu2.contains(2) == true ? 1 : 0, thu2.contains(3) == true ? 1 : 0, dt, 1);
		workRepo.save(work2);
		dateMonday.add(Calendar.DATE, 1);
		dt = dateMonday.getTime();

		WorkingCalendar work3 = new WorkingCalendar(staff.getId_staff(), thu3.contains(1) == true ? 1 : 0,
				thu3.contains(2) == true ? 1 : 0, thu3.contains(3) == true ? 1 : 0, dt, 1);
		workRepo.save(work3);
		dateMonday.add(Calendar.DATE, 1);
		dt = dateMonday.getTime();

		WorkingCalendar work4 = new WorkingCalendar(staff.getId_staff(), thu4.contains(1) == true ? 1 : 0,
				thu4.contains(2) == true ? 1 : 0, thu4.contains(3) == true ? 1 : 0, dt, 1);
		workRepo.save(work4);
		dateMonday.add(Calendar.DATE, 1);
		dt = dateMonday.getTime();

		WorkingCalendar work5 = new WorkingCalendar(staff.getId_staff(), thu5.contains(1) == true ? 1 : 0,
				thu5.contains(2) == true ? 1 : 0, thu5.contains(3) == true ? 1 : 0, dt, 1);
		workRepo.save(work5);
		dateMonday.add(Calendar.DATE, 1);
		dt = dateMonday.getTime();

		WorkingCalendar work6 = new WorkingCalendar(staff.getId_staff(), thu6.contains(1) == true ? 1 : 0,
				thu6.contains(2) == true ? 1 : 0, thu6.contains(3) == true ? 1 : 0, dt, 1);
		workRepo.save(work6);
		dateMonday.add(Calendar.DATE, 1);
		dt = dateMonday.getTime();

		WorkingCalendar work7 = new WorkingCalendar(staff.getId_staff(), thu7.contains(1) == true ? 1 : 0,
				thu7.contains(2) == true ? 1 : 0, thu7.contains(3) == true ? 1 : 0, dt, 1);
		workRepo.save(work7);

		return "redirect:/dashboard/quanlylichkham";
	}

	public Integer layIDStaff() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = repo.findByUsernameIs(authentication.getName());
		Staff staff = staffRepo.findByIduser(user.getId()); // lay staff id
		return staff.getId_staff();
	}

	public boolean soSanhTime(Date dateDv, String caBatDau, String caKetthuc) throws ParseException {
		String ca1S = "8:30:00";
		String ca1E = "12:00:00";

		Date time1 = new SimpleDateFormat("HH:mm:ss").parse(caBatDau);
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(time1);

		Date time2 = new SimpleDateFormat("HH:mm:ss").parse(caKetthuc);
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(time2);

		if (dateDv.after(calendar1.getTime()) && dateDv.before(calendar2.getTime())) {
			return true;
		}
		return false;
	}
}
