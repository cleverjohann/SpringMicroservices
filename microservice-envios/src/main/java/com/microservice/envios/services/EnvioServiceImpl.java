package com.microservice.envios.services;

import com.microservice.envios.domain.entities.Envios;
import com.microservice.envios.domain.repositories.EnviosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnvioServiceImpl implements EnviosService{

    private final EnviosRepository enviosRepository;

    @Override
    public Envios generarEnvio(Envios envio) {
        envio.setEstado("Pendiente");
        envio.setFechaEnvio(LocalDate.now());
        return enviosRepository.save(envio);
    }

    @Override
    public Envios consultarEstadoEnvio(Integer envioId) {
        return enviosRepository.findById(envioId).orElse(null);
    }

    @Override
    public Envios actualizarEstadoEnvio(Integer envioId, String estado) {
        Optional<Envios> envioOpt = enviosRepository.findById(envioId);
        if (envioOpt.isPresent()) {
            Envios envio = envioOpt.get();
            envio.setEstado(estado);
            return enviosRepository.save(envio);
        }
        return null;
    }

    @Override
    public void cancelarEnvio(Integer envioId) {
        enviosRepository.deleteById(envioId);
    }

    @Override
    public List<Envios> historialEnviosPorUsuario(Integer usuarioId) {
        return enviosRepository.findByUsuarioId(usuarioId);
    }
}
