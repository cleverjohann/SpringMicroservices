package com.microservice.microserviceauth.controller;

import com.microservice.microserviceauth.model.Usuario;
import com.microservice.microserviceauth.model.dto.RegistrarDto;
import com.microservice.microserviceauth.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;

import static com.microservice.microserviceauth.utils.EntidadNoNulaException.verificarEntidadNoNula;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class registrarController {

    private final IUsuarioService usuarioService;

    @PostMapping(value = "register")
    public ResponseEntity<?> registrar(@RequestBody RegistrarDto dto) {
        Map<String, Object> response = new HashMap<>();
        try {
            //Comprobamos parametros
            verificarEntidadNoNula(dto.getNombres(), "El nombre es requerido");
            verificarEntidadNoNula(dto.getApellidop(), "El apellido paterno es requerido");
            verificarEntidadNoNula(dto.getApellidom(), "El apellido materno es requerido");
            verificarEntidadNoNula(dto.getEmail(), "El correo es requerido");
            verificarEntidadNoNula(dto.getPassword(), "La contraseña es requerida");

            //Verificamos que el correo no exista
            if (usuarioService.findByUsername(dto.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("El correo ya existe");
            }

            //Creamos el usuario
            Usuario usuario = Usuario.builder()
                    .nombres(dto.getNombres())
                    .apellidop(dto.getApellidop())
                    .apellidom(dto.getApellidom())
                    .email(dto.getEmail())
                    .password(dto.getPassword())
                    .roles("ROLE_USER")
                    .build();

            //Guardamos el usuario
            usuarioService.save(usuario);

            response.put("message", "Usuario creado correctamente");
            response.put("data", usuario);
            response.put("status", HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al crear el usuario: " + e.getMessage());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}