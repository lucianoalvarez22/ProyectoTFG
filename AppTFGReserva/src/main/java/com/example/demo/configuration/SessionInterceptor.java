package com.example.demo.configuration;

import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SessionInterceptor implements HandlerInterceptor{
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userEmail") == null) {
            // El usuario no tiene una sesión activa, redirigir al login
            response.sendRedirect("/login");
            return false;
        }
        // El usuario tiene una sesión activa, permitir el acceso
        return true;
    }

}
