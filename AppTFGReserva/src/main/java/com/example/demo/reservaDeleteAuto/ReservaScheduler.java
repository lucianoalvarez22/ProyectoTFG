package com.example.demo.reservaDeleteAuto;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Asientos;
import com.example.demo.entity.Reservas;
import com.example.demo.serviceAdmin.IAsientosService;
import com.example.demo.serviceUser.IReservaService;

@Component
public class ReservaScheduler {
	
	 	@Autowired
	    private IReservaService reservaService;

	    @Autowired
	    private IAsientosService asientoService;

	    @Scheduled(cron = "0 * * * * *") // Ejecutar cada minuto
	    public void eliminarReservasCaducadas() {
	        List<Reservas> reservas = reservaService.getAllReservas();
	        LocalDateTime now = LocalDateTime.now();

	        for (Reservas reserva : reservas) {
	            if (reserva.getFechaSalida().isBefore(now)) {
	                Asientos asiento = reserva.getAsiento();
	                asiento.setAsientoEstado(true);
	                asientoService.saveAsiento(asiento);
	                reservaService.eliminarReserva(reserva.getReservaId()); 
	            }
	        }
	    }

}
