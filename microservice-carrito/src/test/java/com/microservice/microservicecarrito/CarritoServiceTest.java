package com.microservice.microservicecarrito;

import com.microservice.microservicecarrito.model.Carrito;
import com.microservice.microservicecarrito.model.Cupone;
import com.microservice.microservicecarrito.repository.CarritoRepository;
import com.microservice.microservicecarrito.service.impl.CarritoServiceImpl;
import com.microservice.microservicecarrito.service.iservice.ICuponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CarritoServiceTest {

    @Mock
    private CarritoRepository carritoRepository;

    @Mock
    private ICuponService cuponService;

    @InjectMocks
    private CarritoServiceImpl carritoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findByID_whenCarritoExists_thenReturnCarrito() {
        Carrito carrito = new Carrito();
        carrito.setId(1);
        when(carritoRepository.findById(1)).thenReturn(Optional.of(carrito));

        Carrito result = carritoService.findByID(1);

        assertEquals(carrito, result);
    }

    @Test
    void findByID_whenCarritoDoesNotExist_thenReturnNull() {
        when(carritoRepository.findById(1)).thenReturn(Optional.empty());

        Carrito result = carritoService.findByID(1);

        assertNull(result);
    }

    @Test
    void save_whenCarritoIsValid_thenReturnSavedCarrito() {
        Carrito carrito = new Carrito();
        when(carritoRepository.save(carrito)).thenReturn(carrito);

        Carrito result = carritoService.save(carrito);

        assertEquals(carrito, result);
    }

    @Test
    void guardarCarrito_whenCarritoExists_thenChangeEstadoAndSave() {
        Carrito carrito = new Carrito();
        carrito.setId(1);
        carrito.setEstado("CREADO");
        when(carritoRepository.findById(1)).thenReturn(Optional.of(carrito));
        when(carritoRepository.save(carrito)).thenReturn(carrito);

        Carrito result = carritoService.guardarCarrito(1);

        assertEquals("GUARDADO", result.getEstado());
        verify(carritoRepository).save(carrito);
    }

    @Test
    void guardarCarrito_whenCarritoDoesNotExist_thenThrowException() {
        when(carritoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(AssertionError.class, () -> carritoService.guardarCarrito(1));
    }

    @Test
    void aplicarCupon_whenCarritoExistsAndCuponIsValid_thenApplyDiscount() {
        Carrito carrito = new Carrito();
        carrito.setId(1);
        carrito.setSubtotal(new BigDecimal("100.00"));
        Cupone cupon = new Cupone();
        cupon.setCodigo("DESC10");
        cupon.setValorDescuento(new BigDecimal("10.00"));
        cupon.setEstado("ACTIVO");

        when(carritoRepository.findById(1)).thenReturn(Optional.of(carrito));
        when(cuponService.findByCodigo("DESC10")).thenReturn(cupon);
        when(carritoRepository.save(carrito)).thenReturn(carrito);

        Carrito result = carritoService.aplicarCupon(1, "DESC10");

        assertEquals(new BigDecimal("90.00"), result.getTotal());
        verify(carritoRepository).save(carrito);
    }

    @Test
    void aplicarCupon_whenCarritoDoesNotExist_thenThrowException() {
        when(carritoRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> carritoService.aplicarCupon(1, "DESC10"));
    }

    @Test
    void aplicarCupon_whenSubtotalIsZeroOrLess_thenThrowException() {
        Carrito carrito = new Carrito();
        carrito.setId(1);
        carrito.setSubtotal(BigDecimal.ZERO);

        when(carritoRepository.findById(1)).thenReturn(Optional.of(carrito));

        assertThrows(IllegalArgumentException.class, () -> carritoService.aplicarCupon(1, "DESC10"));
    }

    @Test
    void aplicarCupon_whenCuponDoesNotExist_thenThrowException() {
        Carrito carrito = new Carrito();
        carrito.setId(1);
        carrito.setSubtotal(new BigDecimal("100.00"));

        when(carritoRepository.findById(1)).thenReturn(Optional.of(carrito));
        when(cuponService.findByCodigo("DESC10")).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> carritoService.aplicarCupon(1, "DESC10"));
    }

    @Test
    void aplicarCupon_whenCuponIsNotActive_thenThrowException() {
        Carrito carrito = new Carrito();
        carrito.setId(1);
        carrito.setSubtotal(new BigDecimal("100.00"));
        Cupone cupon = new Cupone();
        cupon.setCodigo("DESC10");
        cupon.setValorDescuento(new BigDecimal("10.00"));
        cupon.setEstado("INACTIVO");

        when(carritoRepository.findById(1)).thenReturn(Optional.of(carrito));
        when(cuponService.findByCodigo("DESC10")).thenReturn(cupon);

        assertThrows(IllegalArgumentException.class, () -> carritoService.aplicarCupon(1, "DESC10"));
    }

    @Test
    void borrarCarrito_whenCarritoExists_thenDeleteCarrito() {
        Carrito carrito = new Carrito();
        carrito.setId(1);

        carritoService.borrarCarrito(carrito);

        verify(carritoRepository).delete(carrito);
    }
}