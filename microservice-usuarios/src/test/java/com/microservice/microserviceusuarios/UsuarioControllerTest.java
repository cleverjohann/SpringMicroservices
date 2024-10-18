package com.microservice.microserviceusuarios;
import com.microservice.microserviceusuarios.controller.UsuarioController;
import com.microservice.microserviceusuarios.entities.Usuario;
import com.microservice.microserviceusuarios.entities.dto.UsuarioUpdateDto;
import com.microservice.microserviceusuarios.service.IUsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Mock
    private IUsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_ReturnsUsuario_WhenUsuarioExists() {
        Usuario usuario = new Usuario();
        when(usuarioService.findById(1)).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuarioController.findById(1);

        assertEquals(ResponseEntity.ok(usuario), response);
    }

    @Test
    void findById_ReturnsNotFound_WhenUsuarioDoesNotExist() {
        when(usuarioService.findById(1)).thenReturn(null);

        ResponseEntity<Usuario> response = usuarioController.findById(1);

        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    void findByEmail_ReturnsUsuario_WhenUsuarioExists() {
        Usuario usuario = new Usuario();
        when(usuarioService.findByEmail("test@example.com")).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuarioController.findByUsername("test@example.com");

        assertEquals(ResponseEntity.ok(usuario), response);
    }

    @Test
    void findByEmail_ReturnsNotFound_WhenUsuarioDoesNotExist() {
        when(usuarioService.findByEmail("test@example.com")).thenReturn(null);

        ResponseEntity<Usuario> response = usuarioController.findByUsername("test@example.com");

        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    void listAll_ReturnsListOfUsuarios() {
        List<Usuario> usuarios = Collections.singletonList(new Usuario());
        when(usuarioService.findAll()).thenReturn(usuarios);

        List<Usuario> response = usuarioController.listAll();

        assertEquals(usuarios, response);
    }

    @Test
    void save_ReturnsSavedUsuario() {
        Usuario usuario = new Usuario();
        when(usuarioService.save(usuario)).thenReturn(usuario);

        Usuario response = usuarioController.save(usuario);

        assertEquals(usuario, response);
    }

    @Test
    void edit_ReturnsUpdatedUsuario_WhenUsuarioExists() {
        UsuarioUpdateDto usuarioUpdateDto = new UsuarioUpdateDto();
        Usuario updatedUsuario = new Usuario();
        when(usuarioService.update(1, usuarioUpdateDto)).thenReturn(updatedUsuario);

        ResponseEntity<Usuario> response = usuarioController.edit(1, usuarioUpdateDto);

        assertEquals(ResponseEntity.ok(updatedUsuario), response);
    }

    @Test
    void edit_ReturnsNotFound_WhenUsuarioDoesNotExist() {
        UsuarioUpdateDto usuarioUpdateDto = new UsuarioUpdateDto();
        when(usuarioService.update(1, usuarioUpdateDto)).thenReturn(null);

        ResponseEntity<Usuario> response = usuarioController.edit(1, usuarioUpdateDto);

        assertEquals(ResponseEntity.notFound().build(), response);
    }

    @Test
    void delete_ReturnsNoContent_WhenUsuarioIsDeleted() {
        when(usuarioService.delete(1)).thenReturn(true);

        ResponseEntity<Void> response = usuarioController.delete(1);

        assertEquals(ResponseEntity.noContent().build(), response);
    }

    @Test
    void delete_ReturnsNotFound_WhenUsuarioIsNotDeleted() {
        when(usuarioService.delete(1)).thenReturn(false);

        ResponseEntity<Void> response = usuarioController.delete(1);

        assertEquals(ResponseEntity.notFound().build(), response);
    }
}