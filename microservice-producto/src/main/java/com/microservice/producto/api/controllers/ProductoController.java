package com.microservice.producto.api.controllers;

import com.microservice.producto.api.models.request.ProductoRequest;
import com.microservice.producto.api.models.responses.ProductoResponse;
import com.microservice.producto.domain.entities.ProductoEntity;
import com.microservice.producto.infraestructure.services.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> createProduct(@RequestBody ProductoRequest productRequest) {
        ProductoResponse productoResponse = productoService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoResponse> getProductById(@PathVariable Long id) {
        ProductoResponse productoResponse = productoService.getProductById(id);
        return ResponseEntity.ok(productoResponse);
    }

    @GetMapping
    public ResponseEntity<List<ProductoResponse>> getAllProducts() {
        List<ProductoResponse> productoResponses = productoService.getAllProducts();
        return ResponseEntity.ok(productoResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoResponse> updateProduct(@PathVariable Long id, @RequestBody ProductoRequest productRequest) {
        ProductoResponse productoResponse = productoService.updateProduct(id, productRequest);
        return ResponseEntity.ok(productoResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productoService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping
//    public List<ProductoEntity> findAll() {
//        return productoService.findAll();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ProductoEntity> findById(@PathVariable Integer id) {
//        ProductoEntity entity = productoService.findById(id);
//        return entity != null ? ResponseEntity.ok(entity) : ResponseEntity.notFound().build();
//    }
//
//    @PostMapping
//    public ProductoEntity save(@RequestBody ProductoEntity producto) {
//        return productoService.save(producto);
//    }
//
//    @PutMapping("/editar/{id}")
//    public ResponseEntity<ProductoEntity> edit(@PathVariable Integer id, @RequestBody ProductoEntity producto) {
//        ProductoEntity updated = productoService.update(id, producto);
//        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Integer id) {
//        boolean deleted = productoService.delete(id);
//        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
//    }

}
