package com.microservice.pagos.services;

import com.microservice.pagos.domain.entities.MetodosPago;

import java.util.List;

public interface MetodoPagoServices {
    MetodosPago crearMetodoPago(MetodosPago metodoPago);
    MetodosPago actualizarMetodoPago(Integer id, MetodosPago metodoPago);
    void eliminarMetodoPago(Integer id);
    MetodosPago obtenerMetodoPagoPorId(Integer id);
    List<MetodosPago> obtenerTodosLosMetodosPago();
}
