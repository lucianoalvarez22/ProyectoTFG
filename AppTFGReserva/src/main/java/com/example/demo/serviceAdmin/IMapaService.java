package com.example.demo.serviceAdmin;

import java.util.List;
import com.example.demo.entity.Mapas;
import com.example.demo.entity.Salas;

public interface IMapaService {
	
	List<Mapas> getAllMapas();
	Mapas getMapaById(Long id);
	Mapas saveMapa(Mapas mapa);
	void deleteMapa(Long id);
	List<Salas> findSalasByMapaId(Long mapaId);

}
