package com.microservice.producto.infraestructure.abstract_services;

import com.microservice.producto.api.models.request.ProductoRequest;
import com.microservice.producto.api.models.responses.ProductoResponse;
import com.microservice.producto.domain.entities.ProductoEntity;

import java.util.List;

public interface IProductoService  {

    ProductoEntity save(ProductoEntity producto);
    ProductoEntity update(Integer id, ProductoEntity producto);
    boolean delete(Integer id);
    ProductoEntity findById(Integer id);
    List<ProductoEntity> findAll();

}
