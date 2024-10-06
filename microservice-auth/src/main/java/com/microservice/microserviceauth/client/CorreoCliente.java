package com.microservice.microserviceauth.client;

import com.microservice.microserviceauth.model.dto.CorreoClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-correo", url = "correo-service:9031")
public interface CorreoCliente {

    @PostMapping("/enviar-correo/enviar")
    void enviarCorreo(@RequestBody CorreoClientDto dto);

}