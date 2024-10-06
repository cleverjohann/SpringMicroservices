package com.microservice.microservicecarrito.repository;

import com.microservice.microservicecarrito.model.DetalleCarrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface DetalleCarritoRepository extends JpaRepository<DetalleCarrito, Integer> {

    @Query("SELECT dc FROM DetalleCarrito dc WHERE dc.carrito.id = ?1")
    List<DetalleCarrito> findAllByCarrito_Id(Integer idCarrito);


}