package com.microservice.producto.domain.repositories;

import com.microservice.producto.domain.entities.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {
    ProductoEntity findByNombre(String nombre);
    List<ProductoEntity> findByCategoriaId(Integer categoriaId);
}
