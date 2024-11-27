package com.microservice.microserviceauth.controller;

import com.microservice.microserviceauth.client.UsuarioCliente;
import com.microservice.microserviceauth.model.Usuario;
import com.microservice.microserviceauth.model.dto.VerificarCuentaDto;
import com.microservice.microserviceauth.service.IUsuarioService;
import com.microservice.microserviceauth.service.serviceIntermedio.CodigoServiceIntermedio;
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
public class VerificarCuentaController {

    private final IUsuarioService usuarioService;
    private final CodigoServiceIntermedio codigoServiceIntermedio;
    private final UsuarioCliente usuarioCliente;

    //Primero se envia el correo con el codigo de verificación con enviarcodigocontroller
    @PostMapping("/verificar-cuenta")
    public ResponseEntity<?> verificarCuenta(@RequestBody VerificarCuentaDto dto) {
        Map<String, Object> response = new HashMap<>();
        //Buscar usuario por correo
        Usuario usuario = usuarioService.findByUsername(dto.getCorreo()).get();

        if (usuario.isCuenta_verificada()) {
            response.put("message", "La cuenta ya ha sido verificada");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Verificar código de verificación
        if (codigoServiceIntermedio.getCodigo().equalsIgnoreCase(dto.getCodigo())) {
            //Buscamos el usuario por email

            usuario.setCuenta_verificada(true);

            usuarioCliente.edit(usuario.getId(), usuario);

            response.put("message", "Cuenta verificada correctamente");
            response.put("data", usuario);
            response.put("status", HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("message", "Código incorrecto");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}