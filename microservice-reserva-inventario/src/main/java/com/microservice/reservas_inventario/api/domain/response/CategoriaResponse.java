package com.microservice.reservas_inventario.api.domain.response;
import lombok.Data;

@Data
public class CategoriaResponse {
    private Integer id;
    private String nombre;
    private String descripcion;
}
