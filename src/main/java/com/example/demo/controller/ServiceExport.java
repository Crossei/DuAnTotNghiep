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
import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.dao.Service1;
import com.example.demo.dao.Service_price;
import com.example.demo.dao.Staff;
import com.example.demo.service.ServicePriceRepository;

import net.sf.jxls.exception.ParsePropertyException;

public class ServiceExport {
	private XSSFWorkbook workbook ;
	private Sheet sheet;
	private List<Service1> listService;
	private List<Service_price>  serP;
	private XSSFRow row;
	@Autowired
	private ServicePriceRepository serPRepo;

	
	
	public  ServiceExport(List<Service1> service1, List<Service_price> serP) throws FileNotFoundException, IOException, ParsePropertyException, InvalidFormatException {
		this.listService = service1;
		this.serP = serP;
		
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
		for(Service1 ser1 : listService){
			Service_price serp = serP.get(a++);
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
			cell.setCellValue(serp.getPrice());
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
