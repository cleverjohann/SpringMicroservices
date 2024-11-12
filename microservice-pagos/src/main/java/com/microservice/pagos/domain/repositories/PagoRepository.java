package com.microservice.pagos.domain.repositories;

import com.microservice.pagos.domain.entities.Pago;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    @Query("SELECT p FROM Pago p JOIN p.pedido pe WHERE pe.usuario.id = :usuarioId")
    List<Pago> findByUsuarioId(@Param("usuarioId") Integer usuarioId);

}
