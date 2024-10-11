package com.microservice.microservicepedido.model.dto;

import lombok.Data;

@Data
public class PedidoResponseDto {

    private String id;
    private String usuario;
    private String fechaPedido;
    private String montoTotal;
    private String estado;
    private String direccionEnvio;
    private String codigoCupon;

}