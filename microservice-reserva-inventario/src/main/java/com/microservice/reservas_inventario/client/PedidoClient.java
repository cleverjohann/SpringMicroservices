package com.microservice.reservas_inventario.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "microservice-pedido", url = "http://microservice-pedido:8080/api/pedido")
public interface PedidoClient {
    //Este metodo se corresponde con el endpoint GET /api/pedido/{id} en tu PedidoController
    @GetMapping("/{id}")
    com.microservice.reservas_inventario.api.domain.response.PedidoResponse obtenerPedidoPorId(@PathVariable("id") Integer id);

}
