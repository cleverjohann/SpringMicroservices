package com.microservice.microserviceauth.config.filter;

import com.microservice.microserviceauth.exception.NoTokenJwtException;
import com.microservice.microserviceauth.exception.TokenExpiradoException;
import com.microservice.microserviceauth.service.IAuthenticacionService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final IAuthenticacionService authenticationService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // Se obtiene el token del encabezado de la peticion
            final String token = getTokenFromRequest(request);
            final String username;

            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }
            // Se obtiene el nombre de clienteAcceso del token
            username = authenticationService.getUsernameByToken(token);

            // Si el nombre de clienteAcceso es diferente de nulo y no hay una autenticacion en el contexto
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Se obtiene el clienteAcceso
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                // Si el token es valido se autentica al clienteAcceso

                if (authenticationService.isTokenValid(token, userDetails)) {
                    // Se crea un token de autenticación
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    // Se autentica al clienteAcceso
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }

            }
            // Se continua con la peticion
            filterChain.doFilter(request, response);
        } catch (NullPointerException e) {
            throw new NoTokenJwtException("No se ha encontrado el token JWT");
        } catch (ExpiredJwtException e) {
            throw new TokenExpiradoException("El token ha expirado, por favor inicie sesión nuevamente");
        }
    }

    // Metodo para obtener el token del encabezado de la peticion
    private String getTokenFromRequest(HttpServletRequest request) {
        // Se obtiene el token del encabezado de la peticion
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        // Si el encabezado no es nulo y comienza con "Bearer " se devuelve el token
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}