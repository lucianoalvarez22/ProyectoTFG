package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Mapas;
import com.example.demo.entity.Salas;

public interface SalaRepo extends JpaRepository<Salas, Long> {
	
	List<Salas> findByMapaId(Mapas mapa);
}
