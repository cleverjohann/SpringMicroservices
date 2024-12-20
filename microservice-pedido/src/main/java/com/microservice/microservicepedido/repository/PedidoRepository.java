package com.microservice.microservicepedido.repository;

import com.microservice.microservicepedido.model.Pedido;
import com.microservice.microservicepedido.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    @Query("SELECT p FROM Pedido p WHERE p.usuario= :usuario")
    List<Pedido> findAllByUsuarioId(Usuario usuario);

    @Query("SELECT p FROM Pedido p WHERE p.estado= :enProceso")
    List<Pedido> findAllByEstado(String enProceso);

    @Query("SELECT p FROM Pedido p WHERE p.estado= :enProceso AND p.usuario.id= :idUsuario")
    List<Pedido> findAllByEstadoAndIdUsuario(String enProceso, Integer idUsuario);
}