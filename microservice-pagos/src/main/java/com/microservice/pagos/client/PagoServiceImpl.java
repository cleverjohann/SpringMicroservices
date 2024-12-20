package com.microservice.pagos.services;

import com.microservice.pagos.api.models.HistorialPagosDto;
import com.microservice.pagos.api.models.PedidoResponse;
import com.microservice.pagos.api.models.UsuarioResponse;
import com.microservice.pagos.client.PedidoClient;
import com.microservice.pagos.client.UsuarioClient;
import com.microservice.pagos.domain.entities.Pago;
import com.microservice.pagos.domain.repositories.MetodoPagoRepository;
import com.microservice.pagos.domain.repositories.PagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements PagoServices {
    private final PagoRepository pagoRepository;
    private final MetodoPagoRepository metodoPagoRepository;
    private final UsuarioClient usuarioClient;
    private final PedidoClient pedidoClient;

    @Override
    public Pago iniciarProcesoPago(Pago pago) {
        return pagoRepository.save(pago);
    }

    @Override
    public boolean validarMetodoPago(Integer metodoPagoId) {
        return metodoPagoRepository.existsById(metodoPagoId);
    }

    @Override
    public Pago confirmarPago(Integer pagoId) {
        Pago pago = pagoRepository.findById(pagoId)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        pago.setEstado("pagado");
        return pagoRepository.save(pago);
    }

    @Override
    public void cancelarPago(Integer pagoId) {
        Pago pago = pagoRepository.findById(pagoId)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        pagoRepository.delete(pago);
    }

    @Override
    public Pago consultarDetallesPago(Integer pagoId) {
        return pagoRepository.findById(pagoId)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
    }

    @Override
    public List<HistorialPagosDto> findHistorialPagosByUsuarioId(Integer usuarioId) {
        List<PedidoResponse> pedidos = pedidoClient.listarPorId(usuarioId).getBody();
        return pedidos.stream().flatMap(pedido -> {
            List<Pago> pagos = pagoRepository.findByPedidoId(pedido.getId());
            return pagos.stream().map(pago -> new HistorialPagosDto(
                    pago.getId(),
                    pago.getPedidoId(),
                    pago.getMetodoPagoId(),
                    pago.getMonto(),
                    pago.getFechaPago(),
                    pago.getEstado(),
                    pedido.getUsuarioId(),
                    pedido.getFechaPedido(),
                    pedido.getMontoTotal(),
                    pedido.getEstado(),
                    pedido.getDireccionEnvio()
            ));
        }).collect(Collectors.toList());
    }
}
