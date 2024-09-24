package com.microservice.microserviceauth.controller;

import com.microservice.microserviceauth.model.Usuario;
import com.microservice.microserviceauth.model.dto.LoginDto;
import com.microservice.microserviceauth.model.dto.UsuarioResponseDto;
import com.microservice.microserviceauth.service.IAuthenticacionService;
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
import java.util.Optional;

import static com.microservice.microserviceauth.utils.EntidadNoNulaException.verificarEntidadNoNula;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {

    private final IUsuarioService usuarioService;
    private final IAuthenticacionService authenticacionService;

    @PostMapping(value = "login")
    public ResponseEntity<?> login(@RequestBody LoginDto dto) {
        Map<String, Object> response = new HashMap<>();
        try {
            //Comprobamos parametros
            verificarEntidadNoNula(dto.getEmail(), "El correo es requerido");
            verificarEntidadNoNula(dto.getPassword(), "La contraseña es requerida");

            //Verificamos que el usuario exista y que la contraseña sea correcta
            Optional<Usuario> usuario = usuarioService.findByUsernameAndPassword(dto.getEmail(), dto.getPassword());
            //Generamos el jwt
            String token = authenticacionService.getToken(usuario.get());

            //Creamos el response de Usuario
            UsuarioResponseDto usuarioResponseDto = new UsuarioResponseDto();
            usuarioResponseDto.setNombres(usuario.get().getNombres());
            usuarioResponseDto.setApellidop(usuario.get().getApellidop());
            usuarioResponseDto.setApellidom(usuario.get().getApellidom());
            usuarioResponseDto.setEmail(usuario.get().getEmail());
            usuarioResponseDto.setCuenta_verificada(usuario.get().isCuenta_verificada());

            response.put("message", "Usuario logeado correctamente");
            response.put("token", token);
            response.put("data", usuarioResponseDto);
            response.put("status", HttpStatus.OK);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("message", "Error al logear el usuario: " + e.getMessage());
            response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
