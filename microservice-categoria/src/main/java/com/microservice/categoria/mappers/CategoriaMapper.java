package com.microservice.categoria.mappers;

import com.microservice.categoria.api.modeles.request.CategoriaRequest;
import com.microservice.categoria.api.modeles.responses.CategoriaResponse;
import com.microservice.categoria.domain.entities.CategoriaEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    CategoriaEntity requestToEntity(CategoriaRequest request);
    CategoriaResponse entityToResponse(CategoriaEntity entity);
}
