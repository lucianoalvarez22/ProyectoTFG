package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.CargoName;

public interface CargoRepo extends JpaRepository<CargoName, Long> {
	
	CargoName findByCargoName(String cargoName);

}
