package com.microservice.microservicecarrito.service.impl;

import com.microservice.microservicecarrito.model.Carrito;
import com.microservice.microservicecarrito.model.DetalleCarrito;
import com.microservice.microservicecarrito.repository.DetalleCarritoRepository;
import com.microservice.microservicecarrito.service.iservice.ICarritoService;
import com.microservice.microservicecarrito.service.iservice.IDetalleCarritoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DetalleCarritoServiceImpl implements IDetalleCarritoService {

    private final DetalleCarritoRepository detalleCarritoRepository;
    private final ICarritoService carritoService;

    @Override
    public List<DetalleCarrito> obtenerDetalleCarritoPorIdCarrito(Integer id_carrito) {
        return detalleCarritoRepository.findAllByCarrito_Id(id_carrito);
    }

    @Override
    public DetalleCarrito agregarProducto(DetalleCarrito detalleCarrito) {
        //Obtenemos el monto del detalle carrito que se acaba de agregar
        BigDecimal montoDetalle = detalleCarrito.getProducto().getPrecio().multiply(BigDecimal.valueOf(detalleCarrito.getCantidad()));
        //Obtenemos el subtotal actual del carrito
        Carrito carrito = detalleCarrito.getCarrito();
        BigDecimal subTotal = carrito.getSubtotal();
        //Sumamos el monto del detalle al subtotal
        subTotal = subTotal.add(montoDetalle);
        //Actualizamos el subtotal del carrito
        carrito.setSubtotal(subTotal);
        //Guardamos el carrito
        carritoService.save(carrito);
        //Luego de agregar el producto al carrito, se debe de actualizar el subtotal del carrito
        return detalleCarritoRepository.save(detalleCarrito);
    }

    @Override
    public void borrarProducto(DetalleCarrito detalleCarrito) {
        //Obtenemos el monto del detalle carrito que se va a borrar
        BigDecimal montoDetalle = detalleCarrito.getProducto().getPrecio().multiply(BigDecimal.valueOf(detalleCarrito.getCantidad()));
        //obtenemos el carrito
        Carrito carrito = detalleCarrito.getCarrito();
        //Obtenemos el subtotal actual del carrito
        BigDecimal subTotal = carrito.getSubtotal();
        //Restamos el monto del detalle al subtotal
        subTotal = subTotal.subtract(montoDetalle);
        //Actualizamos el subtotal del carrito
        carrito.setSubtotal(subTotal);
        //Guardamos el carrito
        carritoService.save(carrito);
        //Borramos el detalle del carrito
        detalleCarritoRepository.delete(detalleCarrito);
    }

    @Override
    public DetalleCarrito actualizarCantidadProducto(Integer id, Integer cantidad) {
        //Buscamos el detalle del carrito
        DetalleCarrito detalleCarrito = detalleCarritoRepository.findById(id).orElse(null);
        assert detalleCarrito != null;
        detalleCarrito.setCantidad(cantidad);
        return detalleCarritoRepository.save(detalleCarrito);
    }

    @Override
    public DetalleCarrito obtenerDetalleCarritoPorId(Integer id) {
        return detalleCarritoRepository.findById(id).orElse(null);
    }

    @Override
    public void vaciarCarrito(Integer id_carrito) {
        //Buscamos todos los detalles del carrito
        List<DetalleCarrito> detalleCarritoList = detalleCarritoRepository.findAllByCarrito_Id(id_carrito);
        //Borramos todos los detalles del carrito
        detalleCarritoRepository.deleteAll(detalleCarritoList);
    }

}