package com.example.demo.serviceAdminCompany;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Company;
import com.example.demo.entity.Usuarios;
import com.example.demo.repository.UserRepo;

@Service
public class UserServiceAdminCompany implements IUserServiceAdminCompany {
	
	@Autowired
	private UserRepo userRepositoryAdmin;
	



	@Override
	public List<Usuarios> getAllUser() {
		return (List<Usuarios>)userRepositoryAdmin.findAll();  
	}

	@Override
	public Usuarios getUserById(Long userId) {
		Optional<Usuarios> userOptional = userRepositoryAdmin.findById(userId);
        return userOptional.orElse(null);
	}
	
	@Override
	public List<Usuarios> getUsuariosByCompanyId(Company companyId) {
		return userRepositoryAdmin.findByCompanyId(companyId);
	    }
	
	@Override
	public Usuarios guardarUsuario(Usuarios usuario) {
		return userRepositoryAdmin.save(usuario);
	}

	@Override
	public void eliminarUser(Long id) {
		userRepositoryAdmin.deleteById(id);
		
	}

}
