package com.example.demo.controllerAdminCompany;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.componenteLogueado.UserSessionLog;
import com.example.demo.entity.Asientos;
import com.example.demo.entity.Company;
import com.example.demo.entity.Mapas;
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
	public String getMySalas(Model model) {
		
		Usuarios userLogueado = userSession.getUser();
		Roles rol = userLogueado.getRolLevel();
		Long rolIDUsuario = rol.getRolLevel();
		Company company = userLogueado.getCompanyId();
		Long companyId = company.getCompanyId();
		System.out.println("ID de la compañía del usuario logueado: " + companyId);
		
		
		
	    List<Mapas> mapas = mapaServicio.getListMapaById(companyId);
	    
	    model.addAttribute("myMap", mapas);
		model.addAttribute("rolID", rolIDUsuario);
		
		return "listMapas";
	}
	
	
	@GetMapping("/getMySalas/{id}")
	public String getSalas(@PathVariable(name = "id") Long mapaId, Model model) {
		Usuarios userLogueado = userSession.getUser();
		Roles rol = userLogueado.getRolLevel();
		Long rolIDUsuario = rol.getRolLevel();
		Company company = userLogueado.getCompanyId();
		Long companyId = company.getCompanyId();
		
		List<Salas> sala = mapaServicio.findSalasByMapaId(mapaId);
		List<Mapas> mapas = mapaServicio.getListMapaById(companyId);

		model.addAttribute("salas", sala);
		model.addAttribute("myMap", mapas);
		model.addAttribute("rolID", rolIDUsuario);
	    return "listMapas";
	}
	
	
	
	@GetMapping("/verAsientosAdminCompany/{salaId}")
	public String pintarAsientos(@PathVariable Long salaId, Model model) {
		Usuarios userLogueado = userSession.getUser();
		Roles rol = userLogueado.getRolLevel();
		Long rolIDUsuario = rol.getRolLevel();
		
		
	    Salas sala = salaService.getSalaById(salaId);
	    List<Asientos> asientos = sala.getAsientos();
	    
	    long asientosLibres = asientos.stream().filter(Asientos::isAsientoEstado).count();
	    long asientosOcupados = asientos.size() - asientosLibres;
	    
	    model.addAttribute("sala", sala);
	    model.addAttribute("asientos", asientos);
	    model.addAttribute("asientosLibres", asientosLibres);
	    model.addAttribute("asientosOcupados", asientosOcupados);
	    model.addAttribute("rolID", rolIDUsuario);
	    return "adminCompany/verAsientosAdminCompany";
	}
	
	
	

}
