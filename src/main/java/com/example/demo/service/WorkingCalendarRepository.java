package com.example.demo.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dao.WorkingCalendar;

public interface WorkingCalendarRepository extends JpaRepository<WorkingCalendar, Integer> {
		List findByIdstaff(int i);   
		List findByIdstaffAndStatus(int s,int i);  
}
