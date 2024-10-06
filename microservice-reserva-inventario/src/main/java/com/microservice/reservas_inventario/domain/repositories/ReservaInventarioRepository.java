package com.microservice.reservas_inventario.domain.repositories;

import com.microservice.reservas_inventario.domain.entities.ReservaInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaInventarioRepository extends JpaRepository<ReservaInventario, Long> {
}
