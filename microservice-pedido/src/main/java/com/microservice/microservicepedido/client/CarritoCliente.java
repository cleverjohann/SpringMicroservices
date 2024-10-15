package com.microservice.microservicepedido.client;

import com.microservice.microservicepedido.model.dto.CarritoResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-carrito", url = "carrito-service:9033")
public interface CarritoCliente {

    @GetMapping("/api/carrito/consultar/{id}")
    ResponseEntity<CarritoResponseDto> consultarCarrito(@PathVariable(name = "id") Integer id);

}