package com.example.demo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SessionConfig implements WebMvcConfigurer {
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor())
        .excludePathPatterns("/login", "/loginPost", "/conocenos.html", "/faq.html", "/contacto.html", "/init.html", "/css/**", "/js/**", "/img/**", "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg") // Excluir rutas estáticas
                .addPathPatterns("/**"); 
    }
	
	
 
}
