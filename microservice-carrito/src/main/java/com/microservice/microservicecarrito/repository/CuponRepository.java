package com.microservice.microservicecarrito.repository;

import com.microservice.microservicecarrito.model.Cupone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CuponRepository extends JpaRepository<Cupone, Integer> {

    @Query("SELECT c FROM Cupone c WHERE c.codigo = ?1")
    Cupone findByCodigo(String codigo);


}