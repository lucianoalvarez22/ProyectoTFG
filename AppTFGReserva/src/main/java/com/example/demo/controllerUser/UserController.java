package com.example.demo.controllerUser;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.componenteLogueado.UserSessionLog;
import com.example.demo.entity.Asientos;
import com.example.demo.entity.Company;
import com.example.demo.entity.Reservas;
import com.example.demo.entity.Roles;
import com.example.demo.entity.Salas;
import com.example.demo.entity.Usuarios;
import com.example.demo.serviceAdmin.IAsientosService;
import com.example.demo.serviceAdmin.IMapaService;
import com.example.demo.serviceAdmin.ISalasService;
import com.example.demo.serviceUser.IReservaService;
import com.example.demo.serviceUser.IUserService;

@Controller
public class UserController {
	
	@Autowired
	private UserSessionLog userSession;
	
	@Autowired
	private IUserService userGeneralService;
	
	
	@Autowired
	private IMapaService mapaService; 
	
	@Autowired
	private ISalasService salaService;
	
	@Autowired
	private IAsientosService asientoService;
	
	@Autowired
	private IReservaService reservaService;
	
	@GetMapping("/myPerfilUser")
	public String myPerfilUser(Model model) {
		
		Usuarios userLogueado = userSession.getUser();
		Long userId = userLogueado.getUserId();
		
		Roles rol = userLogueado.getRolLevel();
		Long rolIDUsuario = rol.getRolLevel();
		
		Usuarios myPerfilUser = userGeneralService.getUsuarioGeneralById(userId); 
		
		model.addAttribute("usuarioGeneral", myPerfilUser);
		model.addAttribute("rolID", rolIDUsuario);
		
		return "listCompanies";
	}
	
	
	@GetMapping("/verSalas")
    public String verSalas(Model model) {
        Usuarios userLogueado = userSession.getUser();
        Company company = userLogueado.getCompanyId();
        
        Roles rol = userLogueado.getRolLevel();
		Long rolIDUsuario = rol.getRolLevel();
		
        List<Salas> salas = mapaService.getSalasByCompany(company.getCompanyId());
        
        model.addAttribute("salas", salas);
        model.addAttribute("rolID", rolIDUsuario);
        return "userGeneral/listSalasUserGeneral";
    }
	
	
	@GetMapping("/verAsientosUserGeneral/{salaId}")
	public String pintarAsientos(@PathVariable Long salaId, Model model) {
		Usuarios userLogueado = userSession.getUser();
		Roles rol = userLogueado.getRolLevel();
		Long rolIDUsuario = rol.getRolLevel();
		
		
	    Salas sala = salaService.getSalaById(salaId);
	    List<Asientos> asientos = sala.getAsientos();
	    
//	    for (Asientos as:asientos) {
//	    	System.out.println(as.toString());
//	    }
	    
	    long asientosLibres = asientos.stream().filter(Asientos::isAsientoEstado).count();
	    long asientosOcupados = asientos.size() - asientosLibres;
	    
	    model.addAttribute("sala", sala);
	    model.addAttribute("asientos", asientos);
	    model.addAttribute("asientosLibres", asientosLibres);
	    model.addAttribute("asientosOcupados", asientosOcupados);
	    model.addAttribute("rolID", rolIDUsuario);
	    return "userGeneral/verAsientosUserGeneral";
	}
	
	@PostMapping("/reservarAsiento")
    public String reservarAsiento(@RequestParam("asientoId") Long asientoId,
                                  @RequestParam("fechaEntrada") String fechaEntrada,
                                  @RequestParam("fechaSalida") String fechaSalida,
                                  Model model) {
        Usuarios userLogueado = userSession.getUser();
        Asientos asiento = asientoService.getAsientosById(asientoId);
        Salas sala = asiento.getSala();

        asiento.setAsientoEstado(false); // Cambia el estado a ocupado
        asientoService.saveAsiento(asiento);

        Reservas reserva = new Reservas();
        reserva.setAsiento(asiento);
        reserva.setUsuario(userLogueado);
        reserva.setFechaEntrada(LocalDateTime.parse(fechaEntrada));
        reserva.setFechaSalida(LocalDateTime.parse(fechaSalida));
        reserva.setReservaCompletada(true); // Por defecto la reserva no está completada
        reserva.setSala(sala);
        reserva.setMapa(sala.getMapaId());
        
        
        reservaService.saveReserva(reserva);

        return "redirect:/misReservas?success";
    }
	
	@GetMapping("/misReservas")
    public String misReservas(Model model) {
        Usuarios userLogueado = userSession.getUser();
        Roles rol = userLogueado.getRolLevel();
		Long rolIDUsuario = rol.getRolLevel();
        List<Reservas> reservas = reservaService.getReservasByUsuario(userLogueado);
        model.addAttribute("reservas", reservas);
        model.addAttribute("rolID", rolIDUsuario);

        // Verifica si hay un mensaje de éxito
        if (model.containsAttribute("success")) {
            model.addAttribute("message", "Reserva gestionada exitosamente");
            
        }

        return "userGeneral/misReservas";
    }

	
	

}
