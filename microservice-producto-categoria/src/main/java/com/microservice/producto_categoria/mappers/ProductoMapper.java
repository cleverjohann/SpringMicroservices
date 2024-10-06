package com.microservice.producto_categoria.mappers;

import com.microservice.producto_categoria.api.domain.request.ProductoRequest;
import com.microservice.producto_categoria.api.domain.responses.CategoriaResponse;
import com.microservice.producto_categoria.api.domain.responses.ProductoResponse;
import com.microservice.producto_categoria.domain.entities.Categoria;
import com.microservice.producto_categoria.domain.entities.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {
    public Producto toEntity(ProductoRequest request, Categoria categoria) {
        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setCategoria(categoria);
        return producto;
    }

    public ProductoResponse toResponse(Producto producto) {
        ProductoResponse response = new ProductoResponse();
        response.setId(producto.getId());
        response.setNombre(producto.getNombre());
        response.setDescripcion(producto.getDescripcion());
        response.setPrecio(producto.getPrecio());
        response.setStock(producto.getStock());
        response.setAgotado(producto.getStock() == 0);

        CategoriaResponse categoriaResponse = new CategoriaResponse();
        categoriaResponse.setId(producto.getCategoria().getId());
        categoriaResponse.setNombre(producto.getCategoria().getNombre());
        categoriaResponse.setDescripcion(producto.getCategoria().getDescripcion());

        response.setCategoria(categoriaResponse);
        return response;
    }
}
