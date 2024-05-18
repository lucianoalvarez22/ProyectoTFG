package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "cargoname")
public class CargoName {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cargoname_id")
    private Long cargonameId;
    
    @Column(name = "cargo_name")
    private String cargoName;
    
    @ManyToOne
    @JoinColumn(name = "rol_level", referencedColumnName = "rol_level")
    private Roles rolLevel;
    
    //GET AND SET

	public Long getCargonameId() {
		return cargonameId;
	}

	public void setCargonameId(Long cargonameId) {
		this.cargonameId = cargonameId;
	}

	public String getCargoName() {
		return cargoName;
	}

	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}

	public Roles getRolLevel() {
		return rolLevel;
	}

	public void setRolLevel(Roles rolLevel) {
		this.rolLevel = rolLevel;
	}
    
    
    
	
    
    
}
