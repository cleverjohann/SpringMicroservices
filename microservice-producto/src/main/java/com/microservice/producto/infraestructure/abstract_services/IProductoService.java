package com.microservice.producto.infraestructure.abstract_services;

import com.microservice.producto.api.models.request.ProductoRequest;
import com.microservice.producto.api.models.responses.ProductoResponse;

public interface IProductoService extends CrudService<ProductoRequest, ProductoResponse, Long> {

    ProductoResponse create(ProductoRequest request);

    ProductoResponse update(Long id, ProductoRequest request);

    ProductoResponse delete(Long id);

    ProductoResponse findById(Long id);

    ProductoResponse findAll();

}
