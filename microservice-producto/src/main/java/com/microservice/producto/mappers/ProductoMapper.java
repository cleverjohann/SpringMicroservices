package com.microservice.producto.mappers;

import com.microservice.producto.api.models.request.ProductoRequest;
import com.microservice.producto.api.models.responses.ProductoResponse;
import com.microservice.producto.domain.entities.ProductoEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class ProductoMapper  {
    public ProductoEntity requestToEntity(ProductoRequest request) {
        ProductoEntity entity = new ProductoEntity();
        entity.setNombre(request.getNombre());
        entity.setPrecio(request.getPrecio());
        entity.setStock(request.getStock());
        return entity;
    }
    public ProductoResponse entityToResponse(ProductoEntity entity) {
        ProductoResponse response = new ProductoResponse();
        response.setId(entity.getId());
        response.setNombre(entity.getNombre());
        response.setPrecio(entity.getPrecio());
        response.setStock(entity.getStock());
        return response;
    }
    public List<ProductoResponse> entityToResponseList(List<ProductoEntity> entities) {
        List<ProductoResponse> responses = new ArrayList<>();
        for (ProductoEntity entity : entities) {
            responses.add(entityToResponse(entity));
        }
        return responses;
    }
}
