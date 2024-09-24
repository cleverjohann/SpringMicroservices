package com.microservice.categoria;

import com.microservice.categoria.domain.entities.CategoriaEntity;
import com.microservice.categoria.domain.repositories.CategoriaRepository;
import com.microservice.categoria.infraestructure.services.CategoriaService;
import com.microservice.categoria.util.CategoriaNotFoundException;
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
public class CategoriaServiceTest {

    @Autowired
    private CategoriaService categoriaService;

    @MockBean
    private CategoriaRepository categoriaRepository;

    //Guardar Categoria
    @Test
    public void save_ReturnsSavedCategoria() {
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setNombre("New Categoria");

        when(categoriaRepository.save(categoria)).thenReturn(categoria);

        CategoriaEntity result = categoriaService.save(categoria);

        assertNotNull(result);
        assertEquals("New Categoria", result.getNombre());
    }
    //Actualizar Categoria
    @Test
    public void update_UpdatesAndReturnsCategoria_WhenIdExists() {
        Integer id = 1;
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setNombre("Updated Categoria");
        CategoriaEntity existingCategoria = new CategoriaEntity();
        existingCategoria.setId(id);

        when(categoriaRepository.findById(id)).thenReturn(Optional.of(existingCategoria));
        when(categoriaRepository.save(existingCategoria)).thenReturn(existingCategoria);

        CategoriaEntity result = categoriaService.update(id, categoria);

        assertNotNull(result);
        assertEquals("Updated Categoria", result.getNombre());
    }

    @Test
    public void update_ThrowsException_WhenIdDoesNotExist() {
        Integer id = 999;
        CategoriaEntity categoria = new CategoriaEntity();

        when(categoriaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CategoriaNotFoundException.class, () -> categoriaService.update(id, categoria));
    }

    //Borrar Categoria
    @Test
    public void delete_ReturnsTrue_WhenIdExists() {
        Integer id = 1;
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setId(id);

        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria));

        boolean result = categoriaService.delete(id);

        assertTrue(result);
    }

    @Test
    public void delete_ThrowsException_WhenIdDoesNotExist() {
        Integer id = 999;

        when(categoriaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CategoriaNotFoundException.class, () -> categoriaService.delete(id));
    }

    //Buscar por id categoria
    @Test
    public void findById_ReturnsCategoria_WhenIdExists() {
        Integer id = 1;
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setId(id);

        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria));

        CategoriaEntity result = categoriaService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    public void findById_ReturnsNull_WhenIdDoesNotExist() {
        Integer id = 999;

        when(categoriaRepository.findById(id)).thenReturn(Optional.empty());

        CategoriaEntity result = categoriaService.findById(id);

        assertNull(result);
    }

    //Listar todas las categorias
    @Test
    public void findAll_ReturnsListOfCategorias() {
        List<CategoriaEntity> categorias = Arrays.asList(new CategoriaEntity(), new CategoriaEntity());

        when(categoriaRepository.findAll()).thenReturn(categorias);

        List<CategoriaEntity> result = categoriaService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}