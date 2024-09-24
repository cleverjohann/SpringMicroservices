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

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class VerificarCuentaController {

    private final IUsuarioService usuarioService;
    private final CodigoServiceIntermedio codigoServiceIntermedio;
    private final UsuarioCliente usuarioCliente;

    //Primero se envia el correo con el codigo de verificación con enviarcodigocontroller
    @PostMapping("/verificar-cuenta")
    public ResponseEntity<?> verificarCuenta(@RequestBody VerificarCuentaDto dto) {
        //Buscar usuario por correo
        Usuario usuario = usuarioService.findByUsername(dto.getCorreo()).get();

        if (usuario.isCuenta_verificada()) {
            return ResponseEntity.badRequest().body("La cuenta ya ha sido verificada");
        }

        // Verificar código de verificación
        if (codigoServiceIntermedio.getCodigo().equalsIgnoreCase(dto.getCodigo())) {
            //Buscamos el usuario por email

            usuario.setCuenta_verificada(true);

            usuarioCliente.edit(usuario.getId(), usuario);

            return ResponseEntity.ok().body("Cuenta verificada");
        }
        return ResponseEntity.badRequest().body("Código incorrecto");
    }

}