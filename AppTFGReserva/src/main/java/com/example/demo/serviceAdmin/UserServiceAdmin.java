package com.example.demo.serviceAdmin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Usuarios;
import com.example.demo.repository.UserRepo;

@Service
public class UserServiceAdmin implements IUserServiceAdmin {
	
	@Autowired
	private UserRepo userRepositoryAdminCompany;

	@Override
	public List<Usuarios> getAllUser() {
		return (List<Usuarios>)userRepositoryAdminCompany.findAll();  
	}

	@Override
	public Optional<Usuarios> verificaLogin(String userPassword, String userEmail) {
		Usuarios user = userRepositoryAdminCompany.findByUserPasswordAndUserEmail(userEmail, userPassword);
        return Optional.ofNullable(user);
	}

}
