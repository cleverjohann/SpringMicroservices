package com.microservice.producto_categoria.api.controller;

import com.microservice.producto_categoria.api.domain.request.ProductoRequest;
import com.microservice.producto_categoria.api.domain.responses.ProductoResponse;
import com.microservice.producto_categoria.infraestructure.services.Productoservice;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final Productoservice productoService;
    // Crear un nuevo producto (POST)
    @PostMapping
    public ResponseEntity<ProductoResponse> crearProducto(@Valid @RequestBody ProductoRequest request) {
        ProductoResponse productoCreado = productoService.create(request);
        return new ResponseEntity<>(productoCreado, HttpStatus.CREATED);  // HTTP 201 CREATED
    }

    // Obtener un producto por su ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> obtenerProducto(@PathVariable Long id) {
        ProductoResponse producto = productoService.findById(id);
        return new ResponseEntity<>(producto, HttpStatus.OK);  // HTTP 200 OK
    }

    // Listar todos los productos (GET)
    @GetMapping
    public ResponseEntity<List<ProductoResponse>> listarProductos() {
        List<ProductoResponse> productos = productoService.findAll();
        return new ResponseEntity<>(productos, HttpStatus.OK);  // HTTP 200 OK
    }

    // Actualizar un producto existente por su ID (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> actualizarProducto(
            @PathVariable Long id, @Valid @RequestBody ProductoRequest request) {
        ProductoResponse productoActualizado = productoService.update(id, request);
        return new ResponseEntity<>(productoActualizado, HttpStatus.OK);  // HTTP 200 OK
    }

    // Eliminar un producto por su ID (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        productoService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // HTTP 204 NO CONTENT
    }
}
