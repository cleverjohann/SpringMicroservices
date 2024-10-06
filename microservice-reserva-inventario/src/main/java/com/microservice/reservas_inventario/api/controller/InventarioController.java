package com.microservice.reservas_inventario.api.controller;

import com.microservice.reservas_inventario.api.domain.request.ReservaRequest;
import com.microservice.reservas_inventario.api.domain.response.ProductoResponse;
import com.microservice.reservas_inventario.domain.entities.ReservaInventario;
import com.microservice.reservas_inventario.infraestructure.services.InventarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService inventarioService;

    @PostMapping("/producto/{id}/agregar")
    public ResponseEntity<ProductoResponse> agregarExistencias(@PathVariable Long id, @RequestParam int cantidad) {
        ProductoResponse response = inventarioService.agregarExistencias(id, cantidad);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/producto/{id}/reducir")
    public ResponseEntity<ProductoResponse> reducirExistencias(@PathVariable Long id, @RequestParam int cantidad) {
        ProductoResponse response = inventarioService.reducirExistencias(id, cantidad);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/producto/{id}")
    public ResponseEntity<ProductoResponse> consultarProducto(@PathVariable Long id) {
        ProductoResponse response = inventarioService.consultarProducto(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bajo-stock")
    public ResponseEntity<List<ProductoResponse>> consultarProductosBajoStock(@RequestParam int limite) {
        List<ProductoResponse> response = inventarioService.consultarProductosBajoStock(limite);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reservar")
    public ResponseEntity<ReservaInventario> reservarInventario(@RequestBody ReservaRequest request) {
        ReservaInventario reserva = inventarioService.reservarInventario(request);
        return ResponseEntity.ok(reserva);
    }

    @PostMapping("/liberar")
    public ResponseEntity<Void> liberarInventario(@RequestParam Long reservaId) {
        inventarioService.liberarInventario(reservaId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/producto/{id}/agotado")
    public ResponseEntity<ProductoResponse> marcarProductoComoAgotado(@PathVariable Long id) {
        ProductoResponse response = inventarioService.marcarProductoComoAgotado(id);
        return ResponseEntity.ok(response);
    }
}
