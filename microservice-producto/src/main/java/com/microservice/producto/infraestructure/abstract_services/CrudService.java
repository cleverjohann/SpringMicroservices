package com.microservice.producto.infraestructure.abstract_services;

public interface CrudService <RQ, RS, ID> {
    RS create(RQ request);
    RS update(ID id, RQ request);
    RS delete(ID id);
    RS findById(ID id);
    RS findAll();
}
