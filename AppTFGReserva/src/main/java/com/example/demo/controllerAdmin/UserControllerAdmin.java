package com.example.demo.controllerAdmin;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.componenteLogueado.UserSessionLog;
import com.example.demo.entity.Roles;
import com.example.demo.entity.Usuarios;
import com.example.demo.serviceAdmin.IUserServiceAdmin;

@Controller
public class UserControllerAdmin {
	
	@Autowired
	private IUserServiceAdmin userServicio;
	
	@Autowired
	private UserSessionLog userSession;
	
	//HOME
	@GetMapping("/home")
    public String home(Model model) {
		Usuarios userLogueado = userSession.getUser();
		Roles rol = userLogueado.getRolLevel();
		Long rolIDUsuario = rol.getRolLevel();
		model.addAttribute("rolID", rolIDUsuario);
        return "home";
    }

	
	//PROBANDO ALL USERS
	@GetMapping("/listUsers")
	public String getUsers(Model model) {
		List<Usuarios> users = userServicio.getAllUser();
		Usuarios userLogueado = userSession.getUser();
		Roles rol = userLogueado.getRolLevel();
		Long rolIDUsuario = rol.getRolLevel();
		
		model.addAttribute("users", users);
		model.addAttribute("rolID", rolIDUsuario);
		
		return "listAllUser";
	}
	



}
