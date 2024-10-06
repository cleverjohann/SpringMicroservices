package com.microservice.microservicecarrito.model.dto;

import lombok.Getter;

@Getter
public class DetalleCarritoRequestDto {

    private String id_carrito;
    private String id_producto;
    private String cantidad;

}