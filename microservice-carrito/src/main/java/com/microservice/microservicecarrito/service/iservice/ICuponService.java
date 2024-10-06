package com.microservice.microservicecarrito.service.iservice;

import com.microservice.microservicecarrito.model.Cupone;

public interface ICuponService {

    Cupone findByCodigo(String codigo);

}
