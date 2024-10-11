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
    //1 Agregar existencia a un producto
    @PostMapping("/producto/{id}/agregar")
    public ResponseEntity<ProductoResponse> agregarExistencias(@PathVariable Long id, @RequestParam int cantidad) {
        ProductoResponse response = inventarioService.agregarExistencias(id, cantidad);
        return ResponseEntity.ok(response);
    }
    //2 Reducir existencia a un producto
    @PostMapping("/producto/{id}/reducir")
    public ResponseEntity<ProductoResponse> reducirExistencias(@PathVariable Long id, @RequestParam int cantidad) {
        ProductoResponse response = inventarioService.reducirExistencias(id, cantidad);
        return ResponseEntity.ok(response);
    }
    //3 Consultar producto por ID
    @GetMapping("/producto/{id}")
    public ResponseEntity<ProductoResponse> consultarProducto(@PathVariable Long id) {
        ProductoResponse response = inventarioService.consultarProducto(id);
        return ResponseEntity.ok(response);
    }
    // 4 Consultar productos bajo stock
    @GetMapping("/bajo-stock")
    public ResponseEntity<List<ProductoResponse>> consultarProductosBajoStock(@RequestParam int limite) {
        List<ProductoResponse> response = inventarioService.consultarProductosBajoStock(limite);
        return ResponseEntity.ok(response);
    }

    // 5 Reservar inventario para pedido
    @PostMapping("/reservar")
    public ResponseEntity<ReservaInventario> reservarInventario(@RequestBody ReservaRequest request) {
        ReservaInventario reserva = inventarioService.reservarInventario(request);
        return ResponseEntity.ok(reserva);
    }
    // 6 Liberar inventario
    @PostMapping("/liberar")
    public ResponseEntity<Integer> liberarInventario(@RequestParam Long reservaId) {
        int cantidadLiberada = inventarioService.liberarInventario(reservaId);
        return ResponseEntity.ok(cantidadLiberada);
    }
    // 7 Marcar producto como agotado
    @PostMapping("/producto/{id}/agotado")
    public ResponseEntity<ProductoResponse> marcarProductoComoAgotado(@PathVariable Long id) {
        ProductoResponse response = inventarioService.marcarProductoComoAgotado(id);
        return ResponseEntity.ok(response);
    }

    // 8 Listar todas las reservas del inventario
    @GetMapping("/reservas")
    public ResponseEntity<List<ReservaInventario>> listarReservas() {
        List<ReservaInventario> reservas = inventarioService.listarReservas();
        return ResponseEntity.ok(reservas);
    }
}
