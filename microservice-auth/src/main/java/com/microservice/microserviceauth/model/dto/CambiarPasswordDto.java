package com.microservice.microserviceauth.model.dto;

import lombok.Data;

@Data
public class CambiarPasswordDto extends VerificarCuentaDto {

    private String password;

}