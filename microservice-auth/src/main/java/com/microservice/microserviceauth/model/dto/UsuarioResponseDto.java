package com.microservice.microserviceauth.model.dto;

import lombok.Setter;

@Setter
public class UsuarioResponseDto {

    private String nombres;
    private String apellidop;
    private String apellidom;
    private String email;
    private boolean cuenta_verificada;

}
