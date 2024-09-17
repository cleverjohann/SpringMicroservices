package com.microservice.producto.api.models.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoRequest {
    @NotNull(message = "El nombre es requerido")
    private String nombre;
    @NotNull(message = "La descripción es requerida")
    private String descripcion;
    @NotNull(message = "El precio es requerido")
    private Double precio;
    @NotNull(message = "El stock es requerido")
    private Integer stock;
    @NotNull(message = "La categoría es requerida")
    private Long categoriaId;

    public @NotNull(message = "El nombre es requerido") String getNombre() {
        return nombre;
    }

    public void setNombre(@NotNull(message = "El nombre es requerido") String nombre) {
        this.nombre = nombre;
    }

    public @NotNull(message = "La descripción es requerida") String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(@NotNull(message = "La descripción es requerida") String descripcion) {
        this.descripcion = descripcion;
    }

    public @NotNull(message = "El stock es requerido") Integer getStock() {
        return stock;
    }

    public void setStock(@NotNull(message = "El stock es requerido") Integer stock) {
        this.stock = stock;
    }

    public @NotNull(message = "La categoría es requerida") Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(@NotNull(message = "La categoría es requerida") Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public @NotNull(message = "El precio es requerido") Double getPrecio() {
        return precio;
    }

    public void setPrecio(@NotNull(message = "El precio es requerido") Double precio) {
        this.precio = precio;
    }
}
