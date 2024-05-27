package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Mapas;

public interface MapaRepo extends JpaRepository<Mapas, Long> {
	
	List<Mapas> findByCompanyMapaCompanyId(Long companyId);

}
