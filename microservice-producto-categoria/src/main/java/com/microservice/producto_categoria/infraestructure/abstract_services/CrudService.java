package com.microservice.producto_categoria.infraestructure.abstract_services;

import java.util.List;

public interface CrudService <RQ,RS, ID> {
    RS create(RQ request);
    RS update(ID id, RQ request);
    RS findById(ID id);
    void delete(ID id);

    RS read(ID id);
    // Añadir este método para listar todas las entidades
    List<RS> findAll();
}
