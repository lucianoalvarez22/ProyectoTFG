package com.example.demo.serviceAdmin;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Company;
import com.example.demo.entity.Usuarios;
import com.example.demo.repository.CompanyRepo;
import com.example.demo.repository.UserRepo;

@Service
public class CompanyServiceAdmin implements ICompanyServiceAdmin {
	
	@Autowired
	private CompanyRepo companyRepository;
	
	@Autowired
	private UserRepo adminCompanyRepository;

	@Override
	public List<Company> getAllCompany() {
		return (List<Company>) companyRepository.findAll();
	}

	@Override
	public Company guardarCompany(Company company) {
		return companyRepository.save(company);
	}

	@Override
	public Usuarios guardarAdminCompany(Usuarios adminCompany) {
		return adminCompanyRepository.save(adminCompany);
	}

	@Override
	public Company getCompanyById(Long companyId) {
		Optional<Company> companyOptional = companyRepository.findById(companyId);
        return companyOptional.orElse(null);
	}

	@Override
	public void eliminarCompany(Long id) {
		companyRepository.deleteById(id);
		
	}

}
