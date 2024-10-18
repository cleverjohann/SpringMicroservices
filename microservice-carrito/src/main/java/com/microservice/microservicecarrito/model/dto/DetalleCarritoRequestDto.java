package com.microservice.microservicecarrito.model.dto;

import lombok.Data;

@Data
public class DetalleCarritoRequestDto {

    private String id_carrito;
    private String id_producto;
    private String cantidad;

}