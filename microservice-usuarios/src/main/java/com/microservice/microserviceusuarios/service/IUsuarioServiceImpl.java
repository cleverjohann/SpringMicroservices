package com.microservice.microserviceusuarios.service;

import com.microservice.microserviceusuarios.entities.Usuario;
import com.microservice.microserviceusuarios.entities.dto.UsuarioUpdateDto;
import com.microservice.microserviceusuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IUsuarioServiceImpl implements IUsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public Usuario findById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario update(Integer id, UsuarioUpdateDto usuario) {
        // Buscamos el usuario por id y si no lo encuentra lanza una excepción
        Usuario usuarioEncontrado = usuarioRepository.findById(id).get();
        // Cambiamos los datos del usuario encontrado por los datos del usuario que nos
        // pasan
        usuarioEncontrado.setNombres(usuario.getNombres());
        usuarioEncontrado.setApellidop(usuario.getApellidop());
        usuarioEncontrado.setApellidom(usuario.getApellidom());
        usuarioEncontrado.setCuenta_verificada(usuario.getCuenta_verificada());
        usuarioEncontrado.setEmail(usuario.getEmail());
        usuarioEncontrado.setPassword(usuario.getPassword());
        usuarioEncontrado.setActivo(usuario.getActivo());
        // Actualizamos el usuario
        return usuarioRepository.save(usuarioEncontrado);
    }

    @Override
    public boolean delete(Integer id) {
        // Buscamos el usuario por id y cambiamos su estado a false
        Usuario usuarioEncontrado = usuarioRepository.findById(id).orElse(null);
        if (usuarioEncontrado != null) {
            usuarioEncontrado.setActivo(false);
            usuarioRepository.save(usuarioEncontrado);
            return true;
        }
        return false;
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAllActiveUsers();
    }
}
