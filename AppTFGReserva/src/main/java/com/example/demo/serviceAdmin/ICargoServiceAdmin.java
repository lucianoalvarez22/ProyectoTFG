package com.example.demo.serviceAdmin;

import java.util.List;

import com.example.demo.entity.CargoName;

public interface ICargoServiceAdmin {
	
	List<CargoName> getAllCargos();
	CargoName findByCargoName(String cargoName);

}
