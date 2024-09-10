package com.microservice.microserviceusuarios.service;

import com.microservice.microserviceusuarios.entities.Usuario;

import java.util.List;

public interface IUsuarioService {

    Usuario findByEmail(String email);

    Usuario findById(Integer id);

    Usuario save(Usuario usuario);

    Usuario update(Integer id, Usuario usuario);

    boolean delete(Integer id);

    List<Usuario> findAll();

}