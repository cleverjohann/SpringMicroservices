package com.microservice.producto_categoria.infraestructure.abstract_services;

import com.microservice.producto_categoria.api.domain.request.CategoriaRequest;
import com.microservice.producto_categoria.api.domain.responses.CategoriaResponse;
import com.microservice.producto_categoria.domain.entities.Categoria;

public interface ICategoriaService extends CrudService<CategoriaRequest, CategoriaResponse, Long> {
}
