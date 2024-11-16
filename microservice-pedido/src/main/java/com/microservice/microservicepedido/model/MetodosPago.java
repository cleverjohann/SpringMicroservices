package com.microservice.microservicepedido.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "metodos_pago")
@AllArgsConstructor
@NoArgsConstructor
public class MetodosPago {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "metodos_pago_id_gen")
    @SequenceGenerator(name = "metodos_pago_id_gen", sequenceName = "metodos_pago_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Size(max = 100)
    @Column(name = "descripcion", length = 100)
    private String descripcion;

}