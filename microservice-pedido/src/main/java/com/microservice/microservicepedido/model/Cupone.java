package com.microservice.microservicepedido.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "cupones")
public class Cupone {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cupones_id_gen")
    @SequenceGenerator(name = "cupones_id_gen", sequenceName = "cupones_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "codigo", nullable = false, length = 50)
    private String codigo;

    @NotNull
    @Column(name = "valor_descuento", nullable = false, precision = 12, scale = 2)
    private BigDecimal valorDescuento;

    @Size(max = 20)
    @ColumnDefault("'Activo'")
    @Column(name = "estado", length = 20)
    private String estado;

}