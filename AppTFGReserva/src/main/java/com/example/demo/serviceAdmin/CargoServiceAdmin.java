package com.example.demo.serviceAdmin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.CargoName;
import com.example.demo.repository.CargoRepo;

@Service
public class CargoServiceAdmin implements ICargoServiceAdmin {
	
	@Autowired
	private CargoRepo cargoRepository;

	@Override
	public List<CargoName> getAllCargos() {
		return (List<CargoName>) cargoRepository.findAll();
	}

	@Override
	public CargoName findByCargoName(String cargoName) {
		return cargoRepository.findByCargoName(cargoName);
	}

}
