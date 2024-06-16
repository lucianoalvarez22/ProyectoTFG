package com.example.demo.controllerAdminCompany;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.componenteLogueado.UserSessionLog;
import com.example.demo.entity.Asientos;
import com.example.demo.entity.Company;
import com.example.demo.entity.Mapas;
import com.example.demo.entity.Reservas;
import com.example.demo.entity.Roles;
import com.example.demo.entity.Salas;
import com.example.demo.entity.Usuarios;
import com.example.demo.serviceAdmin.IMapaService;
import com.example.demo.serviceAdmin.ISalasService;

@Controller
public class SalaControllerAdminCompany {
	
	@Autowired
	private UserSessionLog userSession;
	
	@Autowired
	private IMapaService mapaServicio;
	
	@Autowired
	private ISalasService salaService;
	

	
	@GetMapping("/mySalas")
	public String getMySalas(@RequestParam(value = "search", required = false) String search, Model model) {
		
		Usuarios userLogueado = userSession.getUser();
		Roles rol = userLogueado.getRolLevel();
		Long rolIDUsuario = rol.getRolLevel();
		Company company = userLogueado.getCompanyId();
		Long companyId = company.getCompanyId();
		System.out.println("ID de la compañía del usuario logueado: " + companyId);
		
		List<Mapas> mapas;
	    if (search != null && !search.isEmpty()) {
	        mapas = mapaServicio.searchMapasByNameAndCompanyId(search, companyId);
	    } else {
	        mapas = mapaServicio.getListMapaById(companyId);
	    }
		
		
	 
	    
	    model.addAttribute("myMap", mapas);
	    model.addAttribute("search", search);
		model.addAttribute("rolID", rolIDUsuario);
		
		return "listMapas";
	}
	
	
	@GetMapping("/getMySalas/{id}")
	public String getSalas(@PathVariable(name = "id") Long mapaId,@RequestParam(value = "search", required = false) String search, Model model) {
		Usuarios userLogueado = userSession.getUser();
		Roles rol = userLogueado.getRolLevel();
		Long rolIDUsuario = rol.getRolLevel();
		Company company = userLogueado.getCompanyId();
		Long companyId = company.getCompanyId();
		
		List<Salas> sala = mapaServicio.findSalasByMapaId(mapaId);
	    List<Mapas> mapas;
	    if (search != null && !search.isEmpty()) {
	        mapas = mapaServicio.searchMapasByNameAndCompanyId(search, companyId);
	    } else {
	        mapas = mapaServicio.getListMapaById(companyId);
	    }
		
		

		model.addAttribute("salas", sala);
		model.addAttribute("myMap", mapas);
		model.addAttribute("search", search);
		model.addAttribute("rolID", rolIDUsuario);
	    return "listMapas";
	}
	
	
	
