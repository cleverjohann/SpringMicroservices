package com.microservice.categoria.api.modeles.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CategoriaRequest {
    private String nombre;
    private String descripcion;
}
