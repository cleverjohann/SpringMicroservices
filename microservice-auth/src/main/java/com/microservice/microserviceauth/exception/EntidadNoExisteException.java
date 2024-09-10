package com.microservice.microserviceauth.exception;

public class EntidadNoExisteException extends RuntimeException {

    public EntidadNoExisteException(String message) {
        super(message);
    }
}