package com.example.demo.serviceAdminCompany;

import java.util.List;

import com.example.demo.entity.Company;

public interface ICompanyServiceAdminComp {
	
	List<Company> getMyCompany(Long companyId);
	List<Company> getAllCompany();

}
