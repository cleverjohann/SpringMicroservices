package com.microservice.categoria.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categorias_p")
public class CategoriaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Size(max = 100)
    @NotNull
    @Column(name = "descripcion", nullable = false, length = 100)
    private String descripcion;

    public @Size(max = 100) @NotNull String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(@Size(max = 100) @NotNull String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @Size(max = 50) @NotNull String getNombre() {
        return nombre;
    }

    public void setNombre(@Size(max = 50) @NotNull String nombre) {
        this.nombre = nombre;
    }
}
