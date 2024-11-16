package com.microservice.envios.services;

import com.microservice.envios.client.CorreoCliente;
import com.microservice.envios.client.UsuarioCliente;
import com.microservice.envios.client.dto.CorreoClientDto;
import com.microservice.envios.domain.entities.Envios;
import com.microservice.envios.domain.entities.Usuario;
import com.microservice.envios.domain.repositories.EnviosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnvioServiceImpl implements EnviosService {

    private final EnviosRepository enviosRepository;
    private final CorreoCliente correoCliente;
    private final UsuarioCliente usuarioCliente;

    @Override
    public Envios generarEnvio(Envios envio) {
        envio.setEstado("Pendiente");
        envio.setFechaEnvio(LocalDate.now());
        obtenerUsuarioYEnviarCorreo(envio, "Envio pendiente", "Su envio está pendiente de ser enviado");
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
            obtenerUsuarioYEnviarCorreo(envio, "Estado de envío actualizado", "El estado de su envío se ha actualizado a: " + estado);
            return enviosRepository.save(envio);
        }
        return null;
    }

    @Override
    public void cancelarEnvio(Integer envioId) {
        obtenerUsuarioYEnviarCorreo(enviosRepository.findById(envioId).get(), "Envío cancelado", "Su envío ha sido cancelado, para más información contacte con el soporte");
        enviosRepository.deleteById(envioId);
    }

    @Override
    public List<Envios> historialEnviosPorUsuario(Integer usuarioId) {
        return enviosRepository.findByUsuarioId(usuarioId);
    }


    private void obtenerUsuarioYEnviarCorreo(Envios envio, String asunto, String mensaje) {
        //Buscamos el usuario por UsuarioCliente
        Usuario usuario = usuarioCliente.findByUsername(String.valueOf(envio.getUsuarioId())).getBody();
        //Enviamos el correo de notificación
        correoCliente.enviarCorreo(new CorreoClientDto(usuario.getEmail(), asunto, mensaje));
    }

}
