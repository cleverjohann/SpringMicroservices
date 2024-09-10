package com.microservice.categoria.util;

public class CategoriaNotFoundException extends RuntimeException {
    public CategoriaNotFoundException(String id) {
        super("Categoia not found with id: " + id);
    }
}
