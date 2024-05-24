package com.example.demo.serviceAdmin;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.entity.Mapas;
import com.example.demo.entity.Salas;
import com.example.demo.repository.MapaRepo;


@Service
public class MapaService implements IMapaService { 
	
	@Autowired
    private MapaRepo mapaRepo;

	@Override
	public List<Mapas> getAllMapas() {
		return mapaRepo.findAll();
	}

	@Override
	public Mapas getMapaById(Long mapaId) {
		Optional<Mapas> mapaOptional = mapaRepo.findById(mapaId);
        return mapaOptional.orElse(null);
	}

	@Override
	public Mapas saveMapa(Mapas mapa) {
		return mapaRepo.save(mapa);
	}

	@Override
	public void deleteMapa(Long id) {
		mapaRepo.deleteById(id);
		
	}

	@Override
	public List<Salas> findSalasByMapaId(Long mapaId) {
		Optional<Mapas> mapa = mapaRepo.findById(mapaId);
	    return mapa.map(Mapas::getSalas).orElse(Collections.emptyList()); 
	}


}
