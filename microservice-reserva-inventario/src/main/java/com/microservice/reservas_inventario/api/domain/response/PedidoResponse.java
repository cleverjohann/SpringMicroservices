package com.microservice.reservas_inventario.api.domain.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
public class PedidoResponse {
    private Integer id;
    private LocalDate fechaPedido;
    private BigDecimal montoTotal;
    private String estado;
    private String direccionEnvio;
    private Integer usuarioId;
}
