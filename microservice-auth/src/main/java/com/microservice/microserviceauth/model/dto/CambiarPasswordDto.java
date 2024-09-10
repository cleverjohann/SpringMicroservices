package com.microservice.microserviceauth.model.dto;

import lombok.Getter;

@Getter
public class CambiarPasswordDto extends VerificarCuentaDto {

    private String password;

}