package com.microservice.microserviceauth.exception;

public class DatoNoIngresadoException extends RuntimeException {

    public DatoNoIngresadoException(String mensaje) {
        super(mensaje);
    }
}
