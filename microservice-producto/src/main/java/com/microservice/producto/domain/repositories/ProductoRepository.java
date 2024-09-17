package com.microservice.producto.domain.repositories;

import com.microservice.producto.domain.entities.ProductoEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {

//    @EntityGraph(attributePaths = "categoria")
//    List<ProductoEntity> findAll();
//
//    @EntityGraph(attributePaths = "categoria")
//    Optional<ProductoEntity> findById(Integer id);
}
