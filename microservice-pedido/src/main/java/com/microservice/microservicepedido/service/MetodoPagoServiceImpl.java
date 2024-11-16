package com.microservice.microservicepedido.service;

import com.microservice.microservicepedido.model.MetodosPago;
import com.microservice.microservicepedido.repository.MetodoPagoRepository;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetodoPagoServiceImpl implements IMetodoPagoService {

    private final MetodoPagoRepository metodoPagoRepository;

    @Override
    public MetodosPago save(MetodosPago metodosPago) {
        return metodoPagoRepository.save(metodosPago);
    }
}
