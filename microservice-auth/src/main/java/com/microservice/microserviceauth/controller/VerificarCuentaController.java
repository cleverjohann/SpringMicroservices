package com.microservice.microserviceauth.controller;

import com.microservice.microserviceauth.client.UsuarioCliente;
import com.microservice.microserviceauth.model.Usuario;
import com.microservice.microserviceauth.model.dto.VerificarCuentaDto;
import com.microservice.microserviceauth.service.IUsuarioService;
import com.microservice.microserviceauth.service.serviceIntermedio.CodigoServiceIntermedio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class VerificarCuentaController {

    private final IUsuarioService usuarioService;
    private final CodigoServiceIntermedio codigoServiceIntermedio;
    private UsuarioCliente usuarioCliente;

    //Primero se envia el correo con el codigo de verificación con enviarcodigocontroller
    @PostMapping("/verificar-cuenta")
    public ResponseEntity<?> verificarCuenta(@RequestBody VerificarCuentaDto dto) {
        //Buscar usuario por correo
        Optional<Usuario> usuario = usuarioService.findByUsername(dto.getCorreo());
        // Verificar código de verificación
        if (codigoServiceIntermedio.getCodigo().equalsIgnoreCase(dto.getCodigo())) {
            //Buscamos el usuario por email
            Usuario usuarioEncontrado = usuarioCliente.findByUsername(dto.getCorreo()).getBody();
            assert usuarioEncontrado != null;
            usuarioEncontrado.setCuenta_verificada(true);

            usuarioCliente.edit(usuarioEncontrado.getId(), usuarioEncontrado);

            return ResponseEntity.ok().body("Cuenta verificada");
        }
        return ResponseEntity.badRequest().body("Código incorrecto");
    }

}