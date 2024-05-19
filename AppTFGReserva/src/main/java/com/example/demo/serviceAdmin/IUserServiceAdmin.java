package com.example.demo.serviceAdmin;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Usuarios;

public interface IUserServiceAdmin {
	
	List<Usuarios> getAllUser();
	Optional<Usuarios> verificaLogin(String userPassword, String userEmail);
	

}
