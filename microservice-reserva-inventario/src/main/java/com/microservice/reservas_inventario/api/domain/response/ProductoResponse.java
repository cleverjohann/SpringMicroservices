package com.microservice.reservas_inventario.api.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductoResponse {
    private Integer id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private Boolean agotado;
    private CategoriaResponse categoria;
}
