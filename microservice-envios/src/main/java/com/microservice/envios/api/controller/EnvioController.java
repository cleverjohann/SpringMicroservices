package com.microservice.envios.api.controller;

import com.microservice.envios.domain.entities.Envios;
import com.microservice.envios.services.EnviosService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/envios")
@RequiredArgsConstructor
public class EnvioController {

    private final EnviosService enviosService;

    @PostMapping
    public ResponseEntity<Envios> generarEnvio(@RequestBody Envios envio) {
        Envios nuevoEnvio = enviosService.generarEnvio(envio);
        return ResponseEntity.ok(nuevoEnvio);
    }

    @GetMapping("/estado/{envioId}")
    public ResponseEntity<Envios> consultarEstadoEnvio(@PathVariable Integer envioId) {
        Envios envio = enviosService.consultarEstadoEnvio(envioId);
        return ResponseEntity.ok(envio);
    }

    @PutMapping("/actualizar/{envioId}")
    public ResponseEntity<Envios> actualizarEstadoEnvio(@PathVariable Integer envioId, @RequestParam String estado) {
        Envios envioActualizado = enviosService.actualizarEstadoEnvio(envioId, estado);
        return ResponseEntity.ok(envioActualizado);
    }

    @DeleteMapping("/cancelar/{envioId}")
    public ResponseEntity<Void> cancelarEnvio(@PathVariable Integer envioId) {
        enviosService.cancelarEnvio(envioId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/historial/{usuarioId}")
    public ResponseEntity<List<Envios>> historialEnviosPorUsuario(@PathVariable Integer usuarioId) {
        List<Envios> historialEnvios = enviosService.historialEnviosPorUsuario(usuarioId);
        return ResponseEntity.ok(historialEnvios);
    }
}