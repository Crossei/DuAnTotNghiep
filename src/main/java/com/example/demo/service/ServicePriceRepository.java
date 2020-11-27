package com.example.demo.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dao.Service_price;

public interface ServicePriceRepository extends JpaRepository<Service_price, Integer> {
	Service_price findByIdser(int ser);
	 void deleteByIdser(int ser);
}
