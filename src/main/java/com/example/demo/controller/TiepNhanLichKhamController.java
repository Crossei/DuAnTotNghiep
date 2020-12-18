package com.example.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
public class TiepNhanLichKhamController {
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
	
	
	@GetMapping(value = "/getSearchResult")
	@ResponseBody
    public   String homePage(HttpServletRequest request) throws ParseException {
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
	


	@RequestMapping("/dashboard/lichkham")
	public String nhanLichKham(Model model) {
		List<TiepNhanLichKhamDTO> tiepNhanList = new ArrayList<>();
		List<BookingDetail> bookingItems = bokDetailRepo.findAll();
		List<Booking> bookingCus = bokRepo.findAll();
		List<Customer> cusList = cusRepo.findAll();
		for(BookingDetail bookList : bookingItems) {
			for(Booking bookCusList : bookingCus) {
				if(bookList.getId_booking() == bookCusList.getId_booking() && bookList.getActive() == 1) {
					TiepNhanLichKhamDTO tiepNhapLich = new TiepNhanLichKhamDTO();
					Customer cus = cusRepo.findById(bookCusList.getId_cus());
					tiepNhapLich.setId_detail(bookList.getId_detail());
					tiepNhapLich.setName(cus.getName_cus());
					tiepNhapLich.setSdt(cus.getPhone());
					
					Service ser = ser1Repo.findById(bookList.getId_service());
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
		return "dashboard/tiepNhanLichKham";
	}
	
	@RequestMapping("/dashboard/lichkham/xacnhan/{id_detail}")
	public String xacNhanDatLich(@PathVariable(name = "id_detail") int id,Model model, RedirectAttributes ra) {
		BookingDetail bookItem = new BookingDetail();
		bookItem = bokDetailRepo.findById(id);
		bookItem.setStatus(1);
		bokDetailRepo.save(bookItem);
		ra.addFlashAttribute("message","Xác nhận lịch khám thành công!");
		return "redirect:/dashboard/lichkham";
	}
	
	@RequestMapping("/dashboard/lichkham/huylich/{id_detail}")
	public String huyDatLich(@PathVariable(name = "id_detail") int id,Model model, RedirectAttributes ra) {
		BookingDetail bookItem = new BookingDetail();
		bookItem = bokDetailRepo.findById(id);
		bookItem.setActive(0);
		bokDetailRepo.save(bookItem);
		ra.addFlashAttribute("message","Hủy lịch thành công!");
		return "redirect:/dashboard/lichkham";
	}
	
	@RequestMapping("/dashboard/lichkham/thaydoi/{id_bookingDetail}")
	public ModelAndView thayDoiLich(@PathVariable(name="id_bookingDetail") Integer id_bookingDetail,
			Model model
			, RedirectAttributes ra) {
		ModelAndView mav= new ModelAndView("dashboard/editLichKham");
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
	
	@PostMapping("/dashboard/lichkham/luu")
	public String thayDoiLichKham(@ModelAttribute("bookItem") TiepNhanLichKhamDTO bookItem
			, RedirectAttributes ra){
		BookingDetail getBookDetail = bokDetailRepo.findById(bookItem.getId_detail());
		
		getBookDetail.setId_staff(Integer.parseInt(bookItem.getTenbs()));
		getBookDetail.setDateWorking_Start(bookItem.getNgayDat());
		 java.sql.Time sqlTime = new java.sql.Time(bookItem.getGioBatDau().getTime());
		getBookDetail.setTime_start(sqlTime);
		
		bokDetailRepo.save(getBookDetail);
		ra.addFlashAttribute("message","Xếp lịch thành công!");
		return "redirect:/dashboard/lichkham";
	}
}
