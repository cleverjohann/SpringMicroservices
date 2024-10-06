package com.microservice.microservicepedido.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.java.Log;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Log
public class FeignClientInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // Obtener la autenticación del contexto de seguridad
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("authentication: " + authentication.getName());
        if (authentication != null && authentication.getCredentials() != null) {
            // Suponiendo que las credenciales son el token JWT
            String token = (String) authentication.getCredentials();

            // Agregar el token JWT al encabezado Authorization
            requestTemplate.header("Authorization", "Bearer " + token);
        }
    }
}
