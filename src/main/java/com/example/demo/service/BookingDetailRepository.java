package com.example.demo.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dao.BookingDetail;

public interface BookingDetailRepository extends JpaRepository<BookingDetail, Integer> {
	List findByIdBooking(int id);
	BookingDetail findById(int id);
}
