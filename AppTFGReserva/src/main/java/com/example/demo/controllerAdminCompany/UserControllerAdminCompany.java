package com.example.demo.controllerAdminCompany;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.componenteLogueado.UserSessionLog;
import com.example.demo.entity.Asientos;
import com.example.demo.entity.Company;
import com.example.demo.entity.Reservas;
import com.example.demo.entity.Roles;
import com.example.demo.entity.Salas;
import com.example.demo.entity.Usuarios;
import com.example.demo.serviceAdmin.IAsientosService;
import com.example.demo.serviceAdmin.ICargoServiceAdmin;
import com.example.demo.serviceAdmin.ICompanyServiceAdmin;
import com.example.demo.serviceAdmin.IRolServiceAdmin;
import com.example.demo.serviceAdmin.ISalasService;
import com.example.demo.serviceAdminCompany.IUserServiceAdminCompany;
import com.example.demo.serviceUser.IReservaService;

@Controller
public class UserControllerAdminCompany {
	
	@Autowired
	private IUserServiceAdminCompany userServiceAdminCompany;
	
	@Autowired
	private UserSessionLog userSession;
	
	
	@Autowired
	private ICompanyServiceAdmin companyServicio;
	@Autowired
	private ICargoServiceAdmin cargoServicio;
	
	@Autowired
	private IRolServiceAdmin rolServicio;
	
	@Autowired
	private IReservaService reservaServicio;
	
	
	@Autowired
	private IAsientosService asientoService;
	

	

	
	//SACAR LOS USUARIOS DE UNA DETERMINADA EMPRESA
		@GetMapping("/listUserAdminCompany")
		public String getUsersCompany(Model model) {
			
			Usuarios userLogueado = userSession.getUser();
//			Long companyId = userLogueado.getCompanyId().getCompanyId(); esto funciona
//			List<Usuarios> usuariosEmpresa = userServiceAdminCompany.getUsuariosByCompanyId(companyId); esto funciona
			
			 Company empresa = userLogueado.getCompanyId();
			 List<Usuarios> usuariosEmpresa = userServiceAdminCompany.getUsuariosByCompanyId(empresa);
			
			Roles rol = userLogueado.getRolLevel();
			Long rolIDUsuario = rol.getRolLevel();
			
			
			
			model.addAttribute("usuariosEmpresa", usuariosEmpresa);
			model.addAttribute("rolID", rolIDUsuario);
			

			return "listAllUser";
		}
		
		
		
		//RESERVAS ACTIVAS DE CADA USUARIO
		
		@GetMapping("/userReserva/{id}")
		public String getUserReservas(@PathVariable(name = "id") Long userId, Model model) {
			Usuarios userLogueado = userSession.getUser();
			Roles rol = userLogueado.getRolLevel();
			Long rolIDUsuario = rol.getRolLevel();
			
			Company empresa = userLogueado.getCompanyId();
			List<Usuarios> usuariosEmpresa = userServiceAdminCompany.getUsuariosByCompanyId(empresa);	
			List<Reservas> reservaByUser = reservaServicio.getReservasByUserId(userId);
		
		
		  
			
			model.addAttribute("reservaUser", reservaByUser);
			model.addAttribute("usuariosEmpresa", usuariosEmpresa);
			model.addAttribute("rolID", rolIDUsuario);
			return "listAllUser";
			
		}

		
		
		
		//ELIMINAR RESERVA 
		
		@GetMapping("/deleteReservaAdminCompany/{id}")
		public String deleteReserva(@PathVariable(name= "id") Long id, RedirectAttributes redirectAttributes) {
			Reservas reserva = reservaServicio.getReservaById(id);
			if (reserva != null) {
		        Asientos asiento = reserva.getAsiento();
		        if (asiento != null) {
		            asiento.setAsientoEstado(true); // Cambia el estado a libre
		            asientoService.saveAsiento(asiento); // Guarda el estado del asiento actualizado
		        }
		        reservaServicio.eliminarReserva(id); // Eliminar la reserva
		    }
		    redirectAttributes.addFlashAttribute("reservaEliminadaExito", "Reserva eliminada exitosamente");
		    return "redirect:/listUserAdminCompany";
			
		}
		
		
		
		
		
