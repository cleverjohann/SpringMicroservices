package com.microservice.microservicecarrito.client;

import com.microservice.microservicecarrito.client.response.ProductoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-producto-categoria", url = "producto-categoria-service:8040")
public interface ProductoCliente {

    @GetMapping("/api/productos/{id}")
    ResponseEntity<ProductoResponse> obtenerProducto(@PathVariable(name="id") Long id);

}