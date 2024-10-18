package com.microservice.reservas_inventario;

import com.microservice.reservas_inventario.api.controller.InventarioController;
import com.microservice.reservas_inventario.api.domain.request.ReservaRequest;
import com.microservice.reservas_inventario.api.domain.response.ProductoResponse;
import com.microservice.reservas_inventario.domain.entities.ReservaInventario;
import com.microservice.reservas_inventario.infraestructure.services.InventarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ReservaInventarioControllerTest {

    @Mock
    private InventarioService inventarioService;

    @InjectMocks
    private InventarioController inventarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void agregarExistencias_whenProductoExists_thenReturnUpdatedProducto() {
        ProductoResponse response = new ProductoResponse();
        when(inventarioService.agregarExistencias(1L, 10)).thenReturn(response);

        ResponseEntity<ProductoResponse> result = inventarioController.agregarExistencias(1L, 10);

        assertEquals(ResponseEntity.ok(response), result);
    }

    @Test
    void reducirExistencias_whenProductoExists_thenReturnUpdatedProducto() {
        ProductoResponse response = new ProductoResponse();
        when(inventarioService.reducirExistencias(1L, 5)).thenReturn(response);

        ResponseEntity<ProductoResponse> result = inventarioController.reducirExistencias(1L, 5);

        assertEquals(ResponseEntity.ok(response), result);
    }

    @Test
    void consultarProducto_whenProductoExists_thenReturnProducto() {
        ProductoResponse response = new ProductoResponse();
        when(inventarioService.consultarProducto(1L)).thenReturn(response);

        ResponseEntity<ProductoResponse> result = inventarioController.consultarProducto(1L);

        assertEquals(ResponseEntity.ok(response), result);
    }

    @Test
    void consultarProductosBajoStock_whenProductosExist_thenReturnProductos() {
        List<ProductoResponse> response = Collections.singletonList(new ProductoResponse());
        when(inventarioService.consultarProductosBajoStock(10)).thenReturn(response);

        ResponseEntity<List<ProductoResponse>> result = inventarioController.consultarProductosBajoStock(10);

        assertEquals(ResponseEntity.ok(response), result);
    }

    @Test
    void reservarInventario_whenRequestIsValid_thenReturnReserva() {
        ReservaRequest request = new ReservaRequest();
        ReservaInventario reserva = new ReservaInventario();
        when(inventarioService.reservarInventario(request)).thenReturn(reserva);

        ResponseEntity<ReservaInventario> result = inventarioController.reservarInventario(request);

        assertEquals(ResponseEntity.ok(reserva), result);
    }

    @Test
    void liberarInventario_whenReservaExists_thenReturnCantidadLiberada() {
        when(inventarioService.liberarInventario(1L)).thenReturn(5);

        ResponseEntity<Integer> result = inventarioController.liberarInventario(1L);

        assertEquals(ResponseEntity.ok(5), result);
    }

    @Test
    void marcarProductoComoAgotado_whenProductoExists_thenReturnUpdatedProducto() {
        ProductoResponse response = new ProductoResponse();
        when(inventarioService.marcarProductoComoAgotado(1L)).thenReturn(response);

        ResponseEntity<ProductoResponse> result = inventarioController.marcarProductoComoAgotado(1L);

        assertEquals(ResponseEntity.ok(response), result);
    }

    @Test
    void listarReservas_whenReservasExist_thenReturnReservas() {
        List<ReservaInventario> reservas = Collections.singletonList(new ReservaInventario());
        when(inventarioService.listarReservas()).thenReturn(reservas);

        ResponseEntity<List<ReservaInventario>> result = inventarioController.listarReservas();

        assertEquals(ResponseEntity.ok(reservas), result);
    }
}