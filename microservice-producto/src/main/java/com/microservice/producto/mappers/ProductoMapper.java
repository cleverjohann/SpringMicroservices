package com.microservice.producto.mappers;

import com.microservice.producto.api.models.request.ProductoRequest;
import com.microservice.producto.api.models.responses.ProductoResponse;
import com.microservice.producto.domain.entities.ProductoEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Mapper(componentModel = "spring")
public interface ProductoMapper  {

    ProductoEntity requestToEntity(ProductoRequest request);

    ProductoResponse entityToResponse(ProductoEntity entity);
}
