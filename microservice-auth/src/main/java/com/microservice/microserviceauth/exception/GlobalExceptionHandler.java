package com.microservice.microserviceauth.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

//Con esta anotación se indica que la clase es un controlador de excepciones
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Map<String, Object> response = new HashMap<>();

    @ExceptionHandler(NoTokenJwtException.class)
    public ResponseEntity<Map<String, Object>> handleNoTokenJwtException(NoTokenJwtException ex) {
        return getMapResponseEntity(ex.getMessage());
    }

    @ExceptionHandler(TokenExpiradoException.class)
    public ResponseEntity<Map<String, Object>> handleTokenExpiradoException(TokenExpiradoException ex) {
        return getMapResponseEntity(ex.getMessage());
    }

    @ExceptionHandler(FirmaException.class)
    public ResponseEntity<Map<String, Object>> handleFirmaException(FirmaException ex) {
        return getMapResponseEntity(ex.getMessage());
    }

    @ExceptionHandler(EntidadNoExisteException.class)
    public ResponseEntity<Map<String, Object>> handleEntidadNoExisteException(EntidadNoExisteException ex) {
        return getMapResponseEntity(ex.getMessage());
    }

    @ExceptionHandler(DatoNoIngresadoException.class)
    public ResponseEntity<Map<String, Object>> handleDatoNoIngresadoException(DatoNoIngresadoException ex) {
        return getMapResponseEntity(ex.getMessage());
    }

    private static ResponseEntity<Map<String, Object>> getMapResponseEntity(String message) {
        response.clear();
        response.put("message", message);
        response.put("status", HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}