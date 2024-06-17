package com.example.demo.controllerAdmin;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.componenteLogueado.UserSessionLog;
import com.example.demo.entity.CargoName;
import com.example.demo.entity.Company;
import com.example.demo.entity.Roles;
import com.example.demo.entity.Usuarios;
import com.example.demo.serviceAdmin.ICargoServiceAdmin;
import com.example.demo.serviceAdmin.ICompanyServiceAdmin;
import com.example.demo.serviceAdmin.IRolServiceAdmin;

@Controller
public class CompanyControllerAdmin {
	
	@Autowired
	private ICompanyServiceAdmin companyServicio;
	
	@Autowired
	private ICargoServiceAdmin cargoServicio;
	
	@Autowired
	private IRolServiceAdmin rolServicio;
	
	@Autowired
	private UserSessionLog userSession;
	
	@GetMapping("/listCompany")
	public String getAllCompany(@RequestParam(value = "search", required = false) String search, Model model) {
		
		List<Company> companies;
	    if (search != null && !search.isEmpty()) {
	        companies = companyServicio.searchCompaniesByName(search);
	    } else {
	        companies = companyServicio.getAllCompany();
	    }
	
		
		Usuarios userLogueado = userSession.getUser();
		Roles rol = userLogueado.getRolLevel();
		Long rolIDUsuario = rol.getRolLevel();
		
		model.addAttribute("company", companies);
		model.addAttribute("rolID", rolIDUsuario);
		return "listCompanies";
	}
	
	@GetMapping("/addCompany")
	public String addCompany(Model model) {
		
		Usuarios userLogueado = userSession.getUser();
		Roles rol = userLogueado.getRolLevel();
		Long rolIDUsuario = rol.getRolLevel();
		
		model.addAttribute("company", new Company());
		model.addAttribute("rolID", rolIDUsuario);
		return "adminSuper/addCompany";
	}
	
	
	@PostMapping("/addComp") 
	public String postAdd(@ModelAttribute("company") Company company) {
	    companyServicio.guardarCompany(company); 
	    return "redirect:/listCompany"; 
	}
	
	@GetMapping("/addAdminCompany")
	public String addAdminCompany(Model model) {
		
		model.addAttribute("adminCompany", new Usuarios());
		model.addAttribute("cargos", cargoServicio.getAllCargos());
		model.addAttribute("companies", companyServicio.getAllCompany());
		return "adminSuper/addAdminCompany";
	}
	
	@PostMapping("/addAdminCompany")
	public String postAddAdminCompany(
			@RequestParam(name = "userName") String userName,
	        @RequestParam(name = "userEmail") String userEmail,
	        @RequestParam(name = "userPassword") String userPassword,
	        @RequestParam(name = "userConfirmPassword") String userConfirmPassword,
	        @RequestParam(name = "userTelefono") String userTelefono,
	        @RequestParam(name = "userCiudad") String userCiudad,
	        @RequestParam(name = "companyId") Long companyId,
	        @RequestParam(name = "rol_level") Long rolID) {
		
		//COMPRUEBO SI LA CONTRASEÑA ES IGUAL A LA CONTRASEÑA A CONFIRMAR
		if (!userPassword.equals(userConfirmPassword)) {
	        return "redirect:/addAdminCompany?error=passwordMismatch";
	    }
		
		
		Usuarios usuarioAdmin = new Usuarios();
		Company company = companyServicio.getCompanyById(companyId);
	
	    Roles rolAdmin = rolServicio.getRolesById(rolID);

	    usuarioAdmin.setUserName(userName);
	    usuarioAdmin.setUserEmail(userEmail);
	    usuarioAdmin.setUserPassword(userPassword);
	    usuarioAdmin.setUserTelefono(userTelefono);
	    usuarioAdmin.setUserCiudad(userCiudad);
	    
	    usuarioAdmin.setCompanyId(company);
	   
	    usuarioAdmin.setRolLevel(rolAdmin);
	    

	    companyServicio.guardarAdminCompany(usuarioAdmin);
		return "redirect:/listCompany";
	}
	
	//EDITAR EMPRESAS
	
		@GetMapping("/editCompany/{id}")
		public String editCompany(@PathVariable(name = "id") Long id, Model model) {
			Usuarios userLogueado = userSession.getUser();
			Roles rol = userLogueado.getRolLevel();
			Long rolIDUsuario = rol.getRolLevel();
			
			model.addAttribute("company", companyServicio.getCompanyById(id));
			model.addAttribute("rolID", rolIDUsuario);
			return "adminSuper/editCompany";
		}
		
		@PostMapping("/editCompany")
		public String postEditCompany(@RequestParam(name = "companyName") String companyName,
				@RequestParam(name = "companyIndustria") String companyIndustria, @RequestParam(name = "companyUbicacion") String companyUbicacion,
				@RequestParam(name = "companyTelefono") String companyTelefono, @RequestParam(name = "companyEmail") String companyEmail,
				@RequestParam(name = "horaApertura") String horaApertura,
		        @RequestParam(name = "horaCierre") String horaCierre,
				Model model, @RequestParam(name = "idCompany") Long idCompany) {
			
			 // Recuperar la entidad Company desde la base de datos
		    Company companyEdit = companyServicio.getCompanyById(idCompany);
		    if (companyEdit == null) {
		        // Manejar el caso en que la empresa no se encuentre
		        return "redirect:/listCompany?error=notfound";
		    }
		    
		 // Actualizar los campos necesarios
		    companyEdit.setCompanyName(companyName);
		    companyEdit.setCompanyIndustria(companyIndustria);
		    companyEdit.setCompanyUbicacion(companyUbicacion);
		    companyEdit.setCompanyTelefono(companyTelefono);
		    companyEdit.setCompanyEmail(companyEmail);
		    companyEdit.setHoraApertura(LocalTime.parse(horaApertura));
		    companyEdit.setHoraCierre(LocalTime.parse(horaCierre));
		    
		 // Mantener la colección de usuarios referenciada (si no se está actualizando)
		    // Asegúrate de que la colección no se pierda
		    // companyEdit.setUsuarios(existingUsuarios);
			
			
		    
		 // Guardar la entidad actualizada
		    companyServicio.guardarCompany(companyEdit);
			return "redirect:/listCompany";
		}
		
		
		
		@GetMapping("/deleteCompany/{id}")
		public String deleteCompany(@PathVariable(name= "id") Long id) {
			companyServicio.eliminarCompany(id);
			return "redirect:/listCompany";
		}
	

}
