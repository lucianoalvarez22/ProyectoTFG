package com.example.demo.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuarios {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "user_name")
    private String userName;
    
    @Column(name = "user_password")
    private String userPassword;
    
    @Column(name = "user_email")
    private String userEmail;
    
    @Column(name = "user_telefono")
    private String userTelefono;
    
    @Column(name = "user_ciudad")
    private String userCiudad;
    
    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "company_id")
    private Company companyId;
    
    @ManyToOne
    @JoinColumn(name = "rol_level", referencedColumnName = "rol_level")
    private Roles rolLevel;
    
    @OneToMany(mappedBy = "usuario")
    private List<Reservas> reservas;

    
    //CONSTRUCTORES 
    
    public Usuarios(Long userId, String userName, String userPassword, String userEmail, String userTelefono,
    		String userCiudad, Company companyId, Roles rolLevel) {
    	this.userId = userId;
    	this.userName = userName;
    	this.userPassword = userPassword;
    	this.userEmail = userEmail;
    	this.userTelefono = userTelefono;
    	this.userCiudad = userCiudad;
    	this.companyId = companyId;
    	this.rolLevel = rolLevel;
    }
    
    

    public Usuarios() {
}



	// GET AND SET

	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserTelefono() {
		return userTelefono;
	}

	public void setUserTelefono(String userTelefono) {
		this.userTelefono = userTelefono;
	}

	public String getUserCiudad() {
		return userCiudad;
	}

	public void setUserCiudad(String userCiudad) {
		this.userCiudad = userCiudad;
	}

	public Company getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Company companyId) {
		this.companyId = companyId;
	}

	public Roles getRolLevel() {
		return rolLevel;
	}

	public void setRolLevel(Roles rolLevel) {
		this.rolLevel = rolLevel;
	}



	public List<Reservas> getReservas() {
		return reservas;
	}



	public void setReservas(List<Reservas> reservas) {
		this.reservas = reservas;
	}
	
	
	
	

}

