package com.microservice.reservas_inventario.api.domain.request;

import lombok.Data;

@Data
public class ReservaRequest {
    private Integer productoId;
    private int cantidad;
    private Integer pedidoId;
}
