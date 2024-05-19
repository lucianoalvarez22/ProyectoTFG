package com.example.demo.serviceAdminCompany;

import java.util.List;

import com.example.demo.entity.Company;
import com.example.demo.entity.Usuarios;

public interface IUserServiceAdminCompany {
	
	List<Usuarios> getAllUser();

	Usuarios getUserById(Long userId);
	
	List<Usuarios> getUsuariosByCompanyId(Company companyId);

	Usuarios guardarUsuario(Usuarios usuario);

	void eliminarUser(Long id);

}
