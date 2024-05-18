package com.example.demo.componenteLogueado;


import org.springframework.stereotype.Component;

import com.example.demo.entity.Usuarios;


@Component
public class UserSessionLog {
	

	 private Usuarios user;
	 
	 
	 public Usuarios getUser() {
	        return user;
	    }

	    public void setUser(Usuarios user) {
	        this.user = user;
	    }

	

}
