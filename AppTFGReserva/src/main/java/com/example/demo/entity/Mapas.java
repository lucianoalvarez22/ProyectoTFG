package com.example.demo.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "mapas")
public class Mapas {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mapa_id")
    private Long mapaId;
    
    @Column(name = "mapa_nombre")
    private String mapaNombre;

    @Column(name = "salas_totales")
    private int numeroTotalDeSalas;
    
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company companyMapa;

    @OneToMany(mappedBy = "mapaId")
    private List<Salas> salas;
    
    @OneToMany(mappedBy = "mapa")
    private List<Reservas> reservas;
    
    //GETTER AND SETTER

	public Long getMapaId() {
		return mapaId;
	}

	public void setMapaId(Long mapaId) {
		this.mapaId = mapaId;
	}

	public String getMapaNombre() {
		return mapaNombre;
	}

	public void setMapaNombre(String mapaNombre) {
		this.mapaNombre = mapaNombre;
	}

	public int getNumeroTotalDeSalas() {
		return numeroTotalDeSalas;
	}

	public void setNumeroTotalDeSalas(int numeroTotalDeSalas) {
		this.numeroTotalDeSalas = numeroTotalDeSalas;
	}

	public Company getCompanyMapa() {
		return companyMapa;
	}

	public void setCompanyMapa(Company companyMapa) {
		this.companyMapa = companyMapa;
	}

	public List<Salas> getSalas() {
		return salas;
	}

	public void setSalas(List<Salas> salas) {
		this.salas = salas;
	}

	public List<Reservas> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reservas> reservas) {
		this.reservas = reservas;
	}
    
    
    

}
