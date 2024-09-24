package com.microservice.microserviceusuarios.controller;

import com.microservice.microserviceusuarios.entities.Usuario;
import com.microservice.microserviceusuarios.entities.dto.UsuarioUpdateDto;
import com.microservice.microserviceusuarios.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping("/findById/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable(name="id") Integer id) {
        Usuario entity = usuarioService.findById(id);
        return entity != null ? ResponseEntity.ok(entity) : ResponseEntity.notFound().build();
    }

    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<Usuario> findByUsername(@PathVariable(name = "email") String email) {
        Usuario entity = usuarioService.findByEmail(email);
        return entity != null ? ResponseEntity.ok(entity) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public List<Usuario> listAll() {
        return usuarioService.findAll();
    }

    @PostMapping
    public Usuario save(@RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Usuario> edit(@PathVariable(name="id") Integer id, @RequestBody UsuarioUpdateDto usuario) {
        Usuario updated = usuarioService.update(id, usuario);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable(name="id") Integer id) {
        boolean deleted = usuarioService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
