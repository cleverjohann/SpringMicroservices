package com.microservice.pagos.services;


import com.microservice.pagos.api.models.HistorialPagosDto;
import com.microservice.pagos.domain.entities.Pago;

import java.util.List;

public interface PagoServices {
    Pago iniciarProcesoPago(Pago pago);

    boolean validarMetodoPago(Integer metodoPagoId);

    Pago confirmarPago(Integer pagoId);

    void cancelarPago(Integer pagoId);

    Pago consultarDetallesPago(Integer pagoId);

    List<HistorialPagosDto> findHistorialPagosByUsuarioId(Integer usuarioId);

}
