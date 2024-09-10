package com.microservice.microserviceauth.repository;

import com.microservice.microserviceauth.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    @Query("SELECT u FROM Usuario u WHERE u.email = ?1")
    Optional<Usuario> findByUsername(String username);

    @Query("SELECT u FROM Usuario u WHERE u.email = ?1 AND u.password = ?2")
    Optional<Usuario> findByUsernameAndPassword(String username, String password);
}