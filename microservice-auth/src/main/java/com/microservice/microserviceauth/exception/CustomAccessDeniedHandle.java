package com.microservice.microserviceauth.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomAccessDeniedHandle implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Map<String,Object> dataResponse = new HashMap<>();
        dataResponse.put("message","Acceso denegado");
        dataResponse.put("status",403);
        // Se establece el tipo de contenido
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // Se establece el estado de la respuesta
        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403
        // Se escribe la respuesta
        response.getOutputStream().println(new ObjectMapper().writeValueAsString(dataResponse)); //Se convierte el objeto a JSON
    }
}
