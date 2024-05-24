package com.example.demo.serviceAdmin;

import java.util.List;

import com.example.demo.entity.Mapas;
import com.example.demo.entity.Salas;

public interface ISalasService {
	
	List<Salas> getAllSalas();
	Salas getSalaById(Long id);
	Salas saveSala(Salas sala);
	void deleteSala(Long id);
}
