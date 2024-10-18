package com.microservice.microservicepedido;

import com.microservice.microservicepedido.client.InventarioCliente;
import com.microservice.microservicepedido.client.response.ReservaRequest;
import com.microservice.microservicepedido.model.Pedido;
import com.microservice.microservicepedido.model.ReservaInventario;
import com.microservice.microservicepedido.model.Usuario;
import com.microservice.microservicepedido.model.dto.CarritoResponseDto;
import com.microservice.microservicepedido.repository.PedidoRepository;
import com.microservice.microservicepedido.service.PedidoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private InventarioCliente inventarioCliente;

    @InjectMocks
    private PedidoServiceImpl pedidoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAllByUsuarioId_whenUsuarioExists_thenReturnPedidos() {
        Usuario usuario = new Usuario();
        List<Pedido> pedidos = new ArrayList<>();
        when(pedidoRepository.findAllByUsuarioId(usuario)).thenReturn(pedidos);

        List<Pedido> result = pedidoService.findAllByUsuarioId(usuario);

        assertEquals(pedidos, result);
    }

    @Test
    void crearPedido_whenReservaExitosa_thenReturnPedidoConfirmado() {
        Pedido pedido = new Pedido();
        CarritoResponseDto carrito = new CarritoResponseDto();
        CarritoResponseDto.DetalleCarrito detalle = new CarritoResponseDto.DetalleCarrito();
        detalle.setProducto(new CarritoResponseDto.DetalleCarrito.Producto(3, "Producto", "Descripcion", 10.0, 10));
        detalle.setCantidad(1);
        carrito.setDetalle_carrito(List.of(detalle));

        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);
        when(inventarioCliente.reservarInventario(any(ReservaRequest.class)))
                .thenReturn(ResponseEntity.ok(new ReservaInventario()));

        Pedido result = pedidoService.crearPedido(pedido, carrito);

        assertEquals("Confirmado", result.getEstado());
        verify(pedidoRepository, times(2)).save(pedido);
    }

    @Test
    void save_whenPedidoIsValid_thenReturnSavedPedido() {
        Pedido pedido = new Pedido();
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        Pedido result = pedidoService.save(pedido);

        assertEquals(pedido, result);
    }

    @Test
    void delete_whenPedidoExists_thenDeletePedido() {
        Pedido pedido = new Pedido();

        pedidoService.delete(pedido);

        verify(pedidoRepository).delete(pedido);
    }

    @Test
    void findById_whenPedidoExists_thenReturnPedido() {
        Pedido pedido = new Pedido();
        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedido));

        Pedido result = pedidoService.findById(1);

        assertEquals(pedido, result);
    }

    @Test
    void findById_whenPedidoDoesNotExist_thenReturnNull() {
        when(pedidoRepository.findById(1)).thenReturn(Optional.empty());

        Pedido result = pedidoService.findById(1);

        assertNull(result);
    }

    @Test
    void consultarEstadoPedido_whenPedidoExists_thenReturnEstado() {
        Pedido pedido = new Pedido();
        pedido.setEstado("En Proceso");
        when(pedidoRepository.findById(1)).thenReturn(Optional.of(pedido));

        String result = pedidoService.consultarEstadoPedido(1);

        assertEquals("En Proceso", result);
    }

    @Test
    void findAllByEstado_whenEstadoExists_thenReturnPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        when(pedidoRepository.findAllByEstado("PENDIENTE")).thenReturn(pedidos);

        List<Pedido> result = pedidoService.findAllByEstadoAndIdUsuario("PENDIENTE",1);

        assertEquals(pedidos, result);
    }
}