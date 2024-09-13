package com.microservice.microserviceauth.utils;

import com.microservice.microserviceauth.client.CorreoCliente;
import com.microservice.microserviceauth.model.dto.CorreoClientDto;
import com.microservice.microserviceauth.service.serviceIntermedio.CodigoServiceIntermedio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class CodigoYCorreo {

    private final CorreoCliente correoCliente;
    private final CodigoServiceIntermedio codigoServiceIntermedio;


    public void enviarCodigo(String correo) {
        Random random = new Random();
        String numero = String.valueOf(random.nextInt(900000) + 100000);
        codigoServiceIntermedio.setCodigo(numero);
        correoCliente.enviarCorreo(new CorreoClientDto(correo, "Codigo de verificación", "Su código de verificación es: " + numero));
    }

}
