package com.microservice.microservicecarrito.client.response;

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