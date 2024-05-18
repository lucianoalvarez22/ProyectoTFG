package com.example.demo.serviceAdmin;

import java.util.List;

import com.example.demo.entity.Roles;

public interface IRolServiceAdmin {
	
	Roles getRolesById(Long id);

	List<Roles> getAllRoles();
}
