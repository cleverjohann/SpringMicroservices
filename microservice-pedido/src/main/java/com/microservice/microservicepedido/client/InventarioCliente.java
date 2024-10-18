package com.microservice.microservicepedido.client;

import com.microservice.microservicepedido.client.response.ProductoResponse;
import com.microservice.microservicepedido.client.response.ReservaRequest;
import com.microservice.microservicepedido.model.ReservaInventario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "msvc-inventario", url = "reserva-inventario-service:8041")
public interface InventarioCliente {

    @PostMapping("/api/inventario/producto/{id}/agregar")
    ResponseEntity<ProductoResponse> agregarExistencias(@PathVariable(name = "id") Long id, @RequestParam("cantidad") int cantidad);

    @PostMapping("/api/inventario/producto/{id}/reducir")
    ResponseEntity<ProductoResponse> reducirExistencias(@PathVariable(name = "id") Long id, @RequestParam("cantidad") int cantidad);

    @PostMapping("/api/inventario/reservar")
    ResponseEntity<ReservaInventario> reservarInventario(@RequestBody ReservaRequest request);

    @PostMapping("/api/inventario/liberar")
    ResponseEntity<Void> liberarInventario(@RequestParam("reservaId") Long reservaId);

}