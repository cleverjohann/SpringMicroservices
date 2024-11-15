package com.microservice.pagos.client;

import com.microservice.pagos.api.models.PedidoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "pedido-service", url = "http://pedido-service:9034/api/pedido")

public interface PedidoClient {

    @GetMapping("/{id}")
    ResponseEntity<PedidoResponse> findById(@PathVariable("id") Integer id);

    // Listar todos los pedidos por usuario
    @GetMapping("/listar/id")
    ResponseEntity<List<PedidoResponse>> listarPorId(@RequestParam("id") Integer id);

}
