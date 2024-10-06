package com.microservice.producto_categoria.domain.repositories;

import com.microservice.producto_categoria.domain.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByStockLessThanEqual(int stock);
}
