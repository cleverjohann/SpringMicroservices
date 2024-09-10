package com.microservice.microserviceauth.exception;

public class NoTokenJwtException extends RuntimeException {

    public NoTokenJwtException(String message) {
        super(message);
    }
}
