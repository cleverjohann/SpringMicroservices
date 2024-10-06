package com.microservice.producto_categoria;
import com.microservice.producto_categoria.api.controller.CategoriaController;
import com.microservice.producto_categoria.api.domain.request.CategoriaRequest;
import com.microservice.producto_categoria.api.domain.responses.CategoriaResponse;
import com.microservice.producto_categoria.infraestructure.services.CategoriaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoriaControllerTest {

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private CategoriaController categoriaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearCategoria_succeedsWithValidRequest() {
        CategoriaRequest request = new CategoriaRequest(); // Assuming valid request
        CategoriaResponse response = new CategoriaResponse();
        when(categoriaService.create(any(CategoriaRequest.class))).thenReturn(response);

        ResponseEntity<CategoriaResponse> result = categoriaController.crearCategoria(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(categoriaService, times(1)).create(request);
    }

    @Test
    void obtenerCategoria_returnsOkForExistingId() {
        Integer id = 1;
        CategoriaResponse response = new CategoriaResponse();
        when(categoriaService.findById(id)).thenReturn(response);

        ResponseEntity<CategoriaResponse> result = categoriaController.obtenerCategoria(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(categoriaService, times(1)).findById(id);
    }

    @Test
    void listarCategorias_returnsListOfCategories() {
        List<CategoriaResponse> responses = List.of(new CategoriaResponse(), new CategoriaResponse());
        when(categoriaService.findAll()).thenReturn(responses);

        ResponseEntity<List<CategoriaResponse>> result = categoriaController.listarCategorias();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responses, result.getBody());
        verify(categoriaService, times(1)).findAll();
    }

    @Test
    void actualizarCategoria_succeedsWithValidRequest() {
        Integer id = 1;
        CategoriaRequest request = new CategoriaRequest(); // Assuming valid request
        CategoriaResponse response = new CategoriaResponse();
        when(categoriaService.update(eq(id), any(CategoriaRequest.class))).thenReturn(response);

        ResponseEntity<CategoriaResponse> result = categoriaController.actualizarCategoria(id, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(categoriaService, times(1)).update(id, request);
    }

    @Test
    void eliminarCategoria_succeedsForExistingId() {
        Integer id = 1;
        doNothing().when(categoriaService).delete(id);

        ResponseEntity<Void> result = categoriaController.eliminarCategoria(id);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(categoriaService, times(1)).delete(id);
    }
}