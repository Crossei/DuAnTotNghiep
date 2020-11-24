package com.example.demo.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dao.Staff;

public interface StaffRepository extends JpaRepository<Staff, Integer> {

}
