package com.example.demo.serviceAdmin;

import java.util.List;

import com.example.demo.entity.*;

public interface ICargoServiceAdmin {
	
	List<CargoName> getAllCargos();
	CargoName findByCargoName(String cargoName);
	CargoName getCargoByRolLevel(Roles rolLevel);
	CargoName getCargoById(Long cargoId);

}
