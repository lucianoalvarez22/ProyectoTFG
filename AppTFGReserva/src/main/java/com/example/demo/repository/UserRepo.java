package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.Company;
import com.example.demo.entity.Usuarios;

public interface UserRepo extends JpaRepository<Usuarios, Long> {
	//METODO LOGIN PARA COMPARAR EMAIL Y PASS
	Usuarios findByUserPasswordAndUserEmail(String userPassword, String userEmail); 
	
	 List<Usuarios> findByCompanyId(Company companyId);
	
	 
	 
	 //ESTO FUNCIONA
//	 @Query("SELECT u FROM Usuarios u WHERE u.companyId.companyId = :companyId")
//	    List<Usuarios> findByCompanyId(@Param("companyId") Long companyId);

	
	


}
