package com.microservice.producto_categoria.infraestructure.abstract_services;

import com.microservice.producto_categoria.api.domain.request.ProductoRequest;
import com.microservice.producto_categoria.api.domain.responses.ProductoResponse;
import com.microservice.producto_categoria.domain.entities.Producto;

public interface IProductoService extends CrudService<ProductoRequest, ProductoResponse, Long> {
}
