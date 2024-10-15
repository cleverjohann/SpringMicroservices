package com.microservice.microservicepedido.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleCarritoDto {

    private Integer id;
    private Integer id_carrito;
    private Integer id_producto;
    private Integer cantidad;

}