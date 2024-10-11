package com.microservice.microservicepedido.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class CarritoResponseDto {

    private String id_carrito;
    private String usuario;
    private LocalDate fechaCreacion;
    private String estado;
    private BigDecimal subtotal;
    private BigDecimal descuento;
    private BigDecimal total;

}
