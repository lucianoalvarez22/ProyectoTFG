package com.example.demo.serviceAdminCompany;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Company;
import com.example.demo.repository.CompanyRepo;

@Service
public class CompanyServiceAdminComp implements ICompanyServiceAdminComp {
	
	@Autowired
	private CompanyRepo companyRepositorio;

	@Override
	public List<Company> getMyCompany(Long companyId) {
        return companyRepositorio.findByCompanyId(companyId);
	}

	@Override
	public List<Company> getAllCompany() {
		return (List<Company>) companyRepositorio.findAll();
	}

}
