package com.microservice.microserviceauth.client;

import com.microservice.microserviceauth.model.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "msvc-usuarios", url = "localhost:9030")
public interface UsuarioCliente {

    @PutMapping("/editar/{id}")
    ResponseEntity<Usuario> edit(@PathVariable("id") Integer id, @RequestBody Usuario usuario);

    @GetMapping("/{email}")
    ResponseEntity<Usuario> findByUsername(@PathVariable("email") String email);

}