package com.microservice.pagos.client;

import com.microservice.pagos.api.models.UsuarioResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "usuario-service", url = "http://usuario-service:8044/api/usuarios")
public interface UsuarioClient {

    @GetMapping("/findById/{id}")
    ResponseEntity<UsuarioResponse> findById(@PathVariable("id") Integer id);
}
