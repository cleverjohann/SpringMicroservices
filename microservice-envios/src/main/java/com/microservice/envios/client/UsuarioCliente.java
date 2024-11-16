package com.microservice.envios.client;

import com.microservice.envios.domain.entities.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-usuario", url = "usuario-service:9030")
public interface UsuarioCliente {

    @GetMapping("/api/usuarios/findByEmail/{email}")
    ResponseEntity<Usuario> findByUsername(@PathVariable(name = "email") String email);

}