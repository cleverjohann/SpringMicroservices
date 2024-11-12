package com.microservice.pagos.domain.repositories;

import com.microservice.pagos.domain.entities.MetodosPago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetodoPagoRepository extends JpaRepository<MetodosPago, Integer> {
}
