package com.microservice.microservicecarrito.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "carritos")

public class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carritos_id_gen")
    @SequenceGenerator(name = "carritos_id_gen", sequenceName = "carritos_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JsonIgnore
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

    private BigDecimal subtotal;

    private BigDecimal descuento;

    private BigDecimal total;


    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDate.now();
        this.estado = "Activo";
        this.subtotal = BigDecimal.ZERO;
        this.descuento = BigDecimal.ZERO;
        this.total = BigDecimal.ZERO;
    }

}