package com.example.demo.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dao.WorkingCalendar;

public interface WorkingCalendarRepository extends JpaRepository<WorkingCalendar, Integer> {
		List findByIdstaff(int i);   
		List findByIdstaffAndStatus(int s,int i);  
		WorkingCalendar findByIdstaffAndDateWorking(int i, Date d);
		List findByDateWorking(Date d);
}
