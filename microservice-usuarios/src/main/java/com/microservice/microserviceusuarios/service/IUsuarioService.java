package com.microservice.microserviceusuarios.service;

import com.microservice.microserviceusuarios.entities.Usuario;
import com.microservice.microserviceusuarios.entities.dto.UsuarioUpdateDto;

import java.util.List;

public interface IUsuarioService {

    Usuario findByEmail(String email);

    Usuario findById(Integer id);

    Usuario save(Usuario usuario);

    Usuario update(Integer id, UsuarioUpdateDto usuario);

    boolean delete(Integer id);

    List<Usuario> findAll();

}