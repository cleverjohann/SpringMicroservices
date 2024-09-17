package com.microservice.mock.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.microservice.categoria.domain.entities.CategoriaEntity;
import com.microservice.categoria.domain.repositories.CategoriaRepository;
import com.microservice.categoria.infraestructure.services.CategoriaService;
import com.microservice.mock.DataProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;


    @Test
    public void testFindCategoriaById() {
        Long id = 1L;

        when(this.categoriaRepository.findById(id)).thenReturn(Optional.of(DataProvider.getCategoria()));
        CategoriaEntity categoria = this.categoriaService.findById(id);

        assertNotNull(categoria);
        assertEquals(id, categoria.getId());
        assertEquals("Categoria 1", categoria.getNombre());
        assertEquals("Descripcion 1", categoria.getDescripcion());
        verify(this.categoriaRepository).findById(id);

    }
}