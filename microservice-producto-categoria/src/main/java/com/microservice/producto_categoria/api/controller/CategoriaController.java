package com.microservice.producto_categoria.api.controller;

import com.microservice.producto_categoria.api.domain.request.CategoriaRequest;
import com.microservice.producto_categoria.api.domain.responses.CategoriaResponse;
import com.microservice.producto_categoria.infraestructure.services.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class CategoriaController {
    private final CategoriaService categoriaService;

    // Crear una nueva categoría (POST)
    @PostMapping
    public ResponseEntity<CategoriaResponse> crearCategoria(@Valid @RequestBody CategoriaRequest request) {
        CategoriaResponse categoriaCreada = categoriaService.create(request);
        return new ResponseEntity<>(categoriaCreada, HttpStatus.CREATED);  // HTTP 201 CREATED
    }

    // Obtener una categoría por su ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponse> obtenerCategoria(@PathVariable Integer id) {
        CategoriaResponse categoria = categoriaService.findById(id);
        return new ResponseEntity<>(categoria, HttpStatus.OK);  // HTTP 200 OK
    }

    // Listar todas las categorías (GET)
    @GetMapping
    public ResponseEntity<List<CategoriaResponse>> listarCategorias() {
        List<CategoriaResponse> categorias = categoriaService.findAll();
        return new ResponseEntity<>(categorias, HttpStatus.OK);  // HTTP 200 OK
    }

    // Actualizar una categoría existente por su ID (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponse> actualizarCategoria(
            @PathVariable Integer id, @Valid @RequestBody CategoriaRequest request) {
        CategoriaResponse categoriaActualizada = categoriaService.update(id, request);
        return new ResponseEntity<>(categoriaActualizada, HttpStatus.OK);  // HTTP 200 OK
    }

    // Eliminar una categoría por su ID (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCategoria(@PathVariable Integer id) {
        categoriaService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // HTTP 204 NO CONTENT
    }
}
