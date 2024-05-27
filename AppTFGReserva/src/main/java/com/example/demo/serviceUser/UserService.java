package com.example.demo.serviceUser;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Company;
import com.example.demo.entity.Usuarios;
import com.example.demo.repository.UserRepo;

@Service
public class UserService implements IUserService {
	
	@Autowired
	private UserRepo userGeneralRepo;

	@Override
	public List<Usuarios> getAllUsuarioGeneral() {
		return userGeneralRepo.findAll();
	}

	@Override
	public Usuarios getUsuarioGeneralById(Long userId) {
		Optional<Usuarios> userGeneralOptional = userGeneralRepo.findById(userId);
        return userGeneralOptional.orElse(null);
	}

	@Override
	public List<Usuarios> getUsuariosGeneralByCompanyId(Company companyId) {
		return userGeneralRepo.findByCompanyId(companyId);
	}

}
