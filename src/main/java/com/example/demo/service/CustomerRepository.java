package com.example.demo.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dao.Customer;
import com.example.demo.dao.Staff;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	Customer findById(int id);
	Customer findByEmail(String email);
	Customer findByIduser(int id_user);

}
