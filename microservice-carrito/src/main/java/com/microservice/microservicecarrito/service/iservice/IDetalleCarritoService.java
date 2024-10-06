package com.microservice.microservicecarrito.service.iservice;

import com.microservice.microservicecarrito.model.DetalleCarrito;

import java.util.List;

public interface IDetalleCarritoService {

    List<DetalleCarrito> obtenerDetalleCarritoPorIdCarrito(Integer id_carrito);

    DetalleCarrito agregarProducto(DetalleCarrito detalleCarrito);

    void borrarProducto(DetalleCarrito detalleCarrito);

    DetalleCarrito actualizarCantidadProducto(Integer id, Integer cantidad);

    DetalleCarrito obtenerDetalleCarritoPorId(Integer id);

    void vaciarCarrito(Integer id_carrito);

}