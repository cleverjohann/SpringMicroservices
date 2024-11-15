package com.microservice.pagos.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistorialPagosDto {
    private Integer pagoId;
    private Integer pedidoId;
    private Integer metodoPagoId;
    private BigDecimal monto;
    private LocalDate fechaPago;
    private String estadoPago;
    private Integer usuarioId;
    private Date fechaPedido;
    private Double montoTotal;
    private String estadoPedido;
    private String direccionEnvio;
}
