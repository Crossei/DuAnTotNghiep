package com.example.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
public class UpdatelichdatController {
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
	private WorkingCalendarRepository workRepo;
	
	@GetMapping(value = "/getSearchResult1")
	@ResponseBody
    public   String homePage1(HttpServletRequest request) throws ParseException {
		String bacsi = request.getParameter("bacsi");
		String ngayDat = request.getParameter("ngayDat");
		SimpleDateFormat fomater = new SimpleDateFormat("yyyy-MM-dd");
		Date date = fomater.parse(ngayDat);
		WorkingCalendar workingValidate = workRepo.findByIdstaffAndDateWorking(Integer.parseInt(bacsi), date);
		System.out.println(workingValidate);
		ObjectMapper mapper = new ObjectMapper();
		String ajaxResponse = "";
		try {
			ajaxResponse = mapper.writeValueAsString(workingValidate);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
       return ajaxResponse;
    }
	
	@RequestMapping("/lichkham/thaydoi/{id_bookingDetail}")
	public ModelAndView thayDoiLich1(@PathVariable(name="id_bookingDetail") Integer id_bookingDetail,Model model
			, RedirectAttributes ra) {
		ModelAndView mav= new ModelAndView("editLichDat");
		TiepNhanLichKhamDTO tiepNhanItem = new TiepNhanLichKhamDTO();
		BookingDetail bookItem =bokDetailRepo.getOne(id_bookingDetail);
		List<Booking> bookingCus = bokRepo.findAll();
		
		for(Booking bookCusList : bookingCus) {
			if(bookItem.getId_booking() == bookCusList.getId_booking() && bookItem.getActive() == 1) {
				tiepNhanItem.setId_detail(id_bookingDetail);
				Customer cus = cusRepo.findById(bookCusList.getId_cus());
				tiepNhanItem.setName(cus.getName_cus());
				tiepNhanItem.setSdt(cus.getPhone());		
				Service ser = ser1Repo.findById(bookItem.getId_service());
				tiepNhanItem.setTendv(ser.getName());
				tiepNhanItem.setGiaTien(ser.getPrice());		

				tiepNhanItem.setGioBatDau(tiepNhanItem.getGioBatDau());
				tiepNhanItem.setNgayDat(bookItem.getDateWorking_Start());

				//Lấy list bác sĩ
				List<Staff> staf = staffRepo.findAll();
				List<Staff> bsList = new ArrayList<>() ;
				for(Staff stafList : staf) {
					User user = repo.findById(stafList.getId_user());
					if(user.getRoles().equalsIgnoreCase("ROLE_BACSI")) {
						bsList.add(stafList);
					}
				}
				model.addAttribute("staffList", bsList);
				break;
			}
		}
		
		
		mav.addObject("bookItem",tiepNhanItem);
		return mav;
	}
	
	@PostMapping("/lichdat/luu")
	public String thayDoiLichKham1(@ModelAttribute("bookItem") TiepNhanLichKhamDTO bookItem
			, RedirectAttributes ra){
		BookingDetail getBookDetail = bokDetailRepo.findById(bookItem.getId_detail());
		
		getBookDetail.setId_staff(Integer.parseInt(bookItem.getTenbs()));
		getBookDetail.setDateWorking_Start(bookItem.getNgayDat());
		 java.sql.Time sqlTime = new java.sql.Time(bookItem.getGioBatDau().getTime());
		getBookDetail.setTime_start(sqlTime);
		
		bokDetailRepo.save(getBookDetail);
		ra.addFlashAttribute("message","Cập nhật lịch đặt thành công!");
		return "redirect:/lichsudat";
	}
}