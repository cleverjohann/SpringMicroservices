package com.microservice.microservicepedido.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "carritos")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carritos_id_gen")
    @SequenceGenerator(name = "carritos_id_gen", sequenceName = "carritos_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @NotNull
    @Column(name = "fecha_creacion", nullable = false)
    private LocalDate fechaCreacion;

    @Size(max = 50)
    @ColumnDefault("'Activo'")
    @Column(name = "estado", length = 50)
    private String estado;

    @ColumnDefault("0")
    @Column(name = "subtotal", precision = 38, scale = 2)
    private BigDecimal subtotal;

    @ColumnDefault("0")
    @Column(name = "descuento", precision = 38, scale = 2)
    private BigDecimal descuento;

    @ColumnDefault("0")
    @Column(name = "total", precision = 38, scale = 2)
    private BigDecimal total;

}