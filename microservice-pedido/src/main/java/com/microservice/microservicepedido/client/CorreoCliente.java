package com.microservice.microservicepedido.client;

import com.microservice.microservicepedido.client.response.CorreoClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-correo", url = "correo-service:9031")
public interface CorreoCliente {

    void enviarCorreo(@RequestBody CorreoClientDto dto);

}