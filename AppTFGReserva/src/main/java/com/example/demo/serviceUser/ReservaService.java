package com.example.demo.serviceUser;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Reservas;
import com.example.demo.entity.Usuarios;
import com.example.demo.repository.ReservaRepo;

@Service
public class ReservaService implements IReservaService {
	
	@Autowired
	private ReservaRepo reservaRepo;

	@Override
	public void saveReserva(Reservas reserva) {
		reservaRepo.save(reserva);
		
	}

	@Override
	public List<Reservas> getAllReservas() {
		return reservaRepo.findAll();
	}

	@Override
	public Reservas getReservaById(Long reservaId) {
		Optional<Reservas> reservaOptional = reservaRepo.findById(reservaId);
        return reservaOptional.orElse(null);
	}

	@Override
	public List<Reservas> getReservasByUsuario(Usuarios usuario) {
		return reservaRepo.findByUsuario(usuario);
	}

}
