package com.example.demo.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "company")
public class Company {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "company_id")
	private Long companyId;
	
	@Column(name = "company_name")
	private String companyName;

	@Column(name = "company_industria")
	private String companyIndustria;
	


	@Column(name = "company_ubicacion")
	private String companyUbicacion;
	
	@Column(name = "company_telefono")
	private String companyTelefono;
	
	@Column(name = "company_email")
	private String companyEmail;
	
	
	@OneToMany(mappedBy = "companyId")
    private List<Usuarios> usuarios;
	
	@OneToMany(mappedBy = "companyMapa")
	private List<Mapas> mapas; 

	
	//CONSTRUCTOR PARA ADD COMPANY
	public Company() {
}


	//CONSTRUCTOR PARA EDITAR COMPANY
	public Company(Long companyId, String companyName, String companyIndustria, String companyUbicacion,
		String companyTelefono, String companyEmail) {
	this.companyId = companyId;
	this.companyName = companyName;
	this.companyIndustria = companyIndustria;
	this.companyUbicacion = companyUbicacion;
	this.companyTelefono = companyTelefono;
	this.companyEmail = companyEmail;
}


	//GET AND SET



	public String getCompanyEmail() {
		return companyEmail;
	}


	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}


	public Long getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getCompanyIndustria() {
		return companyIndustria;
	}


	public void setCompanyIndustria(String companyIndustria) {
		this.companyIndustria = companyIndustria;
	}


	public String getCompanyUbicacion() {
		return companyUbicacion;
	}


	public void setCompanyUbicacion(String companyUbicacion) {
		this.companyUbicacion = companyUbicacion;
	}


	public String getCompanyTelefono() {
		return companyTelefono;
	}


	public void setCompanyTelefono(String companyTelefono) {
		this.companyTelefono = companyTelefono;
	}

	public List<Usuarios> getUsuarios() {
		return usuarios;
	}


	public void setUsuarios(List<Usuarios> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Mapas> getMapas() {
		return mapas;
	}
	
	
	public void setMapas(List<Mapas> mapas) {
		this.mapas = mapas;
	}
	
	
	

	
	
	

}
