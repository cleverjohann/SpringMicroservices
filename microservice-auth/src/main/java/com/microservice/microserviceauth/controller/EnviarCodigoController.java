package com.microservice.microserviceauth.controller;

import com.microservice.microserviceauth.model.dto.CorreoDto;
import com.microservice.microserviceauth.utils.CodigoYCorreo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")

public class EnviarCodigoController {

    @PostMapping("/enviar-codigo")
    public ResponseEntity<?> enviarCodigo(@RequestBody CorreoDto dto) {
        CodigoYCorreo.enviarCodigo(dto.getCorreo());
        return ResponseEntity.ok().body("Codigo enviado");
    }
}