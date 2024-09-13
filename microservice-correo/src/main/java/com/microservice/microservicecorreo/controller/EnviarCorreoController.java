package com.microservice.microservicecorreo.controller;

import com.microservice.microservicecorreo.dto.CorreoDto;
import com.microservice.microservicecorreo.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/enviar-correo")
@RequiredArgsConstructor
public class EnviarCorreoController {

    private final EmailService emailService;


    @PostMapping(value="/enviar")
    public void enviarCorreo(@RequestBody CorreoDto dto) {
        emailService.sendSimpleEmail(dto.getDestino(), dto.getAsunto(), dto.getTexto());
    }

}
