package com.microservice.categoria.infraestructure.abstract_service;

public interface CrudService <RQ, RS, ID> {
    RS create(RQ request);
    RS update(ID id, RQ request);
    RS delete(ID id);
    RS findById(ID id);
    RS findAll();
}
