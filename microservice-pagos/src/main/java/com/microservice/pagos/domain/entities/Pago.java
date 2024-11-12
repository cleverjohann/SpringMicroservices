package com.microservice.pagos.domain.entities;

import jakarta.persistence.*;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "pagos")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer pedidoId;

    private Integer metodoPagoId;

    private BigDecimal monto;

    private LocalDate fechaPago;

    @ColumnDefault("'Completado'")
    @Column(name = "estado", length = 50)
    private String estado;

}