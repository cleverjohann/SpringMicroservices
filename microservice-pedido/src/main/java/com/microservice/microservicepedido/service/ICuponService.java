package com.microservice.microservicepedido.service;

import com.microservice.microservicepedido.model.Cupone;

public interface ICuponService {

    Cupone findByCodigo(String codigo);

}