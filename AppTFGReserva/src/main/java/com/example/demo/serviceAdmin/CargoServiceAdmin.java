package com.example.demo.serviceAdmin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.CargoName;
import com.example.demo.entity.Company;
import com.example.demo.entity.Roles;
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

	@Override
    public CargoName getCargoByRolLevel(Roles rolLevel) {
        return cargoRepository.findFirstByRolLevel(rolLevel);
    }

	@Override
	public CargoName getCargoById(Long cargoId) {
		Optional<CargoName> companyOptional = cargoRepository.findById(cargoId);
        return companyOptional.orElse(null);
	}

}
