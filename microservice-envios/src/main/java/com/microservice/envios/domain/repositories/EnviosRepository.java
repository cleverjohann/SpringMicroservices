package com.microservice.envios.domain.repositories;

import com.microservice.envios.domain.entities.Envios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnviosRepository extends JpaRepository<Envios, Integer> {
    List<Envios> findByUsuarioId(Integer usuarioId);
    List<Envios> findByPedidoId(Integer pedidoId);
}
