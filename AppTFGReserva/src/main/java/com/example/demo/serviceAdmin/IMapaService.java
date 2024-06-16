package com.example.demo.serviceAdmin;

import java.util.List;
import com.example.demo.entity.Mapas;
import com.example.demo.entity.Salas;

public interface IMapaService {
	
	List<Mapas> getAllMapas();
	Mapas getMapaById(Long id);
	List<Mapas> getListMapaById(Long id);
	Mapas saveMapa(Mapas mapa);
	void deleteMapa(Long id);
	List<Salas> findSalasByMapaId(Long mapaId);
	List<Salas> getSalasByCompany(Long companyId);
	List<Mapas> searchMapasByName(String name);
	List<Mapas> searchMapasByNameAndCompanyId(String name, Long companyId);
	
	List<Salas> searchSalasByNumberAndCompany(String search, Long companyId);

}
