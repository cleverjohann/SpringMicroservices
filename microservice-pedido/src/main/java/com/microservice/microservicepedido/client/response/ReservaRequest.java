package com.microservice.microservicepedido.client.response;

import lombok.Data;

@Data
public class ReservaRequest {

    private Integer productoId;
    private int cantidad;
    private Integer pedidoId;

}