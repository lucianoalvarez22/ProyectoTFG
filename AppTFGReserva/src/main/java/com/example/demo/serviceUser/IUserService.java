package com.example.demo.serviceUser;

import java.util.List;

import com.example.demo.entity.Company;
import com.example.demo.entity.Usuarios;

public interface IUserService {
	
	List<Usuarios> getAllUsuarioGeneral();
	Usuarios getUsuarioGeneralById(Long userId);
	List<Usuarios> getUsuariosGeneralByCompanyId(Company companyId);

}
