package com.microservice.microserviceusuarios;

import com.microservice.microserviceusuarios.entities.Usuario;
import com.microservice.microserviceusuarios.entities.dto.UsuarioUpdateDto;
import com.microservice.microserviceusuarios.repository.UsuarioRepository;
import com.microservice.microserviceusuarios.service.IUsuarioServiceImpl;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class IUsuarioServiceImplTest {

    @Autowired
    private IUsuarioServiceImpl usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    //Find By Email

    @Test
    public void findByEmail_ReturnsUsuario_WhenEmailExists() {
        String email = "example@example.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);

        when(usuarioRepository.findByEmail(email)).thenReturn(usuario);

        Usuario result = usuarioService.findByEmail(email);
        // Assert
        assertNotNull(result);
        assertEquals(email, result.getEmail());
    }

    @Test
    public void findByEmail_ReturnsNull_WhenEmailDoesNotExist() {
        String email = "nonexistent@example.com";

        when(usuarioRepository.findByEmail(email)).thenReturn(null);

        Usuario result = usuarioService.findByEmail(email);

        assertNull(result);
    }

    //Find By ID
    @Test
    public void findById_ReturnsUsuario_WhenIdExists() {
        Integer id = 1;
        Usuario usuario = new Usuario();
        usuario.setId(id);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        Usuario result = usuarioService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    public void findById_ReturnsNull_WhenIdDoesNotExist() {
        Integer id = 999;

        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        Usuario result = usuarioService.findById(id);

        assertNull(result);
    }
    //Guardar Usuario

    @Test
    public void save_ReturnsSavedUsuario() {
        Usuario usuario = new Usuario();
        usuario.setEmail("new@example.com");

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario result = usuarioService.save(usuario);

        assertNotNull(result);
        assertEquals("new@example.com", result.getEmail());
    }

    //Actualizar Usuario
    @Test
    public void update_UpdatesAndReturnsUsuario_WhenIdExists() {
        Integer id = 1;
        UsuarioUpdateDto usuarioUpdateDto = new UsuarioUpdateDto();
        usuarioUpdateDto.setEmail("updated@example.com");
        usuarioUpdateDto.setCuenta_verificada(true);
        Usuario usuario = new Usuario();
        usuario.setId(id);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        Usuario result = usuarioService.update(id, usuarioUpdateDto);

        assertNotNull(result);
        assertEquals("updated@example.com", result.getEmail());
    }

    @Test
    public void update_ThrowsException_WhenIdDoesNotExist() {
        Integer id = 999;
        UsuarioUpdateDto usuarioUpdateDto = new UsuarioUpdateDto();

        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> usuarioService.update(id, usuarioUpdateDto));
    }

    //Borrar Usuario
    @Test
    public void delete_ReturnsTrue_WhenIdExists() {
        Integer id = 1;
        Usuario usuario = new Usuario();
        usuario.setId(id);

        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));

        boolean result = usuarioService.delete(id);

        assertTrue(result);
    }

    @Test
    public void delete_ThrowsException_WhenIdDoesNotExist() {
        Integer id = 999;

        when(usuarioRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> usuarioService.delete(id));
    }

    //Listar Usuarios
    @Test
    public void findAll_ReturnsListOfUsuarios() {
        List<Usuario> usuarios = Arrays.asList(new Usuario(), new Usuario());

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<Usuario> result = usuarioService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

}