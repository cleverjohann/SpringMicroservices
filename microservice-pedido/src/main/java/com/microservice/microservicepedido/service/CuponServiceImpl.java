package com.microservice.microservicepedido.service;

import com.microservice.microservicepedido.model.Cupone;
import com.microservice.microservicepedido.repository.CuponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CuponServiceImpl implements ICuponService {

    private final CuponRepository cuponRepository;

    @Override
    public Cupone findByCodigo(String codigo) {
        return cuponRepository.findByCodigo(codigo);
    }
}
