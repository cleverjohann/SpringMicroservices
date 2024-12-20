package com.microservice.microservicepedido.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "pedidos")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pedidos_id_gen")
    @SequenceGenerator(name = "pedidos_id_gen", sequenceName = "pedidos_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JsonIgnore
    @NotNull
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @NotNull
    @Column(name = "fecha_pedido", nullable = false)
    private LocalDate fechaPedido;

    @NotNull
    @Column(name = "monto_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal montoTotal;

    @Size(max = 50)
    @NotNull
    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @Size(max = 255)
    @Column(name = "direccion_envio")
    private String direccionEnvio;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "codigo_cupon")
    
    private Cupone codigoCupon;

    @ManyToOne
    @JoinColumn(name = "id_carrito")
    private Carrito idCarrito;

    @PrePersist
    public void prePersist() {
        this.estado = "PENDIENTE";
        this.fechaPedido = LocalDate.now();
    }

}