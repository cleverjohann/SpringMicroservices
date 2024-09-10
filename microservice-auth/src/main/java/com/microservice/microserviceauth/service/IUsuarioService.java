package com.microservice.microserviceauth.service;

import com.microservice.microserviceauth.model.Usuario;

import java.util.Optional;

public interface IUsuarioService {

    Optional<Usuario> findByUsernameAndPassword(String username, String password);

    Optional<Usuario> findByUsername(String username);

    Usuario findById(Integer id);

    Usuario save(Usuario usuario);

}
