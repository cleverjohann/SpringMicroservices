package com.microservice.microservicepedido.model.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class PedidoDto {

    private String id_usuario;
    private BigDecimal montoTotal;
    private String estado;
    private String direccionEnvio;


}
