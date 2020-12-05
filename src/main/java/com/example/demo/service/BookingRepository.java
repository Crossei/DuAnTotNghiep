package com.example.demo.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dao.Booking;

public interface BookingRepository extends JpaRepository<Booking,Integer> {
	List findByIdCus(int id);

}
