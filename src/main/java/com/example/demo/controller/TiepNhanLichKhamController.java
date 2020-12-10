package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.Booking;
import com.example.demo.dao.BookingDetail;
import com.example.demo.dao.Customer;
import com.example.demo.dao.Service;
import com.example.demo.dao.Staff;
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
					tiepNhapLich.setGioBatDau(tiepNhapLich.getGioBatDau());
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
	public String xacNhanDatLich(@PathVariable(name = "id_detail") int id,Model model) {
		BookingDetail bookItem = new BookingDetail();
		bookItem = bokDetailRepo.findById(id);
		bookItem.setStatus(1);
		bokDetailRepo.save(bookItem);
		
		return "redirect:/dashboard/lichkham";
	}
	
	@RequestMapping("/dashboard/lichkham/huylich/{id_detail}")
	public String huyDatLich(@PathVariable(name = "id_detail") int id,Model model) {
		BookingDetail bookItem = new BookingDetail();
		bookItem = bokDetailRepo.findById(id);
		bookItem.setActive(0);
		bokDetailRepo.save(bookItem);
		
		return "redirect:/dashboard/lichkham";
	}
}
