package com.microservice.microservicepedido.service;

import com.microservice.microservicepedido.model.Pedido;
import com.microservice.microservicepedido.model.Usuario;


import java.util.List;

public interface IPedidoService {

    List<Pedido> findAllByUsuarioId(Usuario usuarioId);

    Pedido save(Pedido pedido);

    Pedido findById(Integer id);

    String consultarEstadoPedido(Integer id);

}