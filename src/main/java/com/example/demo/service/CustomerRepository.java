package com.example.demo.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dao.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	Customer findById(int id);

}
