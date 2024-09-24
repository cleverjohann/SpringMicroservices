package com.microservice.producto.infraestructure.abstract_services;

import com.microservice.producto.api.models.request.ProductoRequest;
import com.microservice.producto.api.models.responses.ProductoResponse;
import com.microservice.producto.domain.entities.ProductoEntity;

import java.util.List;

public interface IProductoService  {

    // Método para crear un nuevo producto
    ProductoResponse createProduct(ProductoRequest productRequest);

    // Método para obtener un producto por su ID
    ProductoResponse getProductById(Long id);

    // Método para obtener todos los productos
    List<ProductoResponse> getAllProducts();

    // Método para actualizar un producto existente
    ProductoResponse updateProduct(Long id, ProductoRequest productRequest);

    // Método para eliminar un producto por su ID
    void deleteProduct(Long id);

}
