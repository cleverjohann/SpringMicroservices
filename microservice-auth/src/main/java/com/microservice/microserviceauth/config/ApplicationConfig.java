package com.microservice.microserviceauth.config;

import com.microservice.microserviceauth.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final IUsuarioService usuarioService;

    //Creamos el Bean para la autenticacion
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        //Creamos el proveedor de authenticacion
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        //Se le asigna el servicio de clienteAcceso
        authenticationProvider.setUserDetailsService(userDetailsService());
        //Se le asigna el encriptador de password
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    //Bean para encriptar las password
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //Metodo para obtener el servicio de clienteAcceso
    @Bean
    public UserDetailsService userDetailsService() {
        //Se retorna el servicio de clienteAcceso
        return username -> usuarioService.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("El usuario no existe."));
    }

}