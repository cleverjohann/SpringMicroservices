package com.microservice.producto.domain.entities;

import com.microservice.categoria.domain.entities.CategoriaEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;

@Entity(name = "productos")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 50)
    @NotNull
    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Size(max = 100)
    @NotNull
    @Column(name = "descripcion", nullable = false, length = 100)
    private String descripcion;

    @NotNull
    @Column(name = "precio", nullable = false, precision = 12, scale = 2)
    private BigDecimal precio;

    @ColumnDefault("0")
    @Column(name = "stock")
    private Integer stock;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    private CategoriaEntity categoria;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @Size(max = 50) @NotNull String getNombre() {
        return nombre;
    }

    public void setNombre(@Size(max = 50) @NotNull String nombre) {
        this.nombre = nombre;
    }

    public @Size(max = 100) @NotNull String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(@Size(max = 100) @NotNull String descripcion) {
        this.descripcion = descripcion;
    }

    public @NotNull BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(@NotNull BigDecimal precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public CategoriaEntity getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaEntity categoria) {
        this.categoria = categoria;
    }
}
