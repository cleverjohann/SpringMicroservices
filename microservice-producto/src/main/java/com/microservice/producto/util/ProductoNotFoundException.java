package com.microservice.producto.util;

public class ProductoNotFoundException extends RuntimeException {
    public ProductoNotFoundException(Long id) {
        super("Producto not found with id: " + id);
    }
}
