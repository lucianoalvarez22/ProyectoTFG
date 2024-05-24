package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Asientos;

public interface AsientoRepo extends JpaRepository<Asientos, Long> {

}
