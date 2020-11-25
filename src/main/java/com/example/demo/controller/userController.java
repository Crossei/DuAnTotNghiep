package com.example.demo.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JFileChooser;

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

import com.example.demo.dao.Staff;
import com.example.demo.service.StaffRepository;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

@Controller
public class userController {

	@Autowired
	private StaffRepository staffRepo;

	int id_staff;

	@RequestMapping("dashboard/staff/delete/{id_staff}")
	public String delete(@PathVariable(name = "id_staff") int id) {
		staffRepo.deleteById(id);
		return "redirect:/dashboard/staff";
	}

	@GetMapping("/dashboard/staff/export")
	public void exportExcel(HttpServletResponse rp) throws IOException, InvalidFormatException {
		rp.setContentType("application/octet-stream");
		String headerkey = "Content-Disposition";
		String headerValue = "attachement; filename=staff.xlsx";
		rp.setHeader(headerkey, headerValue);
	 	List<Staff> staffList = staffRepo.findAll();
		ExcelExporter ex = new ExcelExporter(staffList);
		ex.export(rp);
		/*
		 * Map<String, Object> beans = new HashMap<String, Object>(); SimpleDateFormat
		 * dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		 * 
		 * beans.put("dateFormat", dateFormat);
		 * 
		 * String fileName = "Wniosek_Zapomoga_ZFŚS_.xlsx"; XLSTransformer transformer =
		 * new XLSTransformer();
		 * 
		 * InputStream is = Thread.currentThread().getContextClassLoader()
		 * .getResourceAsStream("templates/export/nhanvien.xlsx"); Workbook
		 * resultWorkbook = transformer.transformXLS(is, beans); ByteArrayOutputStream
		 * bout = new ByteArrayOutputStream(); Sheet sheet =
		 * resultWorkbook.getSheetAt(0); FileOutputStream op = new
		 * FileOutputStream("nhanvien.xlsx"); resultWorkbook.write(op);
		 * resultWorkbook.close();
		 */
		/*
		 * XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream("nhanvien.xlsx"));
		 * FileOutputStream fileOut = new FileOutputStream("new.xlsx"); //Sheet mySheet
		 * = wb.getSheetAt(0); XSSFSheet sheet1 = wb.getSheetAt(0); ServletOutputStream
		 * ops= rp.getOutputStream(); wb.write(ops); fileOut.close();
		 */
	}

}
