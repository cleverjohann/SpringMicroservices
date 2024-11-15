package com.microservice.envios.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "envios")
public class Envios {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "pedido_id", nullable = false)
    private Integer pedidoId;

    @Column(name = "usuario_id", nullable = false)
    private Integer usuarioId;

    @Size(max = 255)
    @NotNull
    @Column(name = "direccion_destino", nullable = false)
    private String direccionDestino;

    @Size(max = 100)
    @NotNull
    @Column(name = "transportista", nullable = false, length = 100)
    private String transportista;

    @Size(max = 50)
    @NotNull
    @ColumnDefault("'Pendiente'")
    @Column(name = "estado", nullable = false, length = 50)
    private String estado;

    @Column(name = "fecha_envio")
    private LocalDate fechaEnvio;

    @Column(name = "fecha_entrega")
    private LocalDate fechaEntrega;

}
