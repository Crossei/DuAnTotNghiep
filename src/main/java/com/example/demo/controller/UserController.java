package com.example.demo.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.Customer;
import com.example.demo.dao.Service;
import com.example.demo.dao.Staff;
import com.example.demo.service.CustomerRepository;
import com.example.demo.service.ServiceRepository;
import com.example.demo.service.StaffRepository;

@Controller
public class UserController {

	@Autowired
	private StaffRepository staffRepo;
	
	@Autowired
	private ServiceRepository serRepo;
	

	@Autowired
	private CustomerRepository cusRepo;

	int id_staff;

	@RequestMapping("dashboard/staff/delete/{id_staff}")
	public String delete(@PathVariable(name = "id_staff") int id) {
		Staff staff = staffRepo.findById(id);
		staff.setStatus(0);
		staffRepo.save(staff);
		return "redirect:/dashboard/staff";
	}
	@RequestMapping("dashboard/customer/delete/{id_cus}")
	public String deleteCus(@PathVariable(name = "id_cus") int id) {
		cusRepo.deleteById(id);
		return "redirect:/dashboard/customer";
	}
	
	@RequestMapping("dashboard/services/delete/{id_ser}")
	@Transactional
	public String deleteService(@PathVariable(name = "id_ser") int id) {
		serRepo.deleteById(id);
		//serRepo.deleteById(id);
		return "redirect:/dashboard/services";
	}

	@GetMapping("/dashboard/staff/export")
	public void exportExcel(HttpServletResponse rp) throws IOException, InvalidFormatException {
		rp.setContentType("application/octet-stream");
		DateFormat curDate = new SimpleDateFormat("dd-MM-YYYY");
		String curDateTime = curDate.format(new Date());
		String fileName = "Nhanvien_" + curDateTime+".xlsx";
		String headerkey = "Content-Disposition";
		String headerValue = "attachement; filename=" + fileName;
		rp.setHeader(headerkey, headerValue);
	 	List<Staff> staffList = staffRepo.findAll();
		ExcelExporter ex = new ExcelExporter(staffList);
		ex.export(rp);

	}
	
	@GetMapping("/dashboard/services/export")
	public void exportExcelService(HttpServletResponse rp) throws IOException, InvalidFormatException {
		rp.setContentType("application/octet-stream");
		DateFormat curDate = new SimpleDateFormat("dd-MM-YYYY");
		String curDateTime = curDate.format(new Date());
		String fileName = "DanhSachDichVu_" + curDateTime+".xlsx";
		String headerkey = "Content-Disposition";
		String headerValue = "attachement; filename=" + fileName;
		rp.setHeader(headerkey, headerValue);
	 	List<Service> service = serRepo.findAll();
		ServiceExport ex = new ServiceExport(service);
		ex.export(rp);

	}
	
	@GetMapping("/dashboard/customer/export")
	public void ExportCus(HttpServletResponse rp) throws IOException, InvalidFormatException {
		rp.setContentType("application/octet-stream");
		DateFormat curDate = new SimpleDateFormat("dd-MM-YYYY");
		String curDateTime = curDate.format(new Date());
		String fileName = "DanhSachKhacHang_" + curDateTime+".xlsx";
		String headerkey = "Content-Disposition";
		String headerValue = "attachement; filename=" + fileName;
		rp.setHeader(headerkey, headerValue);
	 	List<Customer> cus = cusRepo.findAll();
	 	ExportCus ex = new ExportCus(cus);
		ex.export(rp);

	}

}
