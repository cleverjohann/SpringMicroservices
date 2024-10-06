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
        //Obtenemos el monto del detalle carrito que se va a agregar
        BigDecimal montoDetalle = detalleCarrito.getProducto().getPrecio().multiply(BigDecimal.valueOf(detalleCarrito.getCantidad()));
        //obtenemos el carrito
        Carrito carrito = detalleCarrito.getCarrito();
        //Obtenemos el subtotal actual del carrito
        actualizarSubtotal(carrito, montoDetalle, true);  // True porque estamos sumando al subtotal
        return detalleCarritoRepository.save(detalleCarrito);
    }

    @Override
    public void borrarProducto(DetalleCarrito detalleCarrito) {
        //Obtenemos el monto del detalle carrito que se va a borrar
        BigDecimal montoDetalle = detalleCarrito.getProducto().getPrecio().multiply(BigDecimal.valueOf(detalleCarrito.getCantidad()));
        //obtenemos el carrito
        Carrito carrito = detalleCarrito.getCarrito();
        //Obtenemos el subtotal actual del carrito
        actualizarSubtotal(carrito, montoDetalle, false);  // False porque estamos restando del subtotal
        detalleCarritoRepository.delete(detalleCarrito);
    }

    @Override
    public DetalleCarrito actualizarCantidadProducto(Integer id, Integer cantidad) {
        // Obtenemos el detalle del carrito
        DetalleCarrito detalleCarrito = detalleCarritoRepository.findById(id).orElse(null);
        assert detalleCarrito != null;
        // Obtenemos el monto anterior y el nuevo
        BigDecimal montoDetalleAnterior = detalleCarrito.getProducto().getPrecio().multiply(BigDecimal.valueOf(detalleCarrito.getCantidad()));
        detalleCarrito.setCantidad(cantidad);
        // Obtenemos el monto nuevo
        BigDecimal montoDetalleNuevo = detalleCarrito.getProducto().getPrecio().multiply(BigDecimal.valueOf(detalleCarrito.getCantidad()));
        // Obtenemos el carrito
        Carrito carrito = detalleCarrito.getCarrito();
        // Restamos el monto anterior y sumamos el nuevo
        actualizarSubtotal(carrito, montoDetalleAnterior, false);  // Restamos el anterior
        actualizarSubtotal(carrito, montoDetalleNuevo, true);  // Sumamos el nuevo

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
        //Obtenemos el carrito y ponemos todo en 0
        Carrito carrito = detalleCarritoList.get(1).getCarrito();
        carrito.setTotal(BigDecimal.ZERO);
        carrito.setSubtotal(BigDecimal.ZERO);
        carrito.setDescuento(BigDecimal.ZERO);
        //Guardamos el carrito
        carritoService.save(carrito);
        //Borramos todos los detalles del carrito
        detalleCarritoRepository.deleteAll(detalleCarritoList);
    }

    private void actualizarSubtotal(Carrito carrito, BigDecimal monto, boolean sumar) {
        BigDecimal subTotal = carrito.getSubtotal();
        if (sumar) {
            subTotal = subTotal.add(monto);
        } else {
            subTotal = subTotal.subtract(monto);
        }
        carrito.setSubtotal(subTotal);
        carritoService.save(carrito);
    }

}