package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservas")
public class Reservas {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserva_id")
    private Long reservaId;

    @Column(name = "fecha_entrada")
    private LocalDateTime fechaEntrada;

    @Column(name = "fecha_salida")
    private LocalDateTime fechaSalida;

    @Column(name = "reserva_completada")
    private boolean reservaCompletada;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "asiento_id", referencedColumnName = "asiento_id")
    private Asientos asiento;
    
    @ManyToOne
    @JoinColumn(name = "sala_id", referencedColumnName = "sala_id")
    private Salas sala;

    @ManyToOne
    @JoinColumn(name = "mapa_id", referencedColumnName = "mapa_id")
    private Mapas mapa;
    
    //GETTERS AND SETTERS

	public Long getReservaId() {
		return reservaId;
	}

	public void setReservaId(Long reservaId) {
		this.reservaId = reservaId;
	}


	public LocalDateTime getFechaEntrada() {
		return fechaEntrada;
	}

	public void setFechaEntrada(LocalDateTime fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public LocalDateTime getFechaSalida() {
		return fechaSalida;
	}

	public void setFechaSalida(LocalDateTime fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	public Asientos getAsiento() {
		return asiento;
	}

	public void setAsiento(Asientos asiento) {
		this.asiento = asiento;
	}

	public boolean isReservaCompletada() {
		return reservaCompletada;
	}

	public void setReservaCompletada(boolean reservaCompletada) {
		this.reservaCompletada = reservaCompletada;
	}

	public Salas getSala() {
		return sala;
	}

	public void setSala(Salas sala) {
		this.sala = sala;
	}

	public Mapas getMapa() {
		return mapa;
	}

	public void setMapa(Mapas mapa) {
		this.mapa = mapa;
	}
	
	
    
    

}
