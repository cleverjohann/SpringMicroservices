package com.microservice.microservicepedido.repository;

import com.microservice.microservicepedido.model.MetodosPago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MetodoPagoRepository extends JpaRepository<MetodosPago, Integer> {
}