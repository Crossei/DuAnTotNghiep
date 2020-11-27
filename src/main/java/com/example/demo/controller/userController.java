package com.example.demo.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JFileChooser;
import javax.transaction.Transactional;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dao.Customer;
import com.example.demo.dao.Service1;
import com.example.demo.dao.Service_price;
import com.example.demo.dao.Staff;
import com.example.demo.service.CustomerRepository;
import com.example.demo.service.Service1Repository;
import com.example.demo.service.ServicePriceRepository;
import com.example.demo.service.StaffRepository;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

@Controller
public class userController {

	@Autowired
	private StaffRepository staffRepo;
	
	@Autowired
	private Service1Repository serRepo;
	
	@Autowired
	private ServicePriceRepository serPRepo;
	@Autowired
	private CustomerRepository cusRepo;

	int id_staff;

	@RequestMapping("dashboard/staff/delete/{id_staff}")
	public String delete(@PathVariable(name = "id_staff") int id) {
		staffRepo.deleteById(id);
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
		Service_price serp = serPRepo.findByIdser(id);
		serPRepo.deleteByIdser(id);
		serRepo.deleteById(id);
		System.out.println(serp);
		System.out.println(id);
		System.out.println(serp.getId_ser_price());
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
	 	List<Service1> service = serRepo.findAll();
	 	List<Service_price> serP = serPRepo.findAll();
		ServiceExport ex = new ServiceExport(service,serP);
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
