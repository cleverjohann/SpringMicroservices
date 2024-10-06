package com.microservice.producto_categoria;
import com.microservice.producto_categoria.api.domain.request.CategoriaRequest;
import com.microservice.producto_categoria.api.domain.responses.CategoriaResponse;
import com.microservice.producto_categoria.domain.entities.Categoria;
import com.microservice.producto_categoria.domain.repositories.CategoriaRepository;
import com.microservice.producto_categoria.infraestructure.services.CategoriaService;
import com.microservice.producto_categoria.mappers.CategoriaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private CategoriaMapper categoriaMapper;

    @InjectMocks
    private CategoriaService categoriaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCategorySuccessfully() {
        CategoriaRequest request = new CategoriaRequest();
        Categoria categoria = new Categoria();
        CategoriaResponse response = new CategoriaResponse();
        when(categoriaMapper.toEntity(any(CategoriaRequest.class))).thenReturn(categoria);
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);
        when(categoriaMapper.toResponse(any(Categoria.class))).thenReturn(response);

        CategoriaResponse result = categoriaService.create(request);

        assertEquals(response, result);
        verify(categoriaRepository, times(1)).save(categoria);
    }

    @Test
    void updateCategorySuccessfully() {
        Integer id = 1;
        CategoriaRequest request = new CategoriaRequest();
        Categoria categoria = new Categoria();
        CategoriaResponse response = new CategoriaResponse();
        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria));
        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);
        when(categoriaMapper.toResponse(any(Categoria.class))).thenReturn(response);

        CategoriaResponse result = categoriaService.update(id, request);

        assertEquals(response, result);
        verify(categoriaRepository, times(1)).findById(id);
        verify(categoriaRepository, times(1)).save(categoria);
    }

    @Test
    void updateCategoryNotFound() {
        Integer id = 1;
        CategoriaRequest request = new CategoriaRequest();
        when(categoriaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> categoriaService.update(id, request));
        verify(categoriaRepository, times(1)).findById(id);
    }

    @Test
    void findCategoryByIdSuccessfully() {
        Integer id = 1;
        Categoria categoria = new Categoria();
        CategoriaResponse response = new CategoriaResponse();
        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria));
        when(categoriaMapper.toResponse(any(Categoria.class))).thenReturn(response);

        CategoriaResponse result = categoriaService.findById(id);

        assertEquals(response, result);
        verify(categoriaRepository, times(1)).findById(id);
    }

    @Test
    void findCategoryByIdNotFound() {
        Integer id = 1;
        when(categoriaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> categoriaService.findById(id));
        verify(categoriaRepository, times(1)).findById(id);
    }

    @Test
    void deleteCategorySuccessfully() {
        Integer id = 1;
        doNothing().when(categoriaRepository).deleteById(id);

        categoriaService.delete(id);

        verify(categoriaRepository, times(1)).deleteById(id);
    }

    @Test
    void findAllCategoriesSuccessfully() {
        List<Categoria> categorias = Collections.singletonList(new Categoria());
        List<CategoriaResponse> responses = Collections.singletonList(new CategoriaResponse());
        when(categoriaRepository.findAll()).thenReturn(categorias);
        when(categoriaMapper.toResponse(any(Categoria.class))).thenReturn(responses.get(0));

        List<CategoriaResponse> result = categoriaService.findAll();

        assertEquals(responses, result);
        verify(categoriaRepository, times(1)).findAll();
    }
}