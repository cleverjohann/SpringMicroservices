package com.microservice.microserviceauth;

import com.microservice.microserviceauth.service.AuthenticationServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AuthenticationServiceImplTest {

    private AuthenticationServiceImpl authenticationService;

    @Mock
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authenticationService = new AuthenticationServiceImpl("c40ba52c3818c400106e19b56837c238f532e12a0542f75e75f067a03c6d86f1ac27f671cda14c7dbc8fc3eda01959f796c5544a9d556e46459f1ef306bcde25a0d3b904bf721512c85573432f9ca79a119fe24cf926be9aea229ee5ad4731366426a6f49acf2444a1e2486708eeafec3b1caafe4d270806d18a0a69f2ae39cc63006852fcd480b0431d8728c9f0cf90dfe32e99059feb21f8a8b023f7e339f5cd011b3c173fa7750c8cb72e3b5e048a00a0ed6270c60b9be0ab845c2c802ba94e275d9b79d78ccccbab477a5d93f4816d4c930a8ebbe49c36670e1b2d637667337be9cc35b4f84b18f19f6bf8a916a97d8c6094c4bdbf672e5291043c852dec"); // Use the updated constructor
    }

    @Test
    void getToken_returnsToken() {
        when(userDetails.getUsername()).thenReturn("testUser");
        String token = authenticationService.getToken(userDetails);
        assertNotNull(token);
    }

    @Test
    void authenticate_returnsToken() {
        when(userDetails.getUsername()).thenReturn("testUser");
        String token = authenticationService.authenticate(new HashMap<>(), userDetails);
        assertNotNull(token);
    }

    @Test
    void getUsernameByToken_returnsUsername() {
        when(userDetails.getUsername()).thenReturn("testUser");
        String token = authenticationService.getToken(userDetails);
        String username = authenticationService.getUsernameByToken(token);
        assertEquals("testUser", username);
    }

    @Test
    void getClaims_returnsClaims() {
        when(userDetails.getUsername()).thenReturn("testUser");
        String token = authenticationService.getToken(userDetails);
        Claims claims = authenticationService.getAllClaims(token);
        assertNotNull(claims);
    }

    @Test
    void getExpirationDate_returnsExpirationDate() {
        when(userDetails.getUsername()).thenReturn("testUser");
        String token = authenticationService.getToken(userDetails);
        Date expirationDate = authenticationService.getExpirationDate(token);
        assertNotNull(expirationDate);
    }

    @Test
    void isTokenValid_returnsTrueForValidToken() {
        when(userDetails.getUsername()).thenReturn("testUser");
        String token = authenticationService.getToken(userDetails);
        assertTrue(authenticationService.isTokenValid(token, userDetails));
    }

    @Test
    void isTokenExpired_returnsFalseForValidToken() {
        when(userDetails.getUsername()).thenReturn("testUser");
        String token = authenticationService.getToken(userDetails);
        assertFalse(authenticationService.isTokenExpired(token));
    }

    @Test
    void isTokenExpired_returnsTrueForExpiredToken() {
        when(userDetails.getUsername()).thenReturn("testUser");
        String token = Jwts.builder()
                .setSubject("testUser")
                .setExpiration(new Date(System.currentTimeMillis() - 1000))
                .signWith(authenticationService.getKey())
                .compact();
        assertTrue(authenticationService.isTokenExpired(token));
    }
}