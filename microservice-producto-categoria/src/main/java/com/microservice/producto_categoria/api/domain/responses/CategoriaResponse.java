package com.microservice.producto_categoria.api.domain.responses;

import lombok.Data;

@Data
public class CategoriaResponse {
    private Long id;
    private String nombre;
    private String descripcion;
}
