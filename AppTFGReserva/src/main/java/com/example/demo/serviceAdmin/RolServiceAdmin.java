package com.example.demo.serviceAdmin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Company;
import com.example.demo.entity.Roles;
import com.example.demo.repository.RolesRepo;

@Service
public class RolServiceAdmin implements IRolServiceAdmin{
	
	@Autowired
	private RolesRepo rolesRepository;

	@Override
	public Roles getRolesById(Long rolId) {
		Optional<Roles> companyOptional = rolesRepository.findById(rolId);
        return companyOptional.orElse(null);
	}
	
	@Override
	public List<Roles> getAllRoles() {
		return (List<Roles>) rolesRepository.findAll();
	}

}
