package com.microservice.microservicepedido.repository;

import com.microservice.microservicepedido.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Integer> {
}