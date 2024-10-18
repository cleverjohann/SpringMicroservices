package com.microservice.microserviceauth.model.dto;

import lombok.Data;

@Data
public class VerificarCuentaDto {

    private String correo;
    private String codigo;

}
