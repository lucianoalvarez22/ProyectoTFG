package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Company;

public interface CompanyRepo extends JpaRepository<Company, Long> {
	
	List<Company> findByCompanyId(Long companyId);

}
