package com.example.demo.controllerAdmin;

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
	public String getAllCompany(Model model) {
		List<Company> companies = companyServicio.getAllCompany();
		
		Usuarios userLogueado = userSession.getUser();
		Roles rol = userLogueado.getRolLevel();
		Long rolIDUsuario = rol.getRolLevel();
		
		model.addAttribute("company", companies);
		model.addAttribute("rolID", rolIDUsuario);
		return "listCompanies";
	}
	
	@GetMapping("/addCompany")
	public String addCompany(Model model) {
		model.addAttribute("company", new Company());
		return "admin/addCompany";
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
		return "admin/addAdminCompany";
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
			model.addAttribute("company", companyServicio.getCompanyById(id));
			return "editCompany";
		}
		
		@PostMapping("/editCompany")
		public String postEditCompany(@RequestParam(name = "companyName") String companyName,
				@RequestParam(name = "companyIndustria") String companyIndustria, @RequestParam(name = "companyUbicacion") String companyUbicacion,
				@RequestParam(name = "companyTelefono") String companyTelefono, @RequestParam(name = "companyEmail") String companyEmail,
				Model model, @RequestParam(name = "idCompany") Long idCompany) {
			
			String nameEdit = companyName;
			String industriaEdi = companyIndustria;
			String ubicacionEdit = companyUbicacion;
			String telefonoEdit = companyTelefono;
			String emailEdit = companyEmail;
			Long idEdit = idCompany;
			
			Company companyEdit = new Company(idEdit, nameEdit, industriaEdi, ubicacionEdit, telefonoEdit, emailEdit);
			companyServicio.guardarCompany(companyEdit);
			return "redirect:/listCompany";
		}
		
		
		
		@GetMapping("/deleteCompany/{id}")
		public String deleteCompany(@PathVariable(name= "id") Long id) {
			companyServicio.eliminarCompany(id);
			return "redirect:/listCompany";
		}
	

}
