package com.microservice.producto_categoria.api.domain.responses;

import lombok.Data;

@Data
public class ProductoResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private CategoriaResponse categoria;
}
