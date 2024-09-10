package com.microservice.microservicecorreo.dto;

import lombok.Getter;

@Getter
public class CorreoDto {

    private String destino;
    private String asunto;
    private String texto;

}
