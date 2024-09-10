package com.microservice.producto.client;

import com.microservice.categoria.api.modeles.responses.CategoriaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-categoria",url = "localhost:8042")
public interface CategoriaClient {
    @GetMapping("/categoria/{id}")
    public CategoriaResponse findById(@PathVariable Long id);
}
