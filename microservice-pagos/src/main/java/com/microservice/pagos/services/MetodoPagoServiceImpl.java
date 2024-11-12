package com.microservice.pagos.services;

import com.microservice.pagos.domain.entities.MetodosPago;
import com.microservice.pagos.domain.repositories.MetodoPagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class MetodoPagoServiceImpl implements MetodoPagoServices{
    private final MetodoPagoRepository metodoPagoRepository;

    @Override
    public MetodosPago crearMetodoPago(MetodosPago metodoPago) {
        return metodoPagoRepository.save(metodoPago);
    }

    @Override
    public MetodosPago actualizarMetodoPago(Integer id, MetodosPago metodoPago) {
        MetodosPago metodoExistente = metodoPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));
        metodoExistente.setNombre(metodoPago.getNombre());
        metodoExistente.setDescripcion(metodoPago.getDescripcion());
        return metodoPagoRepository.save(metodoExistente);
    }

    @Override
    public void eliminarMetodoPago(Integer id) {
        MetodosPago metodoPago = metodoPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));
        metodoPagoRepository.delete(metodoPago);
    }

    @Override
    public MetodosPago obtenerMetodoPagoPorId(Integer id) {
        return metodoPagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado"));
    }

    @Override
    public List<MetodosPago> obtenerTodosLosMetodosPago() {
        return metodoPagoRepository.findAll();
    }
}
