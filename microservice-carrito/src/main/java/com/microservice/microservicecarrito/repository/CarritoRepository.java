package com.microservice.microservicecarrito.repository;

import com.microservice.microservicecarrito.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<Carrito, Integer> {
}