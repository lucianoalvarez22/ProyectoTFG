package com.example.demo.serviceAdmin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Asientos;
import com.example.demo.entity.Mapas;
import com.example.demo.repository.AsientoRepo;

@Service
public class AsientoService implements IAsientosService {
	
	@Autowired
    private AsientoRepo asientosRepo;

	@Override
	public List<Asientos> getAllAsientos() {
		return asientosRepo.findAll(); 
	}

	@Override
	public Asientos getAsientosById(Long asientoId) {
		Optional<Asientos> asientoOptional = asientosRepo.findById(asientoId);
        return asientoOptional.orElse(null);
	}

	@Override
	public Asientos saveAsiento(Asientos asiento) {
		return asientosRepo.save(asiento);
	}

	@Override
	public void deleteAsiento(Long id) {
		asientosRepo.deleteById(id);
		
	}

}
