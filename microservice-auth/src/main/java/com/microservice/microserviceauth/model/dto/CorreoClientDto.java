package com.microservice.microserviceauth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CorreoClientDto {

    private String destino;
    private String asunto;
    private String texto;
}
