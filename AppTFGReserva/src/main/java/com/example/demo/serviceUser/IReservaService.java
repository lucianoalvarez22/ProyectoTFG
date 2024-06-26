package com.example.demo.serviceUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.entity.Reservas;
import com.example.demo.entity.Usuarios;

public interface IReservaService {
	
	List<Reservas> getAllReservas();
	Reservas getReservaById(Long reservaId);
	 void saveReserva(Reservas reserva);
	List<Reservas> getReservasByUsuario(Usuarios usuario);
	void eliminarReserva(Long id);
	List<Reservas> getReservasByUserId(Long userId);
	List<Reservas> searchReservasByFechaEntrada(Usuarios usuario, LocalDate fechaEntrada);

}
