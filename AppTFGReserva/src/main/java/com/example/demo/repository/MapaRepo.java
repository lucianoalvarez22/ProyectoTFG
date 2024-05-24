package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Mapas;

public interface MapaRepo extends JpaRepository<Mapas, Long> {

}
