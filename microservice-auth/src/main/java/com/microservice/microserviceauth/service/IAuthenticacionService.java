package com.microservice.microserviceauth.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

public interface IAuthenticacionService {

    String authenticate(HashMap<String, Object> extraClaims, UserDetails user);

    String getToken(UserDetails user);

    String getUsernameByToken(String token);

    <T> T getClaims(String token, Function<Claims, T> claimsResolver);

    Claims getAllClaims(String token);

    Date getExpirationDate(String token);

    Boolean isTokenValid(String token, UserDetails user);

    Boolean isTokenExpired(String token);


}