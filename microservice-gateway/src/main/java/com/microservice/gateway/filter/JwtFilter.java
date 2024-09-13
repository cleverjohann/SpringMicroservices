package com.microservice.gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.java.Log;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;

@Component
@Log
public class JwtFilter extends AbstractGatewayFilterFactory<Object> {

    private final SecretKey key;

    public JwtFilter(@Value("${app.jwtSecret}") String secret) {
        super(Object.class); // Se puede usar Object ya que no hay configuración personalizada
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            var request = exchange.getRequest();

            // Verificamos si el header Authorization está presente y empieza con "Bearer "
            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION) ||
                    !request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION).startsWith("Bearer ")) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token JWT no presente en la cabecera de la petición");
            }

            // Obtenemos el token JWT del header Authorization
            String token = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION).substring(7);

            // Validamos el token JWT
            try {
                Claims claims = Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

                // Obtenemos el correo del usuario
                String userCorreo = claims.getSubject();
                log.info("Usuario autenticado: " + userCorreo);
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token JWT inválido o expirado");
            }

            return chain.filter(exchange);
        };
    }
}

