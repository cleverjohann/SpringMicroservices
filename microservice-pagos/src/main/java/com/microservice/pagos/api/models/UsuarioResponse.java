package com.microservice.pagos.api.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponse {
    private Integer id;
    private String nombres;
    private String apellidop;
    private String apellidom;
    private String email;
    private boolean cuentaVerificada;
}
