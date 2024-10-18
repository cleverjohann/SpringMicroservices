package com.microservice.microservicecarrito;

import com.microservice.microservicecarrito.client.ProductoCliente;
import com.microservice.microservicecarrito.client.UsuarioCliente;
import com.microservice.microservicecarrito.client.response.ProductoResponse;
import com.microservice.microservicecarrito.controller.CarritoController;
import com.microservice.microservicecarrito.model.Carrito;
import com.microservice.microservicecarrito.model.DetalleCarrito;
import com.microservice.microservicecarrito.model.Usuario;
import com.microservice.microservicecarrito.model.dto.*;
import com.microservice.microservicecarrito.service.iservice.ICarritoService;
import com.microservice.microservicecarrito.service.iservice.IDetalleCarritoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class CarritoControllerTest {

    @Mock
    private ICarritoService carritoService;

    @Mock
    private IDetalleCarritoService detalleCarritoService;

    @Mock
    private ProductoCliente productoCliente;

    @Mock
    private UsuarioCliente usuarioCliente;

    @InjectMocks
    private CarritoController carritoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void consultarCarrito_whenCarritoExists_thenReturnCarritoDetails() {
        Carrito carrito = new Carrito();
        carrito.setId(1);
        Usuario usuario = new Usuario();
        usuario.setNombres("Test User");
        carrito.setUsuario(usuario);
        when(carritoService.findByID(1)).thenReturn(carrito);
        when(detalleCarritoService.obtenerDetalleCarritoPorIdCarrito(1)).thenReturn(Collections.emptyList());

        ResponseEntity<?> response = carritoController.consultarCarrito(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Test User", ((CarritoResponseDto) ((Map<String, Object>) response.getBody()).get("carrito")).getUsuario());
    }

    @Test
    void consultarCarrito_whenCarritoDoesNotExist_thenReturnNotFound() {
        when(carritoService.findByID(1)).thenReturn(null);

        ResponseEntity<?> response = carritoController.consultarCarrito(1);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Carrito no encontrado", ((Map<String, Object>) response.getBody()).get("mensaje"));
    }

    @Test
    void crearCarrito_whenUsuarioExists_thenCreateCarrito() {
        Usuario usuario = new Usuario();
        usuario.setNombres("Test User");
        when(usuarioCliente.findById(1)).thenReturn(ResponseEntity.ok(usuario));

        UsuarioIdDto dto = new UsuarioIdDto();
        dto.setUsuario_id("1");

        ResponseEntity<?> response = carritoController.crearCarrito(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Carrito creado", ((Map<String, Object>) response.getBody()).get("mensaje"));
    }

    @Test
    void crearCarrito_whenUsuarioDoesNotExist_thenReturnNotFound() {
        when(usuarioCliente.findById(1)).thenReturn(ResponseEntity.of(Optional.empty()));

        UsuarioIdDto dto = new UsuarioIdDto();
        dto.setUsuario_id("1");

        ResponseEntity<?> response = carritoController.crearCarrito(dto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuario no encontrado", ((Map<String, Object>) response.getBody()).get("mensaje"));
    }

    @Test
    void agregarProducto_whenCarritoAndProductoExist_thenAddProducto() {
        Carrito carrito = new Carrito();
        carrito.setId(1);
        ProductoResponse productoResponse = new ProductoResponse();
        productoResponse.setId(1L);
        productoResponse.setNombre("Test Product");
        productoResponse.setPrecio(100.00);
        productoResponse.setStock(10);

        when(carritoService.findByID(1)).thenReturn(carrito);
        when(productoCliente.obtenerProducto(1L)).thenReturn(ResponseEntity.ok(productoResponse));

        DetalleCarritoRequestDto dto = new DetalleCarritoRequestDto();
        dto.setId_carrito("1");
        dto.setId_producto("1");
        dto.setCantidad("1");

        ResponseEntity<?> response = carritoController.agregarProducto(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Producto agregado al carrito", ((Map<String, Object>) response.getBody()).get("mensaje"));
    }


    @Test
    void agregarProducto_whenStockIsInsufficient_thenReturnBadRequest() {
        Carrito carrito = new Carrito();
        carrito.setId(1);
        ProductoResponse productoResponse = new ProductoResponse();
        productoResponse.setId(1L);
        productoResponse.setNombre("Test Product");
        productoResponse.setPrecio(100.00);
        productoResponse.setStock(0);

        when(carritoService.findByID(1)).thenReturn(carrito);
        when(productoCliente.obtenerProducto(1L)).thenReturn(ResponseEntity.ok(productoResponse));

        DetalleCarritoRequestDto dto = new DetalleCarritoRequestDto();
        dto.setId_carrito("1");
        dto.setId_producto("1");
        dto.setCantidad("1");

        ResponseEntity<?> response = carritoController.agregarProducto(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No hay stock suficiente", ((Map<String, Object>) response.getBody()).get("mensaje"));
    }

    @Test
    void actualizarCantidad_whenDetalleCarritoExists_thenUpdateCantidad() {
        DetalleCarrito detalleCarrito = new DetalleCarrito();
        detalleCarrito.setId(1);
        detalleCarrito.setCantidad(5);

        when(detalleCarritoService.actualizarCantidadProducto(1, 5)).thenReturn(detalleCarrito);

        CambiarCantidadProductoDto dto = new CambiarCantidadProductoDto();
        dto.setCantidad("5");

        ResponseEntity<?> response = carritoController.actualizarCantidad(1, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Cantidad actualizada", ((Map<String, Object>) response.getBody()).get("mensaje"));
    }

    @Test
    void quitarProducto_whenDetalleCarritoExists_thenRemoveProducto() {
        DetalleCarrito detalleCarrito = new DetalleCarrito();
        detalleCarrito.setId(1);

        when(detalleCarritoService.obtenerDetalleCarritoPorId(1)).thenReturn(detalleCarrito);

        DetalleCarritoRequestIdDto dto = new DetalleCarritoRequestIdDto();
        dto.setId_detalle_carrito("1");

        ResponseEntity<?> response = carritoController.quitarProducto(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Producto eliminado del carrito", ((Map<String, Object>) response.getBody()).get("mensaje"));
    }

    @Test
    void aplicarDescuento_whenCuponIsValid_thenApplyDiscount() {
        Carrito carrito = new Carrito();
        carrito.setId(1);
        carrito.setSubtotal(new BigDecimal("100.00"));
        carrito.setTotal(new BigDecimal("90.00"));

        when(carritoService.aplicarCupon(1, "DESC10")).thenReturn(carrito);

        CuponResponseDto dto = new CuponResponseDto();
        dto.setCodigo("DESC10");

        ResponseEntity<?> response = carritoController.aplicarDescuento(1, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Descuento aplicado", ((Map<String, Object>) response.getBody()).get("mensaje"));
    }

    @Test
    void aplicarDescuento_whenCuponIsInvalid_thenReturnNotFound() {
        when(carritoService.aplicarCupon(1, "DESC10")).thenReturn(null);

        CuponResponseDto dto = new CuponResponseDto();
        dto.setCodigo("DESC10");

        ResponseEntity<?> response = carritoController.aplicarDescuento(1, dto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Cupon no encontrado", ((Map<String, Object>) response.getBody()).get("mensaje"));
    }

    @Test
    void vaciarCarrito_whenCarritoExists_thenEmptyCarrito() {
        ResponseEntity<?> response = carritoController.vaciarCarrito(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Carrito vaciado", ((Map<String, Object>) response.getBody()).get("mensaje"));
    }

    @Test
    void borrarCarrito_whenCarritoExists_thenDeleteCarrito() {
        Carrito carrito = new Carrito();
        carrito.setId(1);

        when(carritoService.findByID(1)).thenReturn(carrito);

        ResponseEntity<?> response = carritoController.borrarCarrito(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Carrito eliminado", ((Map<String, Object>) response.getBody()).get("mensaje"));
    }

    @Test
    void guardarCarritoParaDepues_whenCarritoExists_thenSaveCarrito() {
        Carrito carrito = new Carrito();
        carrito.setId(1);

        when(carritoService.guardarCarrito(1)).thenReturn(carrito);

        ResponseEntity<?> response = carritoController.guardarCarritoParaDepues(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Carrito guardado", ((Map<String, Object>) response.getBody()).get("mensaje"));
    }
}