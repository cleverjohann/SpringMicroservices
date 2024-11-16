package com.microservice.microservicepedido.service;

import com.microservice.microservicepedido.model.Pago;
import com.microservice.microservicepedido.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements IPagoService {

    private final PagoRepository pagoRepository;

    @Override
    public Pago save(Pago pago) {
        return pagoRepository.save(pago);
    }
}
