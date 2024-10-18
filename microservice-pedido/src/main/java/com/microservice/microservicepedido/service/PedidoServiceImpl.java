package com.microservice.microservicepedido.service;

import com.microservice.microservicepedido.client.InventarioCliente;
import com.microservice.microservicepedido.client.response.ReservaRequest;
import com.microservice.microservicepedido.model.Pedido;
import com.microservice.microservicepedido.model.ReservaInventario;
import com.microservice.microservicepedido.model.Usuario;
import com.microservice.microservicepedido.model.dto.CarritoResponseDto;
import com.microservice.microservicepedido.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log
public class PedidoServiceImpl implements IPedidoService {

    private final PedidoRepository pedidoRepository;
    private final InventarioCliente inventarioCliente;

    @Override
    public List<Pedido> findAllByUsuarioId(Usuario usuarioId) {
        return pedidoRepository.findAllByUsuarioId(usuarioId);
    }

    @Override
    public Pedido crearPedido(Pedido pedido, CarritoResponseDto detalle_carrito) {
        //Creamos la lista de ReservaInventario
        List<ReservaInventario> reservas = new ArrayList<>();

        //Guardamos temporalmente el pedido
        Pedido nuevoPedido = pedidoRepository.save(pedido);
        //Reservamos stock
        //Intentar reservar inventario para cada producto en el carrito
        boolean reservaExitosa = true;
        for (CarritoResponseDto.DetalleCarrito detalle : detalle_carrito.getDetalle_carrito()) {
            try {
                //Creamos reserva request
                ReservaRequest reservaRequest = new ReservaRequest();
                reservaRequest.setProductoId(detalle.getProducto().getId());
                reservaRequest.setCantidad(detalle.getCantidad());
                reservaRequest.setPedidoId(nuevoPedido.getId());
                log.info("Reservando inventario para producto: " + detalle.getProducto().getId());
                ResponseEntity<ReservaInventario> reservaInventarioResponseEntity = inventarioCliente.reservarInventario(reservaRequest);

                log.info("Reserva exitosa para producto: " + detalle.getProducto().getId());
                reservas.add(reservaInventarioResponseEntity.getBody());
            } catch (Exception e) {
                reservaExitosa = false;
                break;
            }
        }

        // 4. Si la reserva fue exitosa, confirmar el pedido
        if (reservaExitosa) {
            nuevoPedido.setEstado("Confirmado");
            pedidoRepository.save(nuevoPedido);
        } else {
            // Liberamos las reservas
            assert false;
            for (ReservaInventario reserva : reservas) {
                inventarioCliente.liberarInventario(Long.valueOf(reserva.getId()));
            }
            nuevoPedido.setEstado("Cancelado");
            pedidoRepository.save(nuevoPedido);
            throw new RuntimeException("No se pudo reservar el inventario para algunos productos.");
        }
        return nuevoPedido;
    }

    @Override
    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    public void delete(Pedido pedido) {
        pedidoRepository.delete(pedido);
    }

    @Override public Pedido findById(Integer id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    @Override
    public String consultarEstadoPedido(Integer id) {
        return pedidoRepository.findById(id).get().getEstado();
    }

    @Override
    public List<Pedido> findAllByEstadoAndIdUsuario(String enProceso, Integer idUsuario) {
        return pedidoRepository.findAllByEstadoAndIdUsuario(enProceso,idUsuario);
    }
}