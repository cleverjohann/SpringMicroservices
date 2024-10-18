package com.microservice.microserviceauth;

import com.microservice.microserviceauth.client.CorreoCliente;
import com.microservice.microserviceauth.client.UsuarioCliente;
import com.microservice.microserviceauth.controller.CambiarPasswordController;
import com.microservice.microserviceauth.controller.EnviarCodigoController;
import com.microservice.microserviceauth.controller.LoginController;
import com.microservice.microserviceauth.controller.VerificarCuentaController;
import com.microservice.microserviceauth.model.Usuario;
import com.microservice.microserviceauth.model.dto.*;
import com.microservice.microserviceauth.service.IAuthenticacionService;
import com.microservice.microserviceauth.service.IUsuarioService;
import com.microservice.microserviceauth.service.serviceIntermedio.CodigoServiceIntermedio;
import com.microservice.microserviceauth.utils.CodigoYCorreo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthControllerTest {


    @Mock
    private IUsuarioService usuarioService;

    @Mock
    private CorreoCliente correoCliente;

    @Mock
    private CodigoServiceIntermedio codigoServiceIntermedio;

    @Mock
    private UsuarioCliente usuarioCliente;

    @Mock
    private IAuthenticacionService authenticacionService;

    @InjectMocks
    private VerificarCuentaController verificarCuentaController;

    @InjectMocks
    private com.microservice.microserviceauth.controller.registrarController registrarController;

    @InjectMocks
    private LoginController loginController;

    @Mock
    private CodigoYCorreo codigoYCorreo;

    @InjectMocks
    private EnviarCodigoController enviarCodigoController;

    @InjectMocks
    private CambiarPasswordController cambiarPasswordController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void verificarCuenta_whenCuentaYaVerificada_thenBadRequest() {
        VerificarCuentaDto dto = new VerificarCuentaDto();
        dto.setCorreo("test@example.com");
        dto.setCodigo("123456");

        Usuario usuario = new Usuario();
        usuario.setCuenta_verificada(true);

        when(usuarioService.findByUsername(dto.getCorreo())).thenReturn(Optional.of(usuario));

        ResponseEntity<?> response = verificarCuentaController.verificarCuenta(dto);

        assertEquals(ResponseEntity.badRequest().body("La cuenta ya ha sido verificada"), response);
    }

    @Test
    void verificarCuenta_whenCodigoCorrecto_thenCuentaVerificada() {
        VerificarCuentaDto dto = new VerificarCuentaDto();
        dto.setCorreo("test@example.com");
        dto.setCodigo("123456");

        Usuario usuario = new Usuario();
        usuario.setCuenta_verificada(false);

        when(usuarioService.findByUsername(dto.getCorreo())).thenReturn(Optional.of(usuario));
        when(codigoServiceIntermedio.getCodigo()).thenReturn("123456");

        ResponseEntity<?> response = verificarCuentaController.verificarCuenta(dto);

        assertEquals(ResponseEntity.ok().body("Cuenta verificada"), response);
        verify(usuarioCliente, times(1)).edit(usuario.getId(), usuario);
    }

    @Test
    void verificarCuenta_whenCodigoIncorrecto_thenBadRequest() {
        VerificarCuentaDto dto = new VerificarCuentaDto();
        dto.setCorreo("test@example.com");
        dto.setCodigo("123456");

        Usuario usuario = new Usuario();
        usuario.setCuenta_verificada(false);

        when(usuarioService.findByUsername(dto.getCorreo())).thenReturn(Optional.of(usuario));
        when(codigoServiceIntermedio.getCodigo()).thenReturn("654321");

        ResponseEntity<?> response = verificarCuentaController.verificarCuenta(dto);

        assertEquals(ResponseEntity.badRequest().body("Código incorrecto"), response);
    }


    @Test
    void registrar_whenAllFieldsValid_thenUsuarioCreadoCorrectamente() {
        RegistrarDto dto = new RegistrarDto();
        dto.setNombres("John");
        dto.setApellidop("Doe");
        dto.setApellidom("Smith");
        dto.setEmail("john.doe@example.com");
        dto.setPassword("password");

        when(usuarioService.findByUsername(dto.getEmail())).thenReturn(Optional.empty());

        ResponseEntity<?> response = registrarController.registrar(dto);
        Map<String, Object> responseDto = (Map<String, Object>) response.getBody();

        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("message", "Usuario creado correctamente");
        expectedResponse.put("data", responseDto.get("data"));
        expectedResponse.put("status", HttpStatus.OK);

        assertEquals(new ResponseEntity<>(expectedResponse, HttpStatus.OK), response);
    }

    @Test
    void registrar_whenEmailAlreadyExists_thenBadRequest() {
        RegistrarDto dto = new RegistrarDto();
        dto.setNombres("John");
        dto.setApellidop("Doe");
        dto.setApellidom("Smith");
        dto.setEmail("john.doe@example.com");
        dto.setPassword("password");

        when(usuarioService.findByUsername(dto.getEmail())).thenReturn(Optional.of(new Usuario()));

        ResponseEntity<?> response = registrarController.registrar(dto);

        assertEquals(ResponseEntity.badRequest().body("El correo ya existe"), response);
    }


    @Test
    void registrar_whenExceptionThrown_thenInternalServerError() {
        RegistrarDto dto = new RegistrarDto();
        dto.setNombres("John");
        dto.setApellidop("Doe");
        dto.setApellidom("Smith");
        dto.setEmail("john.doe@example.com");
        dto.setPassword("password");

        when(usuarioService.findByUsername(dto.getEmail())).thenThrow(new RuntimeException("Database error"));

        ResponseEntity<?> response = registrarController.registrar(dto);

        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("message", "Error al crear el usuario: Database error");
        expectedResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR);

        assertEquals(new ResponseEntity<>(expectedResponse, HttpStatus.INTERNAL_SERVER_ERROR), response);
    }


    @Test
    void login_whenCredentialsAreValid_thenUsuarioLogeadoCorrectamente() {
        LoginDto dto = new LoginDto();
        dto.setEmail("john.doe@example.com");
        dto.setPassword("password");

        Usuario usuario = new Usuario();
        usuario.setNombres("John");
        usuario.setApellidop("Doe");
        usuario.setApellidom("Smith");
        usuario.setEmail("john.doe@example.com");
        usuario.setCuenta_verificada(true);

        UsuarioResponseDto usuarioResponseDto = new UsuarioResponseDto();
        usuarioResponseDto.setNombres(usuario.getNombres());
        usuarioResponseDto.setApellidop(usuario.getApellidop());
        usuarioResponseDto.setApellidom(usuario.getApellidom());
        usuarioResponseDto.setEmail(usuario.getEmail());
        usuarioResponseDto.setCuenta_verificada(usuario.isCuenta_verificada());

        when(usuarioService.findByUsernameAndPassword(dto.getEmail(), dto.getPassword())).thenReturn(Optional.of(usuario));
        when(authenticacionService.getToken(usuario)).thenReturn("valid_token");

        ResponseEntity<?> response = loginController.login(dto);

        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("message", "Usuario logeado correctamente");
        expectedResponse.put("token", "valid_token");
        expectedResponse.put("data", usuarioResponseDto);
        expectedResponse.put("status", HttpStatus.OK);

        assertEquals(new ResponseEntity<>(expectedResponse, HttpStatus.OK), response);
    }

    @Test
    void login_whenCredentialsAreInvalid_thenInternalServerError() {
        LoginDto dto = new LoginDto();
        dto.setEmail("john.doe@example.com");
        dto.setPassword("wrong_password");

        when(usuarioService.findByUsernameAndPassword(dto.getEmail(), dto.getPassword())).thenReturn(Optional.empty());

        ResponseEntity<?> response = loginController.login(dto);

        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("message", "Error al logear el usuario: No value present");
        expectedResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR);

        assertEquals(new ResponseEntity<>(expectedResponse, HttpStatus.INTERNAL_SERVER_ERROR), response);
    }

    @Test
    void login_whenEmailIsMissing_thenInternalServerError() {
        LoginDto dto = new LoginDto();
        dto.setPassword("password");

        ResponseEntity<?> response = loginController.login(dto);

        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("message", "Error al logear el usuario: El correo es requerido");
        expectedResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR);

        assertEquals(new ResponseEntity<>(expectedResponse, HttpStatus.INTERNAL_SERVER_ERROR), response);
    }

    @Test
    void login_whenPasswordIsMissing_thenInternalServerError() {
        LoginDto dto = new LoginDto();
        dto.setEmail("john.doe@example.com");

        ResponseEntity<?> response = loginController.login(dto);

        Map<String, Object> expectedResponse = new HashMap<>();
        expectedResponse.put("message", "Error al logear el usuario: La contraseña es requerida");
        expectedResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR);

        assertEquals(new ResponseEntity<>(expectedResponse, HttpStatus.INTERNAL_SERVER_ERROR), response);
    }


    @Test
    void enviarCodigo_whenCorreoValido_thenCodigoEnviado() {
        CorreoDto dto = new CorreoDto();
        dto.setCorreo("test@example.com");

        ResponseEntity<?> response = enviarCodigoController.enviarCodigo(dto);

        assertEquals(ResponseEntity.ok().body("Codigo enviado"), response);
        verify(codigoYCorreo).enviarCodigo(dto.getCorreo());
    }

    @Test
    void cambiarPassword_whenCodigoCorrecto_thenPasswordCambiada() {
        CambiarPasswordDto dto = new CambiarPasswordDto();
        dto.setCorreo("test@example.com");
        dto.setCodigo("123456");
        dto.setPassword("newPassword");

        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setPassword("oldPassword");

        when(codigoServiceIntermedio.getCodigo()).thenReturn("123456");
        when(usuarioCliente.findByUsername(dto.getCorreo())).thenReturn(ResponseEntity.ok(usuario));

        ResponseEntity<?> response = cambiarPasswordController.cambiarPassword(dto);

        assertEquals(ResponseEntity.ok().body("Contraseña cambiada"), response);
        verify(usuarioCliente, times(1)).edit(usuario.getId(), usuario);
    }

    @Test
    void cambiarPassword_whenCodigoIncorrecto_thenBadRequest() {
        CambiarPasswordDto dto = new CambiarPasswordDto();
        dto.setCorreo("test@example.com");
        dto.setCodigo("wrongCode");
        dto.setPassword("newPassword");

        when(codigoServiceIntermedio.getCodigo()).thenReturn("123456");

        ResponseEntity<?> response = cambiarPasswordController.cambiarPassword(dto);

        assertEquals(ResponseEntity.badRequest().body("Código incorrecto"), response);
        verify(usuarioCliente, never()).edit(anyInt(), any(Usuario.class));
    }


}
