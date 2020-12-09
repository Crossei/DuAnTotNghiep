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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.DateUtils;

import com.example.demo.dao.BookingDetail;
import com.example.demo.dao.Service;
import com.example.demo.dao.Staff;
import com.example.demo.dao.User;
import com.example.demo.dao.WorkingCalendar;
import com.example.demo.dto.DangKyLichLamViecDTO;
import com.example.demo.dto.ShowLichDTO;
import com.example.demo.service.BookingDetailRepository;
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

	@RequestMapping("/dashboard/quanlylichkham")
	public String quanLyLichKhamCalendar(Model model) throws ParseException {
		Calendar dateNow = Calendar.getInstance();
		dateNow.setTime(new Date());
		int getToday = dateNow.get(Calendar.DAY_OF_WEEK);
		if (getToday == 5 || getToday == 6) {
			model.addAttribute("dangKyLich", true);
		} else {
			model.addAttribute("dangKyLich", false);
		}

		List<WorkingCalendar> workWeek = workRepo.findByStatusAndIdstaff(1, layIDStaff());

		model.addAttribute("workWeek", workWeek);
		List<BookingDetail> bookIDstaff = bookDettailRepo.findByIdstaffAndActiveAndStatus(layIDStaff(), 1, 1);
		WorkingCalendar workStart = workWeek.get(0);
		WorkingCalendar workEnd = workWeek.get(workWeek.size() - 1);
		List<ShowLichDTO> showLichdtoCa1 = new ArrayList<>();
		List<ShowLichDTO> showLichdtoCa2 = new ArrayList<>();
		List<ShowLichDTO> showLichdtoCa3 = new ArrayList<>();

		for (BookingDetail getBooked : bookIDstaff) {
			if (!workStart.getDateWorking().after(getBooked.getDateWorking_Start())
					&& !workEnd.getDateWorking().before(getBooked.getDateWorking_Start())) {
				ShowLichDTO layLich = new ShowLichDTO();
				Service ser = serRepo.findById(getBooked.getId_service());
				Calendar date = Calendar.getInstance();
				date.setTime(getBooked.getDateWorking_Start());

				if (soSanhTime(getBooked.getTime_start(), "8:30:00", "12:00:00")) {
					layLich.setGetDate(date.get(Calendar.DAY_OF_WEEK));
					layLich.setTendv(ser.getName());
					layLich.setThoiGianDat(getBooked.getTime_start());
					showLichdtoCa1.add(layLich);

				} else if (soSanhTime(getBooked.getTime_start(), "13:00:00", "17:00:00")) {
					layLich.setGetDate(date.get(Calendar.DAY_OF_WEEK));
					layLich.setTendv(ser.getName());
					layLich.setThoiGianDat(getBooked.getTime_start());
					showLichdtoCa2.add(layLich);
				} else if (soSanhTime(getBooked.getTime_start(), "18:00:00", "19:30:00")) {
					layLich.setGetDate(date.get(Calendar.DAY_OF_WEEK));
					layLich.setTendv(ser.getName());
					layLich.setThoiGianDat(getBooked.getTime_start());
					showLichdtoCa3.add(layLich);
				}
			}
		}

		model.addAttribute("ca1", showLichdtoCa1);
		model.addAttribute("ca2", showLichdtoCa2);
		model.addAttribute("ca3", showLichdtoCa3);

		return "dashboard/calender_kham";
	}

	@RequestMapping("/dashboard/quanlylichkham/dangky")
	public String dangKyLich(Model model) {

		final DateFormat dateFormat1 = new SimpleDateFormat("dd/MM/YYYY");

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
		Staff staff = staffRepo.findById(user.getId()); // lay staff id
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
		Staff staff = staffRepo.findById(user.getId()); // lay staff id
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
