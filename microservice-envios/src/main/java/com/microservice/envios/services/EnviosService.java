package com.microservice.envios.services;

import com.microservice.envios.domain.entities.Envios;

import java.util.List;

public interface EnviosService {
    Envios generarEnvio(Envios envio);
    Envios consultarEstadoEnvio(Integer envioId);
    Envios actualizarEstadoEnvio(Integer envioId, String estado);
    void cancelarEnvio(Integer envioId);
    List<Envios> historialEnviosPorUsuario(Integer usuarioId);
}
