package com.example.demo.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.Staff;

public interface StaffRepository extends JpaRepository<Staff, Integer> {
		Staff findByIduser(int id);
		Staff findById(int id);
}
