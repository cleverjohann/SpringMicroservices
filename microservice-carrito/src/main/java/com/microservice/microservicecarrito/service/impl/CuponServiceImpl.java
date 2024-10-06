package com.microservice.microservicecarrito.service.impl;

import com.microservice.microservicecarrito.model.Cupone;
import com.microservice.microservicecarrito.repository.CuponRepository;
import com.microservice.microservicecarrito.service.iservice.ICuponService;
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
