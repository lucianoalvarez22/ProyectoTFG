package com.example.demo.controllerAdmin;

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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
	
	@Autowired
	private IUserServiceAdmin userLoginService;
	
	@Autowired
	private UserSessionLog userSession;
	
	
	
	
		//GET LOGIN
		@GetMapping("/login")
	    public String loginForm(HttpSession session) {
			if (session.getAttribute("userEmail") != null) {
		        return "redirect:/home";
		    }
	        return "login"; 
	    }
		
		@PostMapping("/loginPost")
		public String login(@RequestParam(name = "userEmail") String userEmail, @RequestParam(name = "userPassword") String userPassword, Model model, HttpSession session) {
			
			Optional<Usuarios> user = userLoginService.verificaLogin(userEmail, userPassword); 
			
			//System.out.println(user.isPresent() ? user.get() : "Empty");
			
			if(user.isPresent()) {
				Usuarios userLogueado = user.get();
				userSession.setUser(userLogueado);
				
				// System.out.println("Usuario guardado en UserSessionLog: " + userLogueado);
				session.setAttribute("userEmail", userEmail); 
				return "redirect:/home";
			} else {
				model.addAttribute("error", "Credenciales inválidas");
	            return "login";
			}
		}
		
		
		@GetMapping("/logout")
	    public String logout(HttpServletRequest request) {
	        HttpSession session = request.getSession(false);
	        if (session != null) {
	            session.invalidate(); 
	        }
	        return "redirect:/login"; 
	    }
		
		
		@PostMapping("/logout")
	    public String logoutPost(HttpServletRequest request, HttpSession session) {
	        if (session != null) {
	            session.invalidate(); 
	        }
	        return "redirect:/login"; 
	    }
		
		
		
		

}
