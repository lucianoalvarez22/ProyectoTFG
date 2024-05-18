package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.CargoName;
import com.example.demo.entity.*;

public interface CargoRepo extends JpaRepository<CargoName, Long> {
	
	CargoName findByCargoName(String cargoName);
	CargoName findFirstByRolLevel(Roles rolLevel);

}
