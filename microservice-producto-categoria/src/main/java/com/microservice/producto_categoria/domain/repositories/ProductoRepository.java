package com.microservice.producto_categoria.domain.repositories;

import com.microservice.producto_categoria.domain.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}