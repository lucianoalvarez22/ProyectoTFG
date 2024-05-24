package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Reservas;

public interface ReservaRepo extends JpaRepository<Reservas, Long> {

}
