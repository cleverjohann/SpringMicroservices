package com.microservice.pagos.domain.repositories;

import com.microservice.pagos.api.models.HistorialPagosDto;
import com.microservice.pagos.api.models.PedidoResponse;
import com.microservice.pagos.domain.entities.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    List<Pago> findByPedidoId(Integer pedidoId);

}