		@GetMapping("/addUsuarioByAdminCompany")
		public String addUsuariosByAdminCompany(Model model) {
			model.addAttribute("user", new Usuarios());
			model.addAttribute("companies", companyServicio.getAllCompany());
			model.addAttribute("cargos", cargoServicio.getAllCargos());
			return "adminCompany/addUsuarioByAdminCompany";
		}
		
		@PostMapping("/addUsuarioByAdminCompany")
		public String addUsuarioByAdminCompanyPOST(@RequestParam(name = "userName") String userName,
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
			
			Usuarios AddUsuarioByAdmin = new Usuarios();
			Company usuarioCompany = companyServicio.getCompanyById(companyId);
			Roles usuarioRol = rolServicio.getRolesById(rolID); 
			
			AddUsuarioByAdmin.setUserName(userName);
			AddUsuarioByAdmin.setUserPassword(userPassword);
			AddUsuarioByAdmin.setUserEmail(userEmail);
			AddUsuarioByAdmin.setUserTelefono(userTelefono);
			AddUsuarioByAdmin.setUserCiudad(userCiudad);
			AddUsuarioByAdmin.setCompanyId(usuarioCompany);
			AddUsuarioByAdmin.setRolLevel(usuarioRol);
			
			userServiceAdminCompany.guardarUsuario(AddUsuarioByAdmin);
			
			return "redirect:/listUserAdminCompany";
		}
		
		//EDITAR EMPRESAS
		
			@GetMapping("/editUserByAdminCompany/{id}")
			public String editCompany(@PathVariable(name = "id") Long id, Model model) {
				model.addAttribute("user", userServiceAdminCompany.getUserById(id));
				model.addAttribute("companies", companyServicio.getAllCompany());
				model.addAttribute("cargos", cargoServicio.getAllCargos());
				return "adminCompany/editUserByAdminCompany";
			}
			
			
			@PostMapping("/editUserByAdminCompany")
			public String postEditUserByAdmin(@RequestParam(name = "userName") String userName,
			        @RequestParam(name = "userEmail") String userEmail,
			        @RequestParam(name = "userPassword") String userPassword,
			        @RequestParam(name = "userConfirmPassword") String userConfirmPassword,
			        @RequestParam(name = "userTelefono") String userTelefono,
			        @RequestParam(name = "userCiudad") String userCiudad,
			        @RequestParam(name = "companyId") Long companyId,
			        @RequestParam(name = "idUser") Long userID,
			        @RequestParam(name = "rol_level") Long rolID) {
				
				//COMPRUEBO SI LA CONTRASEÑA ES IGUAL A LA CONTRASEÑA A CONFIRMAR
				if (!userPassword.equals(userConfirmPassword)) {
			        return "redirect:/addAdminCompany?error=passwordMismatch";
			    }
				
				
				Long idEdit = userID;
				String nameEdit = userName;
				String passEdit = userPassword;
				String emailEdit = userEmail;
				String teleEdit = userTelefono;
				String cityEdit = userCiudad;
				Company usuarioCompanyEdit = companyServicio.getCompanyById(companyId);
				Roles usuarioRolEdit = rolServicio.getRolesById(rolID); 
			
				
				Usuarios usuarioEdit = new Usuarios(idEdit,nameEdit,passEdit,emailEdit,teleEdit,cityEdit,usuarioCompanyEdit,usuarioRolEdit);
				userServiceAdminCompany.guardarUsuario(usuarioEdit);
				
				
				return "redirect:/listUserAdminCompany";
			}
			
			
			//DELETE USER
			
			@GetMapping("/deleteUser/{id}")
			public String deleteUser(@PathVariable(name= "id") Long id) {
				userServiceAdminCompany.eliminarUser(id);
				return "redirect:/listUserAdminCompany";
			}
			
			
			

}
