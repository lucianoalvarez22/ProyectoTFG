package com.example.demo.controllerAdmin;

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
import com.example.demo.entity.Mapas;
import com.example.demo.entity.Roles;
import com.example.demo.entity.Salas;
import com.example.demo.entity.Usuarios;
import com.example.demo.serviceAdmin.IAsientosService;
import com.example.demo.serviceAdmin.ICompanyServiceAdmin;
import com.example.demo.serviceAdmin.IMapaService;
import com.example.demo.serviceAdmin.ISalasService;


@Controller
public class MapasController {
	
	@Autowired
	private IMapaService mapaService;
	
	@Autowired
	private ICompanyServiceAdmin companyServicio;
	
	@Autowired
	private ISalasService salaService;
	
	@Autowired
	private IAsientosService asientoService;
	
	@Autowired
	private UserSessionLog userSession;
	
	
	@GetMapping("/listMapas")
    public String listMapas(Model model) {
		
		Usuarios userLogueado = userSession.getUser();
		Roles rol = userLogueado.getRolLevel();
		Long rolIDUsuario = rol.getRolLevel();
        model.addAttribute("mapas", mapaService.getAllMapas());
        model.addAttribute("rolID", rolIDUsuario);
        return "listMapas";
    }
	
	@GetMapping("/addMapa")
	public String addMapa(Model model) {
		
		Usuarios userLogueado = userSession.getUser();
		Roles rol = userLogueado.getRolLevel();
		Long rolIDUsuario = rol.getRolLevel();
		
		model.addAttribute("mapa", new Mapas());
		model.addAttribute("companies", companyServicio.getAllCompany());
		model.addAttribute("rolID", rolIDUsuario);
		return "adminSuper/addMapa";
	}
	
	@PostMapping("/addMapaPost")
	public String addMapaPost(@RequestParam(name = "mapaNombre") String mapaNombre,
			@RequestParam(name = "salasTotales") int salasTotales,
	        @RequestParam(name = "companyId") Long companyId) {
		
		
		Mapas mapasNueva = new Mapas();
		Company company = companyServicio.getCompanyById(companyId);
		
		mapasNueva.setMapaNombre(mapaNombre);
		mapasNueva.setNumeroTotalDeSalas(salasTotales);
		mapasNueva.setCompanyMapa(company);
		
		mapaService.saveMapa(mapasNueva);
		
		
		return "redirect:/listMapas";
	}
	
	@GetMapping("/addSala")
	public String addSala(Model model) {
		
		Usuarios userLogueado = userSession.getUser();
		Roles rol = userLogueado.getRolLevel();
		Long rolIDUsuario = rol.getRolLevel();
		
		model.addAttribute("sala", new Salas());
		model.addAttribute("mapas", mapaService.getAllMapas());
		model.addAttribute("rolID", rolIDUsuario);
		
		return "adminSuper/addSala";
	}
	
	
	@PostMapping("/addSalaPost")
	public String addSalaPost(@RequestParam(name = "mapaId") Long mapaId,
			@RequestParam(name = "numeroFilas") int numeroFilas,
	        @RequestParam(name = "numeroColumnas") int numeroColumnas,
	        @RequestParam(name = "salaAsientosTotales") int salaAsientosTotales,
	        @RequestParam(name = "salaNumero") int salaNumero,
	        @RequestParam(name = "salaEstado") boolean salaEstado) {
		
		Salas nuevaSala = new Salas(); 
		Mapas mapa = mapaService.getMapaById(mapaId);
		
		nuevaSala.setMapaId(mapa);
		nuevaSala.setSalaAsientosTotales(salaAsientosTotales);
		nuevaSala.setSalaEstado(salaEstado);
		nuevaSala.setNumeroFilas(numeroFilas);
		nuevaSala.setNumeroColumnas(numeroColumnas);
		nuevaSala.setSalaNumero(salaNumero);
		
		
		
		salaService.saveSala(nuevaSala);
		
		 crearAsientos(nuevaSala, numeroFilas, numeroColumnas);
		
		return "redirect:/listMapas";
	}
	
	
	//CREACION DE ASIENTOS
	private void crearAsientos(Salas sala, int filas, int columnas) {
	    for (int fila = 1; fila <= filas; fila++) {
	        for (int columna = 1; columna <= columnas; columna++) {
	            Asientos asiento = new Asientos();
	            asiento.setSala(sala); 
	            asiento.setPosicionFila(fila);
	            asiento.setPosicionColumna(columna);
	            asiento.setAsientoEstado(true); 
	           
	            
	            asientoService.saveAsiento(asiento);
	        }
	    }
	}
	
	@GetMapping("/verAsientos/{salaId}")
	public String pintarAsientos(@PathVariable Long salaId, Model model) {
		Usuarios userLogueado = userSession.getUser();
		Roles rol = userLogueado.getRolLevel();
		Long rolIDUsuario = rol.getRolLevel();
		
		
	    Salas sala = salaService.getSalaById(salaId);
	    List<Asientos> asientos = sala.getAsientos();
	    
	    for (Asientos as:asientos) {
	    	System.out.println(as.toString());
	    }
	    model.addAttribute("sala", sala);
	    model.addAttribute("asientos", asientos);
	    model.addAttribute("rolID", rolIDUsuario);
	    return "adminSuper/verAsientos";
	}

	
	@GetMapping("/getSalas/{id}")
	public String getSalas(@PathVariable(name = "id") Long mapaId, Model model) {
		List<Salas> sala = mapaService.findSalasByMapaId(mapaId);
		Usuarios userLogueado = userSession.getUser();
		Roles rol = userLogueado.getRolLevel();
		Long rolIDUsuario = rol.getRolLevel();
		
//		for(Salas s:sala) {
//			System.out.println(s.toString());
//		}
		
//		System.out.println(mapaId);
		model.addAttribute("salas", sala);
		model.addAttribute("mapas", mapaService.getAllMapas());
		model.addAttribute("rolID", rolIDUsuario);
	    return "listMapas";
	}



}
