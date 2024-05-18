package com.example.demo.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Roles {
	
	@Id
	@Column(name = "rol_level")
	private Long rolLevel;
	
	@Column(name = "rol_descripcion")
	private String rolDescripcion;
	
	
	@OneToMany(mappedBy = "rolLevel")
    private List<CargoName> cargos;
	
	
	@OneToMany(mappedBy = "rolLevel")
    private List<Usuarios> usuarios;
	

	//GET AND SET


	public Long getRolLevel() {
		return rolLevel;
	}


	public void setRolLevel(Long rolLevel) {
		this.rolLevel = rolLevel;
	}


	public String getRolDescripcion() {
		return rolDescripcion;
	}


	public void setRolDescripcion(String rolDescripcion) {
		this.rolDescripcion = rolDescripcion;
	}


	public List<CargoName> getCargos() {
		return cargos;
	}


	public void setCargos(List<CargoName> cargos) {
		this.cargos = cargos;
	}


	public List<Usuarios> getUsuarios() {
		return usuarios;
	}


	public void setUsuarios(List<Usuarios> usuarios) {
		this.usuarios = usuarios;
	} 

	
	
	
	

	


    
    
	
	
	

}
