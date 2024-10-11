package com.microservice.microservicepedido.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "msvc-carrito", url = "carrito-service:9033")
public interface CarritoCliente {

    @GetMapping("/api/carrito/consultar/{id}")
    ResponseEntity<Map<String, Object>> consultarCarrito(@PathVariable(name = "id") Integer id);

}