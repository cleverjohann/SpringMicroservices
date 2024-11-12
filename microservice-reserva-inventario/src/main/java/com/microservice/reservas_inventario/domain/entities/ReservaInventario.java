package com.microservice.reservas_inventario.domain.entities;

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
@Table(name = "reservas_inventario")
public class ReservaInventario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private Integer productoId;

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    private Integer pedidoId;

    @NotNull
    @Column(name = "fecha_reserva", nullable = false)
    private LocalDate fechaReserva;

    @Size(max = 20)
    @ColumnDefault("'Pendiente'")
    @Column(name = "estado", length = 20)
    private String estado;

}
