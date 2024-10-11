package com.microservice.microservicepedido.repository;

import com.microservice.microservicepedido.model.Cupone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CuponRepository extends JpaRepository<Cupone, Integer> {

    Cupone findByCodigo(String codigo);

}