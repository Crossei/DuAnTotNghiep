package com.example.demo.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dao.Service;

public interface ServiceRepository extends JpaRepository<Service, Integer> {
	Service findById(int id);
}
