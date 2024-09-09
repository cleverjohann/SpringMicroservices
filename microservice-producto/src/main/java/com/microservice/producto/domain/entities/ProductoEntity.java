package com.microservice.producto.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "productos")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String descripcion;

    @Column(nullable = false, precision = 12, scale = 2)
    private Double precio;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer stock;

    @Column(name = "categoria_id", nullable = false)
    private Integer categoriaId;
}
