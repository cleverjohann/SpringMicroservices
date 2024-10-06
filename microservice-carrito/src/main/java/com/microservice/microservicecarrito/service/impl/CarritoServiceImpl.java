package com.microservice.microservicecarrito.service.impl;

import com.microservice.microservicecarrito.model.Carrito;
import com.microservice.microservicecarrito.model.Cupone;
import com.microservice.microservicecarrito.repository.CarritoRepository;
import com.microservice.microservicecarrito.service.iservice.ICarritoService;
import com.microservice.microservicecarrito.service.iservice.ICuponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CarritoServiceImpl implements ICarritoService {

    private final CarritoRepository carritoRepository;
    private final ICuponService cuponService;

    @Override
    public Carrito findByID(Integer id) {
        return carritoRepository.findById(id).orElse(null);
    }

    //Creamos carrito
    @Override
    public Carrito save(Carrito carrito) {
        return carritoRepository.save(carrito);
    }

    //Cambiamos el estado del carrito
    @Override
    public Carrito guardarCarrito(Integer id_Carrito) {
        //Buscamos el carrito
        Carrito carrito = carritoRepository.findById(id_Carrito).orElse(null);
        //Cambiamos el estado del carrito
        assert carrito != null;
        carrito.setEstado("GUARDADO");
        //Guardamos el carrito
        return carritoRepository.save(carrito);
    }

    @Override
    public Carrito aplicarCupon(Integer id_carrito, String codigoDescuento) {
        // Buscamos el carrito
        Carrito carrito = carritoRepository.findById(id_carrito).orElse(null);
        if (carrito == null) {
            throw new IllegalArgumentException("Carrito no encontrado");
        }

        // Verificamos que el subtotal del carrito sea mayor que cero
        BigDecimal subTotal = carrito.getSubtotal();
        if (subTotal.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El subtotal del carrito debe ser mayor que cero");
        }

        // Buscamos el cupon
        Cupone descuento = cuponService.findByCodigo(codigoDescuento);
        if (descuento == null) {
            throw new IllegalArgumentException("Cupón no encontrado");
        }

        // Verificamos que el cupon esté activo
        if (!descuento.getEstado().equalsIgnoreCase("ACTIVO")) {
            throw new IllegalArgumentException("Cupón no está activo");
        }

        // Hacemos el cálculo del descuento
        BigDecimal subTotalConDescuento = subTotal.subtract(descuento.getValorDescuento());

        carrito.setSubtotal(subTotal);
        carrito.setDescuento(descuento.getValorDescuento());
        carrito.setTotal(subTotalConDescuento);

        // Guardamos el carrito
        return carritoRepository.save(carrito);
    }

    @Override
    public void borrarCarrito(Carrito carrito) {
        carritoRepository.delete(carrito);
    }
}
