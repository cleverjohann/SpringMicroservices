package com.microservice.microserviceauth.controller;

import com.microservice.microserviceauth.client.UsuarioCliente;
import com.microservice.microserviceauth.model.Usuario;
import com.microservice.microserviceauth.model.dto.CambiarPasswordDto;
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
public class CambiarPasswordController {

    private final CodigoServiceIntermedio codigoServiceIntermedio;
    private final UsuarioCliente usuarioCliente;

    @PostMapping("/cambiar-password")
    public ResponseEntity<?> cambiarPassword(@RequestBody CambiarPasswordDto dto) {
        Map<String, Object> response = new HashMap<>();
        //Verificamos que el codigo de verificación sea correcto
        if (codigoServiceIntermedio.getCodigo().equalsIgnoreCase(dto.getCodigo())) {
            //Cambiar la contraseña del usuario

            //Buscamos el usuario por email
            Usuario usuario = usuarioCliente.findByUsername(dto.getCorreo()).getBody();
            assert usuario != null;

            //Cambiamos la contraseña
            usuario.setPassword(dto.getPassword());

            //Actualizamos el usuario
            usuarioCliente.edit(usuario.getId(), usuario);

            response.put("message", "Contraseña cambiada correctamente");
            response.put("data", usuario);
            response.put("status", HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("message", "Código incorrecto");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
