package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Reservas;
import com.example.demo.entity.Usuarios;

public interface ReservaRepo extends JpaRepository<Reservas, Long> {
	
	List<Reservas> findByUsuario(Usuarios usuario);
		
}
