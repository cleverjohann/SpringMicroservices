package com.microservice.microservicepedido;

import com.microservice.microservicepedido.client.CarritoCliente;
import com.microservice.microservicepedido.client.UsuarioCliente;
import com.microservice.microservicepedido.controller.PedidoController;
import com.microservice.microservicepedido.model.Carrito;
import com.microservice.microservicepedido.model.Cupone;
import com.microservice.microservicepedido.model.Pedido;
import com.microservice.microservicepedido.model.Usuario;
import com.microservice.microservicepedido.model.dto.CarritoResponseDto;
import com.microservice.microservicepedido.model.dto.PedidoDto;
import com.microservice.microservicepedido.model.dto.PedidoResponseDto;
import com.microservice.microservicepedido.service.ICuponService;
import com.microservice.microservicepedido.service.IPedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PedidoControllerTest {

    @Mock
    private IPedidoService pedidoService;

    @Mock
    private UsuarioCliente usuarioCliente;

    @Mock
    private ICuponService cuponService;

    @Mock
    private CarritoCliente carritoCliente;

    @InjectMocks
    private PedidoController pedidoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_whenPedidoExists_thenReturnPedidoDetails() {
        Pedido pedido = new Pedido();
        pedido.setId(1);
        Usuario usuario = new Usuario();
        usuario.setNombres("Test User");
        pedido.setUsuario(usuario);
        pedido.setFechaPedido(LocalDate.parse("2023-10-01"));
        pedido.setMontoTotal(BigDecimal.valueOf(100.00));
        pedido.setEstado("EN PROCESO");
        pedido.setDireccionEnvio("Test Address");
        pedido.setCodigoCupon(new Cupone(1, "TEST10", BigDecimal.valueOf(10.00), "Activo"));

        when(pedidoService.findById(1)).thenReturn(pedido);

        ResponseEntity<?> response = pedidoController.findById(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test User", ((PedidoResponseDto) ((Map<String, Object>) response.getBody()).get("data")).getUsuario());
    }


    @Test
    void listarPorId_whenUsuarioExists_thenReturnPedidos() {
        Usuario usuario = new Usuario();
        List<Pedido> pedidos = Collections.singletonList(new Pedido());
        when(usuarioCliente.findById("1")).thenReturn(ResponseEntity.ok(usuario));
        when(pedidoService.findAllByUsuarioId(usuario)).thenReturn(pedidos);

        ResponseEntity<?> response = pedidoController.listarPorId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidos, ((Map<String, Object>) response.getBody()).get("pedidos"));
    }

    @Test
    void listarPorId_whenUsuarioDoesNotExist_thenReturnNotFound() {
        when(usuarioCliente.findById("1")).thenReturn(ResponseEntity.of(Optional.empty()));

        ResponseEntity<?> response = pedidoController.listarPorId(1);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Usuario no encontrado", ((Map<String, Object>) response.getBody()).get("message"));
    }

    @Test
    void consultarEstadoPedido_whenPedidoExists_thenReturnEstado() {
        Pedido pedido = new Pedido();
        pedido.setEstado("PENDIENTE");
        when(pedidoService.findById(10)).thenReturn(pedido);

        ResponseEntity<?> response = pedidoController.consultarEstadoPedido(10);
        Map<String, Object> responseMap = (Map<String, Object>) response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("PENDIENTE", responseMap.get("estado"));
    }

    @Test
    void cambiarEstadoPedido_whenPedidoExists_thenUpdateEstado() {
        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setEstado("EN PROCESO");
        when(pedidoService.findById(1)).thenReturn(pedido);
        when(pedidoService.save(pedido)).thenReturn(pedido);

        ResponseEntity<?> response = pedidoController.cambiarEstadoPedido(1, "ENTREGADO");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ENTREGADO", ((Pedido) ((Map<String, Object>) response.getBody()).get("data")).getEstado());
    }

    @Test
    void cambiarEstadoPedido_whenPedidoDoesNotExist_thenReturnNotFound() {
        when(pedidoService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = pedidoController.cambiarEstadoPedido(1, "ENTREGADO");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Pedido no encontrado", ((Map<String, Object>) response.getBody()).get("message"));
    }

    @Test
    void eliminarPedido_whenPedidoExists_thenDeletePedido() {
        Pedido pedido = new Pedido();
        pedido.setId(1);
        when(pedidoService.findById(1)).thenReturn(pedido);

        ResponseEntity<?> response = pedidoController.eliminarPedido(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Pedido eliminado", ((Map<String, Object>) response.getBody()).get("message"));
    }

    @Test
    void eliminarPedido_whenPedidoDoesNotExist_thenReturnNotFound() {
        when(pedidoService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = pedidoController.eliminarPedido(1);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Pedido no encontrado", ((Map<String, Object>) response.getBody()).get("message"));
    }

    @Test
    void repetirPedido_whenPedidoExists_thenCreateNewPedido() {
        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setMontoTotal(BigDecimal.valueOf(100.00));
        pedido.setDireccionEnvio("Test Address");
        Usuario usuario = new Usuario();
        usuario.setNombres("Test User");
        pedido.setUsuario(usuario);
        pedido.setCodigoCupon(new Cupone(1, "TEST10", BigDecimal.valueOf(10.00), "Activo"));
        pedido.setIdCarrito(new Carrito(1, usuario, LocalDate.now(), "Activo", BigDecimal.valueOf(100.00), BigDecimal.valueOf(10.00), BigDecimal.valueOf(90.00)));

        when(pedidoService.findById(1)).thenReturn(pedido);
        when(pedidoService.save(any(Pedido.class))).thenReturn(pedido);

        ResponseEntity<?> response = pedidoController.repetirPedido(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedido, ((Map<String, Object>) response.getBody()).get("data"));
    }

    @Test
    void repetirPedido_whenPedidoDoesNotExist_thenReturnNotFound() {
        when(pedidoService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = pedidoController.repetirPedido(1);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Pedido no encontrado", ((Map<String, Object>) response.getBody()).get("message"));
    }

    @Test
    void pedidosEnProceso_whenPedidosExist_thenReturnPedidos() {
        List<Pedido> pedidos = Collections.singletonList(new Pedido());
        when(pedidoService.findAllByEstadoAndIdUsuario("PENDIENTE",1)).thenReturn(pedidos);

        ResponseEntity<?> response = pedidoController.pedidosEnProceso(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(pedidos, ((Map<String, Object>) response.getBody()).get("pedidos"));
    }

    @Test
    void confirmarRecepcionPedido_whenPedidoExists_thenUpdateEstado() {
        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setEstado("EN PROCESO");
        when(pedidoService.findById(1)).thenReturn(pedido);
        when(pedidoService.save(pedido)).thenReturn(pedido);

        ResponseEntity<?> response = pedidoController.confirmarRecepcionPedido(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ENTREGADO", ((Pedido) ((Map<String, Object>) response.getBody()).get("data")).getEstado());
    }

    @Test
    void confirmarRecepcionPedido_whenPedidoDoesNotExist_thenReturnNotFound() {
        when(pedidoService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = pedidoController.confirmarRecepcionPedido(1);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Pedido no encontrado", ((Map<String, Object>) response.getBody()).get("message"));
    }

    @Test
    void cancelarPedido_whenPedidoExists_thenUpdateEstado() {
        Pedido pedido = new Pedido();
        pedido.setId(1);
        pedido.setEstado("EN PROCESO");
        when(pedidoService.findById(1)).thenReturn(pedido);
        when(pedidoService.save(pedido)).thenReturn(pedido);

        ResponseEntity<?> response = pedidoController.cancelarPedido(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("CANCELADO", ((Pedido) ((Map<String, Object>) response.getBody()).get("data")).getEstado());
    }

    @Test
    void cancelarPedido_whenPedidoDoesNotExist_thenReturnNotFound() {
        when(pedidoService.findById(1)).thenReturn(null);

        ResponseEntity<?> response = pedidoController.cancelarPedido(1);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Pedido no encontrado", ((Map<String, Object>) response.getBody()).get("message"));
    }

    @Test
    void crear_whenUsuarioAndCarritoExist_thenCreatePedido() {
        PedidoDto dto = new PedidoDto();
        dto.setId_usuario("1");
        dto.setId_carrito("6");
        dto.setDireccionEnvio("Test Address");
        dto.setCodigoCupon("TEST10");

        Usuario usuario = new Usuario();
        CarritoResponseDto carrito = new CarritoResponseDto();
        carrito.setStatus("OK");
        carrito.setCarrito(new CarritoResponseDto.Carrito());
        carrito.getCarrito().setTotal(100.00);

        when(usuarioCliente.findById("1")).thenReturn(ResponseEntity.ok(usuario));
        when(carritoCliente.consultarCarrito(6)).thenReturn(ResponseEntity.ok(carrito));
        when(pedidoService.crearPedido(any(Pedido.class), eq(carrito))).thenReturn(new Pedido());

        ResponseEntity<?> response = pedidoController.crear(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void crear_whenUsuarioDoesNotExist_thenReturnNotFound() {
        PedidoDto dto = new PedidoDto();
        dto.setId_usuario("1");

        when(usuarioCliente.findById("1")).thenReturn(ResponseEntity.of(Optional.empty()));

        ResponseEntity<?> response = pedidoController.crear(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}