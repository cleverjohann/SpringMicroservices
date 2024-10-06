package com.microservice.microserviceauth.model.dto;

import lombok.Data;

@Data
public class UsuarioResponseDto {

    private String nombres;
    private String apellidop;
    private String apellidom;
    private String email;
    private boolean cuenta_verificada;

}
