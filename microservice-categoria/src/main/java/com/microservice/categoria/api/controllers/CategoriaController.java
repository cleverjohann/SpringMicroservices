package com.microservice.categoria.api.controllers;

import com.microservice.categoria.domain.entities.CategoriaEntity;
import com.microservice.categoria.infraestructure.services.CategoriaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaEntity> findById(@PathVariable Long id) {
        CategoriaEntity entity = categoriaService.findById(id);
        return entity != null ? ResponseEntity.ok(entity) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<CategoriaEntity> listAll() {
        return categoriaService.findAll();
    }

    @PostMapping
    public CategoriaEntity save(@RequestBody CategoriaEntity categoria) {
        return categoriaService.save(categoria);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<CategoriaEntity> edit(@PathVariable Long id, @RequestBody CategoriaEntity categoria) {
        CategoriaEntity updated = categoriaService.update(id, categoria);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = categoriaService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}