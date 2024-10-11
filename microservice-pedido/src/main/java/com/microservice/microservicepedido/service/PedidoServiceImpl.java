package com.microservice.microservicepedido.service;

import com.microservice.microservicepedido.model.Pedido;
import com.microservice.microservicepedido.model.Usuario;
import com.microservice.microservicepedido.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements IPedidoService {

    private final PedidoRepository pedidoRepository;

    @Override
    public List<Pedido> findAllByUsuarioId(Usuario usuarioId) {
        return pedidoRepository.findAllByUsuarioId(usuarioId);
    }

    @Override
    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    public Pedido findById(Integer id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    @Override
    public String consultarEstadoPedido(Integer id) {
        return pedidoRepository.findById(id).get().getEstado();
    }
}