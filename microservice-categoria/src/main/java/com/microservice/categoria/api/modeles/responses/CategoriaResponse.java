package com.microservice.categoria.api.modeles.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CategoriaResponse {
    private Integer id;
    private String nombre;
    private String descripcion;
}
