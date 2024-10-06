package com.microservice.producto_categoria;
import com.microservice.producto_categoria.api.controller.ProductoController;
import com.microservice.producto_categoria.api.domain.request.ProductoRequest;
import com.microservice.producto_categoria.api.domain.responses.ProductoResponse;
import com.microservice.producto_categoria.infraestructure.services.Productoservice;
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

class ProductoControllerTest {

    @Mock
    private Productoservice productoService;

    @InjectMocks
    private ProductoController productoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void crearProducto_createsProductSuccessfully() {
        ProductoRequest request = new ProductoRequest();
        ProductoResponse response = new ProductoResponse();
        when(productoService.create(any(ProductoRequest.class))).thenReturn(response);

        ResponseEntity<ProductoResponse> result = productoController.crearProducto(request);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(productoService, times(1)).create(request);
    }

    @Test
    void obtenerProducto_returnsProductSuccessfully() {
        Integer id = 1;
        ProductoResponse response = new ProductoResponse();
        when(productoService.findById(id)).thenReturn(response);

        ResponseEntity<ProductoResponse> result = productoController.obtenerProducto(id);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(productoService, times(1)).findById(id);
    }

    @Test
    void listarProductos_returnsListOfProductsSuccessfully() {
        List<ProductoResponse> responseList = Collections.singletonList(new ProductoResponse());
        when(productoService.findAll()).thenReturn(responseList);

        ResponseEntity<List<ProductoResponse>> result = productoController.listarProductos();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(responseList, result.getBody());
        verify(productoService, times(1)).findAll();
    }

    @Test
    void actualizarProducto_updatesProductSuccessfully() {
        Integer id = 1;
        ProductoRequest request = new ProductoRequest();
        ProductoResponse response = new ProductoResponse();
        when(productoService.update(eq(id), any(ProductoRequest.class))).thenReturn(response);

        ResponseEntity<ProductoResponse> result = productoController.actualizarProducto(id, request);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
        verify(productoService, times(1)).update(id, request);
    }

    @Test
    void eliminarProducto_deletesProductSuccessfully() {
        Integer id = 1;
        doNothing().when(productoService).delete(id);

        ResponseEntity<Void> result = productoController.eliminarProducto(id);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(productoService, times(1)).delete(id);
    }
}