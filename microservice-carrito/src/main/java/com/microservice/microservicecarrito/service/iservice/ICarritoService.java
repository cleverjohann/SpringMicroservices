package com.microservice.microservicecarrito.service.iservice;

import com.microservice.microservicecarrito.model.Carrito;

public interface ICarritoService {

    Carrito findByID(Integer id);

    Carrito save(Carrito carrito);

    //Aca se cambia el estado del carrito
    Carrito guardarCarrito(Integer id_Carrito);

    Carrito aplicarCupon(Integer id_Carrito, String cuponDescuento);

    void borrarCarrito(Carrito carrito);

}