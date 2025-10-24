package com.crud.backend.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfig() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Permitir CORS para todas las rutas
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE") // Permitir estos métodos HTTP
                        .allowedOrigins("http://localhost:5173"); // Permitir solicitudes desde este origen
            }
        };
    }
}
