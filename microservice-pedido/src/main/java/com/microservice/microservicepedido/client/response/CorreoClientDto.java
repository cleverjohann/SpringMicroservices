package com.microservice.microservicepedido.client.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorreoClientDto {

    private String destino;
    private String asunto;
    private String texto;

}
