package com.microservice.categoria.infraestructure.abstract_service;

import com.microservice.categoria.api.modeles.request.CategoriaRequest;
import com.microservice.categoria.api.modeles.responses.CategoriaResponse;
import com.microservice.categoria.domain.entities.CategoriaEntity;

import java.util.List;

public interface ICategoriaService {

    CategoriaEntity save(CategoriaEntity categoria);
    CategoriaEntity update(Long id, CategoriaEntity categoria);
    boolean delete(Long id);
    CategoriaEntity findById(Long id);
    List<CategoriaEntity> findAll();
}
