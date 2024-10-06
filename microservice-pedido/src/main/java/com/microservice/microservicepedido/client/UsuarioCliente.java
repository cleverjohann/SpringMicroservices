package com.microservice.microservicepedido.client;

import com.microservice.microservicepedido.config.FeignConfig;
import com.microservice.microserviceusuarios.entities.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//Ya deberia de funcionar
@FeignClient(name = "msvc-usuario", url = "localhost:9030", configuration = FeignConfig.class)
public interface UsuarioCliente {

    @GetMapping("/api/usuarios/findById/{id}")
    ResponseEntity<Usuario> findById(@PathVariable(name = "id") String id);

}