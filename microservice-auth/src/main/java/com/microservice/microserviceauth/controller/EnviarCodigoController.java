package com.microservice.microserviceauth.controller;

import com.microservice.microserviceauth.model.dto.CorreoDto;
import com.microservice.microserviceauth.utils.CodigoYCorreo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class EnviarCodigoController {

    private final CodigoYCorreo codigoYCorreo;

    @PostMapping("/enviar-codigo")
    public ResponseEntity<?> enviarCodigo(@RequestBody CorreoDto dto) {
        Map<String, Object> response = new HashMap<>();
        codigoYCorreo.enviarCodigo(dto.getCorreo());
        response.put("message", "Código enviado correctamente");
        response.put("status", HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
