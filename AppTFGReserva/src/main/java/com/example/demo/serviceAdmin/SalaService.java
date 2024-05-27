package com.example.demo.serviceAdmin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Asientos;
import com.example.demo.entity.Mapas;
import com.example.demo.entity.Salas;
import com.example.demo.repository.AsientoRepo;
import com.example.demo.repository.SalaRepo;

@Service
public class SalaService implements ISalasService {
	
	@Autowired
    private SalaRepo salaRepo;
	
	@Autowired
    private AsientoRepo asientoRepository;

	@Override
	public List<Salas> getAllSalas() {
		return salaRepo.findAll();
	}

	@Override
	public Salas getSalaById(Long salaId) {
		Optional<Salas> salaOptional = salaRepo.findById(salaId);
        return salaOptional.orElse(null);
	}

	@Override
	public Salas saveSala(Salas sala) {
		return salaRepo.save(sala);
	}

	@Override
	public void deleteSala(Long id) {
		salaRepo.deleteById(id);
		
	}

	@Override
	public List<Salas> getSalaByMapaId(Long mapaId) {
		return salaRepo.findByMapaIdMapaId(mapaId);
	}
	


}
