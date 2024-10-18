package com.microservice.reservas_inventario;

import com.microservice.reservas_inventario.api.domain.request.ReservaRequest;
import com.microservice.reservas_inventario.api.domain.response.ProductoResponse;
import com.microservice.reservas_inventario.client.ProductoClient;
import com.microservice.reservas_inventario.domain.entities.ReservaInventario;
import com.microservice.reservas_inventario.domain.repositories.ReservaInventarioRepository;
import com.microservice.reservas_inventario.infraestructure.services.InventarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class InventarioServiceTest {

    @Mock
    private ReservaInventarioRepository reservaInventarioRepository;

    @Mock
    private ProductoClient productoClient;

    @InjectMocks
    private InventarioService inventarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void agregarExistencias_whenProductoExists_thenReturnUpdatedProducto() {
        ProductoResponse producto = new ProductoResponse(1, "Producto", "Descripcion", 100.0, 10, false, null);
        when(productoClient.obtenerProductoPorId(1)).thenReturn(producto);
        when(productoClient.actualizarStockProducto(1, 20)).thenReturn(producto);

        ProductoResponse result = inventarioService.agregarExistencias(1L, 10);

        assertEquals(producto, result);
    }

    @Test
    void reducirExistencias_whenProductoExists_thenReturnUpdatedProducto() {
        ProductoResponse producto = new ProductoResponse(1, "Producto", "Descripcion", 100.0, 10, false, null);
        when(productoClient.obtenerProductoPorId(1)).thenReturn(producto);
        when(productoClient.actualizarStockProducto(1, 5)).thenReturn(producto);

        ProductoResponse result = inventarioService.reducirExistencias(1L, 5);

        assertEquals(producto, result);
    }

    @Test
    void reducirExistencias_whenStockIsInsufficient_thenThrowException() {
        ProductoResponse producto = new ProductoResponse(1, "Producto", "Descripcion", 100.0, 3, false, null);
        when(productoClient.obtenerProductoPorId(1)).thenReturn(producto);

        assertThrows(IllegalArgumentException.class, () -> inventarioService.reducirExistencias(1L, 5));
    }

    @Test
    void consultarProducto_whenProductoExists_thenReturnProducto() {
        ProductoResponse producto = new ProductoResponse(1, "Producto", "Descripcion", 100.0, 10, false, null);
        when(productoClient.obtenerProductoPorId(1)).thenReturn(producto);

        ProductoResponse result = inventarioService.consultarProducto(1L);

        assertEquals(producto, result);
    }

    @Test
    void consultarProductosBajoStock_whenProductosExist_thenReturnProductos() {
        List<ProductoResponse> productos = Collections.singletonList(new ProductoResponse(1, "Producto", "Descripcion", 100.0, 10, false, null));
        when(productoClient.obtenerProductosBajoStock(10)).thenReturn(productos);

        List<ProductoResponse> result = inventarioService.consultarProductosBajoStock(10);

        assertEquals(productos, result);
    }

    @Test
    void reservarInventario_whenRequestIsValid_thenReturnReserva() {
        ReservaRequest request = new ReservaRequest();
        request.setProductoId(1);
        request.setCantidad(5);
        request.setPedidoId(1);

        ProductoResponse producto = new ProductoResponse(1, "Producto", "Descripcion", 100.0, 10, false, null);
        when(productoClient.obtenerProductoPorId(1)).thenReturn(producto);

        ReservaInventario reserva = new ReservaInventario();
        reserva.setProductoId(1);
        reserva.setCantidad(5);
        reserva.setPedidoId(1);
        reserva.setFechaReserva(LocalDate.now());
        reserva.setEstado("Pendiente");

        when(reservaInventarioRepository.save(any(ReservaInventario.class))).thenReturn(reserva);

        ReservaInventario result = inventarioService.reservarInventario(request);

        assertEquals(reserva, result);
    }

    @Test
    void reservarInventario_whenStockIsInsufficient_thenThrowException() {
        ReservaRequest request = new ReservaRequest();
        request.setProductoId(1);
        request.setCantidad(15);
        request.setPedidoId(1);

        ProductoResponse producto = new ProductoResponse(1, "Producto", "Descripcion", 100.0, 10, false, null);
        when(productoClient.obtenerProductoPorId(1)).thenReturn(producto);

        assertThrows(IllegalArgumentException.class, () -> inventarioService.reservarInventario(request));
    }

    @Test
    void liberarInventario_whenReservaExists_thenReturnCantidadLiberada() {
        ReservaInventario reserva = new ReservaInventario();
        reserva.setId(1);
        reserva.setProductoId(1);
        reserva.setCantidad(5);
        reserva.setEstado("Pendiente");

        ProductoResponse producto = new ProductoResponse(1, "Producto", "Descripcion", 100.0, 10, false, null);

        when(reservaInventarioRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(productoClient.obtenerProductoPorId(1)).thenReturn(producto);

        int result = inventarioService.liberarInventario(1L);

        assertEquals(5, result);
    }

    @Test
    void liberarInventario_whenReservaDoesNotExist_thenThrowException() {
        when(reservaInventarioRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> inventarioService.liberarInventario(1L));
    }

    @Test
    void marcarProductoComoAgotado_whenProductoExists_thenReturnUpdatedProducto() {
        ProductoResponse producto = new ProductoResponse(1, "Producto", "Descripcion", 100.0, 0, true, null);
        when(productoClient.obtenerProductoPorId(1)).thenReturn(producto);
        when(productoClient.actualizarStockProducto(1, 0)).thenReturn(producto);

        ProductoResponse result = inventarioService.marcarProductoComoAgotado(1L);

        assertEquals(producto, result);
    }

    @Test
    void listarReservas_whenReservasExist_thenReturnReservas() {
        List<ReservaInventario> reservas = Collections.singletonList(new ReservaInventario());
        when(reservaInventarioRepository.findAll()).thenReturn(reservas);

        List<ReservaInventario> result = inventarioService.listarReservas();

        assertEquals(reservas, result);
    }
}