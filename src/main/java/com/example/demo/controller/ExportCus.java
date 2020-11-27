package com.example.demo.controller;

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
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.example.demo.dao.Customer;
import com.example.demo.dao.Staff;

import net.sf.jxls.exception.ParsePropertyException;

public class ExportCus {
	private XSSFWorkbook workbook ;
	private Sheet sheet;
	List<Customer> cusList;
	private XSSFRow row;

	
	
	public  ExportCus(List<Customer> cusList) throws FileNotFoundException, IOException, ParsePropertyException, InvalidFormatException {
		this.cusList = cusList;
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("templates/export/customer.xlsx");
		workbook =  new XSSFWorkbook(is);
		sheet  = workbook.getSheetAt(0);
		
	}
	
	private void writeDataRow() {
		int rowCount = 2;
		CellStyle cellStyle = workbook.createCellStyle();  
		CreationHelper createHelper = workbook.getCreationHelper();  
        cellStyle.setDataFormat(  
            createHelper.createDataFormat().getFormat("dd-MM-yyyy"));  
		int i =1;
		for(Customer cus : cusList){
			Row row =sheet.createRow(rowCount++);
			Cell cell = row.createCell(1);
			cell.setCellValue(i);
			
			cell = row.createCell(2);
			cell.setCellValue(cus.getName_cus());
			
			cell = row.createCell(3);
			cell.setCellValue(cus.getPhone());
			
			cell = row.createCell(4);
			cell.setCellValue(cus.getEmail());
			
			cell = row.createCell(5);
			cell.setCellValue(cus.getDiachi());
			
			cell = row.createCell(6);
			cell.setCellValue(cus.getNgaysinh());
			
			cell.setCellStyle(cellStyle); 
			i++;
		}
	}
	
	public void export(HttpServletResponse rp) throws IOException {
		writeDataRow();
		ServletOutputStream ops = rp.getOutputStream();
		workbook.write(ops);
		workbook.close();
		ops.close();
	}
}
