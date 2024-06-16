package com.example.demo.serviceAdmin;

import java.util.List;

import com.example.demo.entity.Company;
import com.example.demo.entity.Usuarios;

public interface ICompanyServiceAdmin {
	
	List<Company> getAllCompany();
	Company guardarCompany(Company company);
	Usuarios guardarAdminCompany(Usuarios adminCompany);
	Company getCompanyById(Long companyId);
	void eliminarCompany(Long id);
	List<Company> searchCompaniesByName(String name);

}
