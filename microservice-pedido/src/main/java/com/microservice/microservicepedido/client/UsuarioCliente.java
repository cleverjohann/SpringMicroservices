package com.microservice.microservicepedido.client;

import com.microservice.microservicepedido.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "msvc-usuario", url = "usuario-service:9030")
public interface UsuarioCliente {

    @GetMapping("/api/usuarios/findById/{id}")
    ResponseEntity<Usuario> findById(@PathVariable(name = "id") String id);

}