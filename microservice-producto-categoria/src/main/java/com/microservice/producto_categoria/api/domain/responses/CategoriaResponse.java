package com.microservice.producto_categoria.api.domain.responses;

import lombok.Data;

@Data
public class CategoriaResponse {
    private Integer id;
    private String nombre;
    private String descripcion;
}
