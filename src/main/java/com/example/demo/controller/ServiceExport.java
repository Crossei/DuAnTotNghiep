package com.example.demo.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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

import com.example.demo.dao.Service;

import net.sf.jxls.exception.ParsePropertyException;

public class ServiceExport {
	private XSSFWorkbook workbook ;
	private Sheet sheet;
	private List<Service> listService;
	private XSSFRow row;


	
	
	public  ServiceExport(List<Service> service1) throws FileNotFoundException, IOException, ParsePropertyException, InvalidFormatException {
		this.listService = service1;
		
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("templates/export/service.xlsx");
		workbook =  new XSSFWorkbook(is);
		sheet  = workbook.getSheetAt(0);
		
	}
	
	private void writeDataRow() {
		int rowCount = 2;
		CellStyle cellStyle = workbook.createCellStyle();  
		CreationHelper createHelper = workbook.getCreationHelper();  
        cellStyle.setDataFormat(  
            createHelper.createDataFormat().getFormat("HH:mm"));  
		int i =1;
		int a = 0;
		for(Service ser1 : listService){
		
			Row row =sheet.createRow(rowCount++);
			Cell cell = row.createCell(1);
			cell.setCellValue(i);
			cell = row.createCell(2);
			cell.setCellValue(ser1.getName());
			cell = row.createCell(3);
			cell.setCellValue(ser1.getDsc());
			cell = row.createCell(4);
			cell.setCellValue(ser1.getTime_working());
			cell.setCellStyle(cellStyle); 
			cell = row.createCell(5);
			cell.setCellValue(ser1.getPrice());
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
