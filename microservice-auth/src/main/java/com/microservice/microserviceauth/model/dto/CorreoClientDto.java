package com.microservice.microserviceauth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CorreoClientDto {

    private String destino;
    private String asunto;
    private String texto;
}
