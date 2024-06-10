package com.example.demo.controllerAdminCompany;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.componenteLogueado.UserSessionLog;
import com.example.demo.entity.Company;
import com.example.demo.entity.Roles;
import com.example.demo.entity.Usuarios;
import com.example.demo.serviceAdminCompany.ICompanyServiceAdminComp;
import com.example.demo.serviceUser.IUserService;

@Controller
public class CompanyControllerAdminCompany {
	
	@Autowired
    private IUserService userService;
	
	@Autowired
	private UserSessionLog userSession;
	
	@Autowired
	private ICompanyServiceAdminComp companyServiceAdmComp;
	
	
	@GetMapping("/myCompany")
	public String getMyCompany(Model model) {
		
		Usuarios userLogueado = userSession.getUser();
		Company empresa = userLogueado.getCompanyId();
		Roles rol = userLogueado.getRolLevel();
		Long rolIDUsuario = rol.getRolLevel();
		
		Long idCompany = empresa.getCompanyId();
		
		List<Company> listMyCompany = companyServiceAdmComp.getMyCompany(idCompany);
		
		
		
		model.addAttribute("myCompany", listMyCompany);
		model.addAttribute("rolID", rolIDUsuario);
		
		return "listCompanies";
	}
	
	@GetMapping("/myPerfilAdminCompany")
    public String myPerfilUser(Model model) {

        Usuarios userLogueado = userSession.getUser();
        Long userId = userLogueado.getUserId();

        Roles rol = userLogueado.getRolLevel();
        Long rolIDUsuario = rol.getRolLevel();

        Usuarios myPerfilUser = userService.getUsuarioGeneralById(userId); 

        model.addAttribute("usuarioGeneral", myPerfilUser);
        model.addAttribute("rolID", rolIDUsuario);

        return "adminCompany/myPerfil";
    }

}
