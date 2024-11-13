package com.microservice.pagos.client;

import com.microservice.pagos.api.models.PedidoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "pedido-service", url = "http://pedido-service:9034/api/pedido")

public interface PedidoClient {

    @GetMapping("/{id}")
    ResponseEntity<PedidoResponse> findById(@PathVariable("id") Integer id);
}
