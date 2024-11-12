package com.microservice.pagos.api.controller;

import com.microservice.pagos.domain.entities.Pago;
import com.microservice.pagos.services.PagoServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {
    private final PagoServices pagoServices;

    @PostMapping
    public ResponseEntity<Pago> iniciarProcesoPago(@RequestBody Pago pago) {
        Pago nuevoPago = pagoServices.iniciarProcesoPago(pago);
        return ResponseEntity.ok(nuevoPago);
    }

    @GetMapping("/validar/{metodoPagoId}")
    public ResponseEntity<Boolean> validarMetodoPago(@PathVariable Integer metodoPagoId) {
        boolean esValido = pagoServices.validarMetodoPago(metodoPagoId);
        return ResponseEntity.ok(esValido);
    }

    @PostMapping("/confirmar/{pagoId}")
    public ResponseEntity<Pago> confirmarPago(@PathVariable Integer pagoId) {
        Pago pagoConfirmado = pagoServices.confirmarPago(pagoId);
        return ResponseEntity.ok(pagoConfirmado);
    }

    @DeleteMapping("/cancelar/{pagoId}")
    public ResponseEntity<Void> cancelarPago(@PathVariable Integer pagoId) {
        pagoServices.cancelarPago(pagoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{pagoId}")
    public ResponseEntity<Pago> consultarDetallesPago(@PathVariable Integer pagoId) {
        Pago pago = pagoServices.consultarDetallesPago(pagoId);
        return ResponseEntity.ok(pago);
    }

    @GetMapping("/historial/{usuarioId}")
    public ResponseEntity<List<Pago>> historialPagosPorUsuario(@PathVariable Integer usuarioId) {
        List<Pago> historial = pagoServices.historialPagosPorUsuario(usuarioId);
        return ResponseEntity.ok(historial);
    }}
