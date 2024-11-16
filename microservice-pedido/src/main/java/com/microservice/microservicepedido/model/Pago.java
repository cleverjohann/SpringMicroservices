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
@Table(name = "pagos")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pagos_id_gen")
    @SequenceGenerator(name = "pagos_id_gen", sequenceName = "pagos_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @NotNull
    @Column(name = "monto", nullable = false, precision = 38, scale = 2)
    private BigDecimal monto;

    @NotNull
    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;

    @Size(max = 50)
    @ColumnDefault("'Completado'")
    @Column(name = "estado", length = 50)
    private String estado;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "metodo_pago_id", nullable = false)
    private MetodosPago metodoPago;

    @PrePersist
    public void prePersist() {
        this.fechaPago = LocalDate.now();
    }

}