	@GetMapping("/verAsientosAdminCompany/{salaId}")
    public String pintarAsientos(@PathVariable Long salaId, 
                                 @RequestParam(required = false) String fechaInicio, 
                                 @RequestParam(required = false) String fechaFin, 
                                 Model model) {
        Usuarios userLogueado = userSession.getUser();
        Roles rol = userLogueado.getRolLevel();
        Long rolIDUsuario = rol.getRolLevel();
        Salas sala = salaService.getSalaById(salaId);
        List<Asientos> asientos = sala.getAsientos();

        model.addAttribute("sala", sala);
        model.addAttribute("asientos", asientos);
        model.addAttribute("rolID", rolIDUsuario);
        model.addAttribute("fechaInicio", fechaInicio);
        model.addAttribute("fechaFin", fechaFin);
        
        Company company = userLogueado.getCompanyId();
        model.addAttribute("horaApertura", company.getHoraApertura().toString());
        model.addAttribute("horaCierre", company.getHoraCierre().toString());

        boolean mostrarMapa = fechaInicio != null && fechaFin != null;

        if (mostrarMapa) {
            LocalDateTime inicio = LocalDateTime.parse(fechaInicio);
            LocalDateTime fin = LocalDateTime.parse(fechaFin);
            long asientosLibres = asientos.stream().filter(asiento -> isAsientoDisponible(asiento, inicio, fin)).count();
            long asientosOcupados = asientos.size() - asientosLibres;
            model.addAttribute("asientosLibres", asientosLibres);
            model.addAttribute("asientosOcupados", asientosOcupados);
        } else {
            model.addAttribute("asientosLibres", asientos.size());
            model.addAttribute("asientosOcupados", 0);
        }

        model.addAttribute("mostrarMapa", mostrarMapa);

        return "adminCompany/verAsientosAdminCompany";
    }
	
	
	@GetMapping("/buscarAsientosAdmin")
    public String buscarAsientosAdminCompany(@RequestParam("fecha") String fecha, 
                                 @RequestParam("tipoReserva") String tipoReserva, 
                                 @RequestParam("salaId") Long salaId,
                                 @RequestParam("salaNumero") String salaNumero,
                                 @RequestParam(name = "horaInicio", required = false) String horaInicioParam,
                                 @RequestParam(name = "horaFin", required = false) String horaFinParam,
                                 @RequestParam(name = "fechaFormateada", required = false) String fechaFormateada,
                                 @RequestParam(name = "tipoReservaTexto", required = false) String tipoReservaTexto,
                                 Model model) {
        Company company = userSession.getUser().getCompanyId();
        LocalTime horaApertura = company.getHoraApertura();
        LocalTime horaCierre = company.getHoraCierre();
        LocalDate date = LocalDate.parse(fecha);
        LocalDateTime fechaInicio = null;
        LocalDateTime fechaFin = null;

        switch (tipoReserva) {
            case "completa":
                fechaInicio = date.atTime(horaApertura);
                fechaFin = date.atTime(horaCierre);
                break;
            case "media_manana":
                fechaInicio = date.atTime(horaApertura);
                fechaFin = fechaInicio.plusHours(4);
                break;
            case "media_tarde":
                fechaFin = date.atTime(horaCierre);
                fechaInicio = fechaFin.minusHours(4);
                break;
            case "por_horas":
                if (horaInicioParam != null && horaFinParam != null) {
                    fechaInicio = LocalDateTime.parse(fecha + "T" + horaInicioParam);
                    fechaFin = LocalDateTime.parse(fecha + "T" + horaFinParam);
                } else {
                    throw new IllegalArgumentException("Para reservas por horas, se deben proporcionar horaInicio y horaFin");
                }
                break;
            default:
                throw new IllegalArgumentException("Tipo de reserva no válido");
        }

        Salas sala = salaService.getSalaById(salaId);
        List<Asientos> asientos = sala.getAsientos();

        for (Asientos asiento : asientos) {
            boolean disponible = isAsientoDisponible(asiento, fechaInicio, fechaFin);
            asiento.setAsientoEstado(disponible);
        }

        long asientosLibres = asientos.stream().filter(Asientos::isAsientoEstado).count();
        long asientosOcupados = asientos.size() - asientosLibres;
        
        if (fechaFormateada == null || tipoReservaTexto == null) {
            // Formatear la fecha seleccionada si no se proporciona
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM yyyy", new Locale("es", "ES"));
            fechaFormateada = date.format(formatter);
            tipoReservaTexto = tipoReserva.equals("completa") ? "Jornada Completa" :
                               tipoReserva.equals("media_manana") ? "Media Jornada Mañana" :
                               tipoReserva.equals("media_tarde") ? "Media Jornada Tarde" :
                               "Por Horas";
        }

        model.addAttribute("sala", sala);
        model.addAttribute("salaNumero", salaNumero);
        model.addAttribute("asientos", asientos);
        model.addAttribute("asientosLibres", asientosLibres);
        model.addAttribute("asientosOcupados", asientosOcupados);
        model.addAttribute("rolID", userSession.getUser().getRolLevel().getRolLevel());
        model.addAttribute("fechaInicio", fechaInicio.toString());
        model.addAttribute("fechaFin", fechaFin.toString());
        model.addAttribute("fecha", fecha); // Agregar fecha al modelo
        model.addAttribute("tipoReserva", tipoReserva);
        model.addAttribute("fechaFormateada", fechaFormateada);
        model.addAttribute("tipoReservaTexto", tipoReservaTexto);
        model.addAttribute("horaApertura", horaApertura.toString());
        model.addAttribute("horaCierre", horaCierre.toString());
        model.addAttribute("mostrarMapa", true);
        return "adminCompany/verAsientosAdminCompany";
    }

    private boolean isAsientoDisponible(Asientos asiento, LocalDateTime entrada, LocalDateTime salida) {
        List<Reservas> reservas = asiento.getReservas();
        for (Reservas reserva : reservas) {
            if (entrada.isBefore(reserva.getFechaSalida()) && salida.isAfter(reserva.getFechaEntrada())) {
                return false;
            }
        }
        return true;
    }
	
	
	

}
