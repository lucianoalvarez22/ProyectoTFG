package com.example.demo.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
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
@Table(name = "salas")
public class Salas {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sala_id")
    private Long salaId;
    
    @Column(name = "sala_numero")
    private int salaNumero;
    
    @Column(name = "sala_asientostotales")
    private int salaAsientosTotales;
    
    @Column(name = "sala_estado")
    private boolean salaEstado;
    
    @Column(name = "num_filas")
    private int numeroFilas;
    
    @Column(name = "num_columnas")
    private int numeroColumnas;
    

    @ManyToOne
    @JoinColumn(name = "mapa_id", referencedColumnName = "mapa_id")
    private Mapas mapaId;

    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Asientos> asientos;
    
    @OneToMany(mappedBy = "sala", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservas> reservas;
    
    
    //GETTER AND SETTER
    
    
    
    public Salas(Long salaId, int salaNumero, int salaAsientosTotales, boolean salaEstado, int numeroFilas,
    		int numeroColumnas, Mapas mapaId) {
    	this.salaId = salaId;
    	this.salaNumero = salaNumero;
    	this.salaAsientosTotales = salaAsientosTotales;
    	this.salaEstado = salaEstado;
    	this.numeroFilas = numeroFilas;
    	this.numeroColumnas = numeroColumnas;
    	this.mapaId = mapaId;
    }
    

	public Salas() {
	}


	public Long getSalaId() {
		return salaId;
	}


	public void setSalaId(Long salaId) {
		this.salaId = salaId;
	}

	public int getSalaAsientosTotales() {
		return salaAsientosTotales;
	}

	public void setSalaAsientosTotales(int salaAsientosTotales) {
		this.salaAsientosTotales = salaAsientosTotales;
	}

	

	public Mapas getMapaId() {
		return mapaId;
	}

	public void setMapaId(Mapas mapaId) {
		this.mapaId = mapaId;
	}

	public List<Asientos> getAsientos() {
		return asientos;
	}

	public void setAsientos(List<Asientos> asientos) {
		this.asientos = asientos;
	}

	public int getSalaNumero() {
		return salaNumero;
	}

	public void setSalaNumero(int salaNumero) {
		this.salaNumero = salaNumero;
	}

	public boolean isSalaEstado() {
		return salaEstado;
	}

	public void setSalaEstado(boolean salaEstado) {
		this.salaEstado = salaEstado;
	}

	public int getNumeroFilas() {
		return numeroFilas;
	}

	public void setNumeroFilas(int numeroFilas) {
		this.numeroFilas = numeroFilas;
	}

	public int getNumeroColumnas() {
		return numeroColumnas;
	}

	public void setNumeroColumnas(int numeroColumnas) {
		this.numeroColumnas = numeroColumnas;
	}

	public List<Reservas> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reservas> reservas) {
		this.reservas = reservas;
	}


	
	
    
    
    

}
