package com.microservice.microservicepedido.model.dto;

import lombok.Data;

@Data
public class PedidoDto {

    private String id_usuario;
    private String id_carrito;
    private String direccionEnvio;
    private String codigoCupon;
    private String descripcion_metodo_pago;
    private String nombre_metodo_pago;

}