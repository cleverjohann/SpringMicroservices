package com.microservice.microserviceusuarios.entities.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioUpdateDto {

    private String nombres;
    private String apellidop;
    private String apellidom;
    private String email;
    private String password;
    private Boolean cuenta_verificada;
    private Boolean activo;

}