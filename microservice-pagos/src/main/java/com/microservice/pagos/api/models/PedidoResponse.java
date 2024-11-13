package com.microservice.pagos.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoResponse {
    private Integer id;
    private Integer usuarioId;
    private LocalDate fechaPedido;
    private BigDecimal montoTotal;
    private String estado;
    private String direccionEnvio;
    private Integer codigoCuponId;
    private Integer carritoId;
}
