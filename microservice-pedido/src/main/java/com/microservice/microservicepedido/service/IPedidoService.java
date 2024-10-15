package com.microservice.microservicepedido.service;

import com.microservice.microservicepedido.model.Pedido;
import com.microservice.microservicepedido.model.Usuario;
import com.microservice.microservicepedido.model.dto.CarritoResponseDto;


import java.util.List;

public interface IPedidoService {

    List<Pedido> findAllByUsuarioId(Usuario usuarioId);

    Pedido crearPedido(Pedido pedido, CarritoResponseDto detalle_carrito);

    Pedido save(Pedido pedido);

    Pedido findById(Integer id);

    String consultarEstadoPedido(Integer id);

}