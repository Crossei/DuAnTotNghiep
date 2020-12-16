package com.example.demo.controller;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.dao.Staff;
import com.example.demo.service.StaffRepository;

import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

public class CalenderExport {
//	private XSSFWorkbook workbook ;
//	private Sheet sheet;
//	private List<Staff> listStaff;
//	private XSSFRow row;
//
//	
//	
//	public  CalenderExport(List<Staff> staffList) throws FileNotFoundException, IOException, ParsePropertyException, InvalidFormatException {
//		this.listStaff = staffList;
//		Map<String, Object> beans = new HashMap<String, Object>();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm");		
//		beans.put("dateFormat", dateFormat);	
//		beans.put("staffList", staffList);	
//		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("templates/export/nhanvien.xlsx");
//		workbook =  new XSSFWorkbook(is);
//		sheet  = workbook.getSheetAt(0);
//		
//	}
//	
//	private void writeDataRow() {
//		int rowCount = 2;
//		CellStyle cellStyle = workbook.createCellStyle();  
//		CreationHelper createHelper = workbook.getCreationHelper();  
//        cellStyle.setDataFormat(  
//            createHelper.createDataFormat().getFormat("dd-MM-yyyy"));  
//		int i =1;
//		for(Staff staff : listStaff){
//			Row row =sheet.createRow(rowCount++);
//			Cell cell = row.createCell(1);
//			cell.setCellValue(i);
//			cell = row.createCell(2);
//			cell.setCellValue(staff.getName_staff());
//			cell = row.createCell(3);
//			cell.setCellValue(staff.getSex() == 1 ? "Name" : "Nữ");
//			cell = row.createCell(4);
//			cell.setCellValue(staff.getAddress());
//			cell = row.createCell(5);
//			cell.setCellValue(staff.getEmail());
//			cell = row.createCell(6);
//			cell.setCellValue(staff.getPhone());
//			cell = row.createCell(7);
//			cell.setCellValue(staff.getDateWorking_Start());
//			cell.setCellStyle(cellStyle); 
//			i++;
//		}
//	}
//	
//	public void export(HttpServletResponse rp) throws IOException {
//		writeDataRow();
//		ServletOutputStream ops = rp.getOutputStream();
//		workbook.write(ops);
//		workbook.close();
//		ops.close();
//	}

}
