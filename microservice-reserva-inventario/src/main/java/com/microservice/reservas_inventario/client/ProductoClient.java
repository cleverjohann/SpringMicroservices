package com.microservice.reservas_inventario.client;

import com.microservice.reservas_inventario.api.domain.response.ProductoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "producto-categoria-service", url = "http://producto-categoria-service:8040/api/productos")
public interface ProductoClient {

    // Este método se corresponde con el endpoint GET /api/productos/{id} en tu ProductoController
    @GetMapping("/{id}")
    com.microservice.reservas_inventario.api.domain.response.ProductoResponse obtenerProductoPorId(@PathVariable("id") Integer id);

    // Este método se corresponde con el endpoint PUT /api/productos/{id} para actualizar el stock del producto
    @PutMapping("/{id}/actualizar-stock")
    ProductoResponse actualizarStockProducto(@PathVariable("id") Integer id, @RequestParam("stock") int stock);

    // Este método se corresponde con el endpoint GET /api/productos/bajo-stock en tu ProductoController
    @GetMapping("/bajo-stock")
    List<ProductoResponse> obtenerProductosBajoStock(@RequestParam("limite") int limite);
}
