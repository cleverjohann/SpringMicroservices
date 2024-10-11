package com.microservice.microservicecarrito.client;

import com.microservice.microservicecarrito.client.response.ProductoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "msvc-inventario", url = "reserva-inventario-service:8041")
public interface InventarioCliente {

    @PostMapping("/api/inventario/producto/{id}/agregar")
    ResponseEntity<ProductoResponse> agregarExistencias(@PathVariable(name = "id") Long id, @RequestParam("cantidad") int cantidad);

    @PostMapping("/api/inventario/producto/{id}/reducir")
    ResponseEntity<ProductoResponse> reducirExistencias(@PathVariable(name = "id") Long id, @RequestParam("cantidad") int cantidad);

}