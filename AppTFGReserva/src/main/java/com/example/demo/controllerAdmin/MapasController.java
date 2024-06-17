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
    public String listMapas(@RequestParam(value = "search", required = false) String search, Model model) {
		
		List<Mapas> mapas;
	    if (search != null && !search.isEmpty()) {
	        mapas = mapaService.searchMapasByName(search);
	    } else {
	        mapas = mapaService.getAllMapas();
	    }
		
		Usuarios userLogueado = userSession.getUser();
		Roles rol = userLogueado.getRolLevel();
		Long rolIDUsuario = rol.getRolLevel();
        model.addAttribute("mapas", mapas);
        model.addAttribute("search", search);
        model.addAttribute("rolID", rolIDUsuario);
        return "listMapas";
    }
	
	
	@GetMapping("/getSalas/{id}")
	public String getSalas(@PathVariable(name = "id") Long mapaId,  @RequestParam(value = "search", required = false) String search, Model model) {
		List<Salas> sala = mapaService.findSalasByMapaId(mapaId);
		
		List<Mapas> mapas;
	    if (search != null && !search.isEmpty()) {
	        mapas = mapaService.searchMapasByName(search);
	    } else {
	        mapas = mapaService.getAllMapas();
	    }
	    
		Usuarios userLogueado = userSession.getUser();
		Roles rol = userLogueado.getRolLevel();
		Long rolIDUsuario = rol.getRolLevel();

		model.addAttribute("salas", sala);
		model.addAttribute("mapas", mapas);
		model.addAttribute("search", search);
		model.addAttribute("rolID", rolIDUsuario);
	    return "listMapas";
	}
	
	//BORRAR MAPA DEL PERFIL DE ADMIN SUPER
	@GetMapping("/deleteMapa/{id}")
	public String deleteMapa(@PathVariable(name= "id") Long id) {
		mapaService.deleteMapa(id);
		return "redirect:/listMapas";
	}
	
	//BORRAR SALA DEL PERFIL DE ADMIN SUPER
		@GetMapping("/deleteSala/{id}")
		public String deleteSala(@PathVariable(name= "id") Long id) {
			salaService.deleteSala(id);
			return "redirect:/listMapas";
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
	
	//EDITAR MAPAS 
	@GetMapping("/editMapa/{id}")
	public String editMapa(@PathVariable(name = "id") Long mapaId, Model model) {
		Usuarios userLogueado = userSession.getUser();
		Roles rol = userLogueado.getRolLevel();
		Long rolIDUsuario = rol.getRolLevel();
		
		model.addAttribute("mapa", mapaService.getMapaById(mapaId));
		model.addAttribute("companies", companyServicio.getAllCompany());
		model.addAttribute("rolID", rolIDUsuario);
		return "adminSuper/editMapa";
	}
	
	
	@PostMapping("/editMapa")
	public String editMapaPost(@RequestParam(name = "mapaNombre") String mapaNombre,
			@RequestParam(name = "numeroTotalDeSalas") int salasTotales,
	        @RequestParam(name = "companyId") Long companyId,
	        @RequestParam(name = "mapaId") Long mapaId) {
		
		 // Recuperar la entidad Mapas desde la base de datos
	    Mapas mapaEditado = mapaService.getMapaById(mapaId);
	    if (mapaEditado == null) {
	        // Manejar el caso en que el mapa no se encuentre
	        return "redirect:/listMapas?error=notfound";
	    }
	    
	 // Recuperar la entidad Company desde la base de datos
	    Company company = companyServicio.getCompanyById(companyId);
	    if (company == null) {
	        // Manejar el caso en que la empresa no se encuentre
	        return "redirect:/listMapas?error=companynotfound";
	    }
	    
	    
	    mapaEditado.setMapaNombre(mapaNombre);
	    mapaEditado.setNumeroTotalDeSalas(salasTotales);
	    mapaEditado.setCompanyMapa(company);
	    
	 // Mantener la colección de salas y reservas referenciadas
	    // Si no se está actualizando, asegúrate de que las colecciones no se pierdan
	    // mapaEditado.setSalas(existingSalas);
	    // mapaEditado.setReservas(existingReservas);
		
		mapaService.saveMapa(mapaEditado);
		return "redirect:/listMapas";
	}
	
	
	
	//SALAS
	
	
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
	
	
	//EDITAR SALA
	
		@GetMapping("/editSala/{id}")
		public String editSala(@PathVariable(name = "id") Long salaId, Model model) {
			Usuarios userLogueado = userSession.getUser();
			Roles rol = userLogueado.getRolLevel();
			Long rolIDUsuario = rol.getRolLevel();
			
			model.addAttribute("sala", salaService.getSalaById(salaId));
			model.addAttribute("mapas", mapaService.getAllMapas());
			model.addAttribute("rolID", rolIDUsuario);
			return "adminSuper/editSala";
		}
	
		
		@PostMapping("/editSala")
		public String editSalaPost(@RequestParam(name = "mapaId") Long mapaId,
				@RequestParam(name = "numeroFilas") int numeroFilas,
		        @RequestParam(name = "numeroColumnas") int numeroColumnas,
		        @RequestParam(name = "salaAsientosTotales") int salaAsientosTotales,
		        @RequestParam(name = "salaNumero") int salaNumero,
		        @RequestParam(name = "salaEstado") boolean salaEstado,
		        @RequestParam(name = "salaId") Long salaId) {
			
			// Recuperar la entidad Salas desde la base de datos
		    Salas salaEdit = salaService.getSalaById(salaId);
		    if (salaEdit == null) {
		        // Manejar el caso en que la sala no se encuentre
		        return "redirect:/listMapas?error=notfound";
		    }

		    // Recuperar la entidad Mapas desde la base de datos
		    Mapas mapaEdit = mapaService.getMapaById(mapaId);
		    if (mapaEdit == null) {
		        // Manejar el caso en que el mapa no se encuentre
		        return "redirect:/listMapas?error=mapanotfound";
		    }
		    
		    // Actualizar los campos necesarios
		    salaEdit.setMapaId(mapaEdit);
		    salaEdit.setNumeroFilas(numeroFilas);
		    salaEdit.setNumeroColumnas(numeroColumnas);
		    salaEdit.setSalaAsientosTotales(salaAsientosTotales);
		    salaEdit.setSalaNumero(salaNumero);
		    salaEdit.setSalaEstado(salaEstado);
			
			
			salaService.saveSala(salaEdit);
	
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
	    
	    long asientosLibres = asientos.stream().filter(Asientos::isAsientoEstado).count();
	    long asientosOcupados = asientos.size() - asientosLibres;
	    
	    String companyName = sala.getMapaId().getCompanyMapa().getCompanyName();	    

	    model.addAttribute("sala", sala);
	    model.addAttribute("asientos", asientos);
	    model.addAttribute("asientosLibres", asientosLibres);
	    model.addAttribute("asientosOcupados", asientosOcupados);
	    model.addAttribute("rolID", rolIDUsuario);
	    model.addAttribute("companyName", companyName);
	    return "adminSuper/verAsientos";
	}

	
	



}
