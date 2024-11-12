package com.microservice.pagos.services;

import com.microservice.pagos.domain.entities.Pago;
import com.microservice.pagos.domain.repositories.MetodoPagoRepository;
import com.microservice.pagos.domain.repositories.PagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements PagoServices {
    private final PagoRepository pagoRepository;
    private final MetodoPagoRepository metodoPagoRepository;

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
    public List<Pago> historialPagosPorUsuario(Integer usuarioId) {
        return pagoRepository.findAllByUsuarioId(usuarioId);
    }
}
