package com.microservice.categoria.mappers;

import com.microservice.categoria.api.modeles.request.CategoriaRequest;
import com.microservice.categoria.api.modeles.responses.CategoriaResponse;
import com.microservice.categoria.domain.entities.CategoriaEntity;

import java.util.List;


public interface CategoriaMapper {

    CategoriaEntity requestToEntity(CategoriaRequest request);
    CategoriaResponse entityToResponse(CategoriaEntity entity);
}
