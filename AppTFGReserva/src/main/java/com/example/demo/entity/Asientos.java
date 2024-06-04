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
@Table(name = "asientos")
public class Asientos {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "asiento_id")
    private Long asientoId;

    @Column(name = "posicion_fila")
    private int posicionFila;
    
    @Column(name = "posicion_columna")
    private int posicionColumna;
    
    @Column(name = "asiento_estado")
    private boolean asientoEstado;

    @ManyToOne
    @JoinColumn(name = "sala_id", referencedColumnName = "sala_id")
    private Salas sala;

    @OneToMany(mappedBy = "asiento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservas> reservas;
    
    //GETTERS AND SETTERS

	public Long getAsientoId() {
		return asientoId;
	}

	public void setAsientoId(Long asientoId) {
		this.asientoId = asientoId;
	}

	public Salas getSala() {
		return sala;
	}

	public void setSala(Salas sala) {
		this.sala = sala;
	}

	public List<Reservas> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reservas> reservas) {
		this.reservas = reservas;
	}

	public int getPosicionFila() {
		return posicionFila;
	}

	public void setPosicionFila(int posicionFila) {
		this.posicionFila = posicionFila;
	}

	public int getPosicionColumna() {
		return posicionColumna;
	}

	public void setPosicionColumna(int posicionColumna) {
		this.posicionColumna = posicionColumna;
	}

	public boolean isAsientoEstado() {
		return asientoEstado;
	}

	public void setAsientoEstado(boolean asientoEstado) {
		this.asientoEstado = asientoEstado;
	}

	@Override
	public String toString() {
		return "Asientos [asientoId=" + asientoId + ", posicionFila=" + posicionFila + ", posicionColumna="
				+ posicionColumna + ", asientoEstado=" + asientoEstado + ", sala=" + sala + ", reservas=" + reservas
				+ "]";
	}
    
	
    

}